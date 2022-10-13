package org.frc1778.robot.commands.shooter

import edu.wpi.first.wpilibj.DriverStation
import org.frc1778.robot.subsystems.shooter.Shooter
import org.ghrobotics.lib.commands.FalconCommand

class ShooterAnglePIDTuner : FalconCommand(Shooter) {
    override fun initialize() {
//        setFF = Shooter.kFTab.getDouble(0.0)
//        setP = Shooter.kPTab.getDouble(0.0)
//        setI = Shooter.kITab.getDouble(0.0)
//        setD = Shooter.kDTab.getDouble(0.0)

    }

    override fun execute() {
//        Shooter.shooterAngle = Shooter.velocityTab.getDouble(0.0)
        Shooter.angleAdjuster.setDutyCycle(Shooter.velocityTab.getDouble(0.0)/100)
//        Shooter.velocityTab.setDouble(Shooter.shooterAngle)
        Shooter.currVelocity.setDouble(Shooter.shooterAngle)

//        Shooter.angleAdjusterProfiledPID.run {
//            if (Shooter.kFTab.getDouble(0.0) != setFF) {
//                Shooter.kFTab.getDouble(0.0)
//                setFF = Shooter.kFTab.getDouble(0.0)
//                DriverStation.reportError("Setting FF", false)
//            }
//            if (Shooter.kPTab.getDouble(0.0) != setP) {
//                p = Shooter.kPTab.getDouble(0.0)
//                setP = Shooter.kPTab.getDouble(0.0)
//                DriverStation.reportError("Setting P", false)
//            }
//            if (Shooter.kITab.getDouble(0.0) != setI) {
//                i = Shooter.kITab.getDouble(0.0)
//                setI = Shooter.kITab.getDouble(0.0)
//                DriverStation.reportError("Setting I", false)
//            }
//            if (Shooter.kDTab.getDouble(0.0) != setD) {
//                d = Shooter.kDTab.getDouble(0.0)
//                setD = Shooter.kDTab.getDouble(0.0)
//                DriverStation.reportError("Setting D", false)
//            }
//        }
    }

    companion object {
        var setFF = 0.0
        var setP = 0.0
        var setI = 0.0
        var setD = 0.0
    }


}