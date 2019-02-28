/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import frc.robot.Constants;
import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class PushDownMainPID extends PIDSubsystem {

  double power;

  public PushDownMainPID(double p, double i, double d, double setpoint, double power) {
    super("PushDownMainPID", p, i, d);
    this.power = power;
    setSetpoint(setpoint);
    setAbsoluteTolerance(Constants.kPushDownMainTolerance);
  }

  @Override
  public void initDefaultCommand() {
  }

  @Override
  protected double returnPIDInput() {
    return Robot.hang.getMainEnc();
  }

  @Override
  protected void usePIDOutput(double output) {
    output *= power;
    Robot.hang.driveMain(output);
  }
}
