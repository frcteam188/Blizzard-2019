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
public class BaseGyroPID extends PIDSubsystem {
  
  double power;
  double forwardPower;
  boolean store;

  public BaseGyroPID(double p, double i, double d, double setpoint, double power)
  {
    this(p, i, d, setpoint, power, 0, false);
  }
  
  public BaseGyroPID(double p, double i, double d, double setpoint, double power, boolean store)
  {
    this(p, i, d, setpoint, power, 0, store);
  }

  public BaseGyroPID(double p, double i, double d, double setpoint, double power, double forwardPower)
  {
    this(p, i, d, setpoint, power, forwardPower, false);
  }

  public BaseGyroPID(double p, double i, double d, double setpoint, double power, double forwardPower, boolean store) {
    super("BaseGyroPID", p, i, d);
    setSetpoint(setpoint);
    setAbsoluteTolerance(Constants.kBaseGyroPIDTolerance);
    this.power = power;
    this.forwardPower = forwardPower;
    this.store = store;
    disable();
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  @Override
  protected double returnPIDInput() {
    double input =  Robot.base.getAngle() % 360;
    /*Makes the Robot take the shortest path to get to an angle (e.g. If we want to get to 90 degrees from 0 degrees, 
      we move 90 degrees clockwise, and not 270 degrees counterclockwise)*/
    if(Math.abs(getSetpoint() - input) > Math.abs(getSetpoint() - input + 360)){
      input = input - 360;
    }
    if(Math.abs(getSetpoint() - input) > Math.abs(getSetpoint() - input - 360)){
      input = input + 360;
    }
    return input;
  }

  @Override
  protected void usePIDOutput(double output) {
    output *= power;
    if (store) Robot.base.gyroPIDPower = output;
    else Robot.base.driveArcade(-forwardPower, output);
  }
}
