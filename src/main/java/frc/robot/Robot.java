/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.subsystems.Base;
import frc.robot.subsystems.Elevator;
import frc.robot.commands.DriveCommand;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends TimedRobot {

  private static Command teleopCommand;

  public static Base base;
  public static Elevator elevator;
  public static OI oi;
  NetworkTableEntry goalEntry;
  @Override
  public void robotInit() {
    base = new Base();
    elevator = new Elevator();
    oi = new OI();
    teleopCommand = new DriveCommand();
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable table = inst.getTable("SmartDashboard");
    goalEntry = table.getEntry("goal:closest");

  }

  @Override
  public void robotPeriodic() {
    double goalAngle = (double)goalEntry.getNumber(0);
    System.out.println(goalAngle);
    if(goalAngle != 0){
    }
  }

  @Override
  public void disabledInit() {
    if(teleopCommand != null) {
      teleopCommand.cancel();
    }
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();

    // Reset encoders
    if (OI.resetBaseEnc.get())
    {
      Robot.base.resetLeftEnc();
      Robot.base.resetRightEnc();
      System.out.print("Base encoders reset.");
    }
    if (OI.resetElevatorEnc.get())
    {
      Robot.elevator.resetElevatorEnc();
      System.out.println("Elevator encoder reset.");
    }
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    if(teleopCommand != null) {
      teleopCommand.start();
    }
  }

  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void testPeriodic() {
  }
}
