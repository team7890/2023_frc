// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
// import java.lang.Math;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Utilities;


public class Wrist_subsystem extends SubsystemBase {

  private CANSparkMax objWristMotor = new CANSparkMax(Constants.canIDs.iWristMotor,MotorType.kBrushless);
  private DutyCycleEncoder objAbsEncoder;

  /** Creates a new Arm_subsystem. */
  public Wrist_subsystem() {
    objWristMotor.setIdleMode(IdleMode.kBrake);
    objWristMotor.setSmartCurrentLimit(Constants.Wrist.iCurrentLimit);
    objAbsEncoder = new DutyCycleEncoder(Constants.Wrist.iDIOPort);
    // objAbsEncoder.setDistancePerRotation(Constants.Wrist.dDegreesPerRev);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    getWristAngle();
  }

  public void moveWrist (double dSpeed) {
    double dSpeedLimit = Constants.Wrist.dSpeedControlMax;
    double dCurrentAngle = getWristAngle();
    if (dCurrentAngle > Constants.Wrist.dMaxAngleLimit) {
      dSpeed = Utilities.limitVariable(-dSpeedLimit, dSpeed, 0.0);
    }
    else if (dCurrentAngle < Constants.Wrist.dMinAngleLimit) {
      dSpeed = Utilities.limitVariable(0.0, dSpeed, dSpeedLimit);
    }
    objWristMotor.set(dSpeed);
  }

  public void stopWrist() {
    objWristMotor.stopMotor();
  }

  public double getWristAngle() {
    double dWristAngle;
    dWristAngle = Utilities.correctAngle(-objAbsEncoder.get(), Constants.Wrist.dOffset, Constants.Wrist.dDegreesPerRev);

    SmartDashboard.putNumber("Wrist Angle", dWristAngle);
    
    return dWristAngle;
  }

  public double moveWristToAngle(double dTargetAngle, double dAngle_old, double dCommand_old) {
    double dSpeedLimit = Constants.Wrist.dSpeedControlMax;
    double dCurrentAngle = getWristAngle();
    double dDifference = dTargetAngle - dCurrentAngle; 
    double dDeriv;
    boolean bArrived = false;

    // computes dCommand, the motor speed
    dDeriv = dCurrentAngle - dAngle_old;
    double dCommand = dDifference * Constants.Wrist.kP - dDeriv * Constants.Wrist.kD;
    // if(Math.abs(dDifference) < 0.75) dCommand = 0.0;

    dCommand = Utilities.limitVariable(-dSpeedLimit, dCommand, dSpeedLimit);
    if (Math.abs(dCommand) > Math.abs(dCommand_old)) {      //Checking that speed is increasing
      dCommand = dCommand_old + Math.min(Math.abs(dCommand - dCommand_old), Constants.Wrist.dSpeedUpLimit) * Math.signum(dCommand);
    }
    moveWrist(dCommand);
    if (Math.abs(dDifference) < 1.5) {
      bArrived = true;
    }
    SmartDashboard.putBoolean("Wrist Arrived", bArrived);
    SmartDashboard.putNumber("WristControlSpeed", dCommand);
    return dCommand;
  }
}