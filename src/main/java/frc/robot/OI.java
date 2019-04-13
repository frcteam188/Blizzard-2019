/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import frc.robot.commands.Score;
import frc.robot.commands.StopPID;
import frc.robot.commands.TrimIntake;
import frc.robot.commandgroups.BeginLevel2Hang;
import frc.robot.commandgroups.BeginLevel3Hang;
import frc.robot.commandgroups.FinishLevel3Hang;
import frc.robot.commandgroups.IntakeBall;
import frc.robot.commandgroups.IntakeHatch;
import frc.robot.commandgroups.IntakeHumanBall;
import frc.robot.commandgroups.Level2Hang;
import frc.robot.commandgroups.Level3Hang;
import frc.robot.commands.CameraScore;
import frc.robot.commands.FlipHatchIntake;
import frc.robot.commands.FlipIntake;
import frc.robot.commands.HangJoystickDrive;
import frc.robot.commands.JoystickDrive;
import frc.robot.commands.ManualPushDown;
import frc.robot.commands.MoveElevator;
import frc.robot.commands.MoveHatchIntake;
import frc.robot.commands.MoveIntake;
import frc.robot.commands.PushDownBackEnc;
import frc.robot.commands.PushDownBackPID;
import frc.robot.commands.PushDownFrontPID;
import frc.robot.commands.SafetyRetractPushDownBack;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;

public class OI {

  // Controllers
  public static Joystick stick;
  public static Joystick stick2;

  // Buttons (Driver)
  public static JoystickButton resetElevatorEnc;
  public static JoystickButton resetBaseEnc;
  public static JoystickButton resetPushDownEnc;
  public static JoystickButton resetGyro;
  public static JoystickButton slowButton;
  public static JoystickButton fastButton;
  public static JoystickButton cameraCorrect;
  public static JoystickButton nextAuto;
  public static JoystickButton prevAuto;
  public static JoystickButton hangLeftButton;
  public static JoystickButton hangRightButton;
  public static JoystickButton hangFrontResetButton;
  public static JoystickButton hangBackResetButton;
  public static JoystickButton hangKillButton;
  public static JoystickButton hangDriveForwardButton;
  public static JoystickButton hangDriveBackButton;
  public static JoystickButton autoHandoffButton;
  public static JoystickButton bearingLockButton;

  // Buttons (Operator)
  public static JoystickButton[] elevatorButtons;
  public static JoystickButton intakeHatch;
  public static JoystickButton intakeBall;
  public static JoystickButton intakeHumanBall;
  public static JoystickButton ballToggle;
  
  // Axis Numbers (Driver)
  public static final int fwdAxis = 1;
  public static final int turnAxis = 4;
  // public static final int pushDownDownAxis = 3;
  // public static final int pushDownUpAxis = 2;
  public static final int pushDownMainAxis = 2;
  public static final int pushDownCorrectionAxis = 3;

  // Axis Numbers (Operator)
  public static final int elevatorAxis = 3;
  public static final int hangArmAxis = 1;
  public static final int intakeAxis = 1;

  // D-Pad (Driver)
  public static final int intermediateHangPOV = 180;


  public OI() {
    stick = new Joystick(0);
    stick2 = new Joystick(1);

    resetGyro = new JoystickButton(stick, 2);
    resetElevatorEnc = new JoystickButton(stick, 1);
    resetBaseEnc = resetElevatorEnc;
    resetPushDownEnc = resetElevatorEnc;
    hangFrontResetButton = new JoystickButton(stick, 4);
    hangBackResetButton = new JoystickButton(stick, 3);
    hangKillButton = new JoystickButton(stick, 2);

    bearingLockButton = new JoystickButton(stick, 1);
    autoHandoffButton = new JoystickButton(stick, 1);

    slowButton = new JoystickButton(stick, 5);
    fastButton = new JoystickButton(stick, 6);
    
    elevatorButtons = new JoystickButton[] {new JoystickButton(stick2, 2), 
      new JoystickButton(stick2, 3), new JoystickButton(stick2, 4), new JoystickButton(stick2, 1)};
    intakeHumanBall = new JoystickButton(stick2, 5);
    ballToggle = new JoystickButton(stick2, 6);
    intakeHatch = new JoystickButton(stick2, 7);
    intakeBall = new JoystickButton(stick2, 8);

    cameraCorrect = new JoystickButton(stick, 6);

    prevAuto = new JoystickButton(stick, 5);
    nextAuto = new JoystickButton(stick, 6);

    hangLeftButton = new JoystickButton(stick, 7);
    hangRightButton = new JoystickButton(stick, 8);

    for (int i = 0; i < OI.elevatorButtons.length; ++i)
    {
      int preset = i;
      elevatorButtons[i].whenPressed(new MoveElevator(i));
      elevatorButtons[i].whenPressed(new ConditionalCommand(new FlipHatchIntake(Intake.Direction.OUT)) {
        @Override
        protected boolean condition()
        {
          return !OI.ballToggle.get();
        }
      }
      );
      elevatorButtons[i].whenReleased(new Score());
    }

    // cameraCorrect.whenPressed(new CameraScore(0));
    // cameraCorrect.whenReleased(new StopPID());

    // intakeHatch.whenPressed(new IntakeHatch(true));
    intakeHatch.whenPressed(new FlipHatchIntake(Intake.Direction.OUT));
    intakeHatch.whenPressed(new MoveHatchIntake(-1.0, Double.POSITIVE_INFINITY));
    intakeHatch.whenPressed(new MoveElevator(4, Elevator.GamePiece.HATCH));
    intakeHatch.whenReleased(new FlipHatchIntake(Intake.Direction.IN));
    intakeHatch.whenReleased(new MoveHatchIntake(-Constants.kHatchIntakeTrim, 1.0));
    intakeHatch.whenReleased(new MoveElevator(-1));
    
    intakeBall.whenPressed(new IntakeBall());
    intakeBall.whenReleased(new FlipIntake(Intake.Direction.IN));
    intakeBall.whenReleased(new TrimIntake());
    intakeHumanBall.whenPressed(new IntakeHumanBall());
    intakeHumanBall.whenReleased(new TrimIntake());
    intakeHumanBall.whenReleased(new MoveElevator(-1));

    hangLeftButton.whenPressed(new BeginLevel3Hang());
    // hangLeftButton.whenReleased(new ManualPushDown());
    // hangRightButton.whenPressed(new Level2Hang(Level2Hang.Side.RIGHT));
    hangRightButton.whenPressed(new BeginLevel2Hang());
    hangKillButton.whenPressed(new ManualPushDown());
    hangKillButton.whenPressed(new JoystickDrive());

    hangFrontResetButton.whenPressed(new PushDownFrontPID(0));
    hangFrontResetButton.whenPressed(new HangJoystickDrive());
    hangFrontResetButton.whenPressed(new PushDownBackPID(true));
    hangBackResetButton.whenPressed(new SafetyRetractPushDownBack());
    hangBackResetButton.whenPressed(new HangJoystickDrive());

  }

  public static boolean isOverriding()
  {
    for (int i = 0; i <= 5; ++i) if (Math.abs(stick.getRawAxis(i)) >= 0.1) return true;
    for (int i = 1; i <= 10; ++i) if (stick.getRawButton(i)) return true;
    for (int i = 0; i <= 3; ++i) if (Math.abs(stick2.getRawAxis(i)) >= 0.1) return true;
    for (int i = 1; i <= 12; ++i) if (stick2.getRawButton(i)) return true;
    return false;
  }

}
