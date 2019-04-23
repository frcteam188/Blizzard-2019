/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.UpdateLimelight;

/**
 * Add your docs here.
 */
public class Limelight extends Subsystem {
  
  double angle;
  double area;
  boolean seesTarget;
  NetworkTable table;

  public Limelight()
  {
    angle = 0.0;
    area = 0.0;
    seesTarget = false;
    table = NetworkTableInstance.getDefault().getTable("limelight");
  }

  public void update()
  {
    angle = table.getEntry("tx").getDouble(0.0);
    area = table.getEntry("ta").getDouble(0.0);
    seesTarget = table.getEntry("tv").getDouble(0) == 1;
  }

  public double getAngle() {
    return angle;
  }

  public double getArea() {
    return area;
  }

  public boolean hasTarget()
  {
    return seesTarget;
  }  

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new UpdateLimelight());
  }
}
