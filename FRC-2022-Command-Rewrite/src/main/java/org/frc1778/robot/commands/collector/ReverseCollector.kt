package org.frc1778.robot.commands.collector

import org.frc1778.robot.subsystems.collector.Collector
import org.ghrobotics.lib.commands.FalconCommand

class ReverseCollector : FalconCommand(Collector) {
    override fun execute() {
        Collector.runCollector(-.15)
    }

    override fun cancel() {
        end(true)
    }

    override fun end(interrupted: Boolean) {
        Collector.runCollector(0.0)
    }
}