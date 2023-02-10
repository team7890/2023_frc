// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Wrist_subsystem;

public class Wrist_command extends CommandBase {

  private final double dSpeed;
  private final Wrist_subsystem objWrist_subsystem;

  /** Creates a new Wrist_command. */
  public Wrist_command(Wrist_subsystem objWrist_subsystem_in, double dSpeed_in) {
    objWrist_subsystem = objWrist_subsystem_in;
    dSpeed = dSpeed_in;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(objWrist_subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    objWrist_subsystem.stopWrist();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    objWrist_subsystem.moveWrist(dSpeed);
    // double dTest = objWrist_subsystem.getWristAngle();
    // objWrist_subsystem.getWristAngle();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    objWrist_subsystem.stopWrist();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
