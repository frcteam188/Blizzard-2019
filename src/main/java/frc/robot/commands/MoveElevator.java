/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.subsystems.Elevator;

public class MoveElevator extends Command {

  int preset;
  double setpoint;
  Elevator.GamePiece gamePieceType;
  boolean willEnd;

  public MoveElevator(int preset) {
    this(preset, Elevator.GamePiece.NONE);
  }

  public MoveElevator(int preset, Elevator.GamePiece gamePieceType)
  {
    this(preset, gamePieceType, true);
  }
  public MoveElevator(int preset, Elevator.GamePiece gamePieceType, boolean willEnd)
  {
    requires(Robot.elevator);
    this.preset = preset;
    this.gamePieceType = gamePieceType;
    this.willEnd = willEnd;
    System.out.println("Move elevator " + preset);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    System.out.println("Move elevator " + preset + " started");
    Robot.elevator.flashPIDValues();
    if (preset < 0)
      setpoint = Constants.bottomPreset;
    else if (gamePieceType == Elevator.GamePiece.BALL || gamePieceType == Elevator.GamePiece.NONE && OI.ballToggle.get())
      setpoint = Constants.ballPresets[preset];
    else
      setpoint = Constants.hatchPresets[preset];
    Robot.elevator.setPID(setpoint);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(Math.abs(setpoint - Robot.elevator.getElevatorEnc()) < 3.0 && setpoint == Constants.bottomPreset && !willEnd) {
      Robot.elevator.stopPID();
      Robot.elevator.stop();
    }
    else Robot.elevator.runPID(setpoint);
    // System.out.println(setpoint);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Math.abs(setpoint - Robot.elevator.getElevatorEnc()) < 3.0 && setpoint == Constants.bottomPreset && willEnd
            || DriverStation.getInstance().isDisabled();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    if(setpoint == 1 || DriverStation.getInstance().isDisabled()) {
      Robot.elevator.stopPID();
      Robot.elevator.stop();
    }
    // System.out.println("FINISHED PID");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
