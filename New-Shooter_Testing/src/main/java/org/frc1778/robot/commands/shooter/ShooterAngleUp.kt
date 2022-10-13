package org.frc1778.robot.commands.shooter

import org.frc1778.robot.subsystems.shooter.Shooter
import org.ghrobotics.lib.commands.FalconCommand
import org.ghrobotics.lib.mathematics.units.SIUnit

class ShooterAngleUp: FalconCommand(Shooter) {

    override fun execute() {
        Shooter.angleAdjuster.setDutyCycle(.035)
    }

    override fun cancel() {
//        Shooter.angleAdjuster.setVelocity(SIUnit(0.0))
        Shooter.angleAdjuster.setDutyCycle(0.0)
        super.cancel()
    }



}