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
import frc.robot.commandgroups.ReleaseHatch;
import frc.robot.commands.DriveSlow;
import frc.robot.commands.DriveStraight;
import frc.robot.commands.FlipIntake;
import frc.robot.commands.GyroCorrect;
import frc.robot.commands.GyroTurn;
import frc.robot.commands.MoveElevator;
import frc.robot.commands.MoveIntake;
import frc.robot.commands.TuneBaseEncPID;
import frc.robot.commands.TuneBaseGyroPID;
import frc.robot.commands.WaitOnTarget;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;

public class RightSide2HatchAuto extends CommandGroup {

  //Currently more up to date than Left

  public RightSide2HatchAuto() {
    addSequential(new DriveSlow(77.5, 0, true));
    addSequential(new GyroCorrect(-90, false, 7, 0)); // RIGHT-
    addSequential(new DriveStraight(111, 0)); // 104 RIGHT-
    addSequential(new GyroCorrect(27.75, false, 7, 0)); // RIGHT+
    addParallel(new MoveElevator(0, Elevator.GamePiece.HATCH));
    addSequential(new DriveSlow(12, 0, false), 1.5); //changed from 20

    addParallel(new MoveElevator(0, Elevator.GamePiece.HATCH));
    addSequential(new WaitOnTarget());
    addParallel(new MoveIntake(0, 1));
    addSequential(new FlipIntake(Intake.Direction.OUT));
    addSequential(new ReleaseHatch());
    addSequential(new FlipIntake(Intake.Direction.IN));

    addParallel(new MoveElevator(-1));
    addSequential(new DriveSlow(-5, 0, false)); //changed from -13
    addSequential(new GyroCorrect(90, true, 7, 0)); // RIGHT+
    // addParallel(new MoveElevator(4, Elevator.GamePiece.HATCH));
    // addParallel(new MoveHatch(Intake.Direction.OUT));
    // addSequential(new DriveStraight(154.5, 1)); // 179.5


    // addSequential(new IntakeHatch());
    // addSequential(new DriveStraight(-179.5, 0, true, 0.75, -60));
    // addSequential(new GyroTurn(-45));
    // addSequential(new DriveStraight(-40, 0));
    // addSequential(new GyroTurn(45));
    // addSequential(new DriveStraight(-20, 0));
  }
}
