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

  TalonSRX drive;

  public HangBack() {

    pushDown = new CANSparkMax(RobotMap.pushDownBack, MotorType.kBrushless);
    pushDown.setInverted(true);
    pushDown.setOpenLoopRampRate(1.0);
    // pushDownBack.enableVoltageCompensation(true);

    pushDownEnc = pushDown.getEncoder();
    pushDownPID = new BackHangPID(Constants.backHangPID[0], Constants.backHangPID[1],
                          Constants.backHangPID[2], 0, Constants.kPushDownBackPower);

    drive = new TalonSRX(RobotMap.hangDrive);
    drive.configOpenloopRamp(0.2);
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
    SmartDashboard.putNumber("Back Hang Current", pushDown.getOutputCurrent());
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ManualPushDown());
  }
  
}
