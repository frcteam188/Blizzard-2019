/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Base extends Subsystem {

  public CANSparkMax frontLeft;
  public CANSparkMax midLeft;
  public CANSparkMax backLeft;
  public CANSparkMax frontRight;
  public CANSparkMax midRight;
  public CANSparkMax backRight;

  AHRS navx;

  private double leftEncOffset;
  private double rightEncOffset;

  public Base() {
    navx = new AHRS(RobotMap.navxPort);
    frontLeft = new CANSparkMax(RobotMap.frontLeft, MotorType.kBrushless);
    midLeft = new CANSparkMax(RobotMap.midLeft, MotorType.kBrushless);
    backLeft = new CANSparkMax(RobotMap.backLeft, MotorType.kBrushless);
    frontRight = new CANSparkMax(RobotMap.frontRight, MotorType.kBrushless);
    midRight = new CANSparkMax(RobotMap.midRight, MotorType.kBrushless);
    backRight = new CANSparkMax(RobotMap.backRight, MotorType.kBrushless);
    leftEncOffset = 0.;
    rightEncOffset = 0.;
  }

  public void driveArcade(double y, double x) {
    double leftPower = (-y + x) * Constants.kBasePower;
    double rightPower = (y + x) * Constants.kBasePower;
    frontLeft.set(leftPower);
    midLeft.set(leftPower);
    backLeft.set(leftPower);
    frontRight.set(rightPower);
    midRight.set(rightPower);
    backRight.set(rightPower);
  }

  public void driveTank(double left, double right) {
    frontLeft.set(left);
    midLeft.set(left);
    backLeft.set(left);
    frontRight.set(-right);
    midRight.set(-right);
    backRight.set(-right);
  }

  public void report()
  {
    SmartDashboard.putNumber("Base Left Enc", getLeftEnc());
    SmartDashboard.putNumber("Base Right Enc", getRightEnc());
    SmartDashboard.putNumber("Base Gyro", getAngle());
    SmartDashboard.putNumber("Base Left Raw Enc", frontLeft.getEncoder().getPosition());
    SmartDashboard.putNumber("Base Right Raw Enc", frontRight.getEncoder().getPosition());
    SmartDashboard.putNumber("Base Left Velocity", getLeftVel());
    SmartDashboard.putNumber("Base Right Velocity", getRightVel());
  }

  public double getAngle()
  {
    return navx.getAngle();
  }

  public double getPitch()
  {
    return navx.getPitch();
  }

  public double getRoll()
  {
    return navx.getRoll();
  }

  public double getYaw()
  {
    return navx.getYaw();
  }

  public void resetGyro()
  {
    navx.reset();
  }

  public void resetLeftEnc()
  {
    leftEncOffset += getLeftEnc();
  }

  public void resetRightEnc()
  {
    rightEncOffset += getRightEnc();
  }

  public double getLeftEnc()
  {
    return frontLeft.getEncoder().getPosition() - leftEncOffset;
  }

  public double getRightEnc()
  {
    return -frontRight.getEncoder().getPosition() - rightEncOffset;
  }

  public double getLeftVel()
  {
    return frontLeft.getEncoder().getVelocity();
  }

  public double getRightVel()
  {
    return -frontRight.getEncoder().getVelocity();
  }

  public void setSetpoint(double setpoint)
  {
    setSetpoint(setpoint, Constants.kBasePIDDefaultType);
  }

  public void setLeftSetpoint(double setpoint)
  {
    setLeftSetpoint(setpoint, Constants.kBasePIDDefaultType);
  }

  public void setRightSetpoint(double setpoint)
  {
    setRightSetpoint(setpoint, Constants.kBasePIDDefaultType);
  }
  
  public void setSetpoint(double setpoint, ControlType type)
  {
    setLeftSetpoint(setpoint, type);
    setRightSetpoint(setpoint, type);
  }

  public void setLeftSetpoint(double setpoint, ControlType type)
  {
    frontLeft.getPIDController().setReference(setpoint, type);
    midLeft.getPIDController().setReference(setpoint, type);
    backLeft.getPIDController().setReference(setpoint, type);
  }

  public void setRightSetpoint(double setpoint, ControlType type)
  {
    setpoint = -setpoint;
    frontRight.getPIDController().setReference(setpoint, type);
    midRight.getPIDController().setReference(setpoint, type);
    backRight.getPIDController().setReference(setpoint, type);
  }

  public void setP(double p)
  {
    frontLeft.getPIDController().setP(p);
    midLeft.getPIDController().setP(p);
    backLeft.getPIDController().setP(p);
    frontRight.getPIDController().setP(p);
    midRight.getPIDController().setP(p);
    backRight.getPIDController().setP(p);
  }

  public void setI(double i)
  {
    frontLeft.getPIDController().setI(i);
    midLeft.getPIDController().setI(i);
    backLeft.getPIDController().setI(i);
    frontRight.getPIDController().setI(i);
    midRight.getPIDController().setI(i);
    backRight.getPIDController().setI(i);
  }

  public void setD(double d)
  {
    frontLeft.getPIDController().setD(d);
    midLeft.getPIDController().setD(d);
    backLeft.getPIDController().setD(d);
    frontRight.getPIDController().setD(d);
    midRight.getPIDController().setD(d);
    backRight.getPIDController().setD(d);
  }

  public void setFF(double f)
  {
    frontLeft.getPIDController().setFF(f);
    midLeft.getPIDController().setFF(f);
    backLeft.getPIDController().setFF(f);
    frontRight.getPIDController().setFF(f);
    midRight.getPIDController().setFF(f);
    backRight.getPIDController().setFF(f);
  }

  public double getP()
  {
    return frontLeft.getPIDController().getP();
  }
  
  public double getI()
  {
    return frontLeft.getPIDController().getI();
  }

  public double getD()
  {
    return frontLeft.getPIDController().getD();
  }

  public double getFF()
  {
    return frontLeft.getPIDController().getFF();
  }

  public void stop() {
    frontLeft.set(0);
    midLeft.set(0);
    backLeft.set(0);
    frontRight.set(0);
    midRight.set(0);
    backRight.set(0);    
  }

  @Override
  public void initDefaultCommand() {
  }
}
