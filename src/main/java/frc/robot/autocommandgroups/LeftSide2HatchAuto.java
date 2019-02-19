/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autocommandgroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commandgroups.AutoScore;
import frc.robot.commandgroups.IntakeHatch;
import frc.robot.commands.DriveSlow;
import frc.robot.commands.DriveStraight;
import frc.robot.commands.GyroCorrect;
import frc.robot.commands.GyroTurn;
import frc.robot.commands.MoveElevator;
import frc.robot.commands.TuneBaseEncPID;
import frc.robot.commands.TuneBaseGyroPID;
import frc.robot.subsystems.Elevator;

public class LeftSide2HatchAuto extends CommandGroup {

  public LeftSide2HatchAuto() {
    addSequential(new DriveSlow(76, 0));
    addSequential(new GyroTurn(90));
    addSequential(new DriveStraight(109, 0));
    addSequential(new GyroTurn(-38));
    addParallel(new MoveElevator(2, Elevator.GamePiece.HATCH));
    addSequential(new DriveSlow(12, 0));
    addSequential(new AutoScore(2, Elevator.GamePiece.HATCH));
    addSequential(new DriveSlow(-17, 0));
    addSequential(new GyroTurn(220));
    addSequential(new DriveStraight(149, 0));
    addSequential(new IntakeHatch());
    addSequential(new DriveStraight(-157, 0));
    addSequential(new GyroTurn(-100, false), 0.3);
    addSequential(new GyroTurn(47, true));
    addSequential(new DriveSlow(16, 0));
    addSequential(new AutoScore(1, Elevator.GamePiece.HATCH));
  }
}
