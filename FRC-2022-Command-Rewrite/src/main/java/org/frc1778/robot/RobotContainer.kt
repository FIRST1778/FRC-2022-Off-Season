package org.frc1778.robot

import com.pathplanner.lib.PathPlanner
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.InstantCommand
import org.frc1778.robot.Robot.unaryPlus
import org.frc1778.robot.commands.RunIntake
import org.frc1778.robot.commands.drive.Aim
import org.frc1778.robot.commands.drive.TurnToAngle
import org.frc1778.robot.commands.loader.ManualLoadCommand
import org.frc1778.robot.commands.shooter.Shoot
import org.frc1778.robot.subsystems.climber.Climber
import org.frc1778.robot.subsystems.collector.Collector
import org.frc1778.robot.subsystems.drive.Drive
import org.frc1778.robot.subsystems.loader.Loader
import org.frc1778.robot.subsystems.shooter.Shooter
import org.frc1778.util.pathing.FastLine
import org.frc1778.util.pathing.Follow
import org.frc1778.util.pathing.Line
import org.frc1778.util.pathing.Path
import org.frc1778.util.pathing.PathCommand
import org.frc1778.util.pathing.Turn
import org.frc1778.util.pathing.events.*
import org.ghrobotics.lib.mathematics.units.derived.degrees
import org.ghrobotics.lib.mathematics.units.inches
import org.ghrobotics.lib.utils.Source

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the [Robot]
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
object RobotContainer {
    val autoPath1 = {
        PathCommand(Path().apply {
            add(CollectorDown)
            add(Wait(.3))
            add(CollectorOn)
            add(Follow(PathPlanner.loadPath("4 Ball First", 8.0, 2.5)))
            add(Turn((-218).degrees))
            add(Stop)
            add(Wait(.05))
            add(Aim)
            add(Stop)

            //Shoot
            add(CollectorOff)
            add(ShooterOn)
            add(Wait(.95))
            add(LoaderOn)
            add(Wait(.1))
            add(LoaderOff)
            add(Wait(.1))
            add(CollectorOn)
            add(Wait(.15))
            add(LoaderOn)
            add(Wait(.2))
            add(LoaderOff)
            add(ShooterOff)

            add(Follow(PathPlanner.loadPath("4 Ball Second", 8.0, 2.0)))


//            add(FastLine(105.inches.value, (-93).degrees))
//            add(Stop)
//
//            add(FastLine(142.inches.value, (-49).degrees))
//            add(Stop)
//
//            add(FastLine(120.inches.value, 170.degrees))
//            add(Stop)

            add(Wait(.15))
            add(Aim)
            add(Stop)

            //Shoot
            add(CollectorOff)
            add(ShooterOn)
            add(Wait(.95))
            add(LoaderOn)
            add(Wait(.1))
            add(LoaderOff)
            add(Wait(.1))
            add(CollectorOn)
            add(Wait(.15))
            add(LoaderOn)
            add(Wait(.2))
            add(LoaderOff)
            add(ShooterOff)


        })
    }

    val autoPath2 = {
        PathCommand(Path().apply {

            add(CollectorDown)
            add(Wait(.3))
            add(CollectorOn)
            add(Follow(PathPlanner.loadPath("Standard 2 Ball", 8.0, 2.5)))
            add(Turn(190.degrees))
            add(Stop)
            add(Aim)
            add(Stop)

            //Shoot
            add(CollectorOff)
            add(ShooterOn)
            add(Wait(.95))
            add(LoaderOn)
            add(Wait(.1))
            add(LoaderOff)
            add(Wait(.1))
            add(CollectorOn)
            add(Wait(.15))
            add(LoaderOn)
            add(Wait(.2))
            add(LoaderOff)
            add(ShooterOff)
            add(CollectorOff)

        })
    }

    val autoPath4 = {
        PathCommand(Path().apply
        {
            add(CollectorDown)
            add(Wait(.3))
            add(CollectorOn)
            add(Follow(PathPlanner.loadPath("Short 2 Ball", 8.0, 2.5)))
            add(Turn((-210).degrees))
            add(Stop)
            add(Aim)
            add(Stop)

            //Shoot
            add(CollectorOff)
            add(ShooterOn)
            add(Wait(.95))
            add(LoaderOn)
            add(Wait(.1))
            add(LoaderOff)
            add(Wait(.1))
            add(CollectorOn)
            add(Wait(.15))
            add(LoaderOn)
            add(Wait(.2))
            add(LoaderOff)
            add(ShooterOff)
            add(CollectorOff)
        })
    }

    val autoPath3 = {
        PathCommand(Path().apply
        {
            add(Line(-50.inches.value, 0.degrees))
            add(Stop)
            add(Wait(.25))

            add(Aim)
            add(Stop)

            add(Wait(.35))
            add(ShooterOn)
            add(Wait(1.25))
            add(LoaderOn)
            add(Wait(.45))
            add(LoaderOff)
            add(ShooterOff)
            add(Stop)
        })
    }

    val autoPath5 = {
        PathCommand(Path().apply
        {
            add(CollectorDown)
            add(Wait(.3))
            add(CollectorOn)
            add(Follow(PathPlanner.loadPath("3 Ball First", 8.0, 2.5)))
            add(Turn((-218).degrees))
            add(Stop)
            add(Wait(.05))
            add(Aim)
            add(Stop)

            //Shoot
            add(CollectorOff)
            add(ShooterOn)
            add(Wait(.95))
            add(LoaderOn)
            add(Wait(.1))
            add(LoaderOff)
            add(Wait(.1))
            add(CollectorOn)
            add(Wait(.15))
            add(LoaderOn)
            add(Wait(.2))
            add(LoaderOff)
            add(ShooterOff)

//            add(Line(105.inches.value, (-93).degrees))
            add(Follow(PathPlanner.loadPath("3 Ball Second", 8.0, 1.25)))
            add(Turn((115).degrees))
            add(Stop)

            add(Aim)
            add(Stop)

            //Shoot
            add(CollectorOff)
            add(ShooterOn)
            add(Wait(.95))
            add(LoaderOn)
            add(Wait(.1))
            add(LoaderOff)
            add(Wait(.15))
            add(ShooterOff)
        })
    }


    val autoPath6 = {
        PathCommand(Path().apply
        {
            add(CollectorDown)
            add(Wait(.3))
            add(CollectorOn)
            add(Line(43.inches.value, 0.degrees))
            add(Turn((-196).degrees))
            add(Stop)
//            add(Wait(.15))
//            add(Aim)
//            add(Stop)

            //Shoot
            add(CollectorOff)
            add(ShooterOn)
            add(Wait(.95))
            add(LoaderOn)
            add(Wait(.1))
            add(LoaderOff)
            add(Wait(.1))
            add(CollectorOn)
            add(Wait(.15))
            add(LoaderOn)
            add(Wait(.2))
            add(LoaderOff)
            add(ShooterOff)
//
            add(Line(105.inches.value, (-82).degrees))
            add(Turn((115).degrees))
            add(Stop)

            add(Aim)
            add(Stop)
//
//            //Shoot
            add(CollectorOff)
            add(ShooterOn)
            add(Wait(.95))
            add(LoaderOn)
            add(Wait(.1))
            add(LoaderOff)
            add(Wait(.15))
            add(ShooterOff)
        })
    }

    val autoPath7 = {
        PathCommand(Path().apply
        {
            add(CollectorDown)
            add(Wait(.3))
            add(CollectorOn)
            add(Line(43.inches.value, 0.degrees))
            add(Turn((-187).degrees))
            add(Stop)

            //Shoot
            add(CollectorOff)
            add(ShooterOn)
            add(Wait(.7))
            add(LoaderOn)
            add(Wait(.1))
            add(LoaderOff)
            add(Wait(.1))
            add(CollectorOn)
            add(Wait(.15))
            add(LoaderOn)
            add(Wait(.2))
            add(LoaderOff)
            add(ShooterOff)


            add(FastLine(95.inches.value, (-73).degrees))
            add(Stop)
            add(Wait(.275))

            add(FastLine(132.inches.value, (-35).degrees))
            add(Stop)
            add(Wait(.275))

//            add(FastLine(120.inches.value, 170.degrees))
//            add(Stop)
//
//            add(Wait(.15))
//            add(Aim)
//            add(Stop)
//
//            //Shoot
//            add(CollectorOff)
//            add(ShooterOn)
//            add(Wait(.95))
//            add(LoaderOn)
//            add(Wait(.1))
//            add(LoaderOff)
//            add(Wait(.1))
//            add(CollectorOn)
//            add(Wait(.15))
//            add(LoaderOn)
//            add(Wait(.2))
//            add(LoaderOff)
//            add(ShooterOff)
        })
    }

    val testAutoPath = {
        PathCommand(Path().apply
        {
            add(Turn(90.degrees))
        })
    }

    val driveForward = {
        Drive.followTrajectory(PathPlanner.loadPath("Forward", 8.0, 1.25, true), first=true)
            .andThen(Aim().alongWith(Shoot()).withTimeout(.375))
            .andThen(Shoot().alongWith(ManualLoadCommand()).withTimeout(.5))
    }

    val testAutoCommand = {
        Drive.followTrajectory(PathPlanner.loadPath("Short 2 Ball", 5.0, 2.5), first=true)
            .deadlineWith(RunIntake())
            .andThen(TurnToAngle(-100.0))
            .andThen(
                Aim().alongWith(Shoot()).withTimeout(.375)
                    .andThen(Shoot().alongWith(ManualLoadCommand()))
            )
    }



    private val autoModeChooser = SendableChooser<AutoMode>().apply {
        AutoMode.values().forEach { addOption(it.optionName, it) }
        setDefaultOption(AutoMode.default.optionName, AutoMode.default)
    }

    /**
     * A enumeration of the available autonomous modes.
     *
     * @param optionName The name for the [autoModeChooser] option.
     * @param command The [Command] to run for this mode.
     */
    private enum class AutoMode(val optionName: String, val command: Source<Command>) {
        CUSTOM_AUTO_1("4 Ball? Red", autoPath1),
        CUSTOM_AUTO_8("4 Ball? Blue", autoPath7),
        CUSTOM_AUTO_6("3 Ball Red", autoPath5),
        CUSTOM_AUTO_7("3 Ball Blue", autoPath6),
        CUSTOM_AUTO_2("Standard 2 Ball", autoPath2),
        CUSTOM_AUTO_5("Short 2 Ball", autoPath4),
        CUSTOM_AUTO_3("1 Ball", autoPath3),
        CUSTOM_AUTO_4("Auto Command Test: Short 2 Ball", testAutoCommand),
        CUSTOM_AUTO_9("Drive Back And Shoot", driveForward)
        ;

        companion object {
            /** The default auto mode. */
            val default = CUSTOM_AUTO_1
        }
    }

    /** The command to run in autonomous. */
    val selectedAutonomousCommand: Command
        get() = autoModeChooser.selected?.command?.let { it() } ?: AutoMode.default.command()


    init {
        Constants.Shooter.ShuffleBoard
        +Shooter
        +Drive
        +Climber
        +Collector
        +Loader
        configureButtonBindings()
        SmartDashboard.putData("Auto choices", autoModeChooser)
    }


    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a [GenericHID][edu.wpi.first.wpilibj.GenericHID] or one of its subclasses such
     * as [Joystick][edu.wpi.first.wpilibj.Joystick] or [XboxController][edu.wpi.first.wpilibj.XboxController],
     * and then passing it to a [JoystickButton][edu.wpi.first.wpilibj2.command.button.JoystickButton].
     */
    private fun configureButtonBindings() {
        // TODO: Add button to command mappings here.
        //       See https://docs.wpilib.org/en/stable/docs/software/commandbased/binding-commands-to-triggers.html
    }
}