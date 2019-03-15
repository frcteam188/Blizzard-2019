/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import frc.robot.Robot;

public class SafetyRetractPushDownBack extends ConditionalCommand {
  public SafetyRetractPushDownBack() {
    super(new PushDownBackEnc(-0.5, 0));
  }
  
  @Override
  protected boolean condition() {
    return Robot.hangFront.getEnc() <= 2.0;
  }
}
