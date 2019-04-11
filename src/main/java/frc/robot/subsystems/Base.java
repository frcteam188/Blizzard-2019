/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.RobotMap;
import frc.robot.commands.JoystickDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMaxLowLevel.PeriodicFrame;

public class Base extends Subsystem {

  public CANSparkMax frontLeft;
  public CANSparkMax midLeft;
  // public CANSparkMax backLeft;
  public CANSparkMax frontRight;
  public CANSparkMax midRight;
  // public CANSparkMax backRight;
  
  private CANEncoder leftEncoder;
  private CANEncoder rightEncoder;

  AHRS navx;

  public double encPIDPower;
  public double gyroPIDPower;

  public Base() {
    navx = new AHRS(RobotMap.navxPort);
    frontLeft = new CANSparkMax(RobotMap.frontLeft, MotorType.kBrushless);
    midLeft = new CANSparkMax(RobotMap.midLeft, MotorType.kBrushless);
    // backLeft = new CANSparkMax(RobotMap.backLeft, MotorType.kBrushless);
    frontRight = new CANSparkMax(RobotMap.frontRight, MotorType.kBrushless);
    midRight = new CANSparkMax(RobotMap.midRight, MotorType.kBrushless);
    // backRight = new CANSparkMax(RobotMap.backRight, MotorType.kBrushless);
    frontLeft.setPeriodicFramePeriod(PeriodicFrame.kStatus2, 10);
    frontRight.setPeriodicFramePeriod(PeriodicFrame.kStatus2, 10);

    frontLeft.setInverted(false);
    midLeft.setInverted(false);
    // backLeft.setInverted(false);
    frontRight.setInverted(true);
    midRight.setInverted(true);
    // backRight.setInverted(true);
    leftEncoder = frontLeft.getEncoder();
    rightEncoder = frontRight.getEncoder();

    leftEncoder.setPositionConversionFactor(Constants.kRevsToInches);
    rightEncoder.setPositionConversionFactor(Constants.kRevsToInches);

    encPIDPower = 0;
    gyroPIDPower = 0;
  }

  public void driveArcade(double y, double x) {
    double leftPower = -y + x;
    double rightPower = -y - x;
    driveTank(leftPower, rightPower);
  }

  public void driveTank(double left, double right) {
    frontLeft.set(left);
    midLeft.set(left);
    // backLeft.set(left);
    frontRight.set(right);
    midRight.set(right);
    // backRight.set(right);
  }

  public void driveStored()
  {
    driveArcade(-encPIDPower, gyroPIDPower);
  }

  public void setOpenLoopRampRate(double rate)
  {
    frontLeft.setOpenLoopRampRate(rate);
    midLeft.setOpenLoopRampRate(rate);
    // backLeft.setOpenLoopRampRate(rate);
    frontRight.setOpenLoopRampRate(rate);
    midRight.setOpenLoopRampRate(rate);
    // backRight.setOpenLoopRampRate(rate);

  }

  public void setClosedLoopRampRate(double rate)
  {
    frontLeft.setClosedLoopRampRate(rate);
    midLeft.setClosedLoopRampRate(rate);
    // backLeft.setClosedLoopRampRate(rate);
    frontRight.setClosedLoopRampRate(rate);
    midRight.setClosedLoopRampRate(rate);
    // backRight.setClosedLoopRampRate(rate);
  }

  public void setCoast() {
   // frontLeft.setParameter(CANSpaark., value)
  }

  public void report()
  {
    SmartDashboard.putNumber("Base Left Enc", getLeftEnc());
    SmartDashboard.putNumber("Base Right Enc", getRightEnc());
    SmartDashboard.putNumber("Base Gyro", getAngle());
    // SmartDashboard.putNumber("Base Left Velocity", getLeftVel());
    // SmartDashboard.putNumber("Base Right Velocity", getRightVel());
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

  public void resetEnc()
  {
    resetLeftEnc();
    resetRightEnc();
  }

  public void resetLeftEnc()
  {
    leftEncoder.setPosition(0);
  }

  public void resetRightEnc()
  {
    rightEncoder.setPosition(0);
  }

  public double getLeftEnc()
  {
    return leftEncoder.getPosition();
  }

  public double getRightEnc()
  {
    return rightEncoder.getPosition();
  }

  public double getLeftVel()
  {
    return frontLeft.getEncoder().getVelocity();
  }

  public double getRightVel()
  {
    return frontRight.getEncoder().getVelocity();
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
    // backLeft.getPIDController().setReference(setpoint, type);
  }

  public void setRightSetpoint(double setpoint, ControlType type)
  {
    frontRight.getPIDController().setReference(setpoint, type);
    midRight.getPIDController().setReference(setpoint, type);
    // backRight.getPIDController().setReference(setpoint, type);
  }

  public void setP(double p)
  {
    frontLeft.getPIDController().setP(p);
    midLeft.getPIDController().setP(p);
    // backLeft.getPIDController().setP(p);
    frontRight.getPIDController().setP(p);
    midRight.getPIDController().setP(p);
    // backRight.getPIDController().setP(p);
  }

  public void setI(double i)
  {
    frontLeft.getPIDController().setI(i);
    midLeft.getPIDController().setI(i);
    // backLeft.getPIDController().setI(i);
    frontRight.getPIDController().setI(i);
    midRight.getPIDController().setI(i);
    // backRight.getPIDController().setI(i);
  }

  public void setD(double d)
  {
    frontLeft.getPIDController().setD(d);
    midLeft.getPIDController().setD(d);
    // backLeft.getPIDController().setD(d);
    frontRight.getPIDController().setD(d);
    midRight.getPIDController().setD(d);
    // backRight.getPIDController().setD(d);
  }

  public void setFF(double f)
  {
    frontLeft.getPIDController().setFF(f);
    midLeft.getPIDController().setFF(f);
    // backLeft.getPIDController().setFF(f);
    frontRight.getPIDController().setFF(f);
    midRight.getPIDController().setFF(f);
    // backRight.getPIDController().setFF(f);
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
    // backLeft.set(0);
    frontRight.set(0);
    midRight.set(0);
    // backRight.set(0);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new JoystickDrive());
  }
}
