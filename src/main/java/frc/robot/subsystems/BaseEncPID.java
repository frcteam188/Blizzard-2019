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

/**
 * Add your docs here.
 */
public class BaseEncPID extends PIDSubsystem {

  double setpoint;
  double power;
  boolean store;

  public BaseEncPID(double p, double i, double d, double setpoint, double power)
  {
    this(p, i, d, setpoint, power, false);
  }

  public BaseEncPID(double p, double i, double d, double setpoint, double power, boolean store)
  {
    super("BaseEncPID", p, i, d);
    setSetpoint(setpoint);
    setAbsoluteTolerance(Constants.kBaseEncPIDTolerance);
    this.power = power;
    this.store = store;
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  @Override
  protected double returnPIDInput() {
    return Robot.base.getLeftEnc();
  }

  @Override
  protected void usePIDOutput(double output) {
    output *= power;
    if (store) Robot.base.encPIDPower = output;
    else Robot.base.driveTank(output, output);
  }
}
