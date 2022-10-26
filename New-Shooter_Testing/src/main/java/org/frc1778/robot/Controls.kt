package org.frc1778.robot

import edu.wpi.first.wpilibj.Joystick
import org.frc1778.robot.commands.ReverseIntake
import org.frc1778.robot.commands.RunIntake
import org.frc1778.robot.commands.collector.ToggleCollector
import org.frc1778.robot.commands.loader.Load
import org.frc1778.robot.commands.loader.ManualLoadCommand
import org.frc1778.robot.commands.shooter.Shoot
import org.frc1778.robot.commands.shooter.ShooterAngleDown
import org.frc1778.robot.commands.shooter.ShooterAngleUp
import org.ghrobotics.lib.wrappers.hid.FalconHID
import org.ghrobotics.lib.wrappers.hid.mapControls

object Controls {
    private val operatorControllerGenericHID = Joystick(1)

    val operatorController: FalconHID<Joystick> = operatorControllerGenericHID.mapControls {
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