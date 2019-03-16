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
    addSequential(new DriveSlow(75, 0, true), 10.0);
    // addSequential(new ScoreHatch(true));
  }
}
