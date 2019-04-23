/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autocommandgroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.commandgroups.DelayedCommand;
import frc.robot.commandgroups.DeployHatchIntake;
import frc.robot.commandgroups.RetractHatchIntake;
import frc.robot.commands.CrawlToTarget;
import frc.robot.commands.DriveStraight;
import frc.robot.commands.DriveTowardsTarget;
import frc.robot.commands.GyroTurn;
import frc.robot.commands.MoveHatchIntake;

public class Limelight2HatchAuto extends CommandGroup {

  public enum Side
  {
    LEFT,
    RIGHT
  }

  public Limelight2HatchAuto(Side side) {
    int s = (side == Side.LEFT ? 1 : -1);
    addParallel(new DelayedCommand(0.5, new DeployHatchIntake(false)));
    addSequential(new DriveTowardsTarget(150, 0, false, 3));
    addSequential(new CrawlToTarget());
    addParallel(new MoveHatchIntake(1.0, 2.0));
    addSequential(new WaitCommand(0.8));
    addParallel(new RetractHatchIntake());
    addSequential(new DriveStraight(-10, 0, false, 3));
    addSequential(new GyroTurn(180*s, true, true));
    addParallel(new DelayedCommand(0.5, new DeployHatchIntake()));
    addSequential(new DriveTowardsTarget(123, 180, true, 2));
    addSequential(new CrawlToTarget());
    addSequential(new DriveStraight(-219, 186*s, true, 1), 4.0);
    addParallel(new DelayedCommand(0.5, new DeployHatchIntake()));
    addSequential(new DriveTowardsTarget(20, 0, false, 2));
    addSequential(new CrawlToTarget());
    addSequential(new MoveHatchIntake(1.0, 1.0));
    addSequential(new RetractHatchIntake());
  }
}
