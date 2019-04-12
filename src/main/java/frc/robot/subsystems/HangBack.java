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

import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.robot.commands.ManualPushDown;

/**
 * Add your docs here.
 */
public class HangBack extends Subsystem {
  
  CANSparkMax pushDown;
  CANEncoder pushDownEnc;
  BackHangPID pushDownPID;
  double activeSetpoint;

  TalonSRX drive;

  public HangBack() {

    pushDown = new CANSparkMax(RobotMap.pushDownBack, MotorType.kBrushless);
    pushDown.setInverted(false);
    pushDown.setOpenLoopRampRate(0.2);
    pushDown.setClosedLoopRampRate(0.2);
    // pushDownBack.enableVoltageCompensation(true);

    pushDownEnc = pushDown.getEncoder();
    pushDownPID = new BackHangPID(Constants.backHangPID[0], Constants.backHangPID[1],
                          Constants.backHangPID[2], 0, Constants.kPushDownBackPower);

    drive = new TalonSRX(RobotMap.hangDrive);
    drive.setInverted(true);
    drive.configOpenloopRamp(0.2);

    activeSetpoint = 0.;
  }

  public void drivePushDown(double power)
  {
    power *= Constants.kPushDownBackPower;
    pushDown.set(power);
  }

  public void driveForward(double power)
  {
    power *= Constants.kHangDrivePower;
    drive.set(ControlMode.PercentOutput, power);
  }

  public void driveForward(JoystickButton fwdButton, JoystickButton revButton)
  {
    if(fwdButton.get()) drive.set(ControlMode.PercentOutput, Constants.kHangDrivePower);
    else if(revButton.get()) drive.set(ControlMode.PercentOutput, -Constants.kHangDrivePower);
    else drive.set(ControlMode.PercentOutput, 0);
  }

  public void stop()
  {
    drivePushDown(0);
    driveForward(0);
  }

  public void resetEnc()
  {
    pushDownEnc.setPosition(0);
  }

  public double getEnc()
  {
    return pushDownEnc.getPosition();
  }

  public void report()
  {
    // SmartDashboard.putNumber("Back Hang Current", pushDown.getOutputCurrent());
    // SmartDashboard.putNumber("PushDown Main Back Enc", getEnc());
  }

  public void flashPIDValues()
  {
    setP(Constants.backHangPID[0]);
    setI(Constants.backHangPID[1]);
    setD(Constants.backHangPID[2]);
    // Flash up PID
    // setIZone(Constants.elevatorUpIZone, Constants.kElevatorUpPID);
    // MAKE SURE TO ZERO IACCUM IF ABOVE SETPOINT AND IACCUM < 0

    // Flash down PID
    // setP(Constants.elevatorDownPID[0], Constants.kElevatorDownPID);
    // setI(Constants.elevatorDownPID[1], Constants.kElevatorDownPID);
    // setD(Constants.elevatorDownPID[2], Constants.kElevatorDownPID);
    // setOutputRange(-0.7, 0.7);
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

  public void setSetpoint(double setpoint)
  {
    setSetpoint(setpoint, ControlType.kPosition);
  }

  public void setSetpoint(double setpoint, ControlType type)
  {
    pushDown.getPIDController().setReference(setpoint, type, 0);
  }

  public void setP(double p)
  {
    setP(p, 0);
  }

  public void setP(double p, int slotID)
  {
    pushDown.getPIDController().setP(p, slotID);
  }

  public void setI(double i)
  {
    setI(i, 0);
  }

  public void setI(double i, int slotID)
  {
    pushDown.getPIDController().setI(i, slotID);
  }

  public void setD(double d)
  {
    setD(d, 0);
  }

  public void setD(double d, int slotID)
  {
    pushDown.getPIDController().setD(d, slotID);
  }

  public void setFF(double f)
  {
    setFF(f, 0);
  }

  public void setFF(double f, int slotID)
  {
    pushDown.getPIDController().setFF(f, slotID);
  }

  public void setIAccum(double iAccum)
  {
    pushDown.getPIDController().setIAccum(iAccum);
  }

  public void setIZone(double IZone)
  {
    setIZone(IZone, 0);
  }

  public void setIZone(double IZone, int slotID)
  {
    pushDown.getPIDController().setIZone(IZone, slotID);
  }

  public void setIMaxAccum(double iMaxAccum)
  {
    setIMaxAccum(iMaxAccum, 0);
  }

  public void setIMaxAccum(double iMaxAccum, int slotID)
  {
    pushDown.getPIDController().setIMaxAccum(iMaxAccum, slotID);
  }

  public void setOutputRange(double min, double max)
  {
    setOutputRange(min, max, 0);
  }

  public void setOutputRange(double min, double max, int slotID)
  {
    pushDown.getPIDController().setOutputRange(min, max, slotID);
  }

  public double getP()
  {
    return pushDown.getPIDController().getP();
  }
  
  public double getI()
  {
    return pushDown.getPIDController().getI();
  }

  public double getD()
  {
    return pushDown.getPIDController().getD();
  }

  public double getFF()
  {
    return pushDown.getPIDController().getFF();
  }

  public double getIAccum()
  {
    return pushDown.getPIDController().getIAccum();
  }

  public double getIZone()
  {
    return pushDown.getPIDController().getIZone();
  }

  public double getIMaxAccum()
  {
    return pushDown.getPIDController().getIMaxAccum(0);
  }

  public double getOutputMax()
  {
    return getOutputMax(0);
  }

  public double getOutputMax(int slotID)
  {
    return pushDown.getPIDController().getOutputMax(slotID);
  }

  public double getOutputMin()
  {
    return getOutputMin(0);
  }

  public double getOutputMin(int slotID)
  {
    return pushDown.getPIDController().getOutputMin(slotID);
  }

  @Override
  public void initDefaultCommand() {
    // setDefaultCommand(new ManualPushDown());
  }
  
}
