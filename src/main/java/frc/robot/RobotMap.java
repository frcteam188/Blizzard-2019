/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.SPI.Port;

public class RobotMap {

  public static int frontLeft = 7; // 7
  public static int midLeft = 6; // 6
  public static int backLeft = 5; // 5
  public static int frontRight = 3; // 3
  public static int midRight = 2; // 2
  public static int backRight = 1; // 1

  public static int elevatorLeft = 8;
  public static int elevatorRight = 4;

  public static int intakeMotor = 1;

  public static int[] hangPiston = {0, 4};
  public static int[] pivotPiston = {3, 7};
  public static int[] innerPushPiston = {5, 2};
  public static int[] outerPushPiston = {6, 1};

  public static int intakeSensor = 2; // NOT ATTACHED

  // public static int hangLeft = 9;
  // public static int hangRight = 10;

  public static int pushDownLeft = 10;
  public static int pushDownRight = 9;
  public static int pushDownBack = 0;

  public static int[] pushDownCorrectionEncoder = {0, 1}; // NOT ATTACHED

  public static int frontHangSensor = 3; // NOT ATTACHED
  public static int backHangSensor = 4; // NOT ATTACHED

  public static Port navxPort = Port.kMXP;

}

