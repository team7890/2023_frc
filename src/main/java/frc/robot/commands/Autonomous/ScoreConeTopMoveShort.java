// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

// our Imports
import frc.robot.subsystems.Arm_subsystem;
import frc.robot.subsystems.Forearm_subsystem;
import frc.robot.subsystems.Wrist_subsystem;
import frc.robot.subsystems.Grabber_subsystem;
import frc.robot.subsystems.Swerve_subsystem;

import frc.robot.commands.Grabber_command;
import frc.robot.commands.AutoSwerve;
import frc.robot.commands.Button_commands.ScoreConeTop2;

import edu.wpi.first.wpilibj.DriverStation;




// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ScoreConeTopMoveShort extends SequentialCommandGroup {

  // private boolean bAllianceColor = DriverStation.getAlliance();

  /** Creates a new AutoScoreConeTopAndMove. */
  public ScoreConeTopMoveShort(Arm_subsystem objArm, Forearm_subsystem objForearm, Wrist_subsystem objWrist, Grabber_subsystem objGrabber, Swerve_subsystem objSwerve) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new ScoreConeTop2(objArm, objForearm, objWrist).withTimeout(6.0),
      new Grabber_command(objGrabber).withTimeout(0.1),
      new AutoSwerve(objSwerve, -0.2, 0.0, 0.0, false).withTimeout(2.0)
    );
  }
}
