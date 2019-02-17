/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class MoveIntake extends Command {

  double power;
  double time;
  Timer t;

  public MoveIntake(double power, double time) {
    requires(Robot.intake);
    this.power = power;
    this.time = time;
  }
  
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    t = new Timer();
    t.start();
    Robot.intake.setTrim(false);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.intake.drive(power);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return t.get() > time;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.intake.stop();
    Robot.intake.setTrim(true);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
