/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.FlipHatchIntake;
import frc.robot.commands.MoveHatchIntake;
import frc.robot.subsystems.Intake;

public class DeployHatchIntake extends CommandGroup {

  public DeployHatchIntake()
  {
    this(true);
  }

  public DeployHatchIntake(boolean intake) {
    if (intake) addParallel(new MoveHatchIntake(-1.0, 0, false));
    addSequential(new FlipHatchIntake(Intake.Direction.OUT));
  }
}
