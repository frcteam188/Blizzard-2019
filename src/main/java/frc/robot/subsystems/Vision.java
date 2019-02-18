/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

/**
 * Add your docs here.
 */
public class Vision extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  class Goal
  {
    double angle;
    double height;
    double width;

    public Goal(double angle, double height, double width)
    {
      this.angle = angle;
      this.height = height;
      this.width = width;
    }

    public double getAngle()
    {
      return angle;
    }

    public double getHeight()
    {
      return height;
    }

    public double getWidth()
    {
      return width;
    }

  }

  double averageAngle;
  double total;
  double lastAngles[];
  int numStaleAngles;
  int numInAverage;

  public Vision()
  {
    lastAngles = new double[Constants.kRollingAverageSize];
    numStaleAngles = 0;
    numInAverage = 0;
    total = 0;
  }

  public double[] getGoalAngles()
  {
    return SmartDashboard.getNumberArray("goal:angles", new double[]{});
  }

  public double[] getGoalHeights()
  {
    return SmartDashboard.getNumberArray("goal:heights", new double[]{});
  }

  public double[] getGoalWidths()
  {
    return SmartDashboard.getNumberArray("goal:widths", new double[]{});
  }

  public Goal[] getGoals()
  {
    double[] angles = getGoalAngles();
    double[] heights = getGoalHeights();
    double[] widths = getGoalWidths();
    Goal[] goals = new Goal[angles.length];
    for (int i = 0; i < angles.length; ++i)
      goals[i] = new Goal(angles[i], heights[i], widths[i]);
    return goals;
  }

  public Goal getClosestGoal()
  {
    return getGoals()[0];
  }

  public void clearAverage()
  {
    numInAverage = 0;
    for (int i = 0; i < lastAngles.length; ++i)
      lastAngles[i] = 0;
  }

  public void addToAverage(double toAdd)
  {
    if (numInAverage < Constants.kRollingAverageSize)
    {
      lastAngles[numInAverage++] = toAdd;
      total += toAdd;
    }
    else
    {
      total -= lastAngles[0];
      for (int i = 0; i < lastAngles.length - 1; ++i)
        lastAngles[i] = lastAngles[i + 1];
      lastAngles[lastAngles.length - 1] = toAdd;
      total += toAdd;
    }
  }
  
  public boolean isStale(double angle)
  {
    return Math.abs(angle - averageAngle) >= Constants.kStalenessThreshold;
  }
  
  public void updateAverage()
  {
    double toAdd = getClosestGoal().getAngle();
    if (numInAverage > 0 && isStale(toAdd))
    {
      ++numStaleAngles;
      clearAverage();
    }
    else
    {
      numStaleAngles = 0;
      addToAverage(toAdd);
      averageAngle = total / numInAverage;
    }

  }

  public double getAverageAngle()
  {
    updateAverage();
    return averageAngle;
  }

  public void report()
  {
    SmartDashboard.putNumber("Average Angle", averageAngle);
    SmartDashboard.putNumber("Samples in Average", numInAverage);
    SmartDashboard.putNumber("Sum of Samples in Average", total);
    SmartDashboard.putNumber("Stale Samples", numStaleAngles);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
