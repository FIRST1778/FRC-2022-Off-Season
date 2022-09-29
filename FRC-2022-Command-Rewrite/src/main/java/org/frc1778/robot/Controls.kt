package org.frc1778.robot

import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets
import org.frc1778.robot.commands.ReverseIntake
import org.frc1778.robot.commands.RunIntake
import org.frc1778.robot.commands.StopIntake
import org.frc1778.robot.commands.climber.ClimberToClimbPosition
import org.frc1778.robot.commands.climber.ClimberToRestPosition
import org.frc1778.robot.commands.climber.DeployHook1
import org.frc1778.robot.commands.climber.DeployHook2
import org.frc1778.robot.commands.collector.ReverseCollector
import org.frc1778.robot.commands.collector.RunCollector
import org.frc1778.robot.commands.collector.ToggleCollector
import org.frc1778.robot.commands.loader.Load
import org.frc1778.robot.commands.loader.ManualLoadCommand
import org.frc1778.robot.commands.shooter.Shoot
//import org.frc1778.robot.commands.shooter.Shoot
import org.frc1778.robot.commands.shooter.WeakShoot
import org.frc1778.robot.subsystems.drive.commands.Aim
import org.frc1778.robot.subsystems.loader.Loader
import org.ghrobotics.lib.wrappers.hid.FalconHID
import org.ghrobotics.lib.wrappers.hid.FalconHIDBuilder
import org.ghrobotics.lib.wrappers.hid.mapControls

/**
 * Controls object that holds the driver controller and operator controller
 */
private var buttonActive = Constants.debugTab2
    .add("Button One On", false)
    .withWidget(BuiltInWidgets.kTextView)
    .withPosition(3,3)
    .withSize(2,2)
    .entry

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
        button(9) {
            changeOn(ToggleCollector())
        }
        button(2) {
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
