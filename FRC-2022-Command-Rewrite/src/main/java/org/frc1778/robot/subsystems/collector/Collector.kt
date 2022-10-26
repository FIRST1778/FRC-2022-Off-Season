package org.frc1778.robot.subsystems.collector

import com.revrobotics.CANSparkMaxLowLevel
import edu.wpi.first.wpilibj.DriverStation
import org.frc1778.robot.Constants
import org.ghrobotics.lib.commands.FalconSubsystem
import org.ghrobotics.lib.mathematics.units.SIUnit
import org.ghrobotics.lib.mathematics.units.derived.Radian
import org.ghrobotics.lib.mathematics.units.derived.radians
import org.ghrobotics.lib.mathematics.units.nativeunit.DefaultNativeUnitModel
import org.ghrobotics.lib.motors.ctre.falconFX
import org.ghrobotics.lib.motors.rev.falconMAX


object Collector : FalconSubsystem() {



    enum class Position(val position: SIUnit<Radian>) {
        UP(0.radians),
        DOWN(9.5.radians)
    }

    private val miniLeft = falconMAX(Constants.Collector.LEFT_MINI_MASTER, CANSparkMaxLowLevel.MotorType.kBrushless, DefaultNativeUnitModel) {
        brakeMode = true
        outputInverted = false
    }

    val miniRight = falconMAX(Constants.Collector.RIGHT_MINI_SLAVE, CANSparkMaxLowLevel.MotorType.kBrushless, DefaultNativeUnitModel) {
        brakeMode = true
        outputInverted = true
    }


    val deployMotor = falconFX(Constants.Collector.DEPLOY_MOTOR, Constants.Collector.NATIVE_ROTATION_MODEL) {
        brakeMode = true
        outputInverted = false
        useMotionProfileForPosition = true
        motionProfileCruiseVelocity = SIUnit(14.5)
        motionProfileAcceleration = SIUnit(100.0)
    }

    val collectorUp = {deployMotor.encoder.position < (Position.UP.position + 1.0.radians)}
    @Deprecated("Want to Deprecate remove where used", replaceWith = ReplaceWith("collectorUp"))
    var collectorDown = false

    fun runCollector(percent: Double) {
        miniLeft.setDutyCycle(percent)
        miniRight.setDutyCycle(percent)
    }

    var collectorPosition: Position
        get() = if(deployMotor.encoder.position < (Position.UP.position + 1.0.radians) ) Position.UP else Position.DOWN
        set(v) = deployMotor.setPosition(v.position)



    init {
        deployMotor.encoder.resetPosition(SIUnit(0.0))
        deployMotor.motorController.configAllowableClosedloopError(0, 150.0, 30)
        deployMotor.motorController.config_kF(0, 0.075, 30)
        deployMotor.motorController.config_kP(0, 1.6, 30)
        deployMotor.motorController.config_kI(0, 0.0, 30)
        deployMotor.motorController.config_kD(0, 16.0, 30)
    }


}