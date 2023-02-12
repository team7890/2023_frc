// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.Utilities;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Forearm_subsystem extends SubsystemBase {

  private CANSparkMax objForearmMotor = new CANSparkMax(Constants.canIDs.iForearmMotor,MotorType.kBrushless);
  private DutyCycleEncoder objAbsEncoder;

  /** Creates a new Arm_subsystem. */
  public Forearm_subsystem() {
    objForearmMotor.setIdleMode(IdleMode.kBrake);
    objForearmMotor.setSmartCurrentLimit(Constants.Forearm.iCurrentLimit);
    objAbsEncoder = new DutyCycleEncoder(Constants.Forearm.iDIOPort);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    getForearmAngle();
  }

  public void moveForearm(double dSpeed) {
    objForearmMotor.set(dSpeed);
  }

  public void stopForearm() {
    objForearmMotor.stopMotor();
  }

  public double getForearmAngle() {
    double dForearmAngle;
    dForearmAngle = Utilities.correctAngle(objAbsEncoder.get(), Constants.Forearm.dOffset, Constants.Forearm.dDegreesPerRev);

    SmartDashboard.putNumber("Forearm Angle", dForearmAngle);
    
    return dForearmAngle;
  }

  public double moveForearmToAngle(double dTargetAngle, double dAngle_old, double dCommand_old) {
    double dSpeedLimit = Constants.Forearm.dForearmSpeedControlMax;
    double dCurrentAngle = getForearmAngle();
    double dDifference = dTargetAngle - dCurrentAngle; 
    double dDeriv;
    boolean bArrived = false;

    // computes dCommand, the motor speed
    dDeriv = dCurrentAngle - dAngle_old;
    double dCommand = dDifference * Constants.Forearm.kP - dDeriv * Constants.Forearm.kD;
    // if(Math.abs(dDifference) < 0.75) dCommand = 0.0;

    dCommand = Utilities.limitVariable(-dSpeedLimit, dCommand, dSpeedLimit);
    if (Math.abs(dCommand) > Math.abs(dCommand_old)) {      //Checking that speed is increasing
      dCommand = dCommand_old + Math.min(Math.abs(dCommand - dCommand_old), Constants.Arm.dSpeedUpLimit) * Math.signum(dCommand);
    }
    moveForearm(dCommand);
    if (Math.abs(dDifference) < 1.5) {
      bArrived = true;
    }
    SmartDashboard.putBoolean("Forearm Arrived", bArrived);
    SmartDashboard.putNumber("ForearmControlSpeed", dCommand);
    return dCommand;
  }

}