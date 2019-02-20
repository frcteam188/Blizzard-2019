/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autocommandgroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.commandgroups.AutoScore;
import frc.robot.commandgroups.IntakeHatch;
import frc.robot.commandgroups.ReleaseHatch;
import frc.robot.commands.DriveSlow;
import frc.robot.commands.DriveStraight;
import frc.robot.commands.FlipIntake;
import frc.robot.commands.GyroCorrect;
import frc.robot.commands.GyroTurn;
import frc.robot.commands.MoveElevator;
import frc.robot.commands.MoveHatch;
import frc.robot.commands.MoveIntake;
import frc.robot.commands.TuneBaseEncPID;
import frc.robot.commands.TuneBaseGyroPID;
import frc.robot.commands.WaitOnTarget;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;

public class LeftSide2HatchAuto extends CommandGroup {

  public LeftSide2HatchAuto() {
    addSequential(new DriveSlow(77.5, 0, true));
    addSequential(new GyroTurn(89));
    addSequential(new DriveStraight(109, 1));
    addSequential(new GyroTurn(-40));
    addParallel(new MoveElevator(2, Elevator.GamePiece.HATCH));
    addSequential(new DriveSlow(18, 0, false));

    addParallel(new MoveElevator(2, Elevator.GamePiece.HATCH));
    addSequential(new WaitOnTarget());
    addParallel(new MoveIntake(0, 1));
    addSequential(new FlipIntake(Intake.Direction.OUT));
    addSequential(new ReleaseHatch());
    addSequential(new FlipIntake(Intake.Direction.IN));

    addParallel(new MoveElevator(-1));
    addSequential(new DriveSlow(-11, 0, false));
    addSequential(new GyroTurn(219));
    addParallel(new MoveElevator(3, Elevator.GamePiece.HATCH));
    addParallel(new MoveHatch(Intake.Direction.OUT));
    addSequential(new DriveStraight(161.5, 0));
    
    // addSequential(new WaitCommand(0.3));
    addParallel(new MoveElevator(4, Elevator.GamePiece.HATCH));
    addSequential(new WaitCommand(0.5));

    addParallel(new MoveHatch(Intake.Direction.IN));
    addSequential(new DriveStraight(-171.5, 0));
    addSequential(new GyroTurn(-170, false), 0.3);
    addSequential(new GyroTurn(53, true));
    addParallel(new MoveElevator(1, Elevator.GamePiece.HATCH));
    addSequential(new DriveSlow(17, 0, false));
    addSequential(new AutoScore(1, Elevator.GamePiece.HATCH));
    addSequential(new MoveElevator(-1));
  }
}
