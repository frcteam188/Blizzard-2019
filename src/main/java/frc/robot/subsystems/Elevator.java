/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

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

  public Elevator()
  {
    elevatorLeft = new CANSparkMax(RobotMap.elevatorLeft, CANSparkMaxLowLevel.MotorType.kBrushless);
    elevatorRight = new CANSparkMax(RobotMap.elevatorRight, CANSparkMaxLowLevel.MotorType.kBrushless);
    upPID = new ElevatorPID(Constants.elevatorUpPID[0], Constants.elevatorUpPID[1], Constants.elevatorUpPID[2], 0);
    downPID = new ElevatorPID(Constants.elevatorDownPID[0], Constants.elevatorDownPID[1], Constants.elevatorDownPID[2], 0);
    elevatorPID = upPID;
    encOffset = 0;
    activePreset = -1;
    elevatorPID.disable();
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
      drive(OI.stick2.getRawAxis(OI.elevatorAxis));
    }
  }

  private void setPID(int preset)
  {
    if (getElevatorEnc() < Constants.elevatorPresets[preset]) elevatorPID = upPID;
    else elevatorPID = downPID;
    elevatorPID.setSetpoint(Constants.elevatorPresets[preset]);
  }

  public void report()
  {
    SmartDashboard.putNumber("Elevator Enc", getElevatorEnc());
    SmartDashboard.putNumber("Elevator PID Setpoint", elevatorPID.getSetpoint());
  }

  public void drive(double power)
  {
    elevatorLeft.set(power);
    elevatorRight.set(power);
  }

  public double getElevatorEnc()
  {
    return elevatorLeft.getEncoder().getPosition() - encOffset;
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
