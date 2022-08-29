package org.frc1778.robot.commands.shooter

import org.frc1778.robot.commands.loader.Load
import org.frc1778.robot.subsystems.shooter.Shooter
import org.ghrobotics.lib.commands.FalconCommand
import org.ghrobotics.lib.mathematics.units.meters
import org.ghrobotics.lib.mathematics.units.seconds

class WeakShoot: FalconCommand(Shooter) {
    private var done = false

    override fun execute() {
        Shooter.shooterVelocity = 150.0
        Shooter.shooterAngle = 2.0
    }

    override fun cancel() {
        end(true)
    }

    override fun end(interrupted: Boolean) {
        done = true
        Shooter.toIdle()
    }

    override fun isFinished() = done
}