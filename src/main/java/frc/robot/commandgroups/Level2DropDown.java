/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Constants;
import frc.robot.commands.DriveSlow;
import frc.robot.commands.PushDownBack;
import frc.robot.commands.PushDownBackEnc;
import frc.robot.commands.PushDownBackPID;
import frc.robot.commands.PushDownFrontEnc;
import frc.robot.commands.PushDownFrontPID;
import frc.robot.commands.WaitOnHangBackTarget;
import frc.robot.commands.WaitOnHangFrontTarget;

public class Level2DropDown extends CommandGroup {
  /**
   * Add your docs here.
   */
  public Level2DropDown() {

    // addSequential(new DriveSlow(-19, 0, true));
    // addSequential(new PushDownBack(1.0, 0.5));
    // addSequential(new DriveSlow(-20.5, 0, true));
    // addSequential(new PushDownFrontEnc(1.0, 35));
    // addSequential(new PushDownBack(-0.3, 0.8));
    // addSequential(new DriveSlow(-15.5, 0, true));
    // addSequential(new PushDownFrontEnc(-1.0, -32), 2.0);
    // addSequential(new DriveSlow(-40, 0, true));

    // STARTS FACING BACKWARDS
    addSequential(new DriveSlow(-19, 0, true));
    addParallel(new PushDownBackPID(Constants.pushDownBackLevel2Preset));
    addSequential(new WaitOnHangBackTarget(Constants.pushDownBackLevel2Preset, 15));
    // NEED TO RUN LITTLE WHEEL
    addSequential(new DriveSlow(-20.5, 0, true));
    addParallel(new PushDownFrontPID(Constants.pushDownFrontLevel2Preset));
    addSequential(new WaitOnHangFrontTarget(Constants.pushDownFrontLevel2Preset, 15));
    addSequential(new PushDownBackEnc(-0.3, 0, true));
    addSequential(new DriveSlow(-15.5, 0, true));
    addSequential(new PushDownFrontEnc(-1.0, 0, true), 2.0);
    addSequential(new DriveSlow(-40, 0, true));
  }
}
