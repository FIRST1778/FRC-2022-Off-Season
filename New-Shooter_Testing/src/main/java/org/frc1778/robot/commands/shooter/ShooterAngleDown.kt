package org.frc1778.robot.commands.shooter

import org.frc1778.robot.subsystems.shooter.Shooter
import org.ghrobotics.lib.commands.FalconCommand
import org.ghrobotics.lib.mathematics.units.SIUnit

class ShooterAngleDown: FalconCommand(Shooter) {

    override fun execute() {
        Shooter.angleAdjuster.setDutyCycle(7.5)
    }

    override fun cancel() {
        Shooter.angleAdjuster.setVelocity(SIUnit(0.0))
        super.cancel()
    }



}