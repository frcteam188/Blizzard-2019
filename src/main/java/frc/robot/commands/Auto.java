/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.OI;
import frc.robot.Robot;

public class Auto extends Command {

  public enum Side
  {
    LEFT,
    CENTRE,
    RIGHT
  }

  Side side;
  String name;
  CommandGroup auto;

  public Auto(CommandGroup auto, String name, Side side) {
    this.auto = auto;
    this.name = name;
    this.side = side;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    auto.start();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return auto.isCompleted() || OI.isOverriding();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    auto.cancel();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }

  @Override
  public String toString()
  {
    return name;
  }

  public Side getSide()
  {
    return side;
  }
}
