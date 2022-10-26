package org.frc1778.robot.commands.loader

import org.frc1778.robot.subsystems.loader.Loader
import org.ghrobotics.lib.commands.FalconCommand

class ManualLoadCommand : FalconCommand(Loader) {

    private var skip = false
    override fun execute() {
        if(Loader.isLoaded()) {
            Loader.load(.15)
        } else if(!Loader.isLoaded()) {
            skip = true
            Loader.runHopperMotor(.15)
            Loader.runMainMotor(.15)
        } else if(Loader.isLoaded()) {
            Loader.toIdle()
        }
    }

    override fun cancel() {
        Loader.toIdle()
        super.cancel()
    }

    override fun end(interrupted: Boolean) {
        Loader.toIdle()
    }
}