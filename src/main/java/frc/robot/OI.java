/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.Score;
import frc.robot.commands.StopPID;
import frc.robot.commands.TrimIntake;
import frc.robot.commandgroups.IntakeBall;
import frc.robot.commandgroups.IntakeHatch;
import frc.robot.commandgroups.IntakeHumanBall;
import frc.robot.commands.CameraScore;
import frc.robot.commands.FlipIntake;
import frc.robot.commands.MoveElevator;
import frc.robot.commands.MoveIntake;
import frc.robot.subsystems.Intake;

public class OI {

  // Controllers
  public static Joystick stick;
  public static Joystick stick2;

  // Buttons (Driver)
  public static JoystickButton resetElevatorEnc;
  public static JoystickButton resetBaseEnc;
  public static JoystickButton resetGyro;
  public static JoystickButton slowButton;
  // public static JoystickButton hangArmIn;
  // public static JoystickButton hangArmOut;
  public static JoystickButton cameraCorrect;

  // Buttons (Operator)
  public static JoystickButton[] elevatorButtons;
  // public static JoystickButton pivotIntakeOut;
  // public static JoystickButton pivotIntakeIn;
  // public static JoystickButton pushInnerOut;
  // public static JoystickButton pushInnerIn;
  // public static JoystickButton pushOuterOut;
  // public static JoystickButton pushOuterIn;
  public static JoystickButton intakeHatch;
  public static JoystickButton intakeBall;
  public static JoystickButton intakeHumanBall;
  public static JoystickButton ballToggle;
  
  // Axis Numbers (Driver)
  public static final int fwdAxis = 1;
  public static final int turnAxis = 4;

  // Axis Numbers (Operator)
  public static final int elevatorAxis = 3;
  public static final int hangArmAxis = 1;
  public static final int intakeAxis = 1;


  public OI() {
    stick = new Joystick(0);
    stick2 = new Joystick(1);

    resetGyro = new JoystickButton(stick, 2);
    resetElevatorEnc = new JoystickButton(stick, 1);
    resetBaseEnc = resetElevatorEnc;
    // hangArmIn = new JoystickButton(stick, 5);
    // hangArmOut = new JoystickButton(stick, 6);

    slowButton = new JoystickButton(stick, 5);

    // pivotIntakeIn = new JoystickButton(stick2, 7);
    // pivotIntakeOut = new JoystickButton(stick2, 8);
    // pushOuterIn = new JoystickButton(stick2, 5);
    // pushOuterOut = new JoystickButton(stick2, 6);
    // pushInnerIn = new JoystickButton(stick2, 2);
    // pushInnerOut = new JoystickButton(stick2, 4);
    
    elevatorButtons = new JoystickButton[] {new JoystickButton(stick2, 2), 
      new JoystickButton(stick2, 3), new JoystickButton(stick2, 4)};
    intakeHumanBall = new JoystickButton(stick2, 5);
    ballToggle = new JoystickButton(stick2, 6);
    intakeHatch = new JoystickButton(stick2, 7);
    intakeBall = new JoystickButton(stick2, 8);

    cameraCorrect = new JoystickButton(stick, 6);

    for (int i = 0; i < OI.elevatorButtons.length; ++i)
    {
      elevatorButtons[i].whenPressed(new MoveElevator(i));
      elevatorButtons[i].whenReleased(new Score());
    }

    intakeHatch.whenPressed(new IntakeHatch());
    cameraCorrect.whenPressed(new CameraScore(0));
    cameraCorrect.whenReleased(new StopPID());
    intakeBall.whenPressed(new IntakeBall());
    intakeBall.whenReleased(new FlipIntake(Intake.Direction.IN));
    intakeBall.whenReleased(new TrimIntake());
    intakeHumanBall.whenPressed(new IntakeHumanBall());
    intakeHumanBall.whenReleased(new TrimIntake());
    intakeHumanBall.whenReleased(new MoveElevator(-1));
  }


}
