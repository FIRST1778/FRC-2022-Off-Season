package org.frc1778.robot.subsystems.loader

import com.revrobotics.CANSparkMaxLowLevel
import com.revrobotics.ColorSensorV3
import edu.wpi.first.wpilibj.DigitalInput
import edu.wpi.first.wpilibj.I2C
import org.frc1778.robot.Constants
import org.frc1778.robot.Robot.allianceColor
import org.frc1778.robot.subsystems.loader.commands.LoaderCommands
import org.ghrobotics.lib.commands.FalconSubsystem
import org.ghrobotics.lib.mathematics.units.nativeunit.DefaultNativeUnitModel
import org.ghrobotics.lib.motors.ctre.falconFX
import org.ghrobotics.lib.motors.rev.falconMAX
import org.ghrobotics.lib.utils.Source

object Loader : FalconSubsystem() {
    private val mainMotor = falconFX(Constants.Loader.MAIN_WHEEL, DefaultNativeUnitModel) {
        brakeMode = true
        outputInverted = false
    }

    private val hopperMotor = falconMAX(Constants.Loader.INTERMEDIATE_WHEEL, CANSparkMaxLowLevel.MotorType.kBrushless, DefaultNativeUnitModel) {
        brakeMode = true
        outputInverted = true
    }

    private val loaderMotor = falconMAX(Constants.Loader.LOADER_WHEEL, CANSparkMaxLowLevel.MotorType.kBrushless, DefaultNativeUnitModel) {
        brakeMode = true
        outputInverted = false
    }

    val loaderLineBreakSensor = DigitalInput(1 )
    val colorSensor = ColorSensorV3(I2C.Port.kOnboard)

    val badBallLoaded: Source<Boolean> = { colorSensor.color == allianceColor }


    val isLoaded: Source<Boolean> = { loaderLineBreakSensor.get()}

    fun runMain(percent: Double) {
        mainMotor.setDutyCycle(if(isLoaded() || percent < 0.0) percent else 0.0)
        hopperMotor.setDutyCycle(percent)
    }
    fun runLoader(percent: Double) {
        loaderMotor.setDutyCycle(percent)
        if(percent != 0.0) mainMotor.setDutyCycle(percent)
        if(percent != 0.0) hopperMotor.setDutyCycle(percent)
    }

    fun backUpLoader(percent: Double) {
        loaderMotor.setDutyCycle(percent)
        if(percent != 0.0) mainMotor.setDutyCycle(percent)
    }

    init {
        defaultCommand = LoaderCommands()
    }

}