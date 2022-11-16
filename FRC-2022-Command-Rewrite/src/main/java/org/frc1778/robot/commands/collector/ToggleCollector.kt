package org.frc1778.robot.commands.collector

import edu.wpi.first.wpilibj.DriverStation
import org.frc1778.robot.subsystems.collector.Collector
import org.frc1778.robot.subsystems.collector.Collector.collectorUp
import org.ghrobotics.lib.commands.FalconCommand
import kotlin.math.abs

//@Deprecated("Robot Automatically Lifts Collector Arm")
class ToggleCollector: FalconCommand(Collector) {
    private val threshold = .05
    private var done = false

    override fun execute() {
        Collector.collectorPosition = if(collectorUp()) Collector.Position.DOWN else Collector.Position.UP
    }

    override fun isFinished(): Boolean = done


    override fun end(interrupted: Boolean) {
        if(interrupted) {
//            collectorUp = !collectorUp
        }
    }


}