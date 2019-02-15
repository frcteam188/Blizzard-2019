/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.subsystems.Base;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Hang;
import frc.robot.subsystems.Intake;
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
  public static Intake intake;
  public static Hang hang;
  public static OI oi;
  NetworkTableEntry goalEntry;
  @Override
  public void robotInit() {
    oi = new OI();
    base = new Base();
    intake = new Intake();
    elevator = new Elevator();
    hang = new Hang();
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable table = inst.getTable("SmartDashboard");
    goalEntry = table.getEntry("goal:closest");
    
    teleopCommand = new DriveCommand();
  }

  @Override
  public void robotPeriodic() {
    double goalAngle = (double)goalEntry.getNumber(0);
    // System.out.println(goalAngle);
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

    // Reset sensors
    if (OI.resetBaseEnc.get())
    {
      Robot.base.resetLeftEnc();
      Robot.base.resetRightEnc();
      System.out.println("Base encoders reset.");
    }
    if (OI.resetElevatorEnc.get())
    {
      Robot.elevator.resetElevatorEnc();
      System.out.println("Elevator encoder reset.");
    }
    if (OI.resetGyro.get())
    {
      Robot.base.resetGyro();
      System.out.println("Gyro reset.");
    }

    report();
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

  public static void report()
  {
    base.report();
    elevator.report();
    intake.report();
  }

}
