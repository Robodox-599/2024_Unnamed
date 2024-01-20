// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import frc.robot.Constants.ControllerConstants;
import frc.robot.Constants.FieldConstants;
import frc.robot.Constants.SwerveConstants;
import frc.robot.Constants.SwerveConstants.DRIVE_STATE;
import frc.robot.subsystems.subsystem_DriveTrain;

public class command_DriveTeleop extends Command {
  /** Creates a new command_DriveTeleop. */
  private subsystem_DriveTrain m_DriveTrain;
  private DoubleSupplier m_xSpeed;
  private DoubleSupplier m_ySpeed;
  private DoubleSupplier m_zRot;
  private BooleanSupplier m_IsOrientFront;
  private BooleanSupplier m_IsOrientBack;
  private BooleanSupplier m_FieldRelative;
  private BooleanSupplier m_OpenLoop;

  public command_DriveTeleop(subsystem_DriveTrain driveTrain,
                            DoubleSupplier xSpeed,
                            DoubleSupplier ySpeed,
                            DoubleSupplier zRot,
                            BooleanSupplier isOrientFront,
                            BooleanSupplier isOrientBack,
                            BooleanSupplier fieldRelative,
                            BooleanSupplier openLoop) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_DriveTrain = driveTrain;
    m_xSpeed = xSpeed;
    m_ySpeed = ySpeed;
    m_zRot = zRot;
    m_IsOrientFront = isOrientFront;
    m_IsOrientBack = isOrientBack;
    m_FieldRelative = fieldRelative;
    m_OpenLoop = openLoop;
    addRequirements(m_DriveTrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double transformedXSpeed = 0.0;
    double transformedYSpeed = 0.0;
    double transformedZRot = 0.0;

    if(Math.abs(m_xSpeed.getAsDouble()) > ControllerConstants.deadband){
      transformedXSpeed = m_DriveTrain.setThrottle(m_xSpeed.getAsDouble());
    }
    if(Math.abs(m_ySpeed.getAsDouble()) > ControllerConstants.deadband){
      transformedYSpeed = m_DriveTrain.setThrottle(m_ySpeed.getAsDouble());
    }
    if(Math.abs(m_zRot.getAsDouble()) > ControllerConstants.deadband){
      transformedZRot = m_DriveTrain.setThrottle(m_zRot.getAsDouble());
    }

    SmartDashboard.putNumber("X Speed", transformedXSpeed);
    SmartDashboard.putNumber("Y Speed", transformedYSpeed);
    SmartDashboard.putNumber("Z Rot", transformedZRot);

    if (m_DriveTrain.getDriveState() == DRIVE_STATE.SHOOTER_PREP){
      Pose2d drivePose = m_DriveTrain.getPose();
      double deltaX = drivePose.getX();
      double deltaY = drivePose.getY();

      Optional<Alliance> ally = DriverStation.getAlliance();
      if (ally.isPresent()) {
        if (ally.get() == Alliance.Red) {
          deltaX-=FieldConstants.redSpeakerCenter.getX();
          deltaY-=FieldConstants.redSpeakerCenter.getY();
        } else if (ally.get() == Alliance.Blue) {
          deltaX-=FieldConstants.blueSpeakerCenter.getX();
          deltaY-=FieldConstants.blueSpeakerCenter.getY();
        } else {
          SmartDashboard.putBoolean("Alliance Color Error", true);
        }
      m_DriveTrain.setDesiredAngle(-Math.atan(deltaY/deltaX)%360);
    } else {
      SmartDashboard.putBoolean("Alliance Color Error", true);
    }
  }

    m_DriveTrain.swerveDrive(transformedXSpeed * SwerveConstants.maxSpeed,
                            transformedYSpeed * SwerveConstants.maxSpeed,
                            transformedZRot * SwerveConstants.maxAngularVelocity,
                            m_FieldRelative.getAsBoolean(),
                            m_OpenLoop.getAsBoolean());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
