package org.frc1778.robot.commands.loader

import edu.wpi.first.wpilibj.DriverStation
import edu.wpi.first.wpilibj.Timer
import org.frc1778.robot.subsystems.loader.Loader
import org.ghrobotics.lib.commands.FalconCommand
import org.ghrobotics.lib.utils.not
import java.sql.Driver
import kotlin.properties.Delegates


/**
 * Command to load a ball into the flywheel then ready the next ball
 *
 */
class Load: FalconCommand(Loader) {
    private var startTime by Delegates.notNull<Double>()
    private var wait = false
    private var done = false
    private var timer = Timer()
    private val waitTime = .375

    override fun initialize() {
        timer.start()
        startTime = timer.get()
    }

    override fun execute() {
        if(wait) {
          wait = timer.get() - startTime >= waitTime
        } else if(timer.get() - startTime < if(wait) .0625 + waitTime else .0625) {
            Loader.load(.15)
        } else if(!Loader.isLoaded()) {
            if(Timer.getMatchTime() - startTime > if(wait) .375 + waitTime else .375) done = true
            Loader.runLoaderMotor(0.0)
            Loader.runMainMotor(.15)
            Loader.runHopperMotor(.15)

        } else if(Loader.isLoaded()) {
            startTime = timer.get()
            wait = true
        } else {
            done = true
        }
    }

    override fun cancel() {
        Loader.runLoaderMotor(0.0)
        Loader.runMainMotor(.0)
        Loader.runHopperMotor(.0)
        super.cancel()
    }

    override fun end(interrupted: Boolean) {
        Loader.runLoaderMotor(0.0)
        Loader.runMainMotor(.0)
        Loader.runHopperMotor(.0)
    }

    override fun isFinished() = done
}