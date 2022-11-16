package org.frc1778.robot.commands.climber

import edu.wpi.first.wpilibj.DriverStation
import org.frc1778.robot.subsystems.climber.Climber
import org.ghrobotics.lib.commands.FalconCommand
import org.ghrobotics.lib.mathematics.units.derived.radians

class ClimberToClimbPosition : FalconCommand(Climber) {

    override fun initialize() {
        if(DriverStation.getMatchTime() < 30) {
            Climber.position = (-1.75).radians
        } else {
            Climber.position = 0.radians
        }
    }

    override fun isFinished(): Boolean {
        return Climber.position == (-1.75).radians
    }


}