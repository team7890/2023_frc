// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Arm_subsystem;

public class Arm_command extends CommandBase {

  private final double dSpeed;
  private final Arm_subsystem objArm_subsystem;
  private final boolean bMode;
  private double dAngle_old;

  /** Creates a new Arm_command. */
  public Arm_command(Arm_subsystem objArm_subsystem_in, double dSpeed_in, boolean bMode_in) {
    objArm_subsystem = objArm_subsystem_in;
    dSpeed = dSpeed_in;
    bMode = bMode_in;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(objArm_subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    objArm_subsystem.stopArm();
    dAngle_old = objArm_subsystem.getArmAngle();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    objArm_subsystem.moveArm(dSpeed);
    // if(bMode) objArm_subsystem.moveArmToAngle(88.0, dAngle_old);
    // else objArm_subsystem.moveArm(dSpeed);
    // dAngle_old = objArm_subsystem.getArmAngle();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    objArm_subsystem.stopArm();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
