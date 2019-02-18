/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.commandgroups.CameraScoreBall;
import frc.robot.commandgroups.CameraScoreHatch;

public class CameraScore extends Command {

  CommandGroup c;
  int elevatorPreset;

  public CameraScore(int elevatorPreset) {
    this.elevatorPreset = elevatorPreset;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    double[] goals = SmartDashboard.getNumberArray("goal:angles", new double[]{});
    double[] heights = SmartDashboard.getNumberArray("goal:heights", new double[]{});
    double closestGoal;
    if (goals.length > 0) closestGoal = goals[0];
    else return;
    if (OI.ballToggle.get())
    {
      c = new CameraScoreBall(closestGoal, elevatorPreset);
    }
    else
    {
      c = new CameraScoreHatch(closestGoal, elevatorPreset);
    }
    c.start();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return c == null || c.isCompleted();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    System.out.println("CAMERASCORE ENDED.");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
