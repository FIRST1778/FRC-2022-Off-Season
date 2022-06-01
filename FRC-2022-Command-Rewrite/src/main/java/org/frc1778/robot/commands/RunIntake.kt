package org.frc1778.robot.commands

import org.frc1778.robot.commands.collector.RunCollector
import org.frc1778.robot.subsystems.collector.Collector
import org.frc1778.robot.subsystems.loader.Loader
import org.ghrobotics.lib.commands.FalconCommand

class RunIntake : FalconCommand(Collector, Loader) {
    override fun execute() {
        Collector.runCollector(.35)
        Loader.runMain(if(!Loader.isLoaded()) .20 else 0.0)
    }

    override fun cancel() {
        Collector.runCollector(0.0)
        Loader.runMain(0.0)
    }


}