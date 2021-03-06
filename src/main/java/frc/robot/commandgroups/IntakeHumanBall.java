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
import frc.robot.commands.WaitOnElevatorTarget;
import frc.robot.subsystems.Elevator;

public class IntakeHumanBall extends CommandGroup {

  public IntakeHumanBall() {
    addParallel(new MoveElevator(4, Elevator.GamePiece.BALL));
    addSequential(new WaitOnElevatorTarget(1));
    addParallel(new MoveIntake(-1.0));
  }
}
