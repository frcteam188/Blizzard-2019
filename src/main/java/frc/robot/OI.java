/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {

  public static Joystick stick;
  public static Joystick stick2;

  // Buttons
  public static JoystickButton[] elevatorButtons;
  public static JoystickButton resetElevatorEnc;
  public static JoystickButton resetBaseEnc;
  
  // Axes
  public static final int fwdAxis = 1;
  public static final int turnAxis = 4;
  public static final int elevatorAxis = 3;


  public OI() {
    stick = new Joystick(0);
    stick2 = new Joystick(1);

    resetElevatorEnc = new JoystickButton(stick, 2);
    resetBaseEnc = resetElevatorEnc;
    elevatorButtons = new JoystickButton[] {
                      new JoystickButton(stick2, 1), new JoystickButton(stick2, 2),
                      new JoystickButton(stick2, 3), new JoystickButton(stick2, 4)};

  }


}
