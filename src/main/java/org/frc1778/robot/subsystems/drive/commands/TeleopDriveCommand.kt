package org.frc1778.robot.subsystems.drive.commands

import org.frc1778.robot.Controls
import org.frc1778.robot.subsystems.drive.Drive
import org.ghrobotics.lib.commands.FalconCommand

class TeleopDriveCommand : FalconCommand(Drive) {

    override fun execute() {
        Drive.swerveDrive(forwardSource(), strafeSource(), rotationSource(), fieldCentricSource())
    }

    companion object {
        val forwardSource = Controls.driverController.getRawAxis(1)
        val strafeSource = Controls.driverController.getRawAxis(3)
        val rotationSource = Controls.driverController.getRawAxis(2)
        val fieldCentricSource = Controls.driverController.getRawButton(2)
    }
}
