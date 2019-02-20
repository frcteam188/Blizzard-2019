/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.MoveElevator;
import frc.robot.commands.WaitOnTarget;
import frc.robot.subsystems.Elevator;

public class AutoScore extends CommandGroup {

  public AutoScore(int preset, Elevator.GamePiece gamePieceType) {
    addParallel(new MoveElevator(preset, gamePieceType));
    addSequential(new WaitOnTarget());
    if (gamePieceType == Elevator.GamePiece.BALL)
      addSequential(new ScoreBall());
    else if (gamePieceType == Elevator.GamePiece.HATCH)
      addSequential(new ScoreHatch(true));
   // addSequential(new MoveElevator(-1));
  }
}
