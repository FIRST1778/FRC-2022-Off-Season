package org.frc1778.robot.subsystems.drive

import com.kauailabs.navx.frc.AHRS
import edu.wpi.first.math.controller.RamseteController
import edu.wpi.first.math.controller.SimpleMotorFeedforward
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.geometry.Translation2d
import edu.wpi.first.math.kinematics.SwerveDriveKinematics
import edu.wpi.first.math.kinematics.SwerveDriveOdometry
import org.frc1778.robot.Constants
import org.frc1778.robot.subsystems.drive.commands.TeleopDriveCommand
import org.ghrobotics.lib.subsystems.FalconSwerveModule
import org.ghrobotics.lib.subsystems.drive.FalconSwerveDrivetrain
import org.ghrobotics.lib.utils.Source

object Drive : FalconSwerveDrivetrain() {
    override val trackwidth: Double = Constants.Drive.trackwidth

    override val wheelbase: Double = Constants.Drive.wheelBase

    private val navx: AHRS = AHRS()

    override val controller: RamseteController = RamseteController(2.0, 0.7)

    override val gyro: Source<Rotation2d> = { navx.rotation2d }

    override val kinematics: SwerveDriveKinematics = SwerveDriveKinematics(
        Translation2d(-trackwidth / 2, wheelbase / 2),
        Translation2d(trackwidth / 2, wheelbase / 2),
        Translation2d(trackwidth / 2, -wheelbase / 2),
        Translation2d(-trackwidth / 2, -wheelbase / 2)
    )

    override val modules: Array<FalconSwerveModule> = Array(4) { FalconSwerveModule(Constants.Drive.kFrontLeftSwerveConstants) }

    override val odometry: SwerveDriveOdometry = SwerveDriveOdometry(kinematics, gyro())

    override val leftBackCharacterization: SimpleMotorFeedforward = SimpleMotorFeedforward(0.0, 0.0, 0.0)

    override val leftFrontCharacterization: SimpleMotorFeedforward = SimpleMotorFeedforward(0.0, 0.0, 0.0)

    override val rightBackCharacterization: SimpleMotorFeedforward = SimpleMotorFeedforward(0.0, 0.0, 0.0)

    override val rightFrontCharacterization: SimpleMotorFeedforward = SimpleMotorFeedforward(0.0, 0.0, 0.0)

    init {
        modules[0] = FalconSwerveModule(Constants.Drive.kFrontLeftSwerveConstants)
        modules[1] = FalconSwerveModule(Constants.Drive.kFrontRightSwerveConstants)
        modules[2] = FalconSwerveModule(Constants.Drive.kBackRightSwerveConstants)
        modules[3] = FalconSwerveModule(Constants.Drive.kBackLeftSwerveConstants)

        defaultCommand = TeleopDriveCommand()
    }

    override fun disableClosedLoopControl() {}

    override fun enableClosedLoopControl() {}
}
