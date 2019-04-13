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
import frc.robot.commandgroups.BeginIntermediateHang;
import frc.robot.commandgroups.WaitOnButtonCommand;
import frc.robot.commands.Auto;
import frc.robot.commands.ManualPushDown;


public class HangFront extends Subsystem {

  CANSparkMax pushDownLeft;
  CANSparkMax pushDownRight;

  CANEncoder leftEnc;
  CANEncoder rightEnc;

  double activeSetpoint;

  public HangFront()
  {
    pushDownLeft = new CANSparkMax(RobotMap.pushDownLeft, MotorType.kBrushless);
    pushDownRight = new CANSparkMax(RobotMap.pushDownRight, MotorType.kBrushless);
    pushDownLeft.setInverted(false);
    pushDownRight.setInverted(true);
    pushDownLeft.setOpenLoopRampRate(0.2);
    pushDownRight.setOpenLoopRampRate(0.2);
    pushDownLeft.setClosedLoopRampRate(0.2);
    pushDownRight.setClosedLoopRampRate(0.2);
    leftEnc = pushDownLeft.getEncoder();
    rightEnc = pushDownRight.getEncoder();
    leftEnc.setPositionConversionFactor(Constants.kRevsToInches);
    rightEnc.setPositionConversionFactor(Constants.kRevsToInches);

    activeSetpoint = 0.;
  }

  public void flashPIDValues()
  {
    // Flash up PID
    setP(Constants.mainHangPID[0]);
    setI(Constants.mainHangPID[1]);
    setD(Constants.mainHangPID[2]);
    // setIZone(Constants.elevatorUpIZone, Constants.kElevatorUpPID);
    // MAKE SURE TO ZERO IACCUM IF ABOVE SETPOINT AND IACCUM < 0

    // Flash down PID
    // setP(Constants.elevatorDownPID[0], Constants.kElevatorDownPID);
    // setI(Constants.elevatorDownPID[1], Constants.kElevatorDownPID);
    // setD(Constants.elevatorDownPID[2], Constants.kElevatorDownPID);
    
    setOutputRange(-0.7, 0.7);
  }

  public double getActiveSetpoint()
  {
    return activeSetpoint;
  }

  public void runPID(double setpoint)
  {
    setSetpoint(setpoint);
    activeSetpoint = setpoint;
  }

  public void stopPID()
  {
    setP(0);
    setI(0);
    setD(0);
    setFF(0);
    // setSetpoint(0);
    // setSetpoint(0, ControlType.kDutyCycle);
    // stop();
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

  public void drive(double power)
  {
    power *= Constants.kPushDownFrontPower;
    pushDownLeft.set(-power);
    pushDownRight.set(-power);
  }

  public void execute()
  {
    // driveMain(OI.stick.getRawAxis(OI.pushDownDownAxis) - OI.stick.getRawAxis(OI.pushDownUpAxis));

    // if (OI.stick.getRawButton(5)) driveMain(-1);
    // else driveMain(OI.stick.getRawAxis(2));

    // if (OI.stick.getRawButton(6)) driveCorrection(-1);
    // else driveCorrection(OI.stick.getRawAxis(3));
  }

  public void stop()
  {
    drive(0);
  }

  public void resetEnc()
  {
    leftEnc.setPosition(0);
    rightEnc.setPosition(0);
  }

  public double getEnc()
  {
    return leftEnc.getPosition();
  }

  public void report()
  {
    // SmartDashboard.putNumber("PushDown Main Left Enc", leftEnc.getPosition());
    // SmartDashboard.putNumber("PushDown Main Right Enc", rightEnc.getPosition());
    // SmartDashboard.putNumber("Encoder Correction Enc", getCorrectionEnc());
    // SmartDashboard.putNumber("Tilt Angle", Robot.base.getRoll()); // NOSE UP = NEGATIVE ANGLE
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
    // setDefaultCommand(new ManualPushDown());
    // setDefaultCommand(new WaitOnButtonCommand(OI.stick, OI.intermediateHangPOV, new BeginIntermediateHang()));
  }
}
