// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

import com.ctre.phoenix.Util;
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
  private double dSpeed;
  private boolean bSoftStopActive;

  /** Creates a new Wrist_subsystem. */
  public Wrist_subsystem() {
    objWristMotor.setIdleMode(IdleMode.kBrake);
    objWristMotor.setSmartCurrentLimit(Constants.Wrist.iCurrentLimit);
    objAbsEncoder = new DutyCycleEncoder(Constants.Wrist.iDIOPort);
    // objAbsEncoder.setDistancePerRotation(Constants.Wrist.dDegreesPerRev);
    SmartDashboard.putNumber("Test Encoder", 0.0);
    SmartDashboard.putNumber("Test Offset", 0.0);
    SmartDashboard.putNumber("Test DegRev", 0.0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    getWristAngle();
    double testResult = Utilities.correctAngle(SmartDashboard.getNumber("Test Encoder", 0.0), SmartDashboard.getNumber("Test Offset", 0.0), SmartDashboard.getNumber("Test DegRev", 0.0));
    SmartDashboard.putNumber("Test Result", testResult);
    
    if(bSoftStopActive) {
      softStop();
      if(Math.abs(objWristMotor.get()) < 0.03) setSoftStop(false);
    }
  }

  public void setSoftStop(boolean input) { bSoftStopActive = input; }

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

  public double softStop() {
    dSpeed = objWristMotor.get();
    if (dSpeed > 0.0) {
      dSpeed = Math.max(dSpeed - Constants.Arm.dSoftStopLimit, 0.0);
    }
    else {
      dSpeed = Math.min(dSpeed + Constants.Arm.dSoftStopLimit, 0.0);
    }
    if(Math.abs(dSpeed) < 0.035) {
      dSpeed = 0.0;
    }
    objWristMotor.set(dSpeed);
    return Math.abs(dSpeed);
  }

  public double getWristAngle() {
    double dWristAngle;
    // apply - here if + motor speed results in a decreasing angle (make it dWristAngle = -Utilities.correct...)
    // dWristAngle = -Utilities.correctAngle(objAbsEncoder.get(), Constants.Wrist.dOffset, Constants.Wrist.dDegreesPerRev);
    dWristAngle = Utilities.correctAngle2(objAbsEncoder.get(), Constants.Wrist.dOffset, 42.0 / 18.0, true);

    SmartDashboard.putNumber("Raw Wrist Encoder", objAbsEncoder.get());
    SmartDashboard.putNumber("Wrist Angle", dWristAngle);
    
    return dWristAngle;
  }

  public double moveWristToAngle(double dTargetAngle, double dAngle_old, double dCommand_old, double dSpeedMult) {
    double dSpeedLimit = Constants.Wrist.dSpeedControlMax;
    double dCurrentAngle = getWristAngle();
    double dDifference = dTargetAngle - dCurrentAngle; 
    double dDeriv;
    boolean bArrived = false;

    // computes dCommand, the motor speed
    dDeriv = dCurrentAngle - dAngle_old;
    double dCommand = dDifference * Constants.Wrist.kP - dDeriv * Constants.Wrist.kD;
    // if(Math.abs(dDifference) < 0.75) dCommand = 0.0;

    dCommand = Utilities.limitVariable(-dSpeedLimit * dSpeedMult, dCommand, dSpeedLimit * dSpeedMult);
    if (Math.abs(dCommand) > Math.abs(dCommand_old)) {      //Checking that speed is increasing
      dCommand = dCommand_old + Math.min(Math.abs(dCommand - dCommand_old), Constants.Wrist.dSpeedUpLimit) * Math.signum(dCommand);
    }
    moveWrist(dCommand);
    if (Math.abs(dDifference) < Constants.Wrist.dTolerance) {
      bArrived = true;
    }
    SmartDashboard.putBoolean("Wrist Arrived", bArrived);
    SmartDashboard.putNumber("WristControlSpeed", dCommand);
    return dCommand;
  }
}