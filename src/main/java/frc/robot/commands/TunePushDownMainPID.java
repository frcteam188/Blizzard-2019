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

public class TunePushDownMainPID extends Command {

  double setpoint;

  public TunePushDownMainPID() {
    requires(Robot.hangFront);
    requires(Robot.hangBack);
    setpoint = 0;
  }

  private void refreshValues()
  {
    Robot.hangFront.setP(SmartDashboard.getNumber("PushDown Main P", 0.0));
    Robot.hangFront.setI(SmartDashboard.getNumber("PushDown Main I", 0.0));
    Robot.hangFront.setD(SmartDashboard.getNumber("PushDown Main D", 0.0));
    Robot.hangFront.setFF(SmartDashboard.getNumber("PushDown Main FF", 0.0));
    Robot.hangFront.setIAccum(SmartDashboard.getNumber("PushDown Main IAccum", 0.0));
    Robot.hangFront.setIZone(SmartDashboard.getNumber("PushDown Main IZone", 0.0));
    Robot.hangFront.setIMaxAccum(SmartDashboard.getNumber("PushDown Main IMaxAccum", 0.0));
    Robot.hangFront.setOutputRange(SmartDashboard.getNumber("PushDown Main OutputMin", -0.7),
                                  SmartDashboard.getNumber("PushDown Main OutputMax", 0.7));

    setpoint = SmartDashboard.getNumber("PushDown Main Setpoint", 0.0);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    SmartDashboard.putNumber("PushDown Main P", 0.0);
    SmartDashboard.putNumber("PushDown Main I", 0.0);
    SmartDashboard.putNumber("PushDown Main D", 0.0);
    SmartDashboard.putNumber("PushDown Main FF", 0.0);
    SmartDashboard.putNumber("PushDown Main Setpoint", 0.0);
    SmartDashboard.putNumber("PushDown Main IAccum", 0.0);
    SmartDashboard.putNumber("PushDown Main IZone", 0.0);
    SmartDashboard.putNumber("PushDown Main IMaxAccum", 0.0);
    SmartDashboard.putNumber("PushDown Main OutputMax", 0.7);
    SmartDashboard.putNumber("PushDown Main OutputMin", -0.7);
    refreshValues();
    System.out.println("Started tuning.");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
   /* if (!OI.stick2.getRawButton(2))
    {
      Robot.hangFront.setP(0);
      Robot.hangFront.setI(0);
      Robot.hangFront.setD(0);
      Robot.hangFront.setFF(0);
      Robot.hangFront.setIAccum(0);
      Robot.hangFront.setIZone(0);
      Robot.hangFront.setIMaxAccum(0);
      Robot.hangFront.setOutputRange(-1, 1);
      System.out.println("Not running PID.");
      Robot.hangFront.setSetpoint(0, ControlType.kDutyCycle);
      Robot.hangFront.drive(-OI.stick2.getRawAxis(1));
    }
    else */if (SmartDashboard.getNumber("PushDown Main P", 0.0) != Robot.hangFront.getP() ||
        SmartDashboard.getNumber("PushDown Main I", 0.0) != Robot.hangFront.getI() ||
        SmartDashboard.getNumber("PushDown Main D", 0.0) != Robot.hangFront.getD() ||
        SmartDashboard.getNumber("PushDown Main FF", 0.0) != Robot.hangFront.getFF() ||
        SmartDashboard.getNumber("PushDown Main Setpoint", 0.0) != setpoint ||
        SmartDashboard.getNumber("PushDown Main IAccum", 0.0) != Robot.hangFront.getIAccum() ||
        SmartDashboard.getNumber("PushDown Main IZone", 0.0) != Robot.hangFront.getIZone() ||
        SmartDashboard.getNumber("PushDown Main IMaxAccum", 0.0) != Robot.hangFront.getIMaxAccum() ||
        SmartDashboard.getNumber("PushDown Main OutputMax", 0.7) != Robot.hangFront.getOutputMax() ||
        SmartDashboard.getNumber("PushDown Main OutputMin", -0.7) != Robot.hangFront.getOutputMin())
    {
      refreshValues();
      System.out.println("Values refreshed.");
    }
    else {
      System.out.println("Running PID.");
    }
    if (Robot.hangFront.getEnc() > setpoint && Robot.hangFront.getIAccum() > 0)
      Robot.hangFront.setIAccum(0);
    Robot.hangFront.setSetpoint(setpoint);
    SmartDashboard.putNumber("PushDown Main", Robot.hangFront.getEnc());
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
