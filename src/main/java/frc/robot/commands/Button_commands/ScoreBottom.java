// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Button_commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Wrist_subsystem;
import frc.robot.subsystems.Arm_subsystem;
import frc.robot.subsystems.Forearm_subsystem;

public class ScoreBottom extends CommandBase {

  private final Wrist_subsystem objWrist;
  private final Forearm_subsystem objForearm;
  private final Arm_subsystem objArm;
  //Arm Variables
  private double dArmAngle_old;
  private double dArmCommand_old;
  //Forearm Variables
  private double dForearmAngle_old;
  private double dForearmCommand_old;
  //Wrist Variables
  private double dWristAngle_old;
  private double dWristCommand_old;

  private int iState;
  private double dArmSoftStopSpeed;
  private double dForearmSoftStopSpeed;

  // Final Target Positions
  double dArmTarget = 2.0;
  double dForearmTarget = -138.0;
  double dWristTarget = 46.6;

  /** Creates a new ScoreCubeTop. */
  public ScoreBottom(Arm_subsystem objArm_in, Forearm_subsystem objForearm_in, Wrist_subsystem objWrist_in) {
    objArm = objArm_in;
    objForearm = objForearm_in;
    objWrist = objWrist_in;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(objArm);
    addRequirements(objForearm);
    addRequirements(objWrist);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //Arm Variables
    objArm.setSoftStop(false);
    dArmAngle_old = objArm.getArmAngle();
    dArmCommand_old = 0.0;
    //Forearm Variables
    objForearm.setSoftStop(false);
    dForearmAngle_old = objForearm.getForearmAngle();
    dForearmCommand_old = 0.0;
    //Wrist Variables
    objWrist.setSoftStop(false);
    dWristAngle_old = objWrist.getWristAngle();
    dWristCommand_old = 0.0;

    iState = 0;
    if (objForearm.getForearmAngle() > -20.0) iState = 10;
    else iState = 12;

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    switch (iState) {
      case 10:          //if the forearm is out on the high scoring side
                        // first move the wirst up (means the wrist is going to a negative angle)
        dWristCommand_old = objWrist.moveWristToAngle(-90.0, dWristAngle_old, dWristCommand_old, 2.0);
        if (objWrist.getWristAngle() < -10.0) iState = 11;
        break;
      case 11:          // then move the arm so that it doesnt stick out of the front of the robot (to 0.0 degrees)
                        // then move the forearm over the top so that its on the stowing side of the robot
        dArmCommand_old = objArm.moveArmToAngle(0.0, dArmAngle_old, dArmCommand_old, 2.0);
        dForearmCommand_old = objForearm.moveForearmToAngle(dForearmTarget, dForearmAngle_old, dForearmCommand_old, 3.0);
        dWristCommand_old = objWrist.moveWristToAngle(-90.0, dWristAngle_old, dWristCommand_old, 3.0);
        if (objForearm.getForearmAngle() < -30.0) iState = 12;
        break;
      case 12:          // stop arm and forearm until the wrist is clear
                        // once the forearm gets over the top (towards stow position) then move wrist to target
        objArm.softStop();
        objForearm.softStop();
        dWristCommand_old = objWrist.moveWristToAngle(dWristTarget, dWristAngle_old, dWristCommand_old, 3.0);
        if (objWrist.getWristAngle() > 20.0) iState = 13;
        break;
      case 13:          // Move everything to stow targets
        dArmCommand_old = objArm.moveArmToAngle(dArmTarget, dArmAngle_old, dArmCommand_old, 2.0);
        dForearmCommand_old = objForearm.moveForearmToAngle(dForearmTarget, dForearmAngle_old, dForearmCommand_old, 1.0);
        dWristCommand_old = objWrist.moveWristToAngle(dWristTarget, dWristAngle_old, dWristCommand_old, 2.0);
        // if all three joints are at correct angle then iState = 99;
        if (Math.abs(objForearm.getForearmAngle() - dForearmTarget) < 1.0 && Math.abs(objArm.getArmAngle() - dArmTarget) < 1.0 && Math.abs(objWrist.getWristAngle() - dWristTarget) < 1.0) iState = 99;
        break;
      case 99:
        objArm.softStop();
        objForearm.softStop();
        objWrist.softStop();
        break;
    }
    dArmAngle_old = objArm.getArmAngle();
    dForearmAngle_old = objForearm.getForearmAngle();
    dWristAngle_old = objWrist.getWristAngle();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    objArm.setSoftStop(true);
    objForearm.setSoftStop(true);
    objWrist.setSoftStop(true);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}