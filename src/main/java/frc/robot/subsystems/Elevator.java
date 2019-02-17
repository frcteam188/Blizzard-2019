/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.ConfigParameter;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMaxLowLevel.PeriodicFrame;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.RobotMap;
import frc.robot.commands.MoveElevator;


public class Elevator extends Subsystem {
  
  private CANSparkMax elevatorLeft;
  private CANSparkMax elevatorRight;

  private CANEncoder elevatorLeftEncoder;
  private CANEncoder elevatorRightEncoder;

  private double activeSetpoint;
  private int activePID;
  private Direction direction;
  private double deadband;


  public enum GamePiece
  {
    BALL,
    HATCH,
    NONE
  }

  private enum Direction
  {
    DOWN("Down"),
    UP("Up");

    private String name;

    private Direction(String name)
    {
      this.name = name;
    }

    @Override
    public String toString()
    {
      return name;
    }
  }

  public Elevator()
  {
    elevatorLeft = new CANSparkMax(RobotMap.elevatorLeft, MotorType.kBrushless);
    elevatorRight = new CANSparkMax(RobotMap.elevatorRight, MotorType.kBrushless);
    elevatorRight.setPeriodicFramePeriod(PeriodicFrame.kStatus2, 10);
    elevatorLeft.setInverted(true);
    elevatorRight.setInverted(false);
    elevatorLeftEncoder = elevatorLeft.getEncoder();
    elevatorRightEncoder = elevatorRight.getEncoder();
    activeSetpoint = -1;
    activePID = Constants.kElevatorUpPID;
    direction = Direction.UP;
    // deadband = (double)(elevatorLeft.getParameterDouble(ConfigParameter.kInputDeadband).get());
    // System.out.println("DEADBAND: " + elevatorLeft.getParameterDouble(ConfigParameter.kInputDeadband).get());
    deadband = 0.05;

    flashPIDValues();
  }

  public void flashPIDValues()
  {
    // Flash up PID
    setP(Constants.elevatorUpPID[0], Constants.kElevatorUpPID);
    setI(Constants.elevatorUpPID[1], Constants.kElevatorUpPID);
    setD(Constants.elevatorUpPID[2], Constants.kElevatorUpPID);
    setIZone(Constants.elevatorUpIZone, Constants.kElevatorUpPID);
    // MAKE SURE TO ZERO IACCUM IF ABOVE SETPOINT AND IACCUM < 0

    // Flash down PID
    setP(Constants.elevatorDownPID[0], Constants.kElevatorDownPID);
    setI(Constants.elevatorDownPID[1], Constants.kElevatorDownPID);
    setD(Constants.elevatorDownPID[2], Constants.kElevatorDownPID);
    setOutputRange(Constants.elevatorDownPIDOutputRange[0], Constants.elevatorDownPIDOutputRange[1], Constants.kElevatorDownPID);
  }

  // public void execute()
  // {
  //   activePreset = -1;
  //   // RUN THROUGH PRESET BUTTONS
  //   for (int i = 0; i < OI.elevatorButtons.length; ++i)
  //   {
  //     if (OI.elevatorButtons[i].get())
  //     {
  //       activePreset = i;
  //       setPID(activePreset);
  //       break;
  //     }
  //   }
  //   if (activePreset != -1)
  //   {
  //     runPID();
  //   }
  //   else
  //   {
  //     // joystickDrive();

  //   }
  // }

  private void joystickDrive()
  {
    double power = -OI.stick2.getRawAxis(OI.elevatorAxis);
    if (-power >= deadband) direction = Direction.UP;
    else if (-power <= -deadband) direction = Direction.DOWN;
    if (withinLimits()) drive(power);
    else stop();
  }

  private boolean withinLimits()
  {
    return !(direction == Direction.UP && getElevatorEnc() >= Constants.kElevatorUpperLimit
            || direction == Direction.DOWN && getElevatorEnc() <= Constants.kElevatorLowerLimit);
  }

  public void setPID(double setpoint)
  {
    if (getElevatorEnc() > setpoint)
    {
      activePID = Constants.kElevatorDownPID;
    }
    else
    {
      activePID = Constants.kElevatorUpPID;
    }
    setDirection();
    activeSetpoint = setpoint;
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

  public void runPID(double setpoint)
  {
    if (activePID == Constants.kElevatorUpPID && getElevatorEnc() > setpoint && getIAccum() > 0)
      setIAccum(0);
    setSetpoint(setpoint);
    activeSetpoint = setpoint;
  }

  public double getActiveSetpoint()
  {
    return activeSetpoint;
  }

  public void report()
  {
    SmartDashboard.putNumber("Elevator Enc", getElevatorEnc());
    // if (activePreset != -1) SmartDashboard.putNumber("Elevator PID Setpoint", getCurrentPreset());
    SmartDashboard.putString("Elevator Direction", direction.toString());
    SmartDashboard.putBoolean("Elevator Within Limits", withinLimits());
    SmartDashboard.putNumber("Elevator Left Raw Enc", elevatorLeftEncoder.getPosition());
    SmartDashboard.putNumber("Elevator Right Raw Enc", elevatorRightEncoder.getPosition());
    // SmartDashboard.putNumber("Elevator Active Preset", getCurrentPreset());
    // SmartDashboard.putNumber("Elevator Velocity", elevatorLeft.getEncoder().getVelocity());
  }

  public void tuningReport()
  {
    SmartDashboard.putNumber("Elevator IAccum", getIAccum());
    SmartDashboard.putNumber("Elevator Left Temperature", getElevatorLeftTemp());
    SmartDashboard.putNumber("Elevator Right Temperature", getElevatorRightTemp());
  }

  public void drive(double power)
  {
    power *= Constants.kElevatorPower;
    elevatorLeft.set(power);
    elevatorRight.set(power);
  }

  public void stop()
  {
    elevatorLeft.set(0);
    elevatorRight.set(0);
  }

  public void stopPID()
  {
    setP(0);
    setI(0);
    setD(0);
    setFF(0);
    setSetpoint(0);
    activeSetpoint = -1;
  }

  public double getElevatorEnc()
  {
    return elevatorLeftEncoder.getPosition();
  }

  public double getElevatorLeftEnc()
  {
    return elevatorLeftEncoder.getPosition();
  }

  public double getElevatorRightEncoder()
  {
    return elevatorRightEncoder.getPosition();
  }

  public void resetElevatorEnc()
  {
    resetElevatorLeftEnc();
    resetElevatorRightEnc();
  }

  public void resetElevatorLeftEnc()
  {
    elevatorLeftEncoder.setPosition(0);
  }
  
  public void resetElevatorRightEnc()
  {
    elevatorRightEncoder.setPosition(0);
  }

  public double getElevatorLeftTemp()
  {
    return elevatorLeft.getMotorTemperature();
  }

  public double getElevatorRightTemp()
  {
    return elevatorRight.getMotorTemperature();
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
    setP(p, activePID);
  }

  public void setP(double p, int slotID)
  {
    elevatorLeft.getPIDController().setP(p, slotID);
    elevatorRight.getPIDController().setP(p, slotID);
  }

  public void setI(double i)
  {
    setI(i, activePID);
  }

  public void setI(double i, int slotID)
  {
    elevatorLeft.getPIDController().setI(i, slotID);
    elevatorRight.getPIDController().setI(i, slotID);
  }

  public void setD(double d)
  {
    setD(d, activePID);
  }

  public void setD(double d, int slotID)
  {
    elevatorLeft.getPIDController().setD(d, slotID);
    elevatorRight.getPIDController().setD(d, slotID);
  }

  public void setFF(double f)
  {
    setFF(f, activePID);
  }

  public void setFF(double f, int slotID)
  {
    elevatorLeft.getPIDController().setFF(f, slotID);
    elevatorRight.getPIDController().setFF(f, slotID);
  }

  public void setIAccum(double iAccum)
  {
    elevatorLeft.getPIDController().setIAccum(iAccum);
    elevatorRight.getPIDController().setIAccum(iAccum);
  }

  public void setIZone(double IZone)
  {
    setIZone(IZone, activePID);
  }

  public void setIZone(double IZone, int slotID)
  {
    elevatorLeft.getPIDController().setIZone(IZone, slotID);
    elevatorRight.getPIDController().setIZone(IZone, slotID);
  }

  public void setIMaxAccum(double iMaxAccum)
  {
    setIMaxAccum(iMaxAccum, activePID);
  }

  public void setIMaxAccum(double iMaxAccum, int slotID)
  {
    elevatorLeft.getPIDController().setIMaxAccum(iMaxAccum, slotID);
    elevatorRight.getPIDController().setIMaxAccum(iMaxAccum, slotID);
  }

  public void setOutputRange(double min, double max)
  {
    setOutputRange(min, max, activePID);
  }

  public void setOutputRange(double min, double max, int slotID)
  {
    elevatorLeft.getPIDController().setOutputRange(min, max, slotID);
    elevatorRight.getPIDController().setOutputRange(min, max, slotID);
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

  public double getIAccum()
  {
    return elevatorLeft.getPIDController().getIAccum();
  }

  public double getIZone()
  {
    return elevatorLeft.getPIDController().getIZone();
  }

  public double getIMaxAccum()
  {
    return elevatorLeft.getPIDController().getIMaxAccum(activePID);
  }

  public double getOutputMax()
  {
    return getOutputMax(activePID);
  }

  public double getOutputMax(int slotID)
  {
    return elevatorLeft.getPIDController().getOutputMax(slotID);
  }

  public double getOutputMin()
  {
    return getOutputMin(activePID);
  }

  public double getOutputMin(int slotID)
  {
    return elevatorLeft.getPIDController().getOutputMin(slotID);
  }

  private void burnFlash()
  {
    elevatorLeft.burnFlash();
    elevatorRight.burnFlash();
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
