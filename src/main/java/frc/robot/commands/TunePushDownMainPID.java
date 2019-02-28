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
    requires(Robot.hang);
    setpoint = 0;
  }

  private void refreshValues()
  {
    Robot.hang.setP(SmartDashboard.getNumber("PushDown Main P", 0.0));
    Robot.hang.setI(SmartDashboard.getNumber("PushDown Main I", 0.0));
    Robot.hang.setD(SmartDashboard.getNumber("PushDown Main D", 0.0));
    Robot.hang.setFF(SmartDashboard.getNumber("PushDown Main FF", 0.0));
    Robot.hang.setIAccum(SmartDashboard.getNumber("PushDown Main IAccum", 0.0));
    Robot.hang.setIZone(SmartDashboard.getNumber("PushDown Main IZone", 0.0));
    Robot.hang.setIMaxAccum(SmartDashboard.getNumber("PushDown Main IMaxAccum", 0.0));
    Robot.hang.setOutputRange(SmartDashboard.getNumber("PushDown Main OutputMin", -1),
                                  SmartDashboard.getNumber("PushDown Main OutputMax", 1));

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
    SmartDashboard.putNumber("PushDown Main OutputMax", 1.0);
    SmartDashboard.putNumber("PushDown Main OutputMin", -1.0);
    refreshValues();
    System.out.println("Started tuning.");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (!OI.stick2.getRawButton(2))
    {
      Robot.hang.setP(0);
      Robot.hang.setI(0);
      Robot.hang.setD(0);
      Robot.hang.setFF(0);
      Robot.hang.setIAccum(0);
      Robot.hang.setIZone(0);
      Robot.hang.setIMaxAccum(0);
      Robot.hang.setOutputRange(-1, 1);
      System.out.println("Not running PID.");
      Robot.hang.setSetpoint(0, ControlType.kDutyCycle);
      Robot.hang.driveMain(-OI.stick2.getRawAxis(1));
    }
    else if (SmartDashboard.getNumber("PushDown Main P", 0.0) != Robot.hang.getP() ||
        SmartDashboard.getNumber("PushDown Main I", 0.0) != Robot.hang.getI() ||
        SmartDashboard.getNumber("PushDown Main D", 0.0) != Robot.hang.getD() ||
        SmartDashboard.getNumber("PushDown Main FF", 0.0) != Robot.hang.getFF() ||
        SmartDashboard.getNumber("PushDown Main Setpoint", 0.0) != setpoint ||
        SmartDashboard.getNumber("PushDown Main IAccum", 0.0) != Robot.hang.getIAccum() ||
        SmartDashboard.getNumber("PushDown Main IZone", 0.0) != Robot.hang.getIZone() ||
        SmartDashboard.getNumber("PushDown Main IMaxAccum", 0.0) != Robot.hang.getIMaxAccum() ||
        SmartDashboard.getNumber("PushDown Main OutputMax", 1) != Robot.hang.getOutputMax() ||
        SmartDashboard.getNumber("PushDown Main OutputMin", -1) != Robot.hang.getOutputMin())
    {
      refreshValues();
      System.out.println("Values refreshed.");
    }
    else {
      System.out.println("Running PID.");
    }
    if (Robot.hang.getMainEnc() > setpoint && Robot.hang.getIAccum() > 0)
      Robot.hang.setIAccum(0);
    Robot.hang.setSetpoint(setpoint);
    SmartDashboard.putNumber("PushDown Main", Robot.hang.getMainEnc());
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.hang.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
