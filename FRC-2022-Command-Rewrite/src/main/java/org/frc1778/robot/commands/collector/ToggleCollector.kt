package org.frc1778.robot.commands.collector

import org.frc1778.robot.subsystems.collector.Collector
import org.ghrobotics.lib.commands.FalconCommand



class ToggleCollector: FalconCommand(Collector) {

    override fun execute() {
        Collector.collectorPosition = if(collectorUp) Collector.Position.DOWN else Collector.Position.UP
        collectorUp = !collectorUp
    }

    private companion object {
        private var collectorUp = true
    }
}