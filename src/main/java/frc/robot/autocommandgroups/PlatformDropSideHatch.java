/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autocommandgroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.DriveSlow;
import frc.robot.commands.GyroCorrect;
import frc.robot.commands.GyroTurn;
import frc.robot.commands.PushDownCorrection;
import frc.robot.commands.PushDownMainEnc;

public class PlatformDropSideHatch extends CommandGroup {

  public enum Side
  {
    LEFT,
    RIGHT
  }

  public PlatformDropSideHatch(Side side) {
    addSequential(new DriveSlow(-19, 0, true));
    addSequential(new PushDownCorrection(1.0, 0.5));
    addSequential(new DriveSlow(-20.5, 0, true));
    addSequential(new PushDownMainEnc(1.0, 35));
    addSequential(new PushDownCorrection(-0.3, 0.8));
    addSequential(new DriveSlow(-15.5, 0, true));
    addSequential(new PushDownMainEnc(-1.0, -32), 2.0);
    addSequential(new DriveSlow(-176, 0, true));
    if (side == Side.LEFT)
      addSequential(new GyroCorrect(-90, false, 5, 0));
    if (side == Side.RIGHT)
      addSequential(new GyroCorrect(90, false, 5, 0));
    addSequential(new DriveSlow(8, 0, true));
  }
}
