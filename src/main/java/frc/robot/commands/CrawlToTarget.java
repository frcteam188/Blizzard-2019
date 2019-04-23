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
import frc.robot.subsystems.BaseGyroPID;

public class CrawlToTarget extends Command {

  BaseGyroPID pid;
  long lastUpdateTime;

  public CrawlToTarget() {
    requires(Robot.base);
    pid = new BaseGyroPID(Constants.baseGyroCorrectionPID[0], Constants.baseGyroCorrectionPID[1], Constants.baseGyroCorrectionPID[2], 0, Constants.kGyroCorrectionPower, Constants.kBaseCrawlPower, false);
    lastUpdateTime = 0;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.base.setOpenLoopRampRate(Constants.kBaseEncPIDRampRate);
    pid.setSetpoint(Robot.limelight.getAngle() + Robot.base.getAngle());
    lastUpdateTime = System.currentTimeMillis();
    pid.enable();
    System.out.println("CrawlToTarget started.");
  }
  
  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    long currentTime = System.currentTimeMillis();
    if (currentTime - lastUpdateTime >= 10)
    {
      pid.setSetpoint(Robot.limelight.getAngle() + Robot.base.getAngle());
      lastUpdateTime = System.currentTimeMillis();
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Robot.limelight.getArea() >= Constants.kTargetAreaThreshold;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    pid.disable();
    Robot.base.stop();
    System.out.println("CrawlToTarget ended.");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
