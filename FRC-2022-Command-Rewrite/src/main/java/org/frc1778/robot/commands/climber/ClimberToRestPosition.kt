package org.frc1778.robot.commands.climber

import org.frc1778.robot.subsystems.climber.Climber
import org.ghrobotics.lib.commands.FalconCommand
import org.ghrobotics.lib.mathematics.units.derived.radians

class ClimberToRestPosition : FalconCommand(Climber) {
    override fun execute() {
        Climber.position = 0.0.radians
    }

    override fun isFinished(): Boolean {
        return Climber.position == 0.0.radians
    }

    override fun end(interrupted: Boolean) {
        if(!interrupted) {
            Climber.winchMotorRight.setDutyCycle(0.0)
        }
    }

}