package org.frc1778.robot.subsystems.collector.commands

import org.frc1778.robot.Controls
import org.frc1778.robot.subsystems.collector.Collector
import org.frc1778.robot.subsystems.drive.Drive
import org.ghrobotics.lib.commands.FalconCommand





open class CollectorCommands : FalconCommand(Collector) {

    override fun execute() {

        if(!Drive.Autonomous.auto) {


            Collector.runCollector(if (collectSource()) 0.35 else if (reverse()) -.15 else 0.0)
        }
    }

    companion object {
        var collectSource = Controls.operatorController.getRawButton(1)
        var reverse = Controls.operatorController.getRawButton(4)
        var deploySource = Controls.operatorController.getRawButton(9)
    }
}