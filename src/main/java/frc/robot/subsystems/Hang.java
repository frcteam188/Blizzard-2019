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
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.RobotMap;
import frc.robot.commands.JoystickArm;


public class Hang extends Subsystem {

  // Naming based on placement of Sparks, not Neos
  CANSparkMax hangLeft;
  CANSparkMax hangRight;

  CANEncoder armEncoder;

  public Hang()
  {
    hangLeft = new CANSparkMax(RobotMap.hangLeft, MotorType.kBrushless);
    hangRight = new CANSparkMax(RobotMap.hangRight, MotorType.kBrushless);
    hangLeft.setInverted(false);
    hangRight.setInverted(false);
    armEncoder = hangLeft.getEncoder();
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

  public void resetArmEnc()
  {
    armEncoder.setPosition(0);
  }

  public double getArmEnc()
  {
    return armEncoder.getPosition();
  }

  public void setSetpoint(double setpoint)
  {
    setSetpoint(setpoint, ControlType.kPosition);
  }

  public void setSetpoint(double setpoint, ControlType type)
  {
    hangLeft.getPIDController().setReference(setpoint, type, 0);
    hangRight.getPIDController().setReference(setpoint, type, 0);
  }

  public void setP(double p)
  {
    setP(p, 0);
  }

  public void setP(double p, int slotID)
  {
    hangLeft.getPIDController().setP(p, slotID);
    hangRight.getPIDController().setP(p, slotID);
  }

  public void setI(double i)
  {
    setI(i, 0);
  }

  public void setI(double i, int slotID)
  {
    hangLeft.getPIDController().setI(i, slotID);
    hangRight.getPIDController().setI(i, slotID);
  }

  public void setD(double d)
  {
    setD(d, 0);
  }

  public void setD(double d, int slotID)
  {
    hangLeft.getPIDController().setD(d, slotID);
    hangRight.getPIDController().setD(d, slotID);
  }

  public void setFF(double f)
  {
    setFF(f, 0);
  }

  public void setFF(double f, int slotID)
  {
    hangLeft.getPIDController().setFF(f, slotID);
    hangRight.getPIDController().setFF(f, slotID);
  }

  public void setIAccum(double iAccum)
  {
    hangLeft.getPIDController().setIAccum(iAccum);
    hangRight.getPIDController().setIAccum(iAccum);
  }

  public void setIZone(double IZone)
  {
    setIZone(IZone, 0);
  }

  public void setIZone(double IZone, int slotID)
  {
    hangLeft.getPIDController().setIZone(IZone, slotID);
    hangRight.getPIDController().setIZone(IZone, slotID);
  }

  public void setIMaxAccum(double iMaxAccum)
  {
    setIMaxAccum(iMaxAccum, 0);
  }

  public void setIMaxAccum(double iMaxAccum, int slotID)
  {
    hangLeft.getPIDController().setIMaxAccum(iMaxAccum, slotID);
    hangRight.getPIDController().setIMaxAccum(iMaxAccum, slotID);
  }

  public void setOutputRange(double min, double max)
  {
    setOutputRange(min, max, 0);
  }

  public void setOutputRange(double min, double max, int slotID)
  {
    hangLeft.getPIDController().setOutputRange(min, max, slotID);
    hangRight.getPIDController().setOutputRange(min, max, slotID);
  }

  public double getP()
  {
    return hangLeft.getPIDController().getP();
  }
  
  public double getI()
  {
    return hangLeft.getPIDController().getI();
  }

  public double getD()
  {
    return hangLeft.getPIDController().getD();
  }

  public double getFF()
  {
    return hangLeft.getPIDController().getFF();
  }

  public double getIAccum()
  {
    return hangLeft.getPIDController().getIAccum();
  }

  public double getIZone()
  {
    return hangLeft.getPIDController().getIZone();
  }

  public double getIMaxAccum()
  {
    return hangLeft.getPIDController().getIMaxAccum(0);
  }

  public double getOutputMax()
  {
    return getOutputMax(0);
  }

  public double getOutputMax(int slotID)
  {
    return hangLeft.getPIDController().getOutputMax(slotID);
  }

  public double getOutputMin()
  {
    return getOutputMin(0);
  }

  public double getOutputMin(int slotID)
  {
    return hangLeft.getPIDController().getOutputMin(slotID);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new JoystickArm());
  }
}
