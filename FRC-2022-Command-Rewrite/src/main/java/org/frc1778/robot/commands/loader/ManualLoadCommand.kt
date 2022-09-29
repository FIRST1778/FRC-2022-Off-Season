package org.frc1778.robot.commands.loader

import org.frc1778.robot.subsystems.loader.Loader
import org.ghrobotics.lib.commands.FalconCommand

class ManualLoadCommand : FalconCommand(Loader) {
    override fun execute() {
        Loader.load(.15)
    }

    override fun cancel() {
        Loader.load(0.0)
        super.cancel()
    }

    override fun end(interrupted: Boolean) {
        Loader.load(0.0)
    }
}