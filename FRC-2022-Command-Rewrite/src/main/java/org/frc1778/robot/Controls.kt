package org.frc1778.robot

import edu.wpi.first.wpilibj.Joystick
import org.frc1778.robot.commands.ReverseIntake
import org.frc1778.robot.commands.RunIntake
import org.frc1778.robot.commands.climber.ClimberToClimbPosition
import org.frc1778.robot.commands.climber.ClimberToRestPosition
import org.frc1778.robot.commands.climber.DeployHook1
import org.frc1778.robot.commands.climber.DeployHook2
import org.frc1778.robot.commands.loader.ManualLoadCommand
import org.frc1778.robot.commands.shooter.Shoot
//import org.frc1778.robot.commands.shooter.Shoot
import org.frc1778.robot.commands.drive.Aim
import org.ghrobotics.lib.wrappers.hid.FalconHID
import org.ghrobotics.lib.wrappers.hid.mapControls

/**
 * Controls object that holds the driver controller and operator controller
 */


object Controls {
    //TODO: Update to use a more personalized HID to go with new commands
    private val driverControllerGenericHID = Joystick(0)
    private val operatorControllerGenericHID = Joystick(1)


//    private val runIntakeCommand = RunIntake()


    val driverController = driverControllerGenericHID.mapControls {
        button(1) {
            change(Aim())
        }
    }

    //    val operatorController = FalconHIDBuilder<Joystick>(Joystick(1)).build()
    val operatorController: FalconHID<Joystick> = operatorControllerGenericHID.mapControls {
        //Climber Controls
        button(7) {
            change(ClimberToRestPosition())
        }
        button(10) {
            change(DeployHook1())
        }
        button(8) {
            change(DeployHook2())
        }
        button(5) {
            change(ClimberToClimbPosition())
        }
        //Shooter Commands
        button(3) {
//            change(WeakShoot())
            change(Shoot())
        }
        //Collector Controls
//        button(9) {
//            changeOn(ToggleCollector())
//        }
        button(2) {
            change(RunIntake())
        }
        greaterThanAxisButton(3, .35) {
            change(RunIntake())
        }
        button(4) {
            change(ReverseIntake())
        }
        //Loader Commands
        //TODO Choose Button for Manual Load
        button(1) {
            change(ManualLoadCommand())
        }

    }
}
