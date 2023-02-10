// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Wrist_subsystem;
import frc.robot.subsystems.Arm_subsystem;
import frc.robot.subsystems.Forearm_subsystem;

public class Mech_command extends CommandBase {

  private final Wrist_subsystem objWrist;
  private final Forearm_subsystem objForearm;
  private final Arm_subsystem objArm;
  private double dArmAngle;
  private double dForearmAngle;
  private double dWristAngle;


  /** Creates a new Mech_command. */
  public Mech_command(Wrist_subsystem objWrist_in, Forearm_subsystem objForearm_in, Arm_subsystem objArm_in) {
    objWrist = objWrist_in;
    objForearm = objForearm_in;
    objArm = objArm_in;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(objWrist);
    addRequirements(objForearm);
    addRequirements(objArm);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    dArmAngle = objArm.getArmAngle();
    dForearmAngle = objForearm.getForearmAngle();
    dWristAngle = objWrist.getWristAngle();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
