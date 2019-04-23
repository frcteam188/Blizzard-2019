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
import frc.robot.subsystems.BaseEncPID;
import frc.robot.subsystems.BaseGyroPID;

public class DriveTowardsTarget extends Command {

  BaseEncPID encPID;
  BaseGyroPID gyroPID;
  double setpoint;
  double angle;
  int onTargetCount;
  int onTargetThreshold;
  boolean absoluteAngle;
  long lastUpdateTime;

  public DriveTowardsTarget(double setpoint, double angle, boolean absoluteAngle, int onTargetThreshold) {
    requires(Robot.base);
    this.setpoint = setpoint;
    this.angle = angle;
    this.absoluteAngle = absoluteAngle;
    this.onTargetThreshold = onTargetThreshold;
    encPID = new BaseEncPID(Constants.baseEncHighPID[0], Constants.baseEncHighPID[1], Constants.baseEncHighPID[2],
                            0, Constants.kBaseEncHighPIDPower, true);
    gyroPID = new BaseGyroPID(Constants.baseGyroCorrectionPID[0], Constants.baseGyroCorrectionPID[1],
                              Constants.baseGyroCorrectionPID[2], 0, Constants.kGyroCorrectionPower, true);
  }
  
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.base.setOpenLoopRampRate(Constants.kBaseEncPIDRampRate);
    onTargetCount = 0;
    encPID.setSetpoint(setpoint + Robot.base.getLeftEnc());
    gyroPID.setSetpoint(angle + (absoluteAngle ? 0 : Robot.base.getAngle()));
    encPID.enable();
    gyroPID.enable();
    lastUpdateTime = 0;
    System.out.println("DriveTowardsTarget started.");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    long currentTime = System.currentTimeMillis();
    if (Robot.limelight.hasTarget() && currentTime - lastUpdateTime >= 10)
    {
      if (lastUpdateTime <= 0) System.out.println("Target acquired.");
      gyroPID.setSetpoint(Robot.base.getAngle() + Robot.limelight.getAngle());
      lastUpdateTime = currentTime;
    }
    Robot.base.driveStored();
    if (encPID.onTarget() && gyroPID.onTarget()) onTargetCount++;
    else onTargetCount = 0;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return onTargetCount >= onTargetThreshold || Robot.limelight.hasTarget() && Robot.limelight.getArea() >= Constants.kTargetAreaThreshold;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    encPID.disable();
    gyroPID.disable();
    Robot.base.stop();
    System.out.println("DriveTowardsTarget ended.");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
