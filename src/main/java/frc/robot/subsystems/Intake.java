/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.RobotMap;

public class Intake extends Subsystem {

  TalonSRX intakeMotor;

  DigitalInput sensor;

  DoubleSolenoid pivotPiston;
  DoubleSolenoid innerPushPiston;
  DoubleSolenoid outerPushPiston;
  DoubleSolenoid hangPiston;

  boolean trim;

  public enum Direction
  {
    OUT(Constants.kIntakePivotOut, Constants.kInnerPushOut, Constants.kOuterPushOut, Constants.kHangOut),
    IN(Constants.kIntakePivotIn, Constants.kInnerPushIn, Constants.kOuterPushIn, Constants.kHangIn);

    private Value pivotDirection;
    private Value innerPushDirection;
    private Value outerPushDirection;
    private Value hangDirection;

    private Direction(Value pivotDirection, Value innerPushDirection, Value outerPushDirection, Value hangDirection)
    {
      this.pivotDirection = pivotDirection;
      this.innerPushDirection = innerPushDirection;
      this.outerPushDirection = outerPushDirection;
      this.hangDirection = hangDirection;
    }

    public Value getPivotDirection()
    {
      return pivotDirection;
    }

    public Value getInnerPushDirection()
    {
      return innerPushDirection;
    }

    public Value getOuterPushDirection()
    {
      return outerPushDirection;
    }

    public Value getHangDirection()
    {
      return hangDirection;
    }
  }

  // private Solenoid[] solenoids;

  public Intake()
  {
    intakeMotor = new TalonSRX(RobotMap.intakeMotor);
    pivotPiston = new DoubleSolenoid(RobotMap.pivotPiston[0], RobotMap.pivotPiston[1]);
    innerPushPiston = new DoubleSolenoid(RobotMap.innerPushPiston[0], RobotMap.innerPushPiston[1]);
    outerPushPiston = new DoubleSolenoid(RobotMap.outerPushPiston[0], RobotMap.outerPushPiston[1]);
    hangPiston = new DoubleSolenoid(RobotMap.hangPiston[0], RobotMap.hangPiston[1]);
    
    sensor = new DigitalInput(RobotMap.intakeSensor);
    trim = true;
    pivotIntake(Direction.IN);
    innerPush(Direction.IN);
    outerPush(Direction.IN);

    // solenoids = new Solenoid[] {new Solenoid(0), new Solenoid(1), new Solenoid(2), new Solenoid(3),
    //                             new Solenoid(4), new Solenoid(5), new Solenoid(6), new Solenoid(7)};
  }

  // private void fire(Solenoid solenoid, boolean on)
  // {
  //   solenoid.set(on);
  // }

  public void execute()
  {
    // if (trim && Math.abs(power) <= 0.05)
    //   drive(Constants.kIntakeTrim);
    
    double power = OI.stick2.getRawAxis(OI.intakeAxis);
    if (OI.intakeBall.get())
    {
      pivotIntake(Direction.OUT);
      drive(-1.0);
    }
    else if (Math.abs(power) > 0.05)
    {
      drive(power);
    }
    else
    {
      pivotIntake(Direction.IN);
      if (trim) drive(-Constants.kIntakeTrim);
    }

    // if (getSensor() && power >= 0) stop();
    // else drive(power);

    // if (OI.pivotIntakeOut.get())
    //   pivotIntake(Direction.OUT);
    // else if (OI.pivotIntakeIn.get())
    //   pivotIntake(Direction.IN);

    // if (OI.pushInnerOut.get())
    //   innerPush(Direction.OUT);
    // else if (OI.pushInnerIn.get())
    //   innerPush(Direction.IN);

    // if (OI.pushInner.get())
    //   innerPush(Direction.OUT);
    // else
    //   innerPush(Direction.IN);

    // if (OI.pushOuterOut.get())
    //   outerPush(Direction.OUT);
    // else if (OI.pushOuterIn.get())
    //   outerPush(Direction.IN);

    // fire(solenoids[0], OI.stick.getRawButton(4));
    // fire(solenoids[1], OI.stick.getRawButton(3));
    // fire(solenoids[2], OI.stick.getRawButton(1));
    // fire(solenoids[3], OI.stick.getRawButton(2));
    // fire(solenoids[4], OI.stick2.getRawButton(4));
    // fire(solenoids[5], OI.stick2.getRawButton(1));
    // fire(solenoids[6], OI.stick2.getRawButton(2));
    // fire(solenoids[7], OI.stick2.getRawButton(3));
  }

  public void drive(double power)
  {
    intakeMotor.set(ControlMode.PercentOutput, power);
  }

  public void setTrim(boolean on)
  {
    trim = on;
  }

  public boolean getSensor()
  {
    return !sensor.get();
  }

  public void outerPush(Direction direction)
  {
    outerPushPiston.set(direction.getOuterPushDirection());
  }

  public void innerPush(Direction direction)
  {
    innerPushPiston.set(direction.getInnerPushDirection());
  }

  public void pivotIntake(Direction direction)
  {
    pivotPiston.set(direction.getPivotDirection());
  }

  public void pivotIntake()
  {
    pivotPiston.set(pivotPiston.get() == Constants.kIntakePivotIn ? Constants.kIntakePivotOut : Constants.kIntakePivotIn);
  }

  public void hang(Direction direction)
  {
    hangPiston.set(direction.getHangDirection());
  }

  public void report()
  {
    SmartDashboard.putBoolean("Intake Sensor", sensor.get());
    // System.out.println("Intake sensor: " + sensor.get());
  }

  public void stop()
  {
    drive(0);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
