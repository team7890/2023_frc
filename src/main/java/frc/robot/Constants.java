// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

  public static final class canIDs{
    public static final int iArmMotor1 = 11;
    public static final int iArmMotor2 = 12;
    public static final int iForearmMotor = 13;
    public static final int iWristMotor = 10;
  }

  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static final class Arm{
    public static final int iCurrentLimit = 40;
    public static final double dArmSpeed = 0.2;
    public static final int iDIOPort = 0;
    public static final double dDegreesPerRev = 360.0;
    public static final double dOffset = 175.0;                         // Need to set this
  }

  public static final class Forearm{
    public static final int iCurrentLimit = 40;
    public static final double dForearmSpeed = 0.09;
    public static final int iDIOPort = 1;
    public static final double dDegreesPerRev = 360.0;
    public static final double dOffset = 230.0;                         // Need to set this
  }

  public static final class Wrist{
    public static final int iCurrentLimit = 40;
    public static final double dWristSpeed = 0.5;
    public static final int iDIOPort = 2;
    public static final double dDegreesPerRev = 360.0;
    public static final double dOffset = 13.5;                         // Need to set this
  }

}
