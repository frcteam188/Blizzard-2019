/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.commands.MoveHatchRelease;
import frc.robot.subsystems.Intake;

public class ReleaseHatch extends CommandGroup {
  
  public ReleaseHatch() {
    addSequential(new MoveHatchRelease(Intake.Direction.OUT));
    addSequential(new WaitCommand(0.2));
    addSequential(new MoveHatchRelease(Intake.Direction.IN));
  }
}
