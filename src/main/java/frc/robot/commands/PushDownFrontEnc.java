/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class PushDownFrontEnc extends Command {

  double power;
  double setpoint;
  double initialEnc;
  double relativeSetpoint;

  public PushDownFrontEnc(double power, double setpoint) {
    requires(Robot.hangFront);
    this.power = power;
    this.setpoint = setpoint;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    initialEnc = Robot.hangFront.getEnc();
    relativeSetpoint = initialEnc + setpoint;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.hangFront.drive(power);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return setpoint > 0 && Robot.hangFront.getEnc() > relativeSetpoint ||
            setpoint < 0 && Robot.hangFront.getEnc() < relativeSetpoint;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.hangFront.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
