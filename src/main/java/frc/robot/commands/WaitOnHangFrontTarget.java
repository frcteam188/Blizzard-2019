/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;

public class WaitOnHangFrontTarget extends Command {
  int iterationTheshold;
  int iterations;
  int target;

  public WaitOnHangFrontTarget(int target)
  {
    this(target, 5);
  }

  public WaitOnHangFrontTarget(int target, int iterationTheshold) {
    this.target = target;
    this.iterationTheshold = iterationTheshold;    
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    iterations = 0;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (Math.abs(target - Robot.hangFront.getEnc()) <= Constants.kFrontHangTolerance)
      ++iterations;
    else
      iterations = 0;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return iterations >= iterationTheshold;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
