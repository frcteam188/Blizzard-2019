/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMaxLowLevel.ConfigParameter;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.RobotMap;


public class Elevator extends Subsystem {
  
  private CANSparkMax elevatorLeft;
  private CANSparkMax elevatorRight;

  private ElevatorPID elevatorPID;
  private ElevatorPID upPID;
  private ElevatorPID downPID;

  private double encOffset;
  private int activePreset;
  private Direction direction;
  private double deadband;

  enum Direction
  {
    DOWN,
    UP
  }

  public Elevator()
  {
    elevatorLeft = new CANSparkMax(RobotMap.elevatorLeft, CANSparkMaxLowLevel.MotorType.kBrushless);
    elevatorRight = new CANSparkMax(RobotMap.elevatorRight, CANSparkMaxLowLevel.MotorType.kBrushless);
    upPID = new ElevatorPID(Constants.elevatorUpPID[0], Constants.elevatorUpPID[1], Constants.elevatorUpPID[2], 0);
    downPID = new ElevatorPID(Constants.elevatorDownPID[0], Constants.elevatorDownPID[1], Constants.elevatorDownPID[2], 0);
    elevatorPID = upPID;
    encOffset = 0;
    activePreset = -1;
    direction = Direction.UP;
    // deadband = (double)(elevatorLeft.getParameterDouble(ConfigParameter.kInputDeadband).get());
    deadband = 0.05;
  }

  public void execute()
  {
    activePreset = -1;
    for (int i = 0; i < OI.elevatorButtons.length; ++i)
    {
      if (OI.elevatorButtons[i].get())
      {
        activePreset = i;
        setPID(activePreset);
        break;
      }
    }
    if (activePreset != -1)
    {
      elevatorPID.enable();
    }
    else
    {
      elevatorPID.disable();
      double power = OI.stick2.getRawAxis(OI.elevatorAxis);
      if (power >= deadband) direction = Direction.UP;
      else if (power <= -deadband) direction = Direction.DOWN;
      if (withinLimits()) drive(power);
    }
  }

  private boolean withinLimits()
  {
    return !(direction == Direction.UP && getElevatorEnc() >= Constants.kElevatorUpperLimit
            || direction == Direction.DOWN && getElevatorEnc() <= Constants.kElevatorLowerLimit);
  }

  private void setPID(int preset)
  {
    if (getElevatorEnc() > Constants.elevatorPresets[preset])
    {
      elevatorPID = downPID;
      direction = Direction.DOWN;
    }
    else
    {
      elevatorPID = upPID;
      direction = Direction.UP;
    }
    elevatorPID.setSetpoint(Constants.elevatorPresets[preset]);
  }

  public void report()
  {
    SmartDashboard.putNumber("Elevator Enc", getElevatorEnc());
    SmartDashboard.putNumber("Elevator PID Setpoint", elevatorPID.getSetpoint());
    SmartDashboard.putNumber("Elevator Raw Enc", elevatorLeft.getEncoder().getPosition());
    SmartDashboard.putNumber("Elevator Velocity", elevatorLeft.getEncoder().getVelocity());
  }

  public void drive(double power)
  {
    power *= Constants.kElevatorPower;
    elevatorLeft.set(power);
    elevatorRight.set(-power);
  }

  public void stop()
  {
    elevatorLeft.set(0);
    elevatorRight.set(0);
  }

  public double getElevatorEnc()
  {
    return elevatorRight.getEncoder().getPosition() - encOffset;
  }

  public void resetElevatorEnc()
  {
    encOffset += getElevatorEnc();
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
