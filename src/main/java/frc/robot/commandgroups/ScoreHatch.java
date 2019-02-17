/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.commands.MoveElevator;
import frc.robot.commands.MoveHatch;
import frc.robot.subsystems.Intake;

public class ScoreHatch extends CommandGroup {
  
  public ScoreHatch() {
    addSequential(new MoveHatch(Intake.Direction.OUT));
    // addSequential(new WaitCommand(0.1));
    addSequential(new ReleaseHatch());
    addSequential(new MoveHatch(Intake.Direction.IN));
    addSequential(new WaitCommand(0.175));
    addSequential(new MoveElevator(-1));
  }
}
