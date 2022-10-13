package org.frc1778.robot.commands.shooter

import edu.wpi.first.networktables.NetworkTable
import edu.wpi.first.networktables.NetworkTableEntry
import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets
import org.frc1778.robot.Constants
import org.frc1778.robot.Constants.Shooter.Dashboard.actualAngle
import org.frc1778.robot.Constants.Shooter.Dashboard.actualVelocity
import org.frc1778.robot.Constants.Shooter.Dashboard.expectedAngle
import org.frc1778.robot.Constants.Shooter.Dashboard.expectedVelocity
import org.frc1778.robot.subsystems.shooter.Shooter
import org.ghrobotics.lib.commands.FalconCommand
import org.ghrobotics.lib.mathematics.units.derived.degrees
import org.ghrobotics.lib.wrappers.networktables.get
import kotlin.math.atan
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.math.tan
import kotlin.properties.Delegates

class Shoot : FalconCommand(Shooter) {
    private var d by Delegates.notNull<Double>()
    private var shooterAngle by Delegates.notNull<Double>()
    private var shooterVelocity by Delegates.notNull<Double>()
    private var entryAngle by Delegates.notNull<Double>()


    override fun execute() {
//        Shooter.shooterVelocity = velocityTab.getDouble(.5)
//        Shooter.flywheelMotorMaster.setDutyCycle(.75)

        d = Shooter.getDistance()
        entryAngle = -6.285 * d + 87.5
        val midPoint = (-((2 * h / d) + tan(entryAngle)) / (2 * (-(h / d.pow(2)) - (tan(entryAngle) / d))))
        val heightMax = arc(midPoint)
        val vy = sqrt(-2 * g * heightMax)
        val halfTime = heightMax / vy
        val vx = (midPoint - (.5 * a * (vy / g).pow(2))) / (vy / -g)

        expectedAngle.setDouble(atan(vy/vx))
        expectedVelocity.setDouble(sqrt(vx.pow(2) + vy.pow(2)))

        Shooter.shooterAngle = actualAngle.getDouble(0.0)
        Shooter.shooterVelocity = actualVelocity.getDouble(0.0)





//        Shooter.shooterVelocity = Constants.Shooter.SHOOTER_VELOCITY_MULTIPLIER * sqrt(vx.pow(2) + vy.pow(2))
//        Shooter.shooterAngle = atan(vy/vx)
    }

    override fun cancel() {
        Shooter.shooterVelocity = .5
        super.cancel()
    }

    private fun arc(x: Double): Double =
        (-(h / d.pow(2)) - (tan(entryAngle) / d) * x.pow(2)) + (((2 * h / d) + tan(entryAngle)) * x)

    companion object {
        private val limeTable: NetworkTable = NetworkTableInstance.getDefault().getTable("limelight")
        private const val h = 1.905
        private val ty = limeTable["ty"]
        private val tx = limeTable["tx"]
        private val ta = limeTable["ta"]
        private const val a = Constants.Shooter.ASSUMED_DRAG_ACCEL
        private const val g = Constants.Shooter.ASSUMED_GRAVITATION_ACCEL
    }

}