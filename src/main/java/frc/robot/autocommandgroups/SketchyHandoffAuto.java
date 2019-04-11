/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autocommandgroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.DriveStraight;
import frc.robot.commands.JoystickDrive;

public class SketchyHandoffAuto extends CommandGroup {

  public enum Side
  {
    LEFT,
    RIGHT
  }

  public SketchyHandoffAuto(Side side) {
    int s = (side == Side.LEFT ? 1 : -1);
    addSequential(new JoystickDrive(true));
    addSequential(new DriveStraight(-20, -90*s, true));
    addSequential(new DriveStraight(90, 180, true));
    addSequential(new JoystickDrive(true));
  }
}
