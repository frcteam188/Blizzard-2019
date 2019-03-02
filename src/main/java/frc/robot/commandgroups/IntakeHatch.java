/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.commands.DriveUntilJoystick;
import frc.robot.commands.MoveElevator;
import frc.robot.commands.MoveHatch;
import frc.robot.commands.WaitOnTarget;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;

public class IntakeHatch extends CommandGroup {
  
  public IntakeHatch() {
    this(false);
  }

  public IntakeHatch(boolean drive)
  {
    addParallel(new MoveElevator(4, Elevator.GamePiece.HATCH));
    addSequential(new WaitOnTarget(1));
    addSequential(new MoveHatch(Intake.Direction.OUT));
    addSequential(new WaitCommand(0.3));
    addParallel(new MoveElevator(5, Elevator.GamePiece.HATCH));
    addSequential(new WaitCommand(0.5));
    if (drive) addParallel(new DriveUntilJoystick(-0.7, 0, 10));
    addSequential(new MoveHatch(Intake.Direction.IN));
    addSequential(new WaitCommand(0.5));
    addSequential(new MoveElevator(-1));
  }
}
