/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


public class Constants {

    // PID Gains
    public static final double[] basePIDF = {0, 0, 0, 0};
    public static final double[] elevatorPID = {0, 0, 0};
    public static final double[] elevatorUpPID = {0, 0, 0};
    public static final double[] elevatorDownPID = {0, 0, 0};

    // Power Scalars
    public static final double kElevatorPower = 1.0;

    // Tolerances
    public static final double kElevatorPIDTolerance = 10;

    // Presets
    public static final double[] elevatorPresets = {0, 0, 0, 0};

}
