package org.frc1778.robot

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab
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

    val debugTab2: ShuffleboardTab = Shuffleboard.getTab("TeleOp Info")

    object Shooter {
        const val SHOOTER_FLYWHEEL_MASTER = 19
        const val SHOOTER_FLYWHEEL_SLAVE = 0
        const val ANGLE_ADJUSTMENT = 21
        const val TARGET_HEIGHT = 1.905
        const val ANGLE_ADJUSTMENT_MIN = 0.0
        const val ANGLE_ADJUSTMENT_MAX = 0.0
        const val ASSUMED_DRAG_ACCEL = -.5 // in Meters per Second
        const val ASSUMED_GRAVITATION_ACCEL = -9.8 + ASSUMED_DRAG_ACCEL
        val ANGLE_NATIVE_ROTATION_MODEL = NativeUnitRotationModel(2048.nativeUnits)

        val NATIVE_SHOOTER_WHEEL_LENGTH_MODEL = NativeUnitLengthModel(1365.3.nativeUnits, 4.inches)
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