/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.subsystems.PushDownCorrectionPID;

public class TunePushDownCorrectionPID extends Command {

  PushDownCorrectionPID pid;
  boolean toggle;

  public TunePushDownCorrectionPID() {
    requires(Robot.hang);
  }

  private void refreshValues()
  {
    pid.getPIDController().setP(SmartDashboard.getNumber("PushDown Correction P", 0.0));
    pid.getPIDController().setI(SmartDashboard.getNumber("PushDown Correction I", 0.0));
    pid.getPIDController().setD(SmartDashboard.getNumber("PushDown Correction D", 0.0));
    pid.getPIDController().setSetpoint(SmartDashboard.getNumber("PushDown Correction Setpoint", 0.0));
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    SmartDashboard.putNumber("PushDown Correction P", 0.0); 
    SmartDashboard.putNumber("PushDown Correction D", 0.0);
    SmartDashboard.putNumber("PushDown Correction I", 0.0);
    SmartDashboard.putNumber("PushDown Correction Setpoint", 0.0);
    toggle = OI.stick2.getRawButton(1);
    pid = new PushDownCorrectionPID(SmartDashboard.getNumber("PushDown Correction P", 0.0), 
      SmartDashboard.getNumber("PushDown Correction I", 0.0),
      SmartDashboard.getNumber("PushDown Correction D", 0.0),
      SmartDashboard.getNumber("PushDown Correction Setpoint", 0.0), 1);
    refreshValues();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (SmartDashboard.getNumber("PushDown Correction P", 0.0) != pid.getPIDController().getP() ||
        SmartDashboard.getNumber("PushDown Correction I", 0.0) != pid.getPIDController().getI() ||
        SmartDashboard.getNumber("PushDown Correction D", 0.0) != pid.getPIDController().getD() ||
        SmartDashboard.getNumber("PushDown Correction Setpoint", 0.0) != pid.getPIDController().getSetpoint())
    {
      refreshValues();
    }

    if(OI.stick2.getRawButton(1) && !toggle) {
      pid.enable();
    }
    else if(!OI.stick2.getRawButton(1) && toggle) {
      pid.disable();
      Robot.hang.stop();
    }

    toggle = OI.stick2.getRawButton(1);
    SmartDashboard.putNumber("PushDown Correction Enc", Robot.hang.getCorrectionEnc());
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    pid.disable();
    Robot.hang.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
