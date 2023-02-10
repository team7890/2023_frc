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

  public void moveArm(double dSpeed) {
    objArmMotor1.set(dSpeed);
    objArmMotor2.set(-dSpeed);
  }

  public void stopArm() {
    objArmMotor1.stopMotor();
    objArmMotor2.stopMotor();
  }

  public double getArmAngle() {
    double dArmAngle;
    dArmAngle = Utilities.correctAngle(objAbsEncoder.get(), Constants.Arm.dOffset, Constants.Arm.dDegreesPerRev);

    SmartDashboard.putNumber("Arm Angle", dArmAngle);
    
    return dArmAngle;
  }

  public void moveArmToAngle(double dTargetAngle, double dAngle_old) {
    double dSpeed = Constants.Arm.dArmSpeed;
    double dCurrentAngle = getArmAngle();
    double dDifference = dTargetAngle - dCurrentAngle; 
    double dDeriv;
    boolean bArrived = false;

    // computes dCommand, the motor speed
    dDeriv = dCurrentAngle - dAngle_old;
    double dCommand = dDifference * Constants.Arm.kP - dDeriv * Constants.Arm.kD;
    // if(Math.abs(dDifference) < 0.75) dCommand = 0.0;

    // limits the max speed in either direction
    if(dCommand > dSpeed) dCommand = dSpeed;
    if(dCommand < (-1 * dSpeed)) dCommand = (-1 * dSpeed);
    moveArm(dCommand);
    if(Math.abs(dDifference) < 1.5) bArrived = true;
    SmartDashboard.putBoolean("Arm Arrived", bArrived);

  }
}

