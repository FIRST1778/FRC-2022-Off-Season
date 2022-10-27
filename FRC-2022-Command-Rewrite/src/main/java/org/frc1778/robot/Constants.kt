package org.frc1778.robot

import edu.wpi.first.networktables.NetworkTableEntry
import edu.wpi.first.wpilibj.shuffleboard.*
import org.ghrobotics.lib.mathematics.units.derived.degrees
import org.ghrobotics.lib.mathematics.units.feet
import org.ghrobotics.lib.mathematics.units.inches
import org.ghrobotics.lib.mathematics.units.nativeunit.NativeUnitLengthModel
import org.ghrobotics.lib.mathematics.units.nativeunit.NativeUnitRotationModel
import org.ghrobotics.lib.mathematics.units.nativeunit.nativeUnits

/*
 * The Constants file provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This file should not be used for any other purpose.
 * All String, Boolean, and numeric (Int, Long, Float, Double) constants should use
 * `const` definitions. Other constant types should use `val` definitions.
 */

object Constants {
    init {
        Shuffleboard.update()
        Shooter.ShuffleBoard //Init Shuffleboard Tabs
    }

    object Drive {
        /**
         * Drive Motor IDs
         * Master is Closer to the front of the robot
         * Left-Right is in reference from the rear of the robot
         */
        const val LEFT_MASTER_ID = 2
        const val LEFT_SLAVE_ID = 4
        const val RIGHT_MASTER_ID = 1
        const val RIGHT_SLAVE_ID = 3

        private val WHEEL_RADIUS = 3.inches
        val TRACK_WIDTH = 23.inches

        val speed = 4.feet //Speed in feet/sec
        val fastSpeed = 80.inches // Faster Speed in inches/sec
        val rotSpeed = 185.degrees // Rotation speed in degrees/sec

        val kP = 0.085
        val kI = 0.0
        val kD = 0.0
        val kF = 0.0045

        val NATIVE_UNIT_MODEL = NativeUnitLengthModel(17000.nativeUnits, WHEEL_RADIUS)
    }

    val debugTab2: ShuffleboardTab = Shuffleboard.getTab("TeleOp Info")
    val shooterPIDTuningTab: ShuffleboardTab = Shuffleboard.getTab("Shooter PID Tuning")
    val shooterTuningTab: ShuffleboardTab = Shuffleboard.getTab("Shooter Tuning")


    object Shooter {
        const val SHOOTER_FLYWHEEL_MASTER = 21
        const val ANGLE_ADJUSTMENT = 19

        const val ANGLE_ADJUSTMENT_MIN = 0.0
        const val ANGLE_ADJUSTMENT_MAX = 8.0

        val NATIVE_ROTATION_MODEL = NativeUnitRotationModel(2048.nativeUnits)

        object ShuffleBoard {
            val velocityTab: NetworkTableEntry = debugTab2.add("Angle", 0.0).withPosition(0, 0).withSize(3, 3)
                .withWidget(BuiltInWidgets.kTextView).entry

            val kFTab: NetworkTableEntry =
                shooterPIDTuningTab.add("kF", 0.0).withPosition(0, 0).withSize(3, 3)
                    .withWidget(BuiltInWidgets.kTextView).entry

            val kPTab: NetworkTableEntry =
                shooterPIDTuningTab.add("kP", 0.0).withPosition(0, 0).withSize(3, 3)
                    .withWidget(BuiltInWidgets.kTextView).entry

            val kITab: NetworkTableEntry =
                shooterPIDTuningTab.add("kI", 0.0).withPosition(0, 0).withSize(3, 3)
                    .withWidget(BuiltInWidgets.kTextView).entry

            val kDTab: NetworkTableEntry =
                shooterPIDTuningTab.add("kD", 0.0).withPosition(0, 0).withSize(3, 3)
                    .withWidget(BuiltInWidgets.kTextView).entry
            val velocitySetPoint = shooterPIDTuningTab
                .add("Velocity", 0.0)
                .entry


            val currAngle = shooterPIDTuningTab.add("Current Angle", 0.0).withPosition(5, 5).withSize(3, 3)
                .withWidget(BuiltInWidgets.kGraph).entry

            val voltageSet = shooterPIDTuningTab.add("Voltage Setpoint", 0.0).withPosition(0, 0).withSize(10, 10)
                .withWidget(BuiltInWidgets.kTextView).entry

            val velocityMultiplier: NetworkTableEntry =
                shooterTuningTab.add("Velocity Multiplier", 0.0).withPosition(0, 0).withSize(5, 5)
                    .withWidget(BuiltInWidgets.kTextView)
                    .withPosition(0,0)
                    .entry

            val distanceTab = shooterTuningTab
                .add("Distance", 0.0)
                .withWidget(BuiltInWidgets.kTextView)
                .withPosition(1,0)
                .entry

            val expectedVelocity = shooterTuningTab
                .add("Expected Velocity", 0.0)
                .withWidget(BuiltInWidgets.kTextView)
                .withPosition(0,1)
                .entry

            val expectedAngle = shooterTuningTab
                .add("Expected Angle", 0.0)
                .withWidget(BuiltInWidgets.kTextView)
                .withPosition(1,1)
                .entry

            val setVelocity = debugTab2
                .add("Set Velocity", 0.0)
                .withWidget(BuiltInWidgets.kTextView)
                .entry

            val currVelocity = debugTab2
                .add("Current Velocity", 0.0)
                .withWidget(BuiltInWidgets.kGraph)
                .entry

            val motorTemp = debugTab2
                .add("Motor Temp", 0.0)
                .entry

        }


    }

    object Loader {
        const val MAIN_WHEEL = 16
        const val LOADER_WHEEL = 40
        const val INTERMEDIATE_WHEEL = 30
    }

    object Collector {
        const val LEFT_MINI_MASTER = 22
        const val RIGHT_MINI_SLAVE = 33
        const val DEPLOY_MOTOR = 11

        val NATIVE_ROTATION_MODEL = NativeUnitRotationModel(2048.nativeUnits)
    }

    object Climber {
        const val CLIMBER_MOTOR_RIGHT = 42
        const val CLIMBER_MOTOR_LEFT = 31

        val NATIVE_ROTATION_MODEL = NativeUnitRotationModel(42.nativeUnits)

    }
}