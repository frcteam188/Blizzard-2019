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
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Elevator extends Subsystem {
  
  private CANSparkMax elevatorLeft;
  private CANSparkMax elevatorRight;

  private ElevatorPID elevatorPID;

  private double encOffset;
  private boolean presetActive;

  public Elevator()
  {
    elevatorLeft = new CANSparkMax(RobotMap.elevatorLeft, CANSparkMaxLowLevel.MotorType.kBrushless);
    elevatorRight = new CANSparkMax(RobotMap.elevatorRight, CANSparkMaxLowLevel.MotorType.kBrushless);
    elevatorPID = new ElevatorPID(0);
    encOffset = 0;
    presetActive = false;
    elevatorPID.disable();
  }

  public void execute()
  {
    presetActive = false;
    for (int i = 0; i < OI.elevatorButtons.length; ++i)
    {
      if (OI.elevatorButtons[i].get())
      {
        elevatorPID.setSetpoint(Constants.elevatorPresets[i]);
        presetActive = true;
      }
    }
    if (presetActive)
    {
      elevatorPID.enable();
    }
    else
    {
      elevatorPID.disable();
      drive(OI.stick2.getRawAxis(OI.elevatorAxis));
    }
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
