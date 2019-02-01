/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.subsystems.ElevatorPID;

public class TuneElevatorPID extends Command {

  ElevatorPID elevatorPID;

  public TuneElevatorPID() {
    requires(Robot.elevator);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    elevatorPID = new ElevatorPID(SmartDashboard.getNumber("Elevator P", 0.0), 
                                  SmartDashboard.getNumber("Elevator I", 0.0), 
                                  SmartDashboard.getNumber("Elevator D", 0.0),
                                  SmartDashboard.getNumber("Elevator Setpoint", 0.0));
    elevatorPID.enable();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (SmartDashboard.getNumber("Elevator P", 0.0) != elevatorPID.getPIDController().getP() ||
        SmartDashboard.getNumber("Elevator I", 0.0) != elevatorPID.getPIDController().getI() ||
        SmartDashboard.getNumber("Elevator D", 0.0) != elevatorPID.getPIDController().getD() ||
        SmartDashboard.getNumber("Elevator Setpoint", 0.0) != elevatorPID.getPIDController().getSetpoint())
    {
      elevatorPID.disable();
      initialize();
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    elevatorPID.disable();
    Robot.elevator.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
