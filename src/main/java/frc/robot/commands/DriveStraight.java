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

public class DriveStraight extends Command {

  BaseEncPID encPID;
  BaseGyroPID gyroPID;
  double setpoint;
  double angle;
  int onTargetCount;
  int onTargetThreshold;
  boolean absoluteAngle;
  double rampValue;
  double rampCutoff;
  double relativeRampCutoff;
  double initialEnc;

  public DriveStraight(double setpoint, double angle) {
    this(setpoint, angle, false);
  }

  public DriveStraight(double setpoint, double angle, boolean absoluteAngle)
  {
    this(setpoint, angle, absoluteAngle, 5, Constants.kBaseEncPIDRampRate, 0);
  }

  public DriveStraight(double setpoint, double angle, boolean absoluteAngle, int onTargetThreshold)
  {
    this(setpoint, angle, absoluteAngle, onTargetThreshold, Constants.kBaseEncPIDRampRate, 0);
  }

  public DriveStraight(double setpoint, double angle, boolean absoluteAngle, int onTargetThreshold, double rampValue, double rampCutoff)
  {
    requires(Robot.base);
    this.setpoint = setpoint;
    this.angle = angle;
    this.onTargetThreshold = onTargetThreshold;
    this.absoluteAngle = absoluteAngle;
    this.rampValue = rampValue;
    this.rampCutoff = rampCutoff;
    encPID = new BaseEncPID(Constants.baseEncHighPID[0], Constants.baseEncHighPID[1], Constants.baseEncHighPID[2],
                            0, Constants.kBaseEncHighPIDPower, true);
    gyroPID = new BaseGyroPID(Constants.baseGyroCorrectionPID[0], Constants.baseGyroCorrectionPID[1],
                              Constants.baseGyroCorrectionPID[2], 0, Constants.kGyroCorrectionPower, true);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    onTargetCount = 0;
    initialEnc = Robot.base.getLeftEnc();
    relativeRampCutoff = initialEnc + rampCutoff;
    double relativeSetpoint = setpoint + Robot.base.getLeftEnc();
    double relativeAngle;
    if (absoluteAngle) relativeAngle = angle;
    else relativeAngle = angle + Robot.base.getAngle();
    Robot.base.setOpenLoopRampRate(rampValue);
    encPID.setSetpoint(relativeSetpoint);
    gyroPID.setSetpoint(relativeAngle);
    encPID.enable();
    gyroPID.enable();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (rampCutoff > 0 && Robot.base.getLeftEnc() > relativeRampCutoff ||
        rampCutoff < 0 && Robot.base.getLeftEnc() < relativeRampCutoff)
    {
      Robot.base.setOpenLoopRampRate(Constants.kBaseEncPIDRampRate);
      rampCutoff = 0;
    }
    Robot.base.driveStored();
    if (encPID.onTarget() && gyroPID.onTarget()) onTargetCount++;
    else onTargetCount = 0;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return onTargetCount >= onTargetThreshold;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    encPID.disable();
    gyroPID.disable();
    Robot.base.stop();
    System.out.println("DriveStraight ended.");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
