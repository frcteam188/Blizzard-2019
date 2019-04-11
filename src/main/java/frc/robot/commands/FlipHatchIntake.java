/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Intake.Direction;

/**
 * Add your docs here.
 */
public class FlipHatchIntake extends InstantCommand {
  
  Direction direction;

  public FlipHatchIntake(Direction direction) {
    super();
    this.direction = direction;
  }

  // Called once when the command executes
  @Override
  protected void initialize() {
    Robot.intake.pivotHatchIntake(direction);
  }

}
