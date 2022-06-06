package org.frc1778.robot.commands.Loader

import edu.wpi.first.wpilibj.Timer
import org.frc1778.robot.subsystems.loader.Loader
import org.ghrobotics.lib.commands.FalconCommand
import kotlin.properties.Delegates

class Load: FalconCommand(Loader) {
    private var startTime by Delegates.notNull<Double>()
    private var done = false

    override fun initialize() {
        startTime = Timer.getMatchTime()
    }

    override fun execute() {
        if(Timer.getMatchTime() - startTime < .375) {
            end(false)
        } else {
            Loader.backUpLoader(.15)
        }


    }

    override fun cancel () {
        end(true)
    }


    override fun end(interrupted: Boolean) {
        done = true
        Loader.backUpLoader(0.0)
    }

    override fun isFinished() = done
}