package org.frc1778.robot.commands.shooter



import edu.wpi.first.networktables.NetworkTable
import edu.wpi.first.networktables.NetworkTableInstance
import org.frc1778.robot.subsystems.collector.Collector
import org.frc1778.robot.subsystems.drive.Drive
import org.frc1778.robot.subsystems.loader.Loader
import org.frc1778.robot.subsystems.shooter.Shooter
import org.ghrobotics.lib.commands.FalconCommand
import org.ghrobotics.lib.mathematics.units.SIUnit
import org.ghrobotics.lib.mathematics.units.derived.Radian
import org.ghrobotics.lib.mathematics.units.derived.degrees
import org.ghrobotics.lib.mathematics.units.derived.radians
import org.ghrobotics.lib.mathematics.units.meters
import org.ghrobotics.lib.wrappers.networktables.get
import kotlin.math.*
import kotlin.properties.Delegates

class ShootAndAim : FalconCommand(Shooter, Loader, Collector, Drive){
    private val limeTable: NetworkTable = NetworkTableInstance.getDefault().getTable("limelight")
    private val h = 1.905
    private var a by Delegates.notNull<Double>()
    private var d by Delegates.notNull<Double>()
    private val acel = -.05
    private val g = -9.8 + acel
    private val ty = limeTable["ty"]
    private val tx = limeTable["tx"]
    private val ta = limeTable["ta"]
    private var loadCommand by Delegates.notNull<FalconCommand>()

    override fun execute() {
        d = ((104.0 - 23.5) / (tan((33.322 + ty.getDouble(0.0)) / 57.296))).meters.value
        a = (30 * (0.75 + (3.302/d))).degrees.value
        val midPoint = (-((2*h/d) + tan(a))/(2 * (-(h/d.pow(2)) - (tan(a)/d))))
        val heightMax = arc(midPoint)
        val vy = sqrt(-2 * g * heightMax)
        var v by Delegates.notNull<Double>()
        var vx by Delegates.notNull<Double>()
        val angleToTarget = Shooter.turretAngle + ty.getDouble(0.0)
        var shooterAngle by Delegates.notNull<Double>()
        var targetAngle by Delegates.notNull<Double>()
        if((Drive.rightVelocity.value.sign == Drive.leftVelocity.value.sign)){
            if(!Loader.badBallLoaded()) {
                if((abs(Drive.rightVelocity.value) > .2 && abs(Drive.leftVelocity.value) > .2)){
                    val vr = Drive.averageRobotVelocity().value

                    vx =  max(((midPoint - (.5 * acel * (vy/g).pow(2)))/(vy/-g)) + (vr * cos((angleToTarget).degrees.value)), 0.0)
                    val vz = -vr * sin((angleToTarget).degrees.value)
                    v = sqrt(vx.pow(2) + vy.pow(2) + vz.pow(2))
                    targetAngle = angleToTarget + atan2(vz, vx)
                } else {
                    vx = (midPoint - (.5 * acel * (vy/g).pow(2)))/(vy/-g)
                    v = sqrt(vx.pow(2) + vy.pow(2))
                    targetAngle = angleToTarget + ty.getDouble(0.0)
                }
                shooterAngle = atan2(vy, vx)



            } else {
                v = 1.0
                shooterAngle = 30.0
            }
            Shooter.turretAngle = targetAngle
            Shooter.shooterAngle = shooterAngle
        }
    }



    private fun arc(x: Double): Double = (-(h/d.pow(2)) - (tan(a)/d) * x.pow(2)) + (((2*h/d) + tan(a)) * x)

}