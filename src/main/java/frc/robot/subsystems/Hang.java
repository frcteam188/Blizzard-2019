/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.Auto;


public class Hang extends Subsystem {


  CANSparkMax pushDownLeft;
  CANSparkMax pushDownRight;
  TalonSRX pushDownBack;

  CANEncoder mainLeftEnc;
  CANEncoder mainRightEnc;
  Encoder correctionEnc;

  DigitalInput frontSensor;
  DigitalInput backSensor;

  PushDownCorrectionPID correctionPID;


  public Hang()
  {
    pushDownLeft = new CANSparkMax(RobotMap.pushDownLeft, MotorType.kBrushless);
    pushDownRight = new CANSparkMax(RobotMap.pushDownRight, MotorType.kBrushless);
    pushDownLeft.setInverted(false);
    pushDownRight.setInverted(true);
    pushDownBack = new TalonSRX(RobotMap.pushDownBack);
    mainLeftEnc = pushDownLeft.getEncoder();
    mainRightEnc = pushDownRight.getEncoder();
    mainLeftEnc.setPositionConversionFactor(Constants.kRevsToInches);
    mainRightEnc.setPositionConversionFactor(Constants.kRevsToInches);
    frontSensor = new DigitalInput(RobotMap.frontHangSensor);
    backSensor = new DigitalInput(RobotMap.backHangSensor);
    correctionEnc = new Encoder(RobotMap.pushDownCorrectionEncoder[0], RobotMap.pushDownCorrectionEncoder[1]);
    correctionPID = new PushDownCorrectionPID(Constants.pushDownCorrectionPID[0], Constants.pushDownCorrectionPID[1],
                          Constants.pushDownCorrectionPID[2], 0, Constants.kPushDownCorrectionPower);
  }

  public static double getHabAngle()
  {
    switch (Robot.getSelectedAuto().getSide())
    {
      case LEFT:
        return -90;
      case CENTRE:
        return 180;
      case RIGHT:
        return 90;
      default:
        return 0;
    }
  }

  public void driveMain(double power)
  {
    power *= Constants.kPushDownMainPower;
    pushDownLeft.set(power);
    pushDownRight.set(power);
  }

  public void driveCorrection(double power)
  {
    pushDownBack.set(ControlMode.PercentOutput, power);
  }

  public void execute()
  {
    driveMain(OI.stick.getRawAxis(OI.pushDownDownAxis) - OI.stick.getRawAxis(OI.pushDownUpAxis));
  }

  public void stop()
  {
    driveMain(0);
    driveCorrection(0);
  }

  public boolean getFrontSensor()
  {
    return frontSensor.get();
  }

  public boolean getBackSensor()
  {
    return backSensor.get();
  }

  public void resetMainEnc()
  {
    mainLeftEnc.setPosition(0);
    mainRightEnc.setPosition(0);
  }

  public double getMainEnc()
  {
    return mainLeftEnc.getPosition();
  }

  public void resetCorrectionEnc()
  {
    pushDownBack.getSensorCollection().setQuadraturePosition(0, 0);
  }

  public double getCorrectionEnc()
  {
    return pushDownBack.getSensorCollection().getQuadraturePosition();
  }

  public void report()
  {
    SmartDashboard.putNumber("PushDown Main Left Enc", mainLeftEnc.getPosition());
    SmartDashboard.putNumber("PushDown Main Right Enc", mainRightEnc.getPosition());
    // SmartDashboard.putNumber("Encoder Correction Enc", getCorrectionEnc());
    SmartDashboard.putNumber("Tilt Angle", Robot.base.getRoll()); // NOSE UP = NEGATIVE ANGLE
  }

  public void setSetpoint(double setpoint)
  {
    setSetpoint(setpoint, ControlType.kPosition);
  }

  public void setSetpoint(double setpoint, ControlType type)
  {
    pushDownLeft.getPIDController().setReference(setpoint, type, 0);
    pushDownRight.getPIDController().setReference(setpoint, type, 0);
  }

  public void setP(double p)
  {
    setP(p, 0);
  }

  public void setP(double p, int slotID)
  {
    pushDownLeft.getPIDController().setP(p, slotID);
    pushDownRight.getPIDController().setP(p, slotID);
  }

  public void setI(double i)
  {
    setI(i, 0);
  }

  public void setI(double i, int slotID)
  {
    pushDownLeft.getPIDController().setI(i, slotID);
    pushDownRight.getPIDController().setI(i, slotID);
  }

  public void setD(double d)
  {
    setD(d, 0);
  }

  public void setD(double d, int slotID)
  {
    pushDownLeft.getPIDController().setD(d, slotID);
    pushDownRight.getPIDController().setD(d, slotID);
  }

  public void setFF(double f)
  {
    setFF(f, 0);
  }

  public void setFF(double f, int slotID)
  {
    pushDownLeft.getPIDController().setFF(f, slotID);
    pushDownRight.getPIDController().setFF(f, slotID);
  }

  public void setIAccum(double iAccum)
  {
    pushDownLeft.getPIDController().setIAccum(iAccum);
    pushDownRight.getPIDController().setIAccum(iAccum);
  }

  public void setIZone(double IZone)
  {
    setIZone(IZone, 0);
  }

  public void setIZone(double IZone, int slotID)
  {
    pushDownLeft.getPIDController().setIZone(IZone, slotID);
    pushDownRight.getPIDController().setIZone(IZone, slotID);
  }

  public void setIMaxAccum(double iMaxAccum)
  {
    setIMaxAccum(iMaxAccum, 0);
  }

  public void setIMaxAccum(double iMaxAccum, int slotID)
  {
    pushDownLeft.getPIDController().setIMaxAccum(iMaxAccum, slotID);
    pushDownRight.getPIDController().setIMaxAccum(iMaxAccum, slotID);
  }

  public void setOutputRange(double min, double max)
  {
    setOutputRange(min, max, 0);
  }

  public void setOutputRange(double min, double max, int slotID)
  {
    pushDownLeft.getPIDController().setOutputRange(min, max, slotID);
    pushDownRight.getPIDController().setOutputRange(min, max, slotID);
  }

  public double getP()
  {
    return pushDownLeft.getPIDController().getP();
  }
  
  public double getI()
  {
    return pushDownLeft.getPIDController().getI();
  }

  public double getD()
  {
    return pushDownLeft.getPIDController().getD();
  }

  public double getFF()
  {
    return pushDownLeft.getPIDController().getFF();
  }

  public double getIAccum()
  {
    return pushDownLeft.getPIDController().getIAccum();
  }

  public double getIZone()
  {
    return pushDownLeft.getPIDController().getIZone();
  }

  public double getIMaxAccum()
  {
    return pushDownLeft.getPIDController().getIMaxAccum(0);
  }

  public double getOutputMax()
  {
    return getOutputMax(0);
  }

  public double getOutputMax(int slotID)
  {
    return pushDownLeft.getPIDController().getOutputMax(slotID);
  }

  public double getOutputMin()
  {
    return getOutputMin(0);
  }

  public double getOutputMin(int slotID)
  {
    return pushDownLeft.getPIDController().getOutputMin(slotID);
  }

  @Override
  public void initDefaultCommand() {
  }
}
