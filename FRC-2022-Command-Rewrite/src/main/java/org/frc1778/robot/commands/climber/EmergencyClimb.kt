package org.frc1778.robot.commands.climber

import org.frc1778.robot.subsystems.climber.Climber
import org.ghrobotics.lib.commands.FalconCommand
import org.ghrobotics.lib.mathematics.units.derived.radians


class EmergencyClimb: FalconCommand(Climber) {
    override fun execute() {
        Climber.winchMotorRight.setDutyCycle(-.75)
    }

    override fun cancel() {
        Climber.position = Climber.position
    }

}