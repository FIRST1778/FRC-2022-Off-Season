//package org.frc1778.robot.commands.shooter
//
//import edu.wpi.first.networktables.NetworkTable
//import edu.wpi.first.networktables.NetworkTableInstance
//import edu.wpi.first.wpilibj.Timer
//import org.frc1778.robot.commands.loader.Load
//import org.frc1778.robot.subsystems.collector.Collector
//import org.frc1778.robot.subsystems.loader.Loader
//import org.frc1778.robot.subsystems.shooter.Shooter
//import org.ghrobotics.lib.commands.FalconCommand
//import org.ghrobotics.lib.mathematics.units.derived.Radian
//import org.ghrobotics.lib.mathematics.units.derived.Velocity
//import org.ghrobotics.lib.mathematics.units.SIUnit
//import org.ghrobotics.lib.mathematics.units.meters
//import org.ghrobotics.lib.wrappers.networktables.get
//import kotlin.math.abs
//import kotlin.math.pow
//import kotlin.math.tan
//import kotlin.properties.Delegates
//
//class Shoot : FalconCommand(Shooter, Loader, Collector) {
//    private val limeTable: NetworkTable = NetworkTableInstance.getDefault().getTable("limelight")
//    private val timer: Timer = Timer()
//    private val ty = limeTable["ty"]
//    private val velocityThreshold = 5.0
//    private val angleThreshold = .125
//    private var d by Delegates.notNull<Double>()
//    private var v by Delegates.notNull<SIUnit<Velocity<Radian>>>()
//    private var a by Delegates.notNull<SIUnit<Radian>>()
//    private var loadCommand: FalconCommand? = null
//    private var done = false
//
//    override fun execute() {
//        timer.start()
//        d = ((104.0 - 23.5) / (tan((33.322 + ty.getDouble(0.0)) / 57.296))).meters.value
//        v = if (d > 75) {
//            SIUnit(
//                ((((0.0004 * d.pow(3)) - (0.109 * d.pow(2)) + (11.759 * d) + 39.691))
//                        * (.86 * ((d - 150) / 750))) + 700 - if (d < 150) 20.5 else 15.0
//            )
//        } else {
//            SIUnit(
//                ((0.0004 * d.pow(3)) - (0.117 * d.pow(2))
//                        + (11.759 * d) + 39.691) + if (d > 75) 25.0 else 10.25
//            )
//        }
//        a = SIUnit(
//            (-(7.324605E-9 * d.pow(4)) + (4.4445282E-6 * d.pow(3)) - (9.211335E-4 * d.pow(2))
//                    + (.1009318946 * d) - .078396) + if (d > 150) .75 else if (d > 120) .825 else if (d < 90) .205 else 0.225
//        )
//
//
//        Shooter.shooterVelocity = v.value
//        Shooter.shooterAngle = a.value
//        if (abs(Shooter.shooterVelocity - v.value) < velocityThreshold
//            && abs(Shooter.shooterAngle - a.value) < angleThreshold
//        ) {
//            if (Loader.isLoaded()) {
//                if (loadCommand?.isFinished != false) {
//                    loadCommand = Load()
//                    loadCommand?.schedule()
//                }
//            } else {
//                Collector.runCollector(.35)
//                Loader.runMain(.20)
//            }
//        }
//
//
//    }
//
//
//}