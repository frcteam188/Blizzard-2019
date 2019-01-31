/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class OI {

  public static Joystick stick;

  private int stickPort = 0;

  // Axes

  public static int fwdAxis = 1;
  public static int turnAxis = 4;



  public OI() {
    stick = new Joystick(stickPort);
  }


}