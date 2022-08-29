package org.frc1778.robot.commands.loader

import edu.wpi.first.wpilibj.Timer
import org.frc1778.robot.subsystems.loader.Loader
import org.ghrobotics.lib.commands.FalconCommand
import org.ghrobotics.lib.utils.not
import kotlin.properties.Delegates


/**
 * Command to load a ball into the flywheel then ready the next ball
 *
 */
class Load: FalconCommand(Loader) {
    private var startTime by Delegates.notNull<Double>()
    private var done = false

    override fun initialize() {
        startTime = Timer.getMatchTime()
    }

    override fun execute() {
        if(Timer.getMatchTime() - startTime < .375) {
            if(Timer.getMatchTime() - startTime > .175 && Loader.isLoaded()) end(false)
            Loader.load(.15)
        } else if(!Loader.isLoaded()) {
            Loader.runLoaderMotor(0.0)
            Loader.runMainMotor(.15)
        } else {
            end(false)
        }
    }


    override fun end(interrupted: Boolean) {
        done = true
        Loader.load(0.0)
    }

    override fun isFinished() = done
}