package org.frc1778.robot.commands

import org.frc1778.robot.commands.collector.RunCollector
import org.frc1778.robot.subsystems.collector.Collector
import org.frc1778.robot.subsystems.loader.Loader
import org.ghrobotics.lib.commands.FalconCommand

class StopIntake : FalconCommand(Collector, Loader) {
    private var done = false
    override fun execute() {
        Collector.runCollector(0.0)
        Loader.runMain(0.0)
    }

    override fun cancel() {
        Collector.runCollector(0.0)
        Loader.runMain(0.0)
    }

    override fun end(interrupted: Boolean) {
        Collector.runCollector(0.0)
        Loader.runMain(0.0)
        done = true
    }

    override fun isFinished() = done


}