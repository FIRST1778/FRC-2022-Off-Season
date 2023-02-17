package org.frc1778.robot.commands.drive

import edu.wpi.first.math.controller.ProfiledPIDController
import edu.wpi.first.math.trajectory.TrapezoidProfile
import edu.wpi.first.wpilibj2.command.ProfiledPIDCommand
import org.frc1778.robot.Constants
import org.frc1778.robot.subsystems.drive.Drive

/**
 * Autonomous Command to turn the robot in place
 *
 * @property targetAngle
 * @constructor Create empty Turn in place
 */
class TurnToAngle(private val targetAngle: Double) : ProfiledPIDCommand(
    ProfiledPIDController(
        Constants.Drive.kP,
        Constants.Drive.kI,
        Constants.Drive.kD,
        TrapezoidProfile.Constraints(
            2.5,
            .25

        )
    ).apply {
        enableContinuousInput(-180.0, 180.0)
        setTolerance(5.0, 5.0)
    },
    Drive::getHeading,
    targetAngle,
    { output: Double, _: TrapezoidProfile.State -> Drive.arcadeDrive(0.0, output)},
    Drive
    ) {

    override fun isFinished(): Boolean {
        return controller.atGoal()
    }
}
