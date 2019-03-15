/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.DriveSlow;
import frc.robot.commands.HangJoystickDrive;
import frc.robot.commands.JoystickDrive;
import frc.robot.commands.PushDownBackPID;

public class FinishLevel3Hang extends CommandGroup {
  /**
   * Add your docs here.
   */
  public FinishLevel3Hang() {
    addSequential(new PushDownBackPID(0));
    addSequential(new HangJoystickDrive());
  }
}
