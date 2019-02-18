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
  BaseGyroPID pid;

  public GyroTurn(double setpoint) {
    this(setpoint, 7);
  }

  public GyroTurn(double setpoint, int onTargetThreshold)
  {
    requires(Robot.base);
    this.setpoint = setpoint + Robot.base.getAngle();
    this.onTargetThreshold = onTargetThreshold;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    onTargetCount = 0;
    pid = new BaseGyroPID(Constants.baseGyroTurnPID[0], Constants.baseGyroTurnPID[1], Constants.baseGyroTurnPID[2], setpoint, Constants.kGyroTurnPower);
    pid.enable();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (pid.onTarget()) ++onTargetCount;
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
    pid.disable();
    Robot.base.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
