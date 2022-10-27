package org.frc1778.robot.commands.drive

import edu.wpi.first.networktables.NetworkTable
import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets
import org.frc1778.robot.Constants
import org.frc1778.robot.subsystems.drive.Drive
import org.frc1778.robot.subsystems.shooter.Shooter
import org.ghrobotics.lib.commands.FalconCommand
import org.ghrobotics.lib.wrappers.networktables.get

class Aim : FalconCommand(Drive) {
    private val limeTable: NetworkTable = NetworkTableInstance.getDefault().getTable("limelight")
    private val tx = limeTable["tx"]
    private val ty = limeTable["ty"]
    private val ta = limeTable["ta"]
    private val lights = limeTable["ledMode"]

    private val Tx = Constants.debugTab2
        .add("Tx", 0)
        .withWidget(BuiltInWidgets.kTextView)
        .entry

    private val limeDistance = Constants.debugTab2
        .add("Distance", 0)
        .withWidget(BuiltInWidgets.kTextView)
        .entry

    private val Ty = Constants.debugTab2
        .add("Ty", 0)
        .withWidget(BuiltInWidgets.kTextView)
        .entry





    override fun execute() {

        Tx.setDouble(tx.getDouble(0.0))
        Ty.setDouble(ty.getDouble(0.0))

        val distance = Shooter.getDistance()
        limeDistance.setDouble(distance)

        lights.setDouble(3.0)

        if(ta.getDouble(0.0) > 0.0) {

            if (tx.getDouble(0.0) > if (distance > 135) 1.95 else 2.7) {
                Drive.curvatureDrive(
                    0.0,
                    if (tx.getDouble(0.0) > if (distance > 135) 3.75 else 4.5) 0.085 else .02,
                    true
                )
            } else if (tx.getDouble(0.0) < if (distance > 135) 1.55 else 2.3) {
                Drive.curvatureDrive(
                    0.0,
                    if (tx.getDouble(0.0) < if (distance > 135) 1.55 else -.5) -0.085 else -.02,
                    true
                )
            } else {
                Drive.stop()
            }
        }
    }

    override fun cancel() {
        Drive.curvatureDrive(0.0, 0.0, false)
        super.cancel()
    }

    override fun end(interrupted: Boolean) {
        Drive.curvatureDrive(0.0, 0.0, false)
    }

}