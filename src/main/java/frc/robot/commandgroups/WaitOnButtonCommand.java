/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class WaitOnButtonCommand extends CommandGroup {
  /**
   * Add your docs here.
   */
  public WaitOnButtonCommand(JoystickButton button, Command command) {
    addSequential(new Command() {
      @Override
      protected boolean isFinished()
      {
        return button.get();
      }
    });
    addSequential(command);
  }

  public WaitOnButtonCommand(Joystick stick, int pov, Command command)
  {
    addSequential(new Command() {
      @Override
      protected boolean isFinished()
      {
        return stick.getPOV() == pov;
      }
    });
    addSequential(command);
  }
}
