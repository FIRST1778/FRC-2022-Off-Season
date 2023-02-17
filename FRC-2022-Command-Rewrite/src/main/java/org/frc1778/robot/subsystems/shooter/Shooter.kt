package org.frc1778.robot.subsystems.shooter

import edu.wpi.first.math.controller.BangBangController
import edu.wpi.first.networktables.NetworkTable
import edu.wpi.first.networktables.NetworkTableInstance
import org.frc1778.robot.Constants
import org.frc1778.robot.commands.shooter.ShooterFlywheelPIDTuning
import org.frc1778.util.UtilMath
import org.ghrobotics.lib.commands.FalconSubsystem
import org.ghrobotics.lib.mathematics.units.SIUnit
import org.ghrobotics.lib.mathematics.units.derived.Radian
import org.ghrobotics.lib.mathematics.units.derived.Velocity
import org.ghrobotics.lib.mathematics.units.derived.degrees
import org.ghrobotics.lib.mathematics.units.derived.radians
import org.ghrobotics.lib.mathematics.units.inches
import org.ghrobotics.lib.motors.ctre.falconFX
import org.ghrobotics.lib.wrappers.networktables.get
import kotlin.math.tan

object Shooter : FalconSubsystem() {
    private val limeTable: NetworkTable = NetworkTableInstance.getDefault().getTable("limelight")
    private val ty = limeTable["ty"]
    private val ta = limeTable["ta"]

    private const val limeLightAngle = 33.322
    private const val limeLightHeight = 23.5
    private const val targetHeight = 104


    var shooterController = BangBangController(

    )

    var shooterAngle: Double
        get() = angleAdjuster.encoder.position.value
        set(a) = angleAdjuster.setPosition(
            (UtilMath.clamp(
                a,
                Constants.Shooter.ANGLE_ADJUSTMENT_MIN,
                Constants.Shooter.ANGLE_ADJUSTMENT_MAX
            )).radians
        )

    var shooterVelocity: Double
        get() = flywheelMotor.encoder.velocity.value
        set(v) = flywheelMotor.setDutyCycle(shooterController.calculate(flywheelMotor.encoder.velocity.value, v) * .75)


    val flywheelMotor =
        falconFX(Constants.Shooter.SHOOTER_FLYWHEEL_MASTER, Constants.Shooter.NATIVE_ROTATION_MODEL) {
            brakeMode = false
            outputInverted = false
            useMotionProfileForPosition = false
        }


    val angleAdjuster = falconFX(
        Constants.Shooter.ANGLE_ADJUSTMENT,
        Constants.Shooter.NATIVE_ROTATION_MODEL,
    ) {
        brakeMode = false
        outputInverted = true
        motionProfileCruiseVelocity = SIUnit(360.0)
        motionProfileAcceleration = SIUnit(520.0)
        useMotionProfileForPosition = true
    }


    fun runShooter(percent: Double) {
        flywheelMotor.setDutyCycle(percent)
    }

    private fun runShooter(velocity: SIUnit<Velocity<Radian>>) {
        flywheelMotor.setVelocity(velocity)
    }


    fun toIdle() {
        flywheelMotor.setDutyCycle(0.0)
        angleAdjuster.setPosition(0.0.radians)
    }

    fun getDistance(): Double =
        if (ta.getDouble(0.0) > 0.0) ((targetHeight - limeLightHeight) / tan((limeLightAngle + ty.getDouble(0.0)).degrees.value)) else 0.0

    override fun periodic() {
        Constants.Shooter.ShuffleBoard.distanceTab.setDouble(getDistance())
        Constants.Shooter.ShuffleBoard.currVelocity.setDouble(flywheelMotor.encoder.velocity.value)
        Constants.Shooter.ShuffleBoard.motorTemp.setDouble(flywheelMotor.motorController.temperature)
    }


    init {
//        defaultCommand = ShooterFlywheelPIDTuning()

        flywheelMotor.motorController.run {
            config_kF(0, 0.045, 30)
            config_kP(0, 0.075, 30)
            config_kI(0, 0.000, 30)
            config_kD(0, 0.006, 30)
        }

        angleAdjuster.motorController.run {
            config_kF(0, 0.075, 30)
            config_kP(0, .85, 30)
            config_kI(0, 0.0, 30)
            config_kD(0, 10.0, 30)
        }
    }
}


