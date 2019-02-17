/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.RobotMap;


public class Hang extends Subsystem {

  // Naming based on placement of Sparks, not Neos
  CANSparkMax hangLeft;
  CANSparkMax hangRight;

  public Hang()
  {
    hangLeft = new CANSparkMax(RobotMap.hangLeft, MotorType.kBrushless);
    hangRight = new CANSparkMax(RobotMap.hangRight, MotorType.kBrushless);
    hangLeft.setInverted(false);
    hangRight.setInverted(false);
  }

  public void drive(double power)
  {
    power *= Constants.kHangArmPower;
    hangLeft.set(power);
    hangRight.set(power);
  }

  public void execute()
  {
    // if (OI.hangArmOut.get()) drive(1.0);
    // else if (OI.hangArmIn.get()) drive(-1.0);
    // else stop();
    // drive(OI.stick.getRawAxis(5));
  }

  public void stop()
  {
    drive(0);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
