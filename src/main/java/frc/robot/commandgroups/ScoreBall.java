/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.commands.FlipIntake;
import frc.robot.commands.MoveElevator;
import frc.robot.commands.MoveIntake;
import frc.robot.subsystems.Intake;

public class ScoreBall extends CommandGroup {
  
  public ScoreBall() {
    this(Robot.elevator.getActiveSetpoint() == Constants.ballPresets[3]);
  }

  public ScoreBall(boolean pivotOut)
  {
    if (pivotOut)
    {
      addSequential(new FlipIntake(Intake.Direction.OUT));
      addSequential(new WaitCommand(0.3));
    }
    addSequential(new MoveIntake(1.0, 1.4));
    if (pivotOut)
    {
      addParallel(new MoveIntake(1.0, 1.0));
      addSequential(new FlipIntake(Intake.Direction.IN));
      addSequential(new WaitCommand(0.4));
    }
    addSequential(new MoveElevator(-1));
  }
}
