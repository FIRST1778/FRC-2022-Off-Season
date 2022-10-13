package org.frc1778.robot.subsystems.shooter

import com.revrobotics.CANSparkMaxLowLevel
import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.math.controller.ProfiledPIDController
import edu.wpi.first.math.trajectory.TrapezoidProfile
import edu.wpi.first.networktables.NetworkTable
import edu.wpi.first.networktables.NetworkTableEntry
import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets
import org.frc1778.robot.Constants
import org.frc1778.robot.UtilMath
import org.frc1778.robot.commands.shooter.ShooterAnglePIDTuner
import org.frc1778.robot.commands.shooter.ShooterFlywheelPIDTuning
import org.ghrobotics.lib.commands.FalconSubsystem
import org.ghrobotics.lib.mathematics.units.Meter
import org.ghrobotics.lib.mathematics.units.SIUnit
import org.ghrobotics.lib.mathematics.units.derived.*
import org.ghrobotics.lib.mathematics.units.nativeunit.DefaultNativeUnitModel
import org.ghrobotics.lib.motors.ctre.falconFX
import org.ghrobotics.lib.motors.rev.FalconMAX
import org.ghrobotics.lib.motors.rev.FalconMAXEncoder
import org.ghrobotics.lib.motors.rev.falconMAX
import org.ghrobotics.lib.wrappers.networktables.get

object Shooter : FalconSubsystem() {
    private val limeTable: NetworkTable = NetworkTableInstance.getDefault().getTable("limelight")
    private val ty = limeTable["ty"]

    val velocityTab: NetworkTableEntry = Constants.debugTab2
        .add("Angle", 0.0)
        .withPosition(0, 0)
        .withSize(3, 3)
        .withWidget(BuiltInWidgets.kTextView)
        .entry

    val kFTab: NetworkTableEntry = Constants.debugTab2
        .add("kF", 0.0)
        .withPosition(0, 0)
        .withSize(3, 3)
        .withWidget(BuiltInWidgets.kTextView)
        .entry

    val kPTab: NetworkTableEntry = Constants.debugTab2
        .add("kP", 0.0)
        .withPosition(0, 0)
        .withSize(3, 3)
        .withWidget(BuiltInWidgets.kTextView)
        .entry

    val kITab: NetworkTableEntry = Constants.debugTab2
        .add("kI", 0.0)
        .withPosition(0, 0)
        .withSize(3, 3)
        .withWidget(BuiltInWidgets.kTextView)
        .entry

    val kDTab: NetworkTableEntry = Constants.debugTab2
        .add("kD", 0.0)
        .withPosition(0, 0)
        .withSize(3, 3)
        .withWidget(BuiltInWidgets.kTextView)
        .entry

    val currVelocity = Constants.debugTab2
        .add("Current Angle", 0.0)
        .withPosition(5, 5)
        .withSize(3, 3)
        .withWidget(BuiltInWidgets.kGraph)
        .entry

    private val velocityMultiplier = Constants.debugTab2
        .add("Velocity Multiplier", 0.0)
        .withPosition(0, 0)
        .withSize(5, 5)
        .withWidget(BuiltInWidgets.kTextView)
        .entry


    var turretAngle: Double
        get() = angleEncoder.position.inDegrees()
        set(v) {}
//        set(v) = angleAdjuster.setPosition(v.degrees)

    var shooterAngle: Double
        //        get() = angleAdjuster.encoder.rawPosition.value
        get() = angleEncoder.position.value
        //        get() = angleAdjuster.canSparkMax.getAlternateEncoder(4096).position
        set(a) = angleAdjusterProfiledPID.setGoal(
            UtilMath.clamp(
                a,
                Constants.Shooter.ANGLE_ADJUSTMENT_MIN,
                Constants.Shooter.ANGLE_ADJUSTMENT_MAX
            )
        )
//        set(v) = angleAdjuster.setPosition((UtilMath.clamp(v, Constants.Shooter.ANGLE_ADJUSTMENT_MIN, Constants.Shooter.ANGLE_ADJUSTMENT_MAX)).degrees)

    var shooterVelocity: Double
        get() = flywheelMotorMaster.encoder.velocity.value
        set(v) = flywheelMotorMaster.setVelocity(
            SIUnit(
                UtilMath.clamp(
                    (v * velocityMultiplier.getDouble(0.0)),
                    Constants.Shooter.SHOOTER_VELOCITY_MIN,
                    Constants.Shooter.SHOOTER_VELOCITY_MAX
                )
            )
        )


    val flywheelMotorMaster =
        falconFX(Constants.Shooter.SHOOTER_FLYWHEEL_MASTER, Constants.Shooter.NATIVE_SHOOTER_WHEEL_LENGTH_MODEL) {
            brakeMode = true
            outputInverted = false
        }

    private val flywheelMotorSlave =
        falconFX(Constants.Shooter.SHOOTER_FLYWHEEL_SLAVE, Constants.Shooter.NATIVE_SHOOTER_WHEEL_LENGTH_MODEL) {
            brakeMode = true
            outputInverted = true
            follow(flywheelMotorMaster)
        }

    val angleAdjuster = falconMAX(
        Constants.Shooter.ANGLE_ADJUSTMENT,
        CANSparkMaxLowLevel.MotorType.kBrushless, DefaultNativeUnitModel,
        useAlternateEncoder = false,
        4096
    ) {
        brakeMode = true
        outputInverted = false
    }

    private val angleEncoder = FalconMAXEncoder(
        angleAdjuster.canSparkMax.getAlternateEncoder(4096),
        Constants.Shooter.ANGLE_NATIVE_ROTATION_MODEL
    )


    val angleAdjusterPID: PIDController = PIDController(0.0, 0.0, 0.0)
    val angleAdjusterProfiledPID = ProfiledPIDController(
        0.0, 0.0, 0.0,
        TrapezoidProfile.Constraints(.5, .75)
    )

    fun runShooter(percent: Double) {
        flywheelMotorMaster.setDutyCycle(percent)
    }

    private fun runShooter(velocity: SIUnit<Velocity<Meter>>) {
        flywheelMotorMaster.setVelocity(velocity)
    }

    fun setAngle(angle: SIUnit<Radian>) {
        angleAdjusterProfiledPID.setGoal(angle.value)
    }

    fun toIdle() {
        flywheelMotorMaster.setDutyCycle(0.0)
    }

//    override fun periodic() {
//        angleAdjuster.setVoltage(angleAdjusterProfiledPID.calculate(angleEncoder.position.value).volts)
//    }


    init {
        defaultCommand = ShooterAnglePIDTuner()

        angleEncoder.resetPosition(20.degrees)

//        angleAdjuster.motorController.config_kF(0, 0.075, 30)
//        angleAdjuster.motorController.config_kP(0, .85, 30)
//        angleAdjuster.motorController.config_kI(0,0.0,30)
//        angleAdjuster.motorController.config_kD(0, 10.0, 30)
//        angleAdjuster.controller.run {
//            ff = 0.00075
//            p = .0085
//            i = 0.0
//            d = .1
//        }

        angleAdjuster.controller.setSmartMotionAllowedClosedLoopError(0.0, 0)



        flywheelMotorMaster.motorController.config_kD(0, 0.003, 30)
        flywheelMotorMaster.motorController.config_kF(0, 0.065, 30)
        flywheelMotorMaster.motorController.config_kI(0, 0.000, 30)
        flywheelMotorMaster.motorController.config_kP(0, 0.075, 30)
    }
}


