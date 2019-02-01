/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import frc.robot.Constants;
import frc.robot.Robot;


public class ElevatorPID extends PIDSubsystem {

  double power;

  public ElevatorPID(double setpoint)
  {
    this(Constants.elevatorPID[0], Constants.elevatorPID[1], Constants.elevatorPID[2], setpoint);
  }

  public ElevatorPID(double p, double i, double d, double setpoint)
  {
    this(p, i, d, setpoint, 1.0);
  }

  public ElevatorPID(double p, double i, double d, double setpoint, double power)
  {
    super("ElevatorPID", p, i, d);
    this.power = power;
    setAbsoluteTolerance(Constants.elevatorPIDTolerance);
    setSetpoint(setpoint);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  @Override
  protected double returnPIDInput() {
    return Robot.elevator.getElevatorEnc();
  }

  @Override
  protected void usePIDOutput(double output) {
    Robot.elevator.drive(output * power);
  }
}
