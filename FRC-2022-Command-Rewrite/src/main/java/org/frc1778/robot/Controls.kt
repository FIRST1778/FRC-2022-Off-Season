package org.frc1778.robot

import edu.wpi.first.wpilibj.Joystick
import org.ghrobotics.lib.wrappers.hid.FalconHIDBuilder

/**
 * Controls object that holds the driver controller and operator controller
 */

object Controls {
    //TODO: Update to use a more personalized HID to go with new commands


    val driverController = FalconHIDBuilder<Joystick>(Joystick(0)).build()
    val operatorController = FalconHIDBuilder<Joystick>(Joystick(1)).build()
}
