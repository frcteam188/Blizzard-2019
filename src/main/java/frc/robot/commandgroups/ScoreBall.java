/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.MoveElevator;
import frc.robot.commands.MoveIntake;

public class ScoreBall extends CommandGroup {
  
  public ScoreBall() {
    addSequential(new MoveIntake(1.0, 1.0));
    addSequential(new MoveElevator(-1));
  }
}
