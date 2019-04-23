/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class DelayedCommand extends CommandGroup {

  public DelayedCommand(double time, Command command)
  {
    this(time, new Command[] {command});
  }

  public DelayedCommand(double time, Command[] commands) {
    addSequential(new WaitCommand(time));
    for (int i = 0; i < commands.length - 1; ++i)
      addParallel(commands[i]);
    addSequential(commands[commands.length - 1]);
  }
}
