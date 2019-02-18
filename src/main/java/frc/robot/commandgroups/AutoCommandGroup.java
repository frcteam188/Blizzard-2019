/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.DriveSlow;
import frc.robot.commands.DriveStraight;
import frc.robot.commands.GyroCorrect;
import frc.robot.commands.GyroTurn;
import frc.robot.commands.TuneBaseEncPID;
import frc.robot.subsystems.Elevator;

public class AutoCommandGroup extends CommandGroup {

  public AutoCommandGroup() {
    addSequential(new DriveSlow(60, 0));
    // addSequential(new GyroTurn(90));
    // addSequential(new DriveStraight(90, 0));
    // addSequential(new GyroCorrect(-45));
    // addSequential(new AutoScore(2, Elevator.GamePiece.HATCH));
  }
}
