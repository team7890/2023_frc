// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Forearm_subsystem;

public class Forearm_command extends CommandBase {

  private final double dSpeed;
  private final Forearm_subsystem objForearm_subsystem;

  /** Creates a new Forearm_command. */
  public Forearm_command(Forearm_subsystem objForearm_subsystem_in, double dSpeed_in) {
    objForearm_subsystem = objForearm_subsystem_in;
    dSpeed = dSpeed_in;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(objForearm_subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    objForearm_subsystem.stopForearm();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    objForearm_subsystem.moveForearm(dSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    objForearm_subsystem.stopForearm();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
