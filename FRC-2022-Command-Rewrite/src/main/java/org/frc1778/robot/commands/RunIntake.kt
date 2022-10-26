package org.frc1778.robot.commands

import edu.wpi.first.wpilibj.DriverStation
import org.frc1778.robot.commands.collector.RunCollector
import org.frc1778.robot.commands.collector.ToggleCollector
import org.frc1778.robot.subsystems.collector.Collector
import org.frc1778.robot.subsystems.loader.Loader
import org.ghrobotics.lib.commands.FalconCommand

class RunIntake : FalconCommand(Collector, Loader) {
    private var done = false

    override fun initialize() {
        if(Collector.collectorPosition != Collector.Position.DOWN) {
            Collector.collectorPosition = Collector.Position.DOWN
        }
    }
    override fun execute() {
        Collector.runCollector(if(!Collector.collectorUp()) .30 else 0.0)
        Loader.runMain(.15)
//        DriverStation.reportError("${this::class.toString()} Command execute", false)
    }

    override fun cancel() {
//        DriverStation.reportError("${this::class.toString()} Command cancel", false)
        if(Collector.collectorPosition != Collector.Position.UP) {
            Collector.collectorPosition = Collector.Position.UP
        }
        Collector.runCollector(0.0)
        Loader.runMain(0.0)
        super.cancel()
    }

    override fun end(interrupted: Boolean) {
        super.end(interrupted)
    }

    override fun isFinished() = done


}