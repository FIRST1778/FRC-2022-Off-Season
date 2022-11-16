package org.frc1778.robot

//import org.frc1778.robot.commands.shooter.Shoot
import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj2.command.InstantCommand
import org.frc1778.robot.commands.ReverseIntake
import org.frc1778.robot.commands.RunIntake
import org.frc1778.robot.commands.climber.ClimberToClimbPosition
import org.frc1778.robot.commands.climber.ClimberToRestPosition
import org.frc1778.robot.commands.climber.DeployHook1
import org.frc1778.robot.commands.climber.DeployHook2
import org.frc1778.robot.commands.collector.ResetCollector
import org.frc1778.robot.commands.drive.Aim
import org.frc1778.robot.commands.loader.ManualLoadCommand
import org.frc1778.robot.commands.shooter.Shoot
import org.frc1778.robot.commands.shooter.WeakShoot
import org.frc1778.robot.subsystems.collector.Collector
import org.ghrobotics.lib.wrappers.hid.FalconHID
import org.ghrobotics.lib.wrappers.hid.mapControls

/**
 * Controls object that holds the driver controller and operator controller
 */


object Controls {
    //TODO: Update to use a more personalized HID to go with new commands
    private val driverControllerGenericHID = Joystick(0)
    val operatorControllerGenericHID = Joystick(1)


//    private val runIntakeCommand = RunIntake()


    val driverController = driverControllerGenericHID.mapControls {
        button(1) {
            change(Aim())
        }
    }

    //    val operatorController = FalconHIDBuilder<Joystick>(Joystick(1)).build()
    val operatorController: FalconHID<Joystick> = operatorControllerGenericHID.mapControls {
        //Climber Controls
        button(6) {
            change(ClimberToRestPosition())
        }
        button(7) {
            change(DeployHook1())
        }
        button(8) {
            change(DeployHook2())
        }
        button(5) {
            change(ClimberToClimbPosition())
        }
        button(9) {
            changeOn(
                ResetCollector()
            )
        }
//        button() {
//            EmergencyClimb()
//        }
//        button(9){
//            ToggleCollector()
//        }
        //Shooter Commands
        button(3) {
            change(WeakShoot())
//            change(Shoot())
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
