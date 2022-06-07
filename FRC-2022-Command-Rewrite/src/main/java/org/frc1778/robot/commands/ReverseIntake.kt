package org.frc1778.robot.commands

import org.frc1778.robot.subsystems.collector.Collector
import org.frc1778.robot.subsystems.loader.Loader
import org.ghrobotics.lib.commands.FalconCommand

class ReverseIntake : FalconCommand(Collector, Loader) {
    override fun execute() {
        Collector.runCollector(-.15)
        Loader.runMain(if(Loader.badBallLoaded()) -.15 else 0.0)
    }

    override fun cancel() {
        end(true)
    }

    override fun end(interrupted: Boolean) {
        Collector.runCollector(0.0)
        Loader.runMain(0.0)
    }
}