package org.frc1778.robot

import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.CommandScheduler
import edu.wpi.first.wpilibj2.command.PrintCommand
import org.frc1778.robot.subsystems.collector.Collector
import org.frc1778.robot.subsystems.loader.Loader
import org.frc1778.robot.subsystems.shooter.Shooter
import org.ghrobotics.lib.mathematics.units.derived.degrees
import org.ghrobotics.lib.wrappers.FalconTimedRobot

/**
 * The VM is configured to automatically run this object (which basically functions as a singleton class),
 * and to call the functions corresponding to each mode, as described in the TimedRobot documentation.
 * This is written as an object rather than a class since there should only ever be a single instance, and
 * it cannot take any constructor arguments. This makes it a natural fit to be an object in Kotlin.
 *
 * If you change the name of this object or its package after creating this project, you must also update
 * the `Main.kt` file in the project. (If you use the IDE's Rename or Move refactorings when renaming the
 * object or package, it will get changed everywhere.)
 */
object Robot : FalconTimedRobot()
{
    /** The autonomous command to run. It is set in [autonomousInit]. */
    private var autonomousCommand: Command?  = null

    /**
     * This method is run when the robot is first started up and should be used for any
     * initialization code.
     */
    init {
        +Collector
        +Loader
        +Shooter
    }

    override fun robotInit()
    {
        // Access the RobotContainer object so that it is initialized
        RobotContainer
    }

    /**
     * This method is called every robot packet, no matter the mode. Use this for items like
     * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
     *
     * This runs after the mode specific periodic methods, but before LiveWindow and
     * SmartDashboard integrated updating.
     */


    /** This method is called once each time the robot enters Disabled mode.  */
    override fun disabledInit()
    {
        Shooter.toIdle()
    }

    override fun disabledPeriodic()
    {
    }

    /** This autonomous runs the autonomous command selected by your [RobotContainer] class.  */
    override fun autonomousInit()
    {
        // We store the command as a Robot property in the rare event that the selector on the dashboard
        // is modified while the command is running since we need to access it again in teleopInit()
        autonomousCommand = RobotContainer.selectedAutonomousCommand
        autonomousCommand?.schedule()
    }

    /** This method is called periodically during autonomous.  */
    override fun autonomousPeriodic()
    {
    }

    override fun teleopInit()
    {
//        CommandScheduler.getInstance().onCommandExecute {
//            when(it) {
//                 !is PrintCommand -> PrintCommand("Command Executed: ${it.name}").schedule()
//                else -> {}
//            }
//        }
        // This makes sure that the autonomous stops running when teleop starts running. If you want the
        // autonomous to continue until interrupted by another command, remove this line or comment it out.
        autonomousCommand?.cancel()
        Shooter.angleAdjusterProfiledPID.run {
            reset(Shooter.shooterAngle)
            setGoal(20.0)
        }
        Shooter.angleEncoder.resetPosition(20.degrees)
    }

    /** This method is called periodically during operator control.  */
    override fun teleopPeriodic()
    {
        Controls.operatorController.update()
    }

    fun testInit()
    {
        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll()
    }

    /** This method is called periodically during test mode.  */
    fun testPeriodic()
    {

    }
}