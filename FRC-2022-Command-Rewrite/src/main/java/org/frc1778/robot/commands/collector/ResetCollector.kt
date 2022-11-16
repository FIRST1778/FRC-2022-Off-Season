package org.frc1778.robot.commands.collector

import org.frc1778.robot.subsystems.collector.Collector
import org.frc1778.util.pathing.events.CollectorUp
import org.ghrobotics.lib.commands.FalconCommand
import org.ghrobotics.lib.mathematics.units.derived.radians


class ResetCollector: FalconCommand(Collector) {
    override fun initialize() {
        Collector.resetCollectorPosition(Collector.Position.DOWN)
        Collector.collectorPosition = Collector.Position.UP
    }

    override fun isFinished(): Boolean {
        return Collector.collectorUp()
    }
}