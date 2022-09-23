package org.frc1778.robot.subsystems.shooter

import edu.wpi.first.networktables.NetworkTable
import edu.wpi.first.networktables.NetworkTableInstance
import org.frc1778.robot.Constants
import org.frc1778.robot.Constants.Shooter.NATIVE_ROTATION_MODEL
import org.ghrobotics.lib.commands.FalconSubsystem
import org.ghrobotics.lib.mathematics.units.Meter
import org.ghrobotics.lib.mathematics.units.SIUnit
import org.ghrobotics.lib.mathematics.units.derived.*
import org.ghrobotics.lib.mathematics.units.meters
import org.ghrobotics.lib.mathematics.units.second
import org.ghrobotics.lib.motors.ctre.falconFX
import org.ghrobotics.lib.wrappers.networktables.get
import kotlin.math.pow
import kotlin.math.tan

object Shooter : FalconSubsystem() {
    private val limeTable: NetworkTable = NetworkTableInstance.getDefault().getTable("limelight")
    private val ty = limeTable["ty"]
    var turretAngle: Double
        get() = angleAdjuster.encoder.position.inDegrees()
        set(v) {}
//        set(v) = angleAdjuster.setPosition(v.degrees)

    var shooterAngle: Double
        get() = angleAdjuster.encoder.position.inRadians()
        set(v) = angleAdjuster.setPosition(v.radians)

    var shooterVelocity: Double
        get() = flywheelMotor.encoder.velocity.value
        set(v) = flywheelMotor.setVelocity(SIUnit(v))



    val flywheelMotor = falconFX(Constants.Shooter.SHOOTER_FLYWHEEL, Constants.Shooter.NATIVE_ROTATION_MODEL) {
        brakeMode = true
        outputInverted = false

    }
    private val angleAdjuster = falconFX(Constants.Shooter.ANGLE_ADJUSTMENT, NATIVE_ROTATION_MODEL) {
        brakeMode = true
        outputInverted = true
        motionProfileCruiseVelocity = SIUnit(360.0)
        motionProfileAcceleration = SIUnit(520.0)
        useMotionProfileForPosition = true

    }

    val angleEncoder = angleAdjuster.encoder

    fun runShooter(percent: Double) {
        flywheelMotor.setDutyCycle(percent)
    }

    private fun runShooter(velocity: SIUnit<Velocity<Radian>>) {
        flywheelMotor.setVelocity(velocity)
    }

    fun setAngle(angle: SIUnit<Radian>) {
        angleAdjuster.setPosition(angle)
    }

    fun toIdle() {
        flywheelMotor.setDutyCycle(0.0)
    }


    init {
//        defaultCommand = ShootCommand()

        angleEncoder.resetPosition(SIUnit(0.0))

        angleAdjuster.motorController.config_kF(0, 0.075, 30)
        angleAdjuster.motorController.config_kP(0, .85, 30)
        angleAdjuster.motorController.config_kI(0,0.0,30)
        angleAdjuster.motorController.config_kD(0, 10.0, 30)

        flywheelMotor.motorController.config_kD(0, 0.065, 30)
        flywheelMotor.motorController.config_kF(0, 0.045, 30)
        flywheelMotor.motorController.config_kI(0, 0.000, 30)
        flywheelMotor.motorController.config_kP(0, 0.17, 30)
    }
}
