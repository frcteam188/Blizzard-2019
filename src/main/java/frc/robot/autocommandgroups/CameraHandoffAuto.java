/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autocommandgroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commandgroups.AutoScore;
import frc.robot.commands.DriveStraight;
import frc.robot.commands.GyroCorrect;
import frc.robot.commands.GyroTurn;
import frc.robot.commands.JoystickDrive;
import frc.robot.subsystems.Elevator;

public class CameraHandoffAuto extends CommandGroup {
  
  public enum Side
  {
    LEFT,
    RIGHT
  }

  public CameraHandoffAuto(Side side) {
    int s = (side == Side.RIGHT ? 1 : -1);
    // addSequential(new DriveStraight(150, 0));
    addSequential(new JoystickDrive(true));
    // addSequential(new AutoScore(3, Elevator.GamePiece.HATCH));
    addSequential(new DriveStraight(-10, 0, false, 1));
    addSequential(new GyroTurn(180*s, true));
    addSequential(new DriveStraight(150, 180, true, 1));
    // addSequential(new GyroCorrect(90*s, false, 5, 0));
    // addSequential(new DriveStraight(180, 0));
    // addSequential(new JoystickDrive(true));
    // addSequential(new DriveStraight(-210, 0));
    // addSequential(new GyroCorrect(90*s, false, 5, 0));

  }
}
