package org.frc1778.robot

import edu.wpi.first.wpilibj.Joystick
import org.frc1778.robot.commands.climber.ClimberToClimbPosition
import org.frc1778.robot.commands.climber.ClimberToRestPosition
import org.frc1778.robot.commands.climber.DeployHook1
import org.frc1778.robot.commands.climber.DeployHook2
import org.ghrobotics.lib.wrappers.hid.FalconHIDBuilder
import org.ghrobotics.lib.wrappers.hid.mapControls

/**
 * Controls object that holds the driver controller and operator controller
 */

object Controls {
    //TODO: Update to use a more personalized HID to go with new commands
    private val driverControllerGenericHID = Joystick(0)
    private val operatorControllerGenericHID = Joystick(1)



    val driverController = FalconHIDBuilder<Joystick>(Joystick(0)).build()
    val operatorController = operatorControllerGenericHID.mapControls {
        button(7) {
            changeOn(ClimberToRestPosition())
        }
        button(10) {
            changeOn(DeployHook1())
        }
        button(8) {
            changeOn(DeployHook2())
        }
        button(5) {
            changeOn(ClimberToClimbPosition())
        }
    }
}
