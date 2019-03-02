/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.DriveSlow;
import frc.robot.commands.GyroCorrect;
import frc.robot.commands.GyroTurn;
import frc.robot.commands.PushDownCorrection;
import frc.robot.commands.PushDownMain;

public class Level2Hang extends CommandGroup {

  public Level2Hang() {

    // front down 1 second
    // drive forward 10 inches slowly
    // front up 1 second
    // back down 0.5 seconds
    // drive forward 15 inches
    // back up 0.5 seconds

    addSequential(new PushDownMain(1.0, 0.75));
    addSequential(new DriveSlow(13, 0, false));
    addSequential(new PushDownMain(-1.0, 0.7));
    addSequential(new PushDownCorrection(1.0, 0.8));
    addSequential(new DriveSlow(20, 0, false));
    addSequential(new PushDownCorrection(-1.0, 0.55));
    addSequential(new DriveSlow(14, 0, false));
    addSequential(new GyroCorrect(90, false, 5, 0));
    
    addSequential(new PushDownMain(1.0, 1.5));
    addSequential(new DriveSlow(13, 0, false));

  }
}
