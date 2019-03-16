/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autocommandgroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commandgroups.AutoScore;
import frc.robot.commandgroups.ScoreHatch;
import frc.robot.commands.DriveSlow;
import frc.robot.commands.DriveStraight;
import frc.robot.commands.MoveElevator;
import frc.robot.subsystems.Elevator;

public class DriveForwardScoreHatchAuto extends CommandGroup {
  /**
   * Add your docs here.
   */
  public DriveForwardScoreHatchAuto() {
    addParallel(new MoveElevator(0, Elevator.GamePiece.HATCH));
    addSequential(new DriveSlow(122.5, 0, true), 10.0);
    // addSequential(new AutoScore(0, Elevator.GamePiece.HATCH));
    addSequential(new ScoreHatch(true));
    // addSequential(new DriveStraight(-20, 0));
  }
}
