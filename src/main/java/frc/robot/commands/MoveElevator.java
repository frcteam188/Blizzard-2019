/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.Robot;

public class MoveElevator extends Command {

  int preset;
  double setpoint;

  public MoveElevator(int preset) {
    requires(Robot.elevator);
    this.preset = preset;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.elevator.flashPIDValues();
    if (preset < 0)
      setpoint = Constants.bottomPreset;
    else if (OI.ballToggle.get())
      setpoint = Constants.ballPresets[preset];
    else
      setpoint = Constants.hatchPresets[preset];
    Robot.elevator.setPID(setpoint);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.elevator.runPID(setpoint);
    System.out.println(setpoint);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Math.abs(setpoint - Robot.elevator.getElevatorEnc()) < 1 &&
            setpoint < 2;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.elevator.stopPID();
    System.out.println("FINISHED PID");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
