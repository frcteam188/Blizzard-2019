/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.io.BufferedReader;

import frc.robot.Robot;
import frc.robot.OI;

import java.util.List;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.*;

public class DriveCommand extends Command {
  
  BufferedReader leftReader, rightReader;
  boolean fileFound = true;
  double lastTime = 1000000;
  boolean shouldMotionProfile = false;
  
  public DriveCommand() {
    requires(Robot.base);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if (shouldMotionProfile){
      try {
        URI leftPath = new URI("/home/lvuser/path_left.csv");
        URI rightPath = new URI("/home/lvuser/path_right.csv");
        leftReader = Files.newBufferedReader(Paths.get("/home/lvuser/path_left.csv"));
        
        rightReader = Files.newBufferedReader(Paths.get("/home/lvuser/path_right.csv"));
      } catch (Exception e){
        System.out.println("Files not read");
        fileFound = false;
      }	
      lastTime = System.currentTimeMillis(); 
    }
  }

  //    long lastTime;
//    double kP = 0.001, kT = 0;
	List<String> leftNext = null;
	List<String> rightNext = null;
  double lastError = 0.0;
  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
   	if(shouldMotionProfile){
       motionProfile();
     } else {
     
      double forward = OI.stick.getRawAxis(1);
      double turn = OI.stick.getRawAxis(2);
      double proposedTurn = 0.0;

      double goalAngle = (double)SmartDashboard.getNumber("goal:closest", 0.0);
      if(Math.abs(goalAngle) > 0){
        double error = goalAngle/25.0;
        double P =.125, I=0.0, D=0.02;
        
       proposedTurn = P*error + D*(lastError - error);
       lastError = error;
       if (OI.stick.getRawButton(5)){
          turn = proposedTurn;
          forward *= 0.45;
        }
        else{
          turn *= 0.75;
        }
      }

      SmartDashboard.putNumber("Forward", forward);
      SmartDashboard.putNumber("Turn", turn);
      SmartDashboard.putNumber("Propsed_Turn", proposedTurn);

      Robot.base.driveArcade(forward, turn);
     }
   
    
    SmartDashboard.putNumber("Left enc", Robot.base.getLeftEnc());
    SmartDashboard.putNumber("Right enc", Robot.base.getRightEnc());
    SmartDashboard.putNumber("Front Left Pwr", Robot.base.frontLeft.getOutputCurrent());
    SmartDashboard.putNumber("Back Left Pwr", Robot.base.backLeft.getOutputCurrent());
    SmartDashboard.putNumber("Front Right Pwr", Robot.base.frontRight.getOutputCurrent());
    SmartDashboard.putNumber("Back Right Pwr", Robot.base.backRight.getOutputCurrent());
  }

  public void motionProfile(){
    if(!fileFound) return;
   	if (leftNext == null || System.currentTimeMillis() > lastTime + 10){
   		try {
   			leftNext = new ArrayList<String>();
   			String leftLine = leftReader.readLine();
   			Collections.addAll(leftNext, leftLine.split(","));
   			rightNext = new ArrayList<String>();
   			String rightLine = rightReader.readLine();
   			Collections.addAll(rightNext,  rightLine.split(","));
   		} catch (IOException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
   	}
   	
   	double leftPos = Double.parseDouble(leftNext.get(0));
   	double rightPos = Double.parseDouble(rightNext.get(0));
   	
   	double leftVel = Double.parseDouble(leftNext.get(1));
   	double rightVel = Double.parseDouble(rightNext.get(1));
   	double vMax = 8.0;
    	
   	// Base Controls
   	double pL = -leftVel/8.0;
   	double pR = -rightVel/8.0;
     Robot.base.driveArcade(pL, pR);	
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.base.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();  
  }
}
