// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

//Stuff we imported
import frc.robot.subsystems.*;

import frc.robot.Constants;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Mech_cmd_group extends SequentialCommandGroup {
  /** Creates a new Mech_cmd_group. */
  public Mech_cmd_group(Arm_subsystem obj_Arm, Forearm_subsystem obj_Forearm, Wrist_subsystem obj_Wrist) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new ParallelCommandGroup(
        new Arm_command(obj_Arm, Constants.Arm.dArmSpeedManual, true, 15.0),
        new Forearm_command(obj_Forearm, Constants.Forearm.dSpeedManual / 5.0, true, -15.0)
      ),
      new Wrist_command(obj_Wrist, Constants.Wrist.dSpeedManual, true, 90)
    );
    
  }
}
