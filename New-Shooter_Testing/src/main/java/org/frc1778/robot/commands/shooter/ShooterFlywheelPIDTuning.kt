package org.frc1778.robot.commands.shooter

import edu.wpi.first.wpilibj.DriverStation
import org.frc1778.robot.subsystems.shooter.Shooter
import org.ghrobotics.lib.commands.FalconCommand

class ShooterFlywheelPIDTuning : FalconCommand(Shooter) {
    override fun initialize() {
//        setFF = Shooter.kFTab.getDouble(0.0)
//        setP = Shooter.kPTab.getDouble(0.0)
//        setI = Shooter.kITab.getDouble(0.0)
//        setD = Shooter.kDTab.getDouble(0.0)

    }

    override fun execute() {
        Shooter.shooterVelocity = Shooter.velocityTab.getDouble(0.0)
        Shooter.currVelocity.setDouble(Shooter.shooterVelocity)

        Shooter.flywheelMotorMaster.motorController.run {
            if (Shooter.kFTab.getDouble(0.0) != setFF) {
                config_kF(0, Shooter.kFTab.getDouble(0.0), 30)
                setFF = Shooter.kFTab.getDouble(0.0)
                DriverStation.reportError("Setting FF", false)
            }
            if (Shooter.kPTab.getDouble(0.0) != setFF) {
                config_kP(0, Shooter.kPTab.getDouble(0.0), 30)
                setP = Shooter.kPTab.getDouble(0.0)
                DriverStation.reportError("Setting P", false)
            }
            if (Shooter.kITab.getDouble(0.0) != setFF) {
                config_kI(0, Shooter.kITab.getDouble(0.0), 30)
                setI = Shooter.kITab.getDouble(0.0)
                DriverStation.reportError("Setting I", false)
            }
            if (Shooter.kDTab.getDouble(0.0) != setFF) {
                config_kD(0, Shooter.kDTab.getDouble(0.0), 30)
                setD = Shooter.kDTab.getDouble(0.0)
                DriverStation.reportError("Setting D", false)
            }
        }
    }

    companion object {
        var setFF = 0.0
        var setP = 0.0
        var setI = 0.0
        var setD = 0.0
    }


}