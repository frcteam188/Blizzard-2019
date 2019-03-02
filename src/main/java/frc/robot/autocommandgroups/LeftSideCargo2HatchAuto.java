/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autocommandgroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commandgroups.IntakeHatch;
import frc.robot.commandgroups.ScoreHatch;
import frc.robot.commands.DriveSlow;
import frc.robot.commands.DriveStraight;
import frc.robot.commands.GyroTurn;
import frc.robot.commands.MoveElevator;
import frc.robot.commands.MoveHatch;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake.Direction;

public class LeftSideCargo2HatchAuto extends CommandGroup {

  public LeftSideCargo2HatchAuto() {
    addParallel(new MoveElevator(0, Elevator.GamePiece.HATCH));
    addSequential(new DriveSlow(75, 0, true));
    addSequential(new DriveStraight(75, 0));
    addSequential(new ScoreHatch(false));
    addSequential(new DriveSlow(-20, 0, false));
    addSequential(new GyroTurn(-90));
    addSequential(new DriveStraight(130, 0));
    addSequential(new GyroTurn(-90));
    addSequential(new MoveHatch(Direction.OUT));
    addSequential(new DriveStraight(100, 0));
    addSequential(new IntakeHatch(false));
    addSequential(new DriveStraight(-100, 0));
    addSequential(new GyroTurn(90, true));
    addSequential(new DriveStraight(130, 0));
    addSequential(new GyroTurn(0, true));
    addSequential(new DriveSlow(20, 0, false));
    addSequential(new ScoreHatch(false));
  }
}
