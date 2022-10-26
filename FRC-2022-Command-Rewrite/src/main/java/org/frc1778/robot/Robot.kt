package org.frc1778.robot

import com.pathplanner.lib.PathPlanner
import edu.wpi.first.math.trajectory.Trajectory
import edu.wpi.first.wpilibj.DriverStation
import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj.util.Color
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.CommandScheduler
import org.frc1778.robot.commands.collector.ToggleCollector
import org.frc1778.robot.subsystems.climber.Climber
import org.frc1778.robot.subsystems.collector.Collector
import org.frc1778.robot.subsystems.drive.Drive
import org.frc1778.robot.subsystems.loader.Loader
import org.frc1778.robot.subsystems.shooter.Shooter
import org.frc1778.util.pathing.*
import org.frc1778.util.pathing.events.*
import org.ghrobotics.lib.mathematics.units.SIUnit
import org.ghrobotics.lib.mathematics.units.derived.degrees
import org.ghrobotics.lib.mathematics.units.derived.radians
import org.ghrobotics.lib.mathematics.units.inches
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

    lateinit var allianceColor: Color






    /**
     * This method is run when the robot is first started up and should be used for any
     * initialization code.
     */
    override fun robotInit()
    {
        RobotContainer //Access RobotContainer to initialize it
    }

    /**
     * This method is called every robot packet, no matter the mode. Use this for items like
     * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
     *
     * This runs after the mode specific periodic methods, but before LiveWindow and
     * SmartDashboard integrated updating.
     */
    override fun robotPeriodic() {}

    override fun autonomousInit() {
        autonomousCommand = RobotContainer.selectedAutonomousCommand
        allianceColor = if(DriverStation.getAlliance() == DriverStation.Alliance.Blue) Color.kBlue else Color.kRed
        Drive.Autonomous.auto = true
        Drive.resetEncoders()
        Climber.winchMotorRight.encoder.resetPosition(SIUnit(0.0))


        /* This autonomousInit function (along with the initAutoChooser function) shows how to select
        between different autonomous modes using the dashboard. The sendable chooser code works with the
        SmartDashboard. You can add additional auto modes by adding additional options to the AutoMode enum
        and then adding them to the `when` statement in the [autonomousPeriodic] function.

        If you prefer the LabVIEW Dashboard, remove all the chooser code and uncomment the following line: */
        //selectedAutoMode = AutoMode.valueOf(SmartDashboard.getString("Auto Selector", AutoMode.default.name))

    }

    /** This method is called periodically during autonomous.  */
    override fun autonomousPeriodic() {

    }




    /** This method is called once when teleop is enabled.  */
    override fun teleopInit() {
        Drive.Autonomous.auto = false
        Climber.winchMotorRight.encoder.resetPosition(SIUnit(0.0))
        Collector.deployMotor.setPosition(SIUnit(0.0))
        Shooter.angleAdjuster.encoder.resetPosition(0.0.radians)

    }

    /** This method is called periodically during operator control.  */
    override fun teleopPeriodic() {
        Controls.operatorController.update()
        Controls.driverController.update()
    }

    /** This method is called once when the robot is disabled.  */
    override fun disabledInit() {}

    /** This method is called periodically when disabled.  */
    override fun disabledPeriodic() {}

    /** This method is called once when test mode is enabled.  */
    fun testInit() {}

    /** This method is called periodically during test mode.  */
    fun testPeriodic() {}
}