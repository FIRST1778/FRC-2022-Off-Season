package org.frc1778.robot.commands

import org.frc1778.robot.commands.collector.ToggleCollector
import org.frc1778.robot.subsystems.collector.Collector
import org.frc1778.robot.subsystems.loader.Loader
import org.ghrobotics.lib.commands.FalconCommand

class ReverseIntake : FalconCommand(Collector, Loader) {
    private var done = false

    override fun initialize() {
        if(Collector.collectorPosition != Collector.Position.DOWN) {
            Collector.collectorPosition = Collector.Position.DOWN
        }
    }
    override fun execute() {
        Collector.runCollector(if(!Collector.collectorUp()) -.15 else 0.0)
        Loader.runMain(if(!Loader.badBallLoaded()) -.15 else 0.0)
    }

    override fun cancel() {
        Collector.runCollector(0.0)
        Loader.runMain(0.0)
        if(Collector.collectorPosition != Collector.Position.UP) {
            Collector.collectorPosition = Collector.Position.UP
        }
        super.cancel()
    }

    override fun end(interrupted: Boolean) {
        super.end(interrupted)
    }

    override fun isFinished() = done
}