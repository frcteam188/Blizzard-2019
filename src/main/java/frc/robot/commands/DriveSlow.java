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


public class DriveSlow extends Command {

  BaseEncPID encPID;
  BaseGyroPID gyroPID;
  double setpoint;
  double angle;
  int onTargetCount;
  int onTargetThreshold;

  public DriveSlow(double setpoint, double angle) {
    requires(Robot.base);
    this.setpoint = setpoint + Robot.base.getLeftEnc();
    this.angle = angle + Robot.base.getAngle();
    this.onTargetThreshold = 5;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    onTargetCount = 0;
    Robot.base.setOpenLoopRampRate(Constants.kBaseEncPIDRampRate);
    encPID = new BaseEncPID(Constants.baseEncLowPID[0], Constants.baseEncLowPID[1], Constants.baseEncLowPID[2],
                            setpoint, Constants.kBaseEncLowPIDPower, true);
    gyroPID = new BaseGyroPID(Constants.baseGyroCorrectionPID[0], Constants.baseGyroCorrectionPID[1],
                              Constants.baseGyroCorrectionPID[2], angle, Constants.kGyroCorrectionPower, true);
    encPID.enable();
    gyroPID.enable();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
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
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
