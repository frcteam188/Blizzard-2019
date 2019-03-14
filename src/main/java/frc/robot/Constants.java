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
    public static final double[] baseGyroTurnPID = {0.0095, 0, 0}; // 75 percent power (0.014, 0, 0.01) (acutally 0.0085, 0, 0.0085)
    public static final double[] baseGyroCorrectionPID = {0.045, 0, 0.06}; // 30 percent power (0.045, 0, 0.04)
    // public static final double elevatorPIDRamp = 0.2;
    public static final double[] elevatorUpPID = {0.06, 0.0004, 0};
    public static final double elevatorUpIZone = 8.5; // MAKE SURE TO ZERO IACCUM IF ABOVE SETPOINT AND IACCUM < 0
    public static final double[] elevatorDownPID = {0.08, 0, 0.0001};
    public static final double[] elevatorDownPIDOutputRange = {-0.5, 1.0};
    public static final double[] mainHangPID = {0, 0, 0};
    public static final double[] backHangPID = {0, 0, 0};

    // Ramp Rates
    public static final double kClosedLoopRampRate = 0.2;
    public static final double kBaseEncPIDRampRate = 0.01;
    public static final double kBaseGyroPIDRampRate = 0.2;

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
    public static final double kGyroTurnPower = 0.5; // 0.75
    public static final double kGyroCorrectionPower = 0.3;
    public static final double kGyroCorrectionForwardPower = 0.2;
    public static final double kElevatorPower = 0.65;
    public static final double kHangArmPower = 0.4;
    public static final double kPushDownFrontPower = 0.5;
    public static final double kPushDownBackPower = 0.7;
    public static final double kHangDrivePower = 0.4;

    // Pneumatics Directions
    public static final Value kIntakePivotOut = Value.kForward;
    public static final Value kIntakePivotIn = (kIntakePivotOut == Value.kForward ? Value.kReverse : Value.kForward);
    public static final Value kInnerPushOut = Value.kReverse;
    public static final Value kInnerPushIn = (kInnerPushOut == Value.kForward ? Value.kReverse : Value.kForward);
    public static final Value kOuterPushOut = Value.kForward;
    public static final Value kOuterPushIn = (kOuterPushOut == Value.kForward ? Value.kReverse : Value.kForward);
    public static final Value kHangOut = Value.kForward;
    public static final Value kHangIn = (kHangOut == Value.kForward ? Value.kReverse : Value.kForward);
    
    // Trim
    public static final double kIntakeTrim = 0.2;
    
    // Tolerances
    public static final double kElevatorPIDTolerance = 1.0;
    public static final double kBaseEncPIDTolerance = 1.0;
    public static final double kBaseGyroPIDTolerance = 2.0;
    public static final double kPushDownMainTolerance = 0.5;
    public static final double kPushDownCorrectionTolerance = 1.0;

    // Default PID Control Types
    public static final ControlType kBasePIDDefaultType = ControlType.kVelocity;
    public static final ControlType kElevatorPIDDefaultType = ControlType.kPosition;

    // Limits
    public static final double kElevatorUpperLimit = 55.;
    public static final double kElevatorLowerLimit = 0.;

    // Presets
    //Make high presets back to 48.5 and 53
    public static final double[] hatchPresets = {2.5, 26.5, 26.5, 2.5, 2, 11}; // Bottom, mid, top rocket; Cargo ship; Bottom, top hatch pickup
    public static final double[] ballPresets = {14, 35, 35, 23, 38}; // Bottom, mid, top rocket; Cargo ship; Human load pickup
    public static final double bottomPreset = 1.0;

    // Vision Constants
    public static final double kWidthThreshold = 210.0;
    public static final int kRollingAverageSize = 3;
    public static final int kStaleSampleThreshold = 5;
    public static final double kStalenessThreshold = 8;

}
