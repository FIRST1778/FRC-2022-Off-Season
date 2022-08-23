package org.frc1778.robot.commands.collector

import org.frc1778.robot.subsystems.collector.Collector
import org.ghrobotics.lib.commands.FalconCommand

class RunCollector : FalconCommand(Collector) {
    override fun execute() {
        Collector.runCollector(.35)
    }


    override fun end(interrupted: Boolean) {
        Collector.runCollector(0.0)
    }


}