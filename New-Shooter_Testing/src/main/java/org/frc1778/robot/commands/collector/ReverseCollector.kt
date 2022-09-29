package org.frc1778.robot.commands.collector

import org.frc1778.robot.subsystems.collector.Collector
import org.ghrobotics.lib.commands.FalconCommand

class ReverseCollector : FalconCommand(Collector) {
    private var done = false
    override fun execute() {
        Collector.runCollector(-.15)
    }

    override fun isFinished(): Boolean {
        return done
    }

    override fun end(interrupted: Boolean) {
        Collector.runCollector(0.0)
    }
}