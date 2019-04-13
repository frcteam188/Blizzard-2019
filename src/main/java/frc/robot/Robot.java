/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.subsystems.Base;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.HangFront;
import frc.robot.subsystems.HangBack;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Vision;
import frc.robot.autocommandgroups.CameraHandoffAuto;
import frc.robot.autocommandgroups.DriveForwardScoreHatchAuto;
import frc.robot.autocommandgroups.LeftSide2HatchAuto;
import frc.robot.autocommandgroups.LeftSideCargo2HatchAuto;
import frc.robot.autocommandgroups.RightSide2HatchAuto;
import frc.robot.autocommandgroups.RightSideCargo2HatchAuto;
import frc.robot.autocommandgroups.SideHatchAuto;
import frc.robot.autocommandgroups.SketchyHandoffAuto;
import frc.robot.autocommandgroups.PlatformDrop;
import frc.robot.autocommandgroups.PlatformDropSideHatch;
import frc.robot.commandgroups.AutoCommandGroup;
import frc.robot.commandgroups.TestCommandGroup;
import frc.robot.commands.Auto;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.JoystickDrive;
import frc.robot.commands.TuneBaseEncPID;
import frc.robot.commands.TuneBaseGyroPID;
import frc.robot.commands.TuneElevatorPID;
import frc.robot.commands.TunePushDownCorrectionPID;
import frc.robot.commands.TunePushDownMainPID;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {

  private static Command teleopCommand;
  private static Command testCommand;
  private static Auto autoCommand;

  static Auto[] autos;
  static int selectedAuto;

  public static Base base;
  public static Elevator elevator;
  public static Intake intake;
  public static HangFront hangFront;
  public static HangBack hangBack;
  // public static Vision vision;
  public static OI oi;
  NetworkTableEntry goalEntry;

  static boolean alwaysReset = true;
  static boolean prevChangeAuto;

  static int currentIter;

  @Override
  public void robotInit() {
    base = new Base();
    intake = new Intake();
    elevator = new Elevator();
    hangFront = new HangFront();
    hangBack = new HangBack();
    // vision = new Vision();
    oi = new OI();

    // UsbCamera cam0 = new UsbCamera("USB Camera 0", 0);
    // cam0.setFPS(30);
    // cam0.setResolution(160, 120);
    // cam0.setBrightness(20);

    // CameraServer.getInstance().addCamera(cam0);
    // CameraServer.getInstance().startAutomaticCapture(cam0);
    
    // MjpegServer server = new MjpegServer("serve_USB Camera 0", 1181);
    // server.setSource(cam0);
    
    CameraServer.getInstance().startAutomaticCapture();
    
    // NetworkTableInstance inst = NetworkTableInstance.getDefault();
    // NetworkTable table = inst.getTable("SmartDashboard");
    // goalEntry = table.getEntry("goal:closest");
    
    testCommand = null;
    teleopCommand = new JoystickDrive();
    // testCommand = new TunePushDownCorrectionPID();
    // testCommand = new TuneBaseGyroPID();
    
    selectedAuto = 0;
    prevChangeAuto = false;
    autos = new Auto[] {

      new Auto(new CommandGroup(), "None",  Auto.Side.CENTRE),
      new Auto(new LeftSide2HatchAuto(),  "LeftSide2HatchAuto",  Auto.Side.LEFT),
      new Auto(new RightSide2HatchAuto(), "RightSide2HatchAuto", Auto.Side.RIGHT),
      // new Auto(new PlatformDrop(PlatformDrop.Side.LEFT), "Left PlatformDrop", null),
      // new Auto(new PlatformDrop(PlatformDrop.Side.RIGHT), "Right PlatformDrop", null),
      // new Auto(new PlatformDropSideHatch(PlatformDropSideHatch.Side.LEFT), "Left PlatformDropSideHatch", null),
      // new Auto(new PlatformDropSideHatch(PlatformDropSideHatch.Side.RIGHT), "Right PlatformDropSideHatch", null),
      // new Auto(new LeftSideCargo2HatchAuto(), "LeftSideCargo2HatchAuto", Auto.Side.CENTRE),
      // new Auto(new RightSideCargo2HatchAuto(), "RightSideCargo2HatchAuto", Auto.Side.CENTRE),
      new Auto(new DriveForwardScoreHatchAuto(), "DriveForwardScoreHatchAuto", Auto.Side.CENTRE),
      new Auto(new SideHatchAuto(SideHatchAuto.Side.LEFT), "Left SideHatchAuto", Auto.Side.CENTRE),
      new Auto(new SideHatchAuto(SideHatchAuto.Side.RIGHT), "Right SideHatchAuto", Auto.Side.CENTRE),
      new Auto(new CameraHandoffAuto(CameraHandoffAuto.Side.LEFT), "Left CameraHandOffAuto", Auto.Side.CENTRE, false),
      new Auto(new CameraHandoffAuto(CameraHandoffAuto.Side.RIGHT), "Right CameraHandOffAuto", Auto.Side.CENTRE, false),
      new Auto(new SketchyHandoffAuto(SketchyHandoffAuto.Side.LEFT), "Left SketchyHandOffAuto", Auto.Side.CENTRE, false),
      new Auto(new SketchyHandoffAuto(SketchyHandoffAuto.Side.RIGHT), "Right SketchyHandOffAuto", Auto.Side.CENTRE, false),
      new Auto(new TestCommandGroup(), "Test", null)
      
      };

    autoCommand = autos[selectedAuto];
    
    push();
    report();
  }

  @Override
  public void disabledInit() {
    if(teleopCommand != null)
      teleopCommand.cancel();
    if (testCommand != null)
      testCommand.cancel();
    if (autoCommand != null)
      autoCommand.cancel();

    Robot.elevator.stopPID();

    currentIter = 0;
  }

  @Override
  public void disabledPeriodic() {
    // long t1, t2, t3;
    // t1 = t2 = t3 = 0;
    // int numIter = 50;
    // if (currentIter == numIter) t1 = System.nanoTime();
    Scheduler.getInstance().run();
    // if (currentIter == numIter) t2 = System.nanoTime();
    report();
    // if (currentIter == numIter) t3 = System.nanoTime();
    // if (currentIter == numIter)
    // {
    //   System.out.println("Scheduler took " + (t2 - t1) / 1000000. + " milliseconds.");
    //   System.out.println("report() took " + (t3 - t2) / 1000000. + " milliseconds.");
    //   currentIter = 0;
    // }
    // ++currentIter;
    
    if (OI.nextAuto.get() && !prevChangeAuto) ++selectedAuto;
    else if (OI.prevAuto.get() && !prevChangeAuto) --selectedAuto;
    
    if (selectedAuto >= autos.length) selectedAuto = 0;
    else if (selectedAuto < 0) selectedAuto = autos.length - 1;

    prevChangeAuto = OI.nextAuto.get() || OI.prevAuto.get();
    
    autoCommand = autos[selectedAuto];
    if (prevChangeAuto) System.out.println(autoCommand);

    resetSensors(alwaysReset);
    alwaysReset = false;

  }

  @Override
  public void autonomousInit() {
    if (testCommand != null)
      testCommand.start();
    else if (autoCommand != null)
      autoCommand.start();
  }

  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
    report();
    if (testCommand != null && !testCommand.isRunning() && 
        autoCommand != null && !autoCommand.isRunning())
        teleopInit();
  }

  @Override
  public void teleopInit() {
    if (autoCommand != null)
      autoCommand.cancel();
    if(teleopCommand != null)
      teleopCommand.start();
    Robot.elevator.stopPID();
  }

  @Override
  public void teleopPeriodic() {
    // alwaysReset = true;
    Scheduler.getInstance().run();
    report();

    // Robot.hangFront.execute(); // REMOVE THIS LATER
    // Robot.hangBack.execute(); // REMOVE THIS LATER
  }

  @Override
  public void testPeriodic() {
    if (testCommand != null)
      testCommand.start();
  }

  public static Auto getSelectedAuto()
  {
    return autoCommand;
  }

  public static void push() 
  {
    SmartDashboard.putNumber("h:lo", 25);
    SmartDashboard.putNumber("s:lo", 50);
    SmartDashboard.putNumber("v:lo", 50);
    SmartDashboard.putNumber("h:hi", 100);
    SmartDashboard.putNumber("s:hi", 255);
    SmartDashboard.putNumber("v:hi", 255);
    SmartDashboard.putNumber("contour:min", 200);
    SmartDashboard.putBoolean("hsv:toggle", false);
  }

  public static void resetSensors(boolean override)
  {
    if (override || OI.resetBaseEnc.get())
    {
      Robot.base.resetLeftEnc();
      Robot.base.resetRightEnc();
      System.out.println("Base encoders reset.");
    }
    if (override || OI.resetElevatorEnc.get())
    {
      Robot.elevator.resetElevatorEnc();
      System.out.println("Elevator encoder reset.");      
    }
    if (override || OI.resetPushDownEnc.get())
    {
      Robot.hangFront.resetEnc();
      Robot.hangBack.resetEnc();
      System.out.println("Pushdown encoders reset.");
    }
    if (override || OI.resetGyro.get())
    {
      Robot.base.resetGyro();
      System.out.println("Gyro reset.");
    }
  }

  public static void report()
  {
    // SmartDashboard.putString("Selected Auto", autoCommand.toString());
    base.report();
    elevator.report();
    intake.report();
    hangFront.report();
    hangBack.report();
    // vision.report();
    // SmartDashboard.putNumberArray("hsv:lower", 
    //   new double[]{SmartDashboard.getNumber("h:lo", 0), SmartDashboard.getNumber("s:lo", 0), SmartDashboard.getNumber("v:lo", 0)});
    // SmartDashboard.putNumberArray("hsv:upper", 
    // new double[]{SmartDashboard.getNumber("h:hi", 0), SmartDashboard.getNumber("s:hi", 0), SmartDashboard.getNumber("v:hi", 0)});
  }

}
