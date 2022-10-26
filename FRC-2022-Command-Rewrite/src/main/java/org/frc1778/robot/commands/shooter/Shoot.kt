package org.frc1778.robot.commands.shooter

import org.frc1778.robot.Constants
import org.frc1778.robot.subsystems.shooter.Shooter
import org.ghrobotics.lib.commands.FalconCommand
import kotlin.math.pow
import kotlin.properties.Delegates

class Shoot : FalconCommand(Shooter) {
    private var distance by Delegates.notNull<Double>()
    private var shooterAngle by Delegates.notNull<Double>()
    private var shooterVelocity by Delegates.notNull<Double>()


    override fun execute() {
        distance = Shooter.getDistance()

        shooterVelocity = if (distance > 75) {
            ((((0.0004 * distance.pow(3)) - (0.109 * distance.pow(2)) + (11.759 * distance) + 39.691)) * (.86 * ((distance - 150) / 750))) + 700 - if (distance < 150) 20.5 else 15.0
        } else {
            ((0.0004 * distance.pow(3)) - (0.117 * distance.pow(2)) + (11.759 * distance) + 39.691) + if (distance > 75) 25.0 else 10.25
        }

        shooterAngle =
            (-(7.324605E-9 * distance.pow(4)) + (4.4445282E-6 * distance.pow(3)) - (9.211335E-4 * distance.pow(2)) + (.1009318946 * distance) - .078396) + if (distance > 150) .75 else if (distance > 120) .825 else if (distance < 90) .140 else 0.225

        Constants.Shooter.ShuffleBoard.setVelocity.setDouble(shooterVelocity)

        Shooter.shooterVelocity = shooterVelocity
        Shooter.shooterAngle = shooterAngle

    }

    override fun cancel() {
//        Shooter.toIdle()
        Shooter.shooterAngle = 0.0
        Shooter.flywheelMotor.setDutyCycle(0.0)
        Shooter.flywheelMotor.motorController.temperature
        super.cancel()
    }


}