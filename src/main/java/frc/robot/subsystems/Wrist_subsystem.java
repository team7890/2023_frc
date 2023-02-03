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
    objWristMotor.set(dSpeed);
  }

  public void stopWrist() {
    objWristMotor.stopMotor();
  }

  public double getWristAngle() {
    double dWristAngle;
    dWristAngle = Utilities.correctAngle(objAbsEncoder.get(), Constants.Wrist.dOffset, Constants.Wrist.dDegreesPerRev);

    SmartDashboard.putNumber("Wrist Angle", dWristAngle);
    
    return dWristAngle;
  }


}