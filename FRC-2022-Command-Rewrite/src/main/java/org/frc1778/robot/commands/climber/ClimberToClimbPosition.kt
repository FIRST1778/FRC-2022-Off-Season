package org.frc1778.robot.commands.climber

import org.frc1778.robot.subsystems.climber.Climber
import org.ghrobotics.lib.commands.FalconCommand
import org.ghrobotics.lib.mathematics.units.derived.radians

class ClimberToClimbPosition : FalconCommand(Climber) {
    override fun execute() {
        Climber.position = (-1.0).radians
    }

    override fun cancel() {
        Climber.position = 0.radians
    }

    override fun isFinished(): Boolean {
        return Climber.position == (-1).radians
    }
}