package org.frc1778.robot.commands.climber


import org.frc1778.robot.subsystems.climber.Climber
import org.ghrobotics.lib.commands.FalconCommand
import org.ghrobotics.lib.mathematics.units.derived.radians

class DeployHook1 : FalconCommand(Climber){
    private val deployedClimberEncoderValue = 7.5.radians

    override fun execute() {
        Climber.position = deployedClimberEncoderValue
    }


    override fun isFinished(): Boolean {
        return Climber.position == deployedClimberEncoderValue
    }

    override fun end(interrupted: Boolean) {
        if(interrupted) {
            Climber.position = 0.radians
        }
    }
}