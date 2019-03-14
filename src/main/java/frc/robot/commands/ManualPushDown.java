/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

public class ManualPushDown extends Command {
  public ManualPushDown() {
    requires(Robot.hangFront);
    requires(Robot.hangBack);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.hangFront.drive(-OI.stick.getRawAxis(OI.pushDownMainAxis));
    Robot.hangBack.drivePushDown(-OI.stick.getRawAxis(OI.pushDownCorrectionAxis));
    Robot.hangBack.driveForward(OI.hangDriveForwardButton, OI.hangDriveBackButton);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.hangFront.stop();
    Robot.hangBack.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
