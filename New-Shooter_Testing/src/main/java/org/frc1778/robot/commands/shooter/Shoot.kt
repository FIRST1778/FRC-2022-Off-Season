package org.frc1778.robot.commands.shooter

import edu.wpi.first.networktables.NetworkTableEntry
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets
import org.frc1778.robot.Constants
import org.frc1778.robot.subsystems.shooter.Shooter
import org.frc1778.robot.subsystems.shooter.Shooter.velocityTab
import org.ghrobotics.lib.commands.FalconCommand

class Shoot: FalconCommand(Shooter) {

    override fun execute() {
        Shooter.shooterVelocity = velocityTab.getDouble(.5)
//        Shooter.flywheelMotorMaster.setDutyCycle(.75)
    }

    override fun cancel() {
        Shooter.shooterVelocity = .5
        super.cancel()
    }

}