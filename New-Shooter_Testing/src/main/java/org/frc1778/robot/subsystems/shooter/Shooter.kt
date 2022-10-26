package org.frc1778.robot.subsystems.shooter

import com.revrobotics.CANSparkMaxLowLevel
import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.math.controller.ProfiledPIDController
import edu.wpi.first.math.trajectory.TrapezoidProfile
import edu.wpi.first.networktables.NetworkTable
import edu.wpi.first.networktables.NetworkTableEntry
import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.wpilibj.DriverStation
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets
import org.frc1778.robot.Constants
import org.frc1778.robot.UtilMath
import org.frc1778.robot.commands.shooter.Shoot
import org.frc1778.robot.commands.shooter.ShooterAnglePIDTuner
import org.frc1778.robot.commands.shooter.ShooterFlywheelPIDTuning
import org.ghrobotics.lib.commands.FalconSubsystem
import org.ghrobotics.lib.mathematics.units.Meter
import org.ghrobotics.lib.mathematics.units.SIUnit
import org.ghrobotics.lib.mathematics.units.derived.*
import org.ghrobotics.lib.mathematics.units.meters
import org.ghrobotics.lib.mathematics.units.nativeunit.DefaultNativeUnitModel
import org.ghrobotics.lib.motors.ctre.FalconCTREEncoder
import org.ghrobotics.lib.motors.ctre.falconFX
import org.ghrobotics.lib.motors.rev.FalconMAX
import org.ghrobotics.lib.motors.rev.FalconMAXEncoder
import org.ghrobotics.lib.motors.rev.falconMAX
import org.ghrobotics.lib.wrappers.networktables.get
import kotlin.math.tan

object Shooter : FalconSubsystem() {
    private val limeTable: NetworkTable = NetworkTableInstance.getDefault().getTable("limelight")
    private val ty = limeTable["ty"]
    private val ta = limeTable["ta"]

    private val limeLightAngle = 35.657
    private val limeLightHeight = 19.75
    private val targetHeight = 104





    var shooterAngle: Double
        get() = angleEncoder.position.inDegrees()
        //        get() = angleEncoder.rawPosition.value
        set(a) = angleAdjusterProfiledPID.setGoal(
            UtilMath.clamp(
                a, Constants.Shooter.ANGLE_ADJUSTMENT_MIN, Constants.Shooter.ANGLE_ADJUSTMENT_MAX
            )
        )
//        set(v) = angleAdjuster.setPosition((UtilMath.clamp(v, Constants.Shooter.ANGLE_ADJUSTMENT_MIN, Constants.Shooter.ANGLE_ADJUSTMENT_MAX)).degrees)

    var shooterVelocity: Double
        get() = flywheelMotorMaster.encoder.velocity.value
        set(v) = flywheelMotorMaster.setVelocity(
            SIUnit(
                UtilMath.clamp(
                    (v * Constants.Shooter.ShuffleBoard.velocityMultiplier.getDouble(1.0)),
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
        CANSparkMaxLowLevel.MotorType.kBrushless,
        Constants.Shooter.ANGLE_NATIVE_ROTATION_MODEL,
        useAlternateEncoder = false,
        4096
    ) {
        brakeMode = false
        outputInverted = false
    }

    val angleEncoder = FalconMAXEncoder(
        angleAdjuster.canSparkMax.getAlternateEncoder(4096),
        Constants.Shooter.ANGLE_NATIVE_ROTATION_MODEL
    )


    val angleAdjusterPID: PIDController = PIDController(0.0, 0.0, 0.0)
    val angleAdjusterProfiledPID = ProfiledPIDController(
        0.0, 0.0, 0.0, TrapezoidProfile.Constraints(100.0, 400.0)
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
        angleAdjusterProfiledPID.setGoal(Constants.Shooter.ANGLE_NATIVE_ROTATION_MODEL.toNativeUnitPosition(20.degrees).value)
    }

        fun getDistance(): Double = if(ta.getDouble(0.0) > 0.0) ((targetHeight - limeLightHeight)/tan((limeLightAngle + ty.getDouble(0.0)).degrees.value)) else 0.0
//    fun getDistance() = Constants.Shooter.ShuffleBoard.distanceTab.getDouble(0.0)
    override fun periodic() {
        if (DriverStation.isEnabled()) {
            angleAdjusterProfiledPID.calculate((angleEncoder.position.inDegrees())).also {
                Constants.Shooter.ShuffleBoard.voltageSet.setDouble(it)
                angleAdjuster.setVoltage(it.volts)

            }
        } else {
            angleAdjuster.setDutyCycle(0.0)
        }
    }


    init {
//        defaultCommand = Shoot()
        Constants.Shooter.ShuffleBoard.distanceTab.setDouble(getDistance())

        angleEncoder.resetPosition(20.degrees)

//
        angleAdjusterProfiledPID.run {
            p = .5
            i = 0.0
            d = 0.005
        }



        flywheelMotorMaster.motorController.config_kD(0, 0.003, 30)
        flywheelMotorMaster.motorController.config_kF(0, 0.065, 30)
        flywheelMotorMaster.motorController.config_kI(0, 0.000, 30)
        flywheelMotorMaster.motorController.config_kP(0, 0.075, 30)
    }
}


