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

public class GyroTurn extends Command {

  double setpoint;
  int onTargetCount;
  int onTargetThreshold;
  boolean absolute;
  BaseGyroPID pid;

  public GyroTurn(double setpoint) {
    this(setpoint, false);
  }

  public GyroTurn(double setpoint, boolean absolute)
  {
    this(setpoint, absolute, 1);
  }

  public GyroTurn(double setpoint, boolean absolute, int onTargetThreshold)
  {
    requires(Robot.base);
    this.setpoint = setpoint;
    this.onTargetThreshold = onTargetThreshold;
    this.absolute = absolute;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    onTargetCount = 3;
    double absoluteSetpoint;
    if (absolute) absoluteSetpoint = setpoint;
    else absoluteSetpoint = setpoint + Robot.base.getAngle();
    pid = new BaseGyroPID(Constants.baseGyroTurnPID[0], Constants.baseGyroTurnPID[1], Constants.baseGyroTurnPID[2], absoluteSetpoint, Constants.kGyroTurnPower);
    pid.enable();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (pid.onTarget()) ++onTargetCount;
    else onTargetCount = 0;
    System.out.println(pid.onTarget());
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return onTargetCount >= onTargetThreshold;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    pid.disable();
    Robot.base.stop();
    System.out.println("GyroTurn ended.");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
