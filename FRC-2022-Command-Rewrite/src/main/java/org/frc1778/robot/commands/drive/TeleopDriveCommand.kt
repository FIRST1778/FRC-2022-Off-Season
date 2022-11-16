package org.frc1778.robot.commands.drive

import edu.wpi.first.networktables.NetworkTable
import edu.wpi.first.networktables.NetworkTableInstance
import org.frc1778.robot.Constants
import org.frc1778.robot.Controls
//import org.frc1778.util.DriveControl
import org.frc1778.robot.subsystems.drive.Drive
import org.frc1778.util.UtilMath
import org.ghrobotics.lib.commands.FalconCommand
import org.ghrobotics.lib.wrappers.networktables.get

open class TeleopDriveCommand : FalconCommand(Drive) {
    private val limeTable: NetworkTable = NetworkTableInstance.getDefault().getTable("limelight")
    private val lights = limeTable["ledMode"]


    override fun execute() {
//        lights.setDouble()
        val maxThrottle = .175
        Drive.curvatureDrive(
            UtilMath.clamp(linearSource(), -maxThrottle, maxThrottle),
            UtilMath.clamp(turnSource(), -maxThrottle, maxThrottle),
            quickTurnSource()
        )

    }

    override fun cancel() {
        Drive.curvatureDrive(0.0, 0.0, false)
        super.cancel()
    }

    override fun end(interrupted: Boolean) {
        Drive.curvatureDrive(0.0, 0.0, false)
    }

    companion object {
        val linearSource = Controls.driverController.getRawAxis(1)
        val turnSource = Controls.driverController.getRawAxis(2)
        val quickTurnSource = Controls.driverController.getRawButton(2)
        val limeSource = Controls.driverController.getRawButton(1)
    }
}
