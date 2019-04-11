/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.subsystems.BaseGyroPID;

public class JoystickDrive extends Command {

  boolean handoff;
  BaseGyroPID turnPID;
  boolean prevBearingLocked;

  public JoystickDrive()
  {
    this(false);
  }

  public JoystickDrive(boolean handoff) {
    requires(Robot.base);
    this.handoff = handoff;
    prevBearingLocked = false;
  }
  
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.base.setOpenLoopRampRate(0);
    turnPID = new BaseGyroPID(Constants.baseGyroCorrectionPID[0], Constants.baseGyroCorrectionPID[1], Constants.baseGyroCorrectionPID[2],
    0, Constants.kGyroCorrectionPower, 0, true);
    System.out.println("Joystick Driving");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double forward = OI.stick.getRawAxis(OI.fwdAxis);
    double turn = OI.stick.getRawAxis(OI.turnAxis);
    if (OI.slowButton.get())
    {
      forward *= Constants.kBaseSlowPower;
      turn *= Constants.kBaseSlowPower;
    }
    else if (OI.fastButton.get())
    {
      forward *= Constants.kBaseFastPower;
      turn *= Constants.kBaseFastPower;
    }
    else
    {
      forward *= Constants.kBaseDefaultPower;
      turn *= Constants.kBaseDefaultPower;
    }
    // boolean locked = OI.bearingLockButton.get();
    boolean locked = false;
    if (locked)
    {
      if (!prevBearingLocked) turnPID.enable();
      Robot.base.encPIDPower = -forward;
      Robot.base.driveStored();
    }
    else
    {
      if (prevBearingLocked) turnPID.disable();
      Robot.base.driveArcade(forward, turn * Constants.kBaseTeleopTurnPower);
    }
    prevBearingLocked = locked;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return handoff && OI.autoHandoffButton.get();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.base.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
