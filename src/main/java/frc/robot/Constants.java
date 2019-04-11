/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Constants {

    // Conversion Factors
    public static final double kRevsToInches = 31.2 / 6 / Math.PI;

    // PID Gains
    public static final double[] baseEncHighPID = {0.035, 0, 0.013}; // 70 percent power (0.05, 0, 0)
    public static final double[] baseEncLowPID = {0.084, 0, 0}; // 30 percent power (0.08, 0, 0)
    // public static final double[] baseGyroTurnPID = {0.0095, 0, 0}; // 75 percent power (0.014, 0, 0.01) (acutally 0.0085, 0, 0.0085)
    public static final double[] baseGyroTurnPID = {0.015, 0, 0.015}; // 100 percent power, 4 neos
    public static final double[] baseGyroCorrectionPID = {0.045, 0, 0.06}; // 30 percent power (0.045, 0, 0.04)
    // public static final double elevatorPIDRamp = 0.2;
    public static final double[] elevatorUpPID = {0.06, 0.0004, 0};
    public static final double elevatorUpIZone = 8.5; // MAKE SURE TO ZERO IACCUM IF ABOVE SETPOINT AND IACCUM < 0
    public static final double[] elevatorDownPID = {0.08, 0, 0.0001};
    public static final double[] elevatorDownPIDOutputRange = {-0.5, 1.0};
    public static final double[] mainHangPID = {0.03, 0, 0};
    public static final double[] backHangPID = {0.1, 0, 0}; //0.065

    // Ramp Rates
    public static final double kClosedLoopRampRate = 0.2;
    public static final double kBaseEncPIDRampRate = 0.01;
    public static final double kBaseGyroPIDRampRate = 0.01;

    // PID Slots
    public static final int kElevatorUpPID = 0;
    public static final int kElevatorDownPID = 1;

    // Power Scalars
    public static final double kBasePower = 1.0;
    public static final double kBaseSlowPower = 0.2;
    public static final double kBaseDefaultPower = 0.7;
    public static final double kBaseFastPower = 1.0;
    public static final double kBaseTeleopTurnPower = 0.6;
    public static final double kBaseEncHighPIDPower = 0.7;
    public static final double kBaseEncMidPIDPower = 0.3; //is 0.3
    public static final double kBaseEncLowPIDPower = 0.2; //meant to be 0.3
    public static final double kBaseEncTinyPIDPower = 0.1;
    public static final double kGyroTurnPower = 0.5; // 0.75
    public static final double kGyroCorrectionPower = 0.3;
    public static final double kGyroCorrectionForwardPower = 0.2;
    public static final double kElevatorPower = 0.65;
    public static final double kHangArmPower = 0.4;
    public static final double kPushDownFrontPower = 0.5;
    public static final double kPushDownBackPower = 1.0;
    public static final double kHangDrivePower = 0.4;
    public static final double kHangJoystickDrivePower = 0.1;

    // Pneumatics Directions
    public static final Value kIntakePivotOut = Value.kForward;
    public static final Value kIntakePivotIn = (kIntakePivotOut == Value.kForward ? Value.kReverse : Value.kForward);
    public static final Value kHatchPivotOut = Value.kForward;
    public static final Value kHatchPivotIn = (kHatchPivotOut == Value.kForward ? Value.kReverse : Value.kForward);
    public static final Value kOuterPushOut = Value.kForward;
    public static final Value kOuterPushIn = (kOuterPushOut == Value.kForward ? Value.kReverse : Value.kForward);
    
    // Trim
    public static final double kIntakeTrim = 0.2;
    
    // Tolerances
    public static final double kElevatorPIDTolerance = 1.0;
    public static final double kBaseEncPIDTolerance = 1.0;
    public static final double kBaseGyroPIDTolerance = 2.0;
    public static final double kPushDownMainTolerance = 0.5;
    public static final double kPushDownCorrectionTolerance = 1.0;
    public static final double kFrontHangTolerance = 10.0;
    public static final double kBackHangTolerance = 5.0;

    // Default PID Control Types
    public static final ControlType kBasePIDDefaultType = ControlType.kVelocity;
    public static final ControlType kElevatorPIDDefaultType = ControlType.kPosition;

    // Limits
    public static final double kElevatorUpperLimit = 55.;
    public static final double kElevatorLowerLimit = 0.;

    // Presets
    public static final double[] hatchPresets = {2.5, 26.5, 48.5, 2.5, 2, 11}; // Bottom, mid, top rocket; Cargo ship; Bottom, top hatch pickup
    public static final double[] ballPresets = {12.5, 35, 54, 23, 38}; // Bottom, mid, top rocket; Cargo ship; Human load pickup
    public static final double bottomPreset = 1.0;
    public static final double pushDownFrontLevel3Preset = 95;
    public static final double pushDownBackLevel3Preset = 68;
    public static final double pushDownFrontLevel2Preset = (pushDownFrontLevel3Preset * 6./19.) + 7.5;
    public static final double pushDownBackLevel2Preset = (pushDownBackLevel3Preset * 6./19.) + 7.5;

    // Vision Constants
    public static final double kWidthThreshold = 210.0;
    public static final int kRollingAverageSize = 3;
    public static final int kStaleSampleThreshold = 5;
    public static final double kStalenessThreshold = 8;

}
