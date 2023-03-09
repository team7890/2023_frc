// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RollerHand_subsystem extends SubsystemBase {

  private CANSparkMax objMotor1 = new CANSparkMax(14, MotorType.kBrushless);
  private CANSparkMax objMotor2 = new CANSparkMax(15, MotorType.kBrushless);

  private double dIntakeSpeed = 0.9;

  /** Creates a new Grabber. */
  public RollerHand_subsystem() {
    objMotor1.setIdleMode(IdleMode.kBrake);
    objMotor2.setIdleMode(IdleMode.kBrake);
    objMotor1.setSmartCurrentLimit(10);
    objMotor2.setSmartCurrentLimit(10);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Motor 1 Current", getMotor1Current());
  }

  public void intakeCone() {
    objMotor1.set(dIntakeSpeed);
    objMotor2.set(dIntakeSpeed);
  }

  public void outtakeCone() {
    objMotor1.set(-dIntakeSpeed);
    objMotor2.set(-dIntakeSpeed);
  }

  public void intakeCube() {
    objMotor1.set(-dIntakeSpeed / 2.0);
    objMotor2.set(-dIntakeSpeed / 2.0);
  }

  public void outtakeCube(boolean bDirection) {
    // bDirection is true for out the top and false for out the bottom (go out same way it came in when intaking)
    if (bDirection) {
      objMotor1.set(dIntakeSpeed / 2.0);
      objMotor2.set(-dIntakeSpeed / 2.0);  
    }
    else {
      objMotor1.set(-dIntakeSpeed / 2.0);
      objMotor2.set(dIntakeSpeed / 2.0);
    }
  }

  public double getMotor1Current() {
    return objMotor1.getOutputCurrent();
  }

  public double getMotor2Current() {
    return objMotor1.getOutputCurrent();
  }

  public void stopMotors() {
    objMotor1.stopMotor();
    objMotor2.stopMotor();
  }
}
