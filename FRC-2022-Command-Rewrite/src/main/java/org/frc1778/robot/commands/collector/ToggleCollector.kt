package org.frc1778.robot.commands.collector

import org.frc1778.robot.subsystems.collector.Collector
import org.ghrobotics.lib.commands.FalconCommand
import kotlin.math.abs


class ToggleCollector: FalconCommand(Collector) {
    private val threshold = .05

    override fun execute() {
        Collector.collectorPosition = if(collectorUp) Collector.Position.DOWN else Collector.Position.UP

    }

    override fun isFinished(): Boolean {
        return if(collectorUp){
            if(abs((Collector.collectorPosition.position - Collector.Position.DOWN.position).value) < threshold) {
                collectorUp = !collectorUp
                true
            } else {
                if(abs((Collector.collectorPosition.position - Collector.Position.DOWN.position).value) < threshold) {
                    collectorUp = !collectorUp
                    true
                } else {
                    false
                }
            }
        } else {
            false
        }
    }

    override fun end(interrupted: Boolean) {
        if(interrupted) {
            collectorUp = !collectorUp
        }
    }

    private companion object {
        private var collectorUp = true
    }
}