/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.ConfigParameter;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.RobotMap;


public class Elevator extends Subsystem {
  
  private CANSparkMax elevatorLeft;
  private CANSparkMax elevatorRight;

  private double encOffset;
  private int activePreset;
  private int activePID;
  private Direction direction;
  private double deadband;

  private enum Direction
  {
    DOWN,
    UP
  }

  public Elevator()
  {
    elevatorLeft = new CANSparkMax(RobotMap.elevatorLeft, MotorType.kBrushless);
    elevatorRight = new CANSparkMax(RobotMap.elevatorRight, MotorType.kBrushless);
    encOffset = 0;
    activePreset = -1;
    activePID = Constants.kElevatorUpPID;
    direction = Direction.UP;
    // deadband = (double)(elevatorLeft.getParameterDouble(ConfigParameter.kInputDeadband).get());
    // System.out.println("DEADBAND: " + elevatorLeft.getParameterDouble(ConfigParameter.kInputDeadband).get());
    deadband = 0.05;
  }

  public void execute()
  {
    activePreset = -1;
    // RUN THROUGH PRESET BUTTONS
    // for (int i = 0; i < OI.elevatorButtons.length; ++i)
    // {
    //   if (OI.elevatorButtons[i].get())
    //   {
    //     activePreset = i;
    //     setPID(activePreset);
    //     break;
    //   }
    // }
    if (activePreset != -1)
    {
      runPID();
    }
    else
    {
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
      activePID = Constants.kElevatorDownPID;
    }
    else
    {
      activePID = Constants.kElevatorUpPID;
    }
    setDirection();
  }

  private void setDirection()
  {
    switch (activePID)
    {
      case Constants.kElevatorDownPID:
        direction = Direction.DOWN;
        break;
      case Constants.kElevatorUpPID:
        direction = Direction.UP;
        break;
    }
  }

  private void runPID()
  {
    setSetpoint(Constants.elevatorPresets[activePreset]);
  }

  public void report()
  {
    SmartDashboard.putNumber("Elevator Enc", getElevatorEnc());
    SmartDashboard.putNumber("Elevator PID Setpoint", Constants.elevatorPresets[activePreset]);
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

  public void setSetpoint(double setpoint)
  {
    setSetpoint(setpoint, Constants.kElevatorPIDDefaultType);
  }

  public void setSetpoint(double setpoint, ControlType type)
  {
    elevatorLeft.getPIDController().setReference(setpoint, type, activePID);
    elevatorRight.getPIDController().setReference(setpoint, type, activePID);
  }

  public void setP(double p)
  {
    elevatorLeft.getPIDController().setP(p);
    elevatorRight.getPIDController().setP(p);
  }

  public void setI(double i)
  {
    elevatorLeft.getPIDController().setI(i);
    elevatorRight.getPIDController().setI(i);
  }

  public void setD(double d)
  {
    elevatorLeft.getPIDController().setD(d);
    elevatorRight.getPIDController().setD(d);
  }

  public void setFF(double f)
  {
    elevatorLeft.getPIDController().setFF(f);
    elevatorRight.getPIDController().setFF(f);
  }

  public double getP()
  {
    return elevatorLeft.getPIDController().getP();
  }
  
  public double getI()
  {
    return elevatorLeft.getPIDController().getI();
  }

  public double getD()
  {
    return elevatorLeft.getPIDController().getD();
  }

  public double getFF()
  {
    return elevatorLeft.getPIDController().getFF();
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
