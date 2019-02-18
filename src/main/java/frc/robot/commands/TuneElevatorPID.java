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
import frc.robot.Constants;
import frc.robot.OI;
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
    Robot.elevator.setIAccum(SmartDashboard.getNumber("Elevator IAccum", 0.0));
    Robot.elevator.setIZone(SmartDashboard.getNumber("Elevator IZone", 0.0));
    Robot.elevator.setIMaxAccum(SmartDashboard.getNumber("Elevator IMaxAccum", 0.0));
    Robot.elevator.setOutputRange(SmartDashboard.getNumber("Elevator OutputMin", -1),
                                  SmartDashboard.getNumber("Elevator OutputMax", 1));

    setpoint = SmartDashboard.getNumber("Elevator Enc Setpoint", 0.0);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    // Robot.base.setClosedLoopRampRate(0.2);
    SmartDashboard.putNumber("Elevator Enc P", 0.0);
    SmartDashboard.putNumber("Elevator Enc I", 0.0);
    SmartDashboard.putNumber("Elevator Enc D", 0.0);
    SmartDashboard.putNumber("Elevator Enc FF", 0.0);
    SmartDashboard.putNumber("Elevator Enc Setpoint", 0.0);
    SmartDashboard.putNumber("Elevator IAccum", 0.0);
    SmartDashboard.putNumber("Elevator IZone", 0.0);
    SmartDashboard.putNumber("Elevator IMaxAccum", 0.0);
    SmartDashboard.putNumber("Elevator OutputMax", 1.0);
    SmartDashboard.putNumber("Elevator OutputMin", -1.0);
    refreshValues();
    System.out.println("Started tuning.");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (!OI.stick2.getRawButton(2))
    {
      Robot.elevator.setP(0);
      Robot.elevator.setI(0);
      Robot.elevator.setD(0);
      Robot.elevator.setFF(0);
      Robot.elevator.setIAccum(0);
      Robot.elevator.setIZone(0);
      Robot.elevator.setIMaxAccum(0);
      Robot.elevator.setOutputRange(-1, 1);
      System.out.println("Not running PID.");
      Robot.elevator.setSetpoint(0, ControlType.kDutyCycle);
      Robot.elevator.drive(-OI.stick2.getRawAxis(OI.elevatorAxis));
    }
    else if (SmartDashboard.getNumber("Elevator Enc P", 0.0) != Robot.elevator.getP() ||
        SmartDashboard.getNumber("Elevator Enc I", 0.0) != Robot.elevator.getI() ||
        SmartDashboard.getNumber("Elevator Enc D", 0.0) != Robot.elevator.getD() ||
        SmartDashboard.getNumber("Elevator Enc FF", 0.0) != Robot.elevator.getFF() ||
        SmartDashboard.getNumber("Elevator Enc Setpoint", 0.0) != setpoint ||
        SmartDashboard.getNumber("Elevator IAccum", 0.0) != Robot.elevator.getIAccum() ||
        SmartDashboard.getNumber("Elevator IZone", 0.0) != Robot.elevator.getIZone() ||
        SmartDashboard.getNumber("Elevator IMaxAccum", 0.0) != Robot.elevator.getIMaxAccum() ||
        SmartDashboard.getNumber("Elevator OutputMax", 1) != Robot.elevator.getOutputMax() ||
        SmartDashboard.getNumber("Elevator OutputMin", -1) != Robot.elevator.getOutputMin())
    {
      refreshValues();
      System.out.println("Values refreshed.");
    }
    else System.out.println("Running PID.");
    if (Robot.elevator.getElevatorEnc() > setpoint && Robot.elevator.getIAccum() > 0)
      Robot.elevator.setIAccum(0);
    Robot.elevator.setSetpoint(setpoint);
    Robot.elevator.report();
    Robot.elevator.tuningReport();
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
