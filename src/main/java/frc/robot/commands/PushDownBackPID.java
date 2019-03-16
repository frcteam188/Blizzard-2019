  /*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class PushDownBackPID extends Command {

  double setpoint;
  boolean prevHangSetpoint;

  public PushDownBackPID(double setpoint) {
    this(setpoint, false);
  }

  public PushDownBackPID(boolean prevHangSetpoint)
  {
    this(0, prevHangSetpoint);
  }
  
  public PushDownBackPID(double setpoint, boolean prevHangSetpoint)
  {
    requires(Robot.hangBack);
    this.setpoint = setpoint;
    this.prevHangSetpoint = prevHangSetpoint;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if (prevHangSetpoint) this.setpoint = Robot.hangBack.getActiveSetpoint();
    Robot.hangBack.flashPIDValues();
    System.out.println("PushDownBackPID started.");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.hangBack.runPID(setpoint);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return setpoint == 0. && Robot.hangBack.getEnc() <= 1.0 || DriverStation.getInstance().isDisabled();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.hangBack.stopPID();
    System.out.println("PushDownBackPID ended.");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
