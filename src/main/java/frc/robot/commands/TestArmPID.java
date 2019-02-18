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

public class TestArmPID extends Command {
  double setpoint;

  public TestArmPID() {
    requires(Robot.hang);
    setpoint = 0;
  }

  private void refreshValues()
  {
    Robot.hang.setP(SmartDashboard.getNumber("Arm Enc P", 0.0));
    Robot.hang.setI(SmartDashboard.getNumber("Arm Enc I", 0.0));
    Robot.hang.setD(SmartDashboard.getNumber("Arm Enc D", 0.0));
    Robot.hang.setFF(SmartDashboard.getNumber("Arm Enc FF", 0.0));
    Robot.hang.setIAccum(SmartDashboard.getNumber("Arm IAccum", 0.0));
    Robot.hang.setIZone(SmartDashboard.getNumber("Arm IZone", 0.0));
    Robot.hang.setIMaxAccum(SmartDashboard.getNumber("Arm IMaxAccum", 0.0));
    Robot.hang.setOutputRange(SmartDashboard.getNumber("Arm OutputMin", -1),
                                  SmartDashboard.getNumber("Arm OutputMax", 1));

    setpoint = SmartDashboard.getNumber("Arm Enc Setpoint", 0.0);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.base.setClosedLoopRampRate(0.2);
    SmartDashboard.putNumber("Arm Enc P", 0.0);
    SmartDashboard.putNumber("Arm Enc I", 0.0);
    SmartDashboard.putNumber("Arm Enc D", 0.0);
    SmartDashboard.putNumber("Arm Enc FF", 0.0);
    SmartDashboard.putNumber("Arm Enc Setpoint", 0.0);
    SmartDashboard.putNumber("Arm IAccum", 0.0);
    SmartDashboard.putNumber("Arm IZone", 0.0);
    SmartDashboard.putNumber("Arm IMaxAccum", 0.0);
    SmartDashboard.putNumber("Arm OutputMax", 1.0);
    SmartDashboard.putNumber("Arm OutputMin", -1.0);
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
      Robot.hang.drive(-OI.stick2.getRawAxis(1));
    }
    else if (SmartDashboard.getNumber("Arm Enc P", 0.0) != Robot.hang.getP() ||
        SmartDashboard.getNumber("Arm Enc I", 0.0) != Robot.hang.getI() ||
        SmartDashboard.getNumber("Arm Enc D", 0.0) != Robot.hang.getD() ||
        SmartDashboard.getNumber("Arm Enc FF", 0.0) != Robot.hang.getFF() ||
        SmartDashboard.getNumber("Arm Enc Setpoint", 0.0) != setpoint ||
        SmartDashboard.getNumber("Arm IAccum", 0.0) != Robot.hang.getIAccum() ||
        SmartDashboard.getNumber("Arm IZone", 0.0) != Robot.hang.getIZone() ||
        SmartDashboard.getNumber("Arm IMaxAccum", 0.0) != Robot.hang.getIMaxAccum() ||
        SmartDashboard.getNumber("Arm OutputMax", 1) != Robot.hang.getOutputMax() ||
        SmartDashboard.getNumber("Arm OutputMin", -1) != Robot.hang.getOutputMin())
    {
      refreshValues();
      System.out.println("Values refreshed.");
    }
    else {
      System.out.println("Running PID.");
    }
    if (Robot.hang.getArmEnc() > setpoint && Robot.hang.getIAccum() > 0)
      Robot.hang.setIAccum(0);
    Robot.hang.setSetpoint(setpoint);
    SmartDashboard.putNumber("Arm Enc", Robot.hang.getArmEnc());
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
