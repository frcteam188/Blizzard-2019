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

public class TuneElevatorPID extends Command {

  double setpoint;

  public TuneElevatorPID() {
    requires(Robot.elevator);
    setpoint = 0;
  }

  private void refreshValues()
  {
    Robot.elevator.setP(SmartDashboard.getNumber("Elevator Enc P", 0.0));
    Robot.elevator.setI(SmartDashboard.getNumber("Elevator Enc I", 0.0));
    Robot.elevator.setD(SmartDashboard.getNumber("Elevator Enc D", 0.0));
    Robot.elevator.setFF(SmartDashboard.getNumber("Elevator Enc FF", 0.0));

    setpoint = SmartDashboard.getNumber("Elevator Enc Setpoint", 0.0);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    refreshValues();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (SmartDashboard.getNumber("Elevator Enc P", 0.0) != Robot.elevator.getP() ||
        SmartDashboard.getNumber("Elevator Enc I", 0.0) != Robot.elevator.getI() ||
        SmartDashboard.getNumber("Elevator Enc D", 0.0) != Robot.elevator.getD() ||
        SmartDashboard.getNumber("Elevator Enc FF", 0.0) != Robot.elevator.getFF() ||
        SmartDashboard.getNumber("Elevator Enc Setpoint", 0.0) != setpoint)
    {
      refreshValues();
    }
    Robot.elevator.setSetpoint(setpoint);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.elevator.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
