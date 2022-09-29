package org.frc1778.robot.commands.shooter

import edu.wpi.first.networktables.NetworkTableEntry
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets
import org.frc1778.robot.Constants
import org.frc1778.robot.subsystems.shooter.Shooter
import org.ghrobotics.lib.commands.FalconCommand

class Shoot: FalconCommand(Shooter) {

    override fun execute() {
        Shooter.shooterVelocity = velocityTab.getDouble(.5)
        
    }
    
    override fun cancel() {
        Shooter.shooterVelocity = .5
        super.cancel()
    }

    companion object {
        private val velocityTab: NetworkTableEntry = Constants.debugTab2
            .add("Velocity", 0.0)
            .withPosition(0,0)
            .withSize(10,10)
            .withWidget(BuiltInWidgets.kTextView)
            .entry
    }
}