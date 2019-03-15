/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.Robot;

public class TunePushDownCorrectionPID extends Command {

  double setpoint;

  public TunePushDownCorrectionPID() {
    requires(Robot.hangFront);
    requires(Robot.hangBack);
    setpoint = 0;
  }

  private void refreshValues()
  {
    Robot.hangBack.setP(SmartDashboard.getNumber("PushDown Correction P", 0.0));
    Robot.hangBack.setI(SmartDashboard.getNumber("PushDown Correction I", 0.0));
    Robot.hangBack.setD(SmartDashboard.getNumber("PushDown Correction D", 0.0));
    Robot.hangBack.setFF(SmartDashboard.getNumber("PushDown Correction FF", 0.0));
    Robot.hangBack.setIAccum(SmartDashboard.getNumber("PushDown Correction IAccum", 0.0));
    Robot.hangBack.setIZone(SmartDashboard.getNumber("PushDown Correction IZone", 0.0));
    Robot.hangBack.setIMaxAccum(SmartDashboard.getNumber("PushDown Correction IMaxAccum", 0.0));
    Robot.hangBack.setOutputRange(SmartDashboard.getNumber("PushDown Correction OutputMin", -1.0),
                                  SmartDashboard.getNumber("PushDown Correction OutputMax", 1.0));

    setpoint = SmartDashboard.getNumber("PushDown Correction Setpoint", 0.0);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    SmartDashboard.putNumber("PushDown Correction P", 0.0);
    SmartDashboard.putNumber("PushDown Correction I", 0.0);
    SmartDashboard.putNumber("PushDown Correction D", 0.0);
    SmartDashboard.putNumber("PushDown Correction FF", 0.0);
    SmartDashboard.putNumber("PushDown Correction Setpoint", 0.0);
    SmartDashboard.putNumber("PushDown Correction IAccum", 0.0);
    SmartDashboard.putNumber("PushDown Correction IZone", 0.0);
    SmartDashboard.putNumber("PushDown Correction IMaxAccum", 0.0);
    SmartDashboard.putNumber("PushDown Correction OutputMax", 1.0);
    SmartDashboard.putNumber("PushDown Correction OutputMin", -1.0);
    refreshValues();
    System.out.println("Started tuning.");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
   /* if (!OI.stick2.getRawButton(2))
    {
      Robot.hangBack.setP(0);
      Robot.hangBack.setI(0);
      Robot.hangBack.setD(0);
      Robot.hangBack.setFF(0);
      Robot.hangBack.setIAccum(0);
      Robot.hangBack.setIZone(0);
      Robot.hangBack.setIMaxAccum(0);
      Robot.hangBack.setOutputRange(-1, 1);
      System.out.println("Not running PID.");
      Robot.hangBack.setSetpoint(0, ControlType.kDutyCycle);
      Robot.hangBack.drive(-OI.stick2.getRawAxis(1));
    }
    else */if (SmartDashboard.getNumber("PushDown Correction P", 0.0) != Robot.hangBack.getP() ||
        SmartDashboard.getNumber("PushDown Correction I", 0.0) != Robot.hangBack.getI() ||
        SmartDashboard.getNumber("PushDown Correction D", 0.0) != Robot.hangBack.getD() ||
        SmartDashboard.getNumber("PushDown Correction FF", 0.0) != Robot.hangBack.getFF() ||
        SmartDashboard.getNumber("PushDown Correction Setpoint", 0.0) != setpoint ||
        SmartDashboard.getNumber("PushDown Correction IAccum", 0.0) != Robot.hangBack.getIAccum() ||
        SmartDashboard.getNumber("PushDown Correction IZone", 0.0) != Robot.hangBack.getIZone() ||
        SmartDashboard.getNumber("PushDown Correction IMaxAccum", 0.0) != Robot.hangBack.getIMaxAccum() ||
        SmartDashboard.getNumber("PushDown Correction OutputMax", 1.0) != Robot.hangBack.getOutputMax() ||
        SmartDashboard.getNumber("PushDown Correction OutputMin", -1.0) != Robot.hangBack.getOutputMin())
    {
      refreshValues();
      System.out.println("Values refreshed.");
    }
    else {
      System.out.println("Running PID.");
    }
    if (Robot.hangBack.getEnc() > setpoint && Robot.hangBack.getIAccum() > 0)
      Robot.hangBack.setIAccum(0);
    Robot.hangBack.setSetpoint(setpoint);
    Robot.hangBack.report();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.hangFront.stop();
    Robot.hangBack.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
