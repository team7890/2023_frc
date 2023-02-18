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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Utilities;

import java.lang.Math;

public class Arm_subsystem extends SubsystemBase {

  private CANSparkMax objArmMotor1 = new CANSparkMax(Constants.canIDs.iArmMotor1, MotorType.kBrushless);
  private CANSparkMax objArmMotor2 = new CANSparkMax(Constants.canIDs.iArmMotor2, MotorType.kBrushless);
  private DutyCycleEncoder objAbsEncoder;

  /** Creates a new Arm_subsystem. */
  public Arm_subsystem() {
    objArmMotor1.setIdleMode(IdleMode.kBrake);
    objArmMotor1.setSmartCurrentLimit(Constants.Arm.iCurrentLimit);
    objArmMotor2.setIdleMode(IdleMode.kBrake);
    objArmMotor2.setSmartCurrentLimit(Constants.Arm.iCurrentLimit);
    objAbsEncoder = new DutyCycleEncoder(Constants.Arm.iDIOPort);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    getArmAngle();
  }

  public double moveArm(double dSpeed, double dSpeed_old) {
    double dSpeedLimit = Constants.Arm.dArmSpeedControlMax;
    double dCurrentAngle = getArmAngle();

    if (Math.abs(dSpeed) > Math.abs(dSpeed_old)) {      //Checking that speed is increasing
      dSpeed = dSpeed_old + Math.min(Math.abs(dSpeed - dSpeed_old), Constants.Arm.dSpeedUpLimit) * Math.signum(dSpeed);
    }

    if (dCurrentAngle > Constants.Arm.dMaxAngleLimit) {
      dSpeed = Utilities.limitVariable(-dSpeedLimit, dSpeed, 0.0);
    }
    else if (dCurrentAngle < Constants.Arm.dMinAngleLimit) {
      dSpeed = Utilities.limitVariable(0.0, dSpeed, dSpeedLimit);
    }
    objArmMotor1.set(dSpeed);
    objArmMotor2.set(-dSpeed);
    return dSpeed;
  }

  public void stopArm() {
    objArmMotor1.stopMotor();
    objArmMotor2.stopMotor();
  }

  public double getArmAngle() {
    double dArmAngle;
    dArmAngle = Utilities.correctAngle(objAbsEncoder.get(), Constants.Arm.dOffset, Constants.Arm.dDegreesPerRev);

    SmartDashboard.putNumber("Raw Arm Encoder", objAbsEncoder.get());
    SmartDashboard.putNumber("Arm Angle", dArmAngle);
    
    return dArmAngle;
  }

  public double moveArmToAngle(double dTargetAngle, double dAngle_old, double dCommand_old) {
    double dSpeedLimit = Constants.Arm.dArmSpeedControlMax;
    double dCurrentAngle = getArmAngle();
    double dDifference = dTargetAngle - dCurrentAngle; 
    double dDeriv;
    boolean bArrived = false;

    // computes dCommand, the motor speed
    dDeriv = dCurrentAngle - dAngle_old;
    double dCommand = dDifference * Constants.Arm.kP - dDeriv * Constants.Arm.kD;
    dCommand = Utilities.limitVariable(-dSpeedLimit, dCommand, dSpeedLimit);
    if (Math.abs(dCommand) > Math.abs(dCommand_old)) {      //Checking that speed is increasing
      dCommand = dCommand_old + Math.min(Math.abs(dCommand - dCommand_old), Constants.Arm.dSpeedUpLimit) * Math.signum(dCommand);
    }
    moveArm(dCommand, dCommand_old);
    if (Math.abs(dDifference) < Constants.Arm.dTolerance) {
      bArrived = true;
    }
    SmartDashboard.putBoolean("Arm Arrived", bArrived);
    SmartDashboard.putNumber("ArmControlSpeed", dCommand);
    return dCommand;
  }
}

