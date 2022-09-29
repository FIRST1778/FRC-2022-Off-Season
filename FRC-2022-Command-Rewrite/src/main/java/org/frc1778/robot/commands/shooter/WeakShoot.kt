package org.frc1778.robot.commands.shooter

import edu.wpi.first.wpilibj.DriverStation
import org.frc1778.robot.commands.loader.Load
import org.frc1778.robot.subsystems.shooter.Shooter
import org.ghrobotics.lib.commands.FalconCommand
import kotlin.math.abs

class WeakShoot: FalconCommand(Shooter) {
    private var done = false
    private var loadCommand: Load? = null



    override fun execute() {
        Shooter.shooterVelocity = 175.0
        Shooter.shooterAngle = 3.0

//        if((abs(Shooter.shooterVelocity - 175) < 20 && abs(Shooter.shooterAngle  - 3.0) < .5)) {
//            if(loadCommand?.isFinished == true || loadCommand == null) {
//                loadCommand = Load()
//            }
//            loadCommand!!.schedule()
//        } else {
//            if(loadCommand?.isScheduled == true) {
//               loadCommand!!.cancel()
//            }
//        }
    }

    override fun cancel() {
        Shooter.toIdle()
//        if(loadCommand?.isScheduled == true) {
//            loadCommand!!.cancel()
//        }
//        loadCommand = null
        super.cancel()
    }

    override fun end(interrupted: Boolean) {
        if(interrupted) {
            if(loadCommand?.isScheduled == true) {
                loadCommand!!.cancel()
            }
        }
        Shooter.toIdle()
        loadCommand = null
    }

    override fun isFinished() = done
}