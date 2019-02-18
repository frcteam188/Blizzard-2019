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
    addSequential(new DriveSlow(80, 0));
    addSequential(new GyroTurn(90));
    addSequential(new DriveStraight(90, 0));
    addSequential(new GyroTurn(-25));
    addSequential(new DriveSlow(14, 0));
    addSequential(new AutoScore(2, Elevator.GamePiece.HATCH));
    addSequential(new DriveSlow(-16, 0));
    addSequential(new GyroTurn(205));
    addSequential(new DriveStraight(139, 0));
    addSequential(new IntakeHatch());
    addSequential(new DriveStraight(-139, 0));
    addSequential(new GyroTurn(-100), 0.2);
    addSequential(new GyroTurn(-105));
    addSequential(new DriveSlow(16, 0));
    addSequential(new AutoScore(1, Elevator.GamePiece.HATCH));
  }
}
