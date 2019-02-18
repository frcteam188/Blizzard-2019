/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.BaseGyroPID;

public class GyroCorrect extends Command {

  double setpoint;
  int onTargetCount;
  int onTargetThreshold;
  boolean absolute;
  BaseGyroPID pid;

  public GyroCorrect(double setpoint)
  {
    this(setpoint, false);
  }

  public GyroCorrect(double setpoint, boolean absolute)
  {
    this(setpoint, absolute, 7);
  }

  public GyroCorrect(double setpoint, boolean absolute, int onTargetThreshold) {
    requires(Robot.base);
    this.setpoint = setpoint;
    this.onTargetThreshold = onTargetThreshold;
    this.absolute = absolute;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    onTargetCount = 0;
    double absoluteSetpoint;
    if (absolute) absoluteSetpoint = setpoint;
    else absoluteSetpoint = setpoint + Robot.base.getAngle();
    pid = new BaseGyroPID(Constants.baseGyroCorrectionPID[0], Constants.baseGyroCorrectionPID[1], Constants.baseGyroCorrectionPID[2],
                          absoluteSetpoint, Constants.kGyroCorrectionPower, Constants.kGyroCorrectionForwardPower);
    pid.enable();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (pid.onTarget()) onTargetCount++;
    else onTargetCount = 0;
    double[] goals = SmartDashboard.getNumberArray("goal:angles", new double[] {});
    if (goals.length > 0)
      pid.setSetpoint(Robot.base.getAngle() + goals[0]);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    double[] widths = SmartDashboard.getNumberArray("goal:widths", new double[]{});
    double width = -1;
    if (widths.length > 0) width = widths[0];    
    return onTargetCount >= onTargetThreshold && width >= Constants.kWidthThreshold;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    pid.disable();
    Robot.base.stop();
    System.out.println("GyroCorrect ended.");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
