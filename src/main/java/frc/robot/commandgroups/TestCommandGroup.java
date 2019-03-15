/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.DrivePushDownWheel;
import frc.robot.commands.DriveSlow;
import frc.robot.commands.PushDownBackPID;
import frc.robot.commands.PushDownFrontPID;
import frc.robot.commands.WaitOnHangBackTarget;
import frc.robot.commands.WaitOnHangFrontTarget;

public class TestCommandGroup extends CommandGroup {
  /**
   * Add your docs here.
   */
  public TestCommandGroup() {
    addParallel(new PushDownFrontPID(95));
    addSequential(new WaitOnHangFrontTarget(10));
    addParallel(new PushDownBackPID(68));
    addSequential(new WaitOnHangBackTarget(68));
    addSequential(new DrivePushDownWheel(1.0, 1.2));
    addSequential(new PushDownFrontPID(0));
    addParallel(new DrivePushDownWheel(1.0, 100));
    addSequential(new DriveSlow(19, 0, true));
    addParallel(new DrivePushDownWheel(0, 0.01));
    addSequential(new PushDownBackPID(0));
    addSequential(new DriveSlow(12, 0, true));
  }
}
