package org.frc1778.robot

import org.ghrobotics.lib.mathematics.units.SIUnit
import org.ghrobotics.lib.mathematics.units.inches
import org.ghrobotics.lib.mathematics.units.nativeunit.NativeUnitLengthModel
import org.ghrobotics.lib.mathematics.units.nativeunit.NativeUnitRotationModel
import org.ghrobotics.lib.mathematics.units.nativeunit.nativeUnits
import org.ghrobotics.lib.subsystems.FalconSwerveModule.SwerveModuleConstants

/*
 * The Constants file provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This file should not be used for any other purpose.
 * All String, Boolean, and numeric (Int, Long, Float, Double) constants should use
 * `const` definitions. Other constant types should use `val` definitions.
 */

/** An example constant definition. */
object Constants {
    object Drive {
        const val trackwidth = 0.0
        const val wheelBase = 0.0

        val kFrontLeftSwerveConstants = SwerveModuleConstants().also {
            with(it) {
                kName = "Name"
                kDriveTalonId = -1
                kAzimuthTalonId = -1

                // general azimuth
                kInvertAzimuth = false
                kInvertAzimuthSensorPhase = false
                kAzimuthBrakeMode = true // neutral mode could change
                kAzimuthNativeUnitModel = NativeUnitRotationModel(2048.nativeUnits)
                kAzimuthEncoderHomeOffset = 0.0

                // azimuth motion
                kAzimuthKp = 1.3
                kAzimuthKi = 0.05
                kAzimuthKd = 20.0
                kAzimuthKf = 0.5421
                kAzimuthIZone = 25
                kAzimuthCruiseVelocity = SIUnit(2.6) // 1698 native units
                kAzimuthAcceleration = SIUnit(31.26) // 20379 Native Units | 12 * kAzimuthCruiseVelocity
                kAzimuthClosedLoopAllowableError = 5

                // azimuth current/voltage
                kAzimuthContinuousCurrentLimit = 30 // amps
                kAzimuthPeakCurrentLimit = 60 // amps
                kAzimuthPeakCurrentDuration = 200 // ms
                kAzimuthEnableCurrentLimit = true
                kAzimuthMaxVoltage = 10.0 // volts
                kAzimuthVoltageMeasurementFilter = 8 // # of samples in rolling average

                // azimuth measurement
                kAzimuthStatusFrame2UpdateRate = 10 // feedback for selected sensor, ms
                kAzimuthStatusFrame10UpdateRate = 10 // motion magic, ms// dt for velocity measurements, ms
                kAzimuthVelocityMeasurementWindow = 64 // # of samples in rolling average

                // general drive
                kInvertDrive = true
                kInvertDriveSensorPhase = false
                kDriveBrakeMode = true // neutral mode could change
                kWheelDiameter = 4.0 // Probably should tune for each individual wheel maybe
                kDriveNativeUnitModel = NativeUnitLengthModel(4096.nativeUnits, kWheelDiameter.inches)
                kDriveDeadband = 0.01

                // drive current/voltage
                kDriveContinuousCurrentLimit = 30 // amps
                kDrivePeakCurrentLimit = 50 // amps
                kDrivePeakCurrentDuration = 200 // ms
                kDriveEnableCurrentLimit = true
                kDriveMaxVoltage = 10.0 // volts
                kDriveVoltageMeasurementFilter = 8 // # of samples in rolling average

                // drive measurement
                kDriveStatusFrame2UpdateRate = 15 // feedback for selected sensor, ms
                kDriveStatusFrame10UpdateRate = 200 // motion magic, ms// dt for velocity measurements, ms
                kDriveVelocityMeasurementWindow = 64 // # of samples in rolling average
            }
        }

        val kFrontRightSwerveConstants = SwerveModuleConstants().also {
            with(it) {
                kName = "Name"
                kDriveTalonId = -1
                kAzimuthTalonId = -1

                // general azimuth
                kInvertAzimuth = false
                kInvertAzimuthSensorPhase = false
                kAzimuthBrakeMode = true // neutral mode could change
                kAzimuthNativeUnitModel = NativeUnitRotationModel(2048.nativeUnits)
                kAzimuthEncoderHomeOffset = 0.0

                // azimuth motion
                kAzimuthKp = 1.3
                kAzimuthKi = 0.05
                kAzimuthKd = 20.0
                kAzimuthKf = 0.5421
                kAzimuthIZone = 25
                kAzimuthCruiseVelocity = SIUnit(2.6) // 1698 native units
                kAzimuthAcceleration = SIUnit(31.26) // 20379 Native Units | 12 * kAzimuthCruiseVelocity
                kAzimuthClosedLoopAllowableError = 5

                // azimuth current/voltage
                kAzimuthContinuousCurrentLimit = 30 // amps
                kAzimuthPeakCurrentLimit = 60 // amps
                kAzimuthPeakCurrentDuration = 200 // ms
                kAzimuthEnableCurrentLimit = true
                kAzimuthMaxVoltage = 10.0 // volts
                kAzimuthVoltageMeasurementFilter = 8 // # of samples in rolling average

                // azimuth measurement
                kAzimuthStatusFrame2UpdateRate = 10 // feedback for selected sensor, ms
                kAzimuthStatusFrame10UpdateRate = 10 // motion magic, ms// dt for velocity measurements, ms
                kAzimuthVelocityMeasurementWindow = 64 // # of samples in rolling average

                // general drive
                kInvertDrive = true
                kInvertDriveSensorPhase = false
                kDriveBrakeMode = true // neutral mode could change
                kWheelDiameter = 4.0 // Probably should tune for each individual wheel maybe
                kDriveNativeUnitModel = NativeUnitLengthModel(4096.nativeUnits, kWheelDiameter.inches)
                kDriveDeadband = 0.01

                // drive current/voltage
                kDriveContinuousCurrentLimit = 30 // amps
                kDrivePeakCurrentLimit = 50 // amps
                kDrivePeakCurrentDuration = 200 // ms
                kDriveEnableCurrentLimit = true
                kDriveMaxVoltage = 10.0 // volts
                kDriveVoltageMeasurementFilter = 8 // # of samples in rolling average

                // drive measurement
                kDriveStatusFrame2UpdateRate = 15 // feedback for selected sensor, ms
                kDriveStatusFrame10UpdateRate = 200 // motion magic, ms// dt for velocity measurements, ms
                kDriveVelocityMeasurementWindow = 64 // # of samples in rolling average
            }
        }

        val kBackRightSwerveConstants = SwerveModuleConstants().also {
            with(it) {
                kName = "Name"
                kDriveTalonId = -1
                kAzimuthTalonId = -1

                // general azimuth
                kInvertAzimuth = false
                kInvertAzimuthSensorPhase = false
                kAzimuthBrakeMode = true // neutral mode could change
                kAzimuthNativeUnitModel = NativeUnitRotationModel(2048.nativeUnits)
                kAzimuthEncoderHomeOffset = 0.0

                // azimuth motion
                kAzimuthKp = 1.3
                kAzimuthKi = 0.05
                kAzimuthKd = 20.0
                kAzimuthKf = 0.5421
                kAzimuthIZone = 25
                kAzimuthCruiseVelocity = SIUnit(2.6) // 1698 native units
                kAzimuthAcceleration = SIUnit(31.26) // 20379 Native Units | 12 * kAzimuthCruiseVelocity
                kAzimuthClosedLoopAllowableError = 5

                // azimuth current/voltage
                kAzimuthContinuousCurrentLimit = 30 // amps
                kAzimuthPeakCurrentLimit = 60 // amps
                kAzimuthPeakCurrentDuration = 200 // ms
                kAzimuthEnableCurrentLimit = true
                kAzimuthMaxVoltage = 10.0 // volts
                kAzimuthVoltageMeasurementFilter = 8 // # of samples in rolling average

                // azimuth measurement
                kAzimuthStatusFrame2UpdateRate = 10 // feedback for selected sensor, ms
                kAzimuthStatusFrame10UpdateRate = 10 // motion magic, ms// dt for velocity measurements, ms
                kAzimuthVelocityMeasurementWindow = 64 // # of samples in rolling average

                // general drive
                kInvertDrive = true
                kInvertDriveSensorPhase = false
                kDriveBrakeMode = true // neutral mode could change
                kWheelDiameter = 4.0 // Probably should tune for each individual wheel maybe
                kDriveNativeUnitModel = NativeUnitLengthModel(4096.nativeUnits, kWheelDiameter.inches)
                kDriveDeadband = 0.01

                // drive current/voltage
                kDriveContinuousCurrentLimit = 30 // amps
                kDrivePeakCurrentLimit = 50 // amps
                kDrivePeakCurrentDuration = 200 // ms
                kDriveEnableCurrentLimit = true
                kDriveMaxVoltage = 10.0 // volts
                kDriveVoltageMeasurementFilter = 8 // # of samples in rolling average

                // drive measurement
                kDriveStatusFrame2UpdateRate = 15 // feedback for selected sensor, ms
                kDriveStatusFrame10UpdateRate = 200 // motion magic, ms// dt for velocity measurements, ms
                kDriveVelocityMeasurementWindow = 64 // # of samples in rolling average
            }
        }
        val kBackLeftSwerveConstants = SwerveModuleConstants().also {
            with(it) {
                kName = "Name"
                kDriveTalonId = -1
                kAzimuthTalonId = -1

                // general azimuth
                kInvertAzimuth = false
                kInvertAzimuthSensorPhase = false
                kAzimuthBrakeMode = true // neutral mode could change
                kAzimuthNativeUnitModel = NativeUnitRotationModel(2048.nativeUnits)
                kAzimuthEncoderHomeOffset = 0.0

                // azimuth motion
                kAzimuthKp = 1.3
                kAzimuthKi = 0.05
                kAzimuthKd = 20.0
                kAzimuthKf = 0.5421
                kAzimuthIZone = 25
                kAzimuthCruiseVelocity = SIUnit(2.6) // 1698 native units
                kAzimuthAcceleration = SIUnit(31.26) // 20379 Native Units | 12 * kAzimuthCruiseVelocity
                kAzimuthClosedLoopAllowableError = 5

                // azimuth current/voltage
                kAzimuthContinuousCurrentLimit = 30 // amps
                kAzimuthPeakCurrentLimit = 60 // amps
                kAzimuthPeakCurrentDuration = 200 // ms
                kAzimuthEnableCurrentLimit = true
                kAzimuthMaxVoltage = 10.0 // volts
                kAzimuthVoltageMeasurementFilter = 8 // # of samples in rolling average

                // azimuth measurement
                kAzimuthStatusFrame2UpdateRate = 10 // feedback for selected sensor, ms
                kAzimuthStatusFrame10UpdateRate = 10 // motion magic, ms// dt for velocity measurements, ms
                kAzimuthVelocityMeasurementWindow = 64 // # of samples in rolling average

                // general drive
                kInvertDrive = true
                kInvertDriveSensorPhase = false
                kDriveBrakeMode = true // neutral mode could change
                kWheelDiameter = 4.0 // Probably should tune for each individual wheel maybe
                kDriveNativeUnitModel = NativeUnitLengthModel(4096.nativeUnits, kWheelDiameter.inches)
                kDriveDeadband = 0.01

                // drive current/voltage
                kDriveContinuousCurrentLimit = 30 // amps
                kDrivePeakCurrentLimit = 50 // amps
                kDrivePeakCurrentDuration = 200 // ms
                kDriveEnableCurrentLimit = true
                kDriveMaxVoltage = 10.0 // volts
                kDriveVoltageMeasurementFilter = 8 // # of samples in rolling average

                // drive measurement
                kDriveStatusFrame2UpdateRate = 15 // feedback for selected sensor, ms
                kDriveStatusFrame10UpdateRate = 200 // motion magic, ms// dt for velocity measurements, ms
                kDriveVelocityMeasurementWindow = 64 // # of samples in rolling average
            }
        }
    }
}
