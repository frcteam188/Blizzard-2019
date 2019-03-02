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

public class DriveUntilJoystick extends Command {
  BaseGyroPID gyroPID;
  double power;
  double angle;
  double initialEnc;
  double overrideDistance;
  int onTargetCount;
  int onTargetThreshold;

  public DriveUntilJoystick(double power, double angle, double overrideDistance) {
    requires(Robot.base);
    this.power = power;
    this.angle = angle;
    this.overrideDistance = overrideDistance;
    this.onTargetThreshold = 5;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    onTargetCount = 0;
    double relativeAngle = angle + Robot.base.getAngle();
    this.initialEnc = Robot.base.getLeftEnc();
    Robot.base.setOpenLoopRampRate(2.0);
    gyroPID = new BaseGyroPID(Constants.baseGyroCorrectionPID[0], Constants.baseGyroCorrectionPID[1],
                              Constants.baseGyroCorrectionPID[2], relativeAngle, Constants.kGyroCorrectionPower, true);
    gyroPID.enable();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.base.encPIDPower = power;
    Robot.base.driveStored();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Robot.base.getLeftEnc() - initialEnc <= -171.5
            || (Math.abs(OI.stick.getRawAxis(OI.fwdAxis)) > 0.05 || Math.abs(OI.stick.getRawAxis(OI.fwdAxis)) > 0.05)
            && Robot.base.getLeftEnc() - initialEnc < -overrideDistance;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    gyroPID.disable();
    Robot.base.stop();
    System.out.println("DriveUntilJoystick ended.");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
