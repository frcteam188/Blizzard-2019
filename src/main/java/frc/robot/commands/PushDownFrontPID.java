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

public class PushDownFrontPID extends Command {

  double setpoint;

  public PushDownFrontPID(double setpoint) {
    requires(Robot.hangFront);
    this.setpoint = setpoint;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.hangFront.flashPIDValues();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.hangFront.runPID(setpoint);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return setpoint == 0. && Robot.hangFront.getEnc() <= 1.0 || DriverStation.getInstance().isDisabled();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.hangFront.stopPID();
    System.out.println("PushDownFrontPID ended.");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
