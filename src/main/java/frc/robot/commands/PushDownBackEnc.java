/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class PushDownBackEnc extends Command {

  double power;
  double setpoint;
  double initialEnc;
  double relativeSetpoint;
  boolean absolute;

  public PushDownBackEnc(double power, double setpoint) {
    this(power, setpoint, false);
  }

  public PushDownBackEnc(double power, double setpoint, boolean absolute) {
    requires(Robot.hangBack);
    this.power = power;
    this.setpoint = setpoint;
    this.absolute = absolute;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    initialEnc = Robot.hangBack.getEnc();
    if (absolute) relativeSetpoint = setpoint;
    else relativeSetpoint = initialEnc + setpoint;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.hangBack.drivePushDown(power);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return relativeSetpoint > initialEnc && Robot.hangBack.getEnc() > relativeSetpoint ||
    relativeSetpoint < initialEnc && Robot.hangBack.getEnc() < relativeSetpoint;
    // return setpoint > 0 && Robot.hangBack.getEnc() > relativeSetpoint ||
    //         setpoint < 0 && Robot.hangBack.getEnc() < relativeSetpoint;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.hangBack.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
