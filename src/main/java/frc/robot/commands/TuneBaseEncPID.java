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

public class TuneBaseEncPID extends Command {

  double setpoint;

  public TuneBaseEncPID() {
    requires(Robot.base);
    setpoint = 0;
  }

  private void refreshValues()
  {
    Robot.base.setP(SmartDashboard.getNumber("Base Enc P", 0.0));
    Robot.base.setI(SmartDashboard.getNumber("Base Enc I", 0.0));
    Robot.base.setD(SmartDashboard.getNumber("Base Enc D", 0.0));
    Robot.base.setFF(SmartDashboard.getNumber("Base Enc FF", 0.0));

    setpoint = SmartDashboard.getNumber("Base Enc Setpoint", 0.0);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    refreshValues();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (SmartDashboard.getNumber("Base Enc P", 0.0) != Robot.base.getP() ||
        SmartDashboard.getNumber("Base Enc I", 0.0) != Robot.base.getI() ||
        SmartDashboard.getNumber("Base Enc D", 0.0) != Robot.base.getD() ||
        SmartDashboard.getNumber("Base Enc FF", 0.0) != Robot.base.getFF() ||
        SmartDashboard.getNumber("Base Enc Setpoint", 0.0) != setpoint)
    {
      refreshValues();
    }
    Robot.base.setSetpoint(setpoint);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.base.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
