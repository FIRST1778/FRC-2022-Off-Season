package org.frc1778.robot.subsystems.shooter

import com.revrobotics.CANSparkMaxLowLevel
import edu.wpi.first.networktables.NetworkTable
import edu.wpi.first.networktables.NetworkTableInstance
import org.frc1778.robot.Constants
import org.ghrobotics.lib.commands.FalconSubsystem
import org.ghrobotics.lib.mathematics.units.Meter
import org.ghrobotics.lib.mathematics.units.SIUnit
import org.ghrobotics.lib.mathematics.units.derived.*
import org.ghrobotics.lib.motors.ctre.falconFX
import org.ghrobotics.lib.motors.rev.falconMAX
import org.ghrobotics.lib.wrappers.networktables.get

object Shooter : FalconSubsystem() {
    private val limeTable: NetworkTable = NetworkTableInstance.getDefault().getTable("limelight")
    private val ty = limeTable["ty"]
    var turretAngle: Double
        get() = angleAdjuster.encoder.position.inDegrees()
        set(v) {}
//        set(v) = angleAdjuster.setPosition(v.degrees)

    var shooterAngle: Double
        get() = angleAdjuster.encoder.position.inRadians()
        set(v) = angleAdjuster.setPosition(v.degrees)

    var shooterVelocity: Double
        get() = flywheelMotorMaster.encoder.velocity.value
        set(v) = flywheelMotorMaster.setVelocity(SIUnit(v))



    private val flywheelMotorMaster = falconFX(Constants.Shooter.SHOOTER_FLYWHEEL_MASTER, Constants.Shooter.NATIVE_SHOOTER_WHEEL_LENGTH_MODEL) {
        brakeMode = true
        outputInverted = false
    }

    private val flywheelMotorSlave = falconFX(Constants.Shooter.SHOOTER_FLYWHEEL_SLAVE, Constants.Shooter.NATIVE_SHOOTER_WHEEL_LENGTH_MODEL) {
        brakeMode = true
        outputInverted = true
        follow(flywheelMotorMaster)
    }

    val angleAdjuster = falconMAX(Constants.Shooter.ANGLE_ADJUSTMENT,
        CANSparkMaxLowLevel.MotorType.kBrushless, Constants.Shooter.ANGLE_NATIVE_ROTATION_MODEL) {
        brakeMode = true
        outputInverted = true
        motionProfileCruiseVelocity = SIUnit(360.0)
        motionProfileAcceleration = SIUnit(520.0)
        useMotionProfileForPosition = true

    }

    val angleEncoder = angleAdjuster.encoder

    fun runShooter(percent: Double) {
        flywheelMotorMaster.setDutyCycle(percent)
    }

    private fun runShooter(velocity: SIUnit<Velocity<Meter>>) {
        flywheelMotorMaster.setVelocity(velocity)
    }

    fun setAngle(angle: SIUnit<Radian>) {
        angleAdjuster.setPosition(angle)
    }

    fun toIdle() {
        flywheelMotorMaster.setDutyCycle(0.0)
    }


    init {
//        defaultCommand = ShootCommand()

        angleEncoder.resetPosition(SIUnit(0.0))

//        angleAdjuster.motorController.config_kF(0, 0.075, 30)
//        angleAdjuster.motorController.config_kP(0, .85, 30)
//        angleAdjuster.motorController.config_kI(0,0.0,30)
//        angleAdjuster.motorController.config_kD(0, 10.0, 30)
        angleAdjuster.controller.run {
            ff = 0.075
            p = .85
            i = 0.0
            d = 10.0
        }

        flywheelMotorMaster.motorController.config_kD(0, 0.065, 30)
        flywheelMotorMaster.motorController.config_kF(0, 0.045, 30)
        flywheelMotorMaster.motorController.config_kI(0, 0.000, 30)
        flywheelMotorMaster.motorController.config_kP(0, 0.17, 30)
    }
}
