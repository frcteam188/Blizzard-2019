/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.commands.DrivePushDownWheel;
import frc.robot.commands.DriveSlow;
import frc.robot.commands.HangJoystickDrive;
import frc.robot.commands.PushDownBackPID;
import frc.robot.commands.PushDownFrontPID;
import frc.robot.commands.WaitOnHangBackTarget;
import frc.robot.commands.WaitOnHangFrontTarget;

public class BeginLevel3Hang extends CommandGroup {
  /**
   * Add your docs here.
   */
  public BeginLevel3Hang() {
    addParallel(new PushDownFrontPID(Constants.pushDownFrontLevel3Preset));
    addSequential(new WaitOnHangFrontTarget(2));
    addParallel(new PushDownBackPID(Constants.pushDownBackLevel3Preset));
    addSequential(new WaitOnHangBackTarget(Constants.pushDownBackLevel3Preset));
    addSequential(new WaitCommand(1.0));
    addSequential(new DrivePushDownWheel(1.0, 1.2));
    addSequential(new HangJoystickDrive());
  }
}
