/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autocommandgroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commandgroups.ScoreHatch;
import frc.robot.commands.DriveSlow;
import frc.robot.commands.DriveStraight;
import frc.robot.commands.GyroCorrect;

public class SideHatchAuto extends CommandGroup {

  public enum Side
  {
    LEFT,
    RIGHT
  }

  /**
   * Add your docs here.
   */
  public SideHatchAuto(Side side) {
    // addSequential(new DriveSlow(176, 0, true));
    addSequential(new DriveSlow(50, 0, true));
    addSequential(new DriveStraight(125, 0));
    if (side == Side.LEFT)
      addSequential(new GyroCorrect(90, false, 5, 0));
    if (side == Side.RIGHT)
      addSequential(new GyroCorrect(-90, false, 5, 0));

    // addSequential(new DriveSlow(3, 0, true), 3.0);
    
    // addSequential(new ScoreHatch(true));
    // addSequential(new DriveSlow(-15, 0, false));
    // if (side == Side.LEFT)
    // addSequential(new GyroCorrect(90, false, 5, 0));
    // if (side == Side.RIGHT)
    // addSequential(new GyroCorrect(-90, false, 5, 0));
    
    // addSequential(new DriveStraight(127, 0));
    
    }
  }
