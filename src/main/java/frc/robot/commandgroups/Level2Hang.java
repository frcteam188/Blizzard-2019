/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.Robot;
import frc.robot.commands.DriveSlow;
import frc.robot.commands.DriveStraight;
import frc.robot.commands.GyroCorrect;
import frc.robot.commands.GyroTurn;
import frc.robot.commands.PushDownCorrection;
import frc.robot.commands.PushDownMain;
import frc.robot.commands.PushDownMainEnc;

public class Level2Hang extends CommandGroup {

  public enum Side
  {
    // FROM PERSPECTIVE OF DRIVER STATION
    LEFT,
    RIGHT
  }

  public Level2Hang(Side side) {

    // front down 1 second
    // drive forward 10 inches slowly
    // front up 1 second
    // back down 0.5 seconds
    // drive forward 15 inches
    // back up 0.5 seconds

    addParallel(new DriveSlow(9999999, 0, false));
    // addSequential(new PushDownMain(1.0, 0.75));
    addSequential(new PushDownMainEnc(1.0, 50));
    addSequential(new WaitCommand(1.0));
    addSequential(new PushDownMain(-1.0, 0.6));
    addSequential(new PushDownCorrection(1.0, 0.8));
    addSequential(new DriveSlow(20, 0, false), 2.0);
    addSequential(new PushDownCorrection(-1.0, 0.55));
    addSequential(new DriveSlow(10, 0, false), 2.0);

    // if (side == Side.LEFT)
    //   addSequential(new GyroCorrect(-90, false, 5, 0));
    // if (side == Side.RIGHT)
    //   addSequential(new GyroCorrect(90, false, 5, 0));
    
    // addParallel(new PushDownMain(1.0, 0.92));
    // addSequential(new WaitCommand(0.25));
    // addSequential(new DriveSlow(13, 0, false));
    // addSequential(new DriveSlow(30, 0, false), 1.5);
    // addParallel(new PushDownMain(-1.0, 0.8));
    // addSequential(new DriveStraight(14, 0, false), 1.5);
    // addParallel(new PushDownCorrection(1.0, 1.3));
    // addSequential(new DriveStraight(14, 0, false), 1.5);
    // addParallel(new PushDownCorrection(0.2, 1.3));
    // addSequential(new DriveStraight(30, 0, false));
    // addSequential(new PushDownCorrection(-1.0, 0.5));
  }
}
