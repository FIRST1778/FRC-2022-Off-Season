package org.frc1778.robot.commands.shooter



import org.frc1778.robot.subsystems.collector.Collector
import org.frc1778.robot.subsystems.loader.Loader
import org.frc1778.robot.subsystems.shooter.Shooter
import org.ghrobotics.lib.commands.FalconCommand

class Shoot : FalconCommand(Shooter, Loader, Collector){

}