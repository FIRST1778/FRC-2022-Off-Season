package org.frc1778.robot

import edu.wpi.first.networktables.NetworkTableEntry
import edu.wpi.first.wpilibj.shuffleboard.*
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

    val debugTab2: ShuffleboardTab = Shuffleboard.getTab("TeleOp Info")
    val shooterPIDTuningTab: ShuffleboardLayout = Shuffleboard.getTab("Shooter PID Tuning")
        .getLayout("PID Params", BuiltInLayouts.kGrid)
    val shooterTuningTab: ShuffleboardLayout = Shuffleboard.getTab("Shooter Tuning")
        .getLayout("", BuiltInLayouts.kGrid)


    object Shooter {
        const val SHOOTER_FLYWHEEL_MASTER = 21
        const val SHOOTER_FLYWHEEL_SLAVE = 19
        const val ANGLE_ADJUSTMENT = 31

        const val SHOOTER_VELOCITY_MULTIPLIER = 1.2

        const val SHOOTER_VELOCITY_MAX = 45.0
        const val SHOOTER_VELOCITY_MIN = 5.75
        const val TARGET_HEIGHT = 1.905
        const val ANGLE_ADJUSTMENT_MIN = 20.0
        const val ANGLE_ADJUSTMENT_MAX = 40.0
        const val ASSUMED_DRAG_ACCEL = -.5 // in Meters per Second

        const val ASSUMED_GRAVITATION_ACCEL = -9.8 + ASSUMED_DRAG_ACCEL
        val ANGLE_NATIVE_ROTATION_MODEL = NativeUnitRotationModel((18).nativeUnits)

        val NATIVE_SHOOTER_WHEEL_LENGTH_MODEL = NativeUnitLengthModel(1639.3.nativeUnits, 4.inches)

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

            val currVelocity = shooterPIDTuningTab.add("Current Angle", 0.0).withPosition(5, 5).withSize(3, 3)
                .withWidget(BuiltInWidgets.kGraph).entry

            val voltageSet = shooterPIDTuningTab.add("Voltage Setpoint", 0.0).withPosition(0, 0).withSize(10, 10)
                .withWidget(BuiltInWidgets.kTextView).entry

            val velocityMultiplier: NetworkTableEntry =
                shooterTuningTab.add("Velocity Multiplier", 0.0).withPosition(0, 0).withSize(5, 5)
                    .withWidget(BuiltInWidgets.kTextView).entry

            val distanceTab = shooterTuningTab
                .add("Distance", 0.0)
                .withWidget(BuiltInWidgets.kTextView)
                .entry

            val expectedVelocity = shooterTuningTab
                .add("Expected Velocity", 0.0)
                .withWidget(BuiltInWidgets.kTextView)
                .entry

            val expectedAngle = shooterTuningTab
                .add("Expected Angle", 0.0)
                .withWidget(BuiltInWidgets.kTextView)
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
}