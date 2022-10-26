package org.frc1778.util.pathing

import edu.wpi.first.wpilibj.Timer
import org.frc1778.robot.Constants
import org.frc1778.robot.Robot
import org.frc1778.robot.subsystems.collector.Collector
import org.frc1778.robot.subsystems.drive.Drive
import org.frc1778.robot.subsystems.loader.Loader
import org.frc1778.robot.subsystems.shooter.Shooter
import org.ghrobotics.lib.commands.FalconCommand


/**
 * Path command
 * Allows for the bad Autonomous Stuff I made to be used like a command
 * Probably will fall out of use given I can recreate the current autonomous with Command Groups
 * @property path
 * @constructor Create empty Path command
 */
class PathCommand(private val path: Path) : FalconCommand(Drive, Collector, Loader, Shooter) {
    private lateinit var matchTimer: Timer

    override fun initialize() {
        matchTimer = Timer()
        matchTimer.start()
        path.currSegment = 0
    }


    override fun execute() {
        path.runPath(matchTimer)
    }

    override fun cancel() {
        Drive.stop()
        super.cancel()
    }

    override fun end(interrupted: Boolean) {
        Drive.stop()
    }

    override fun isFinished(): Boolean {
        return path.isFinished()
    }
}