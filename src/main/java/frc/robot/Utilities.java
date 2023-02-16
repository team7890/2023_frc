package frc.robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj2.command.Command;

import java.lang.Math;

/* Testing Position Idea */
// import frc.robot.commands.*;
// import frc.robot.subsystems.*;

public class Utilities {

    public static double correctAngle(double dEncoder_in, double dOffset_in, double dDegreesPerRev_in) {
        double dAngle;
        dAngle = dEncoder_in;
        SmartDashboard.putNumber("value", dAngle);
        
        dAngle = (dAngle * dDegreesPerRev_in);   
        dAngle = dAngle - dOffset_in;       //makes the percents aligned with the table horizontally
        dAngle = dAngle % 360.0;
        if ((dAngle % 360.0) < 0) {
          dAngle = dAngle + 360.0;
        }
        SmartDashboard.putNumber("Angle", dAngle);
        return dAngle - 180.0;
    }

    // correctAngle2 generates a corrected mechanism angle from an encoder input
    //      dEncoder_in is the encoder input, with a full revolution changing the sensor's reading by 1.0
    //          in other words, if 12 o'clock is 0.0 then rotating to 12 o'clock again will result in 1.0 and again 2.0
    //          and rotating in the other direction from 12 o'clock would result in -1.0 and again -2.0
    //      dOffset_in is the offset angle which is the resulting angle from the encoder 
    //          through this correctAngle2 calculation when the mechanism is in the 0 degree position
    //          and when dOffset_in is set to 0.0
    //      dRatio is the ratio of encoder revolutions to mechanism revolutions, or the gear ratio from
    //          mechanism to encoder (if the encoder has an 18T pulley and the wrist has a 36T pulle, the ratio would be 2.0
    //      dAngle is the returned value which is between -180.0 and +180.0
    public static double correctAngle2(double dEncoder_in, double dOffset_in, double dRatio) {
        double dAngle;
        double dDegreesPerRev = 360.0 / dRatio;     // degrees of mechanism motion in one encoder revolution

        // dAngle is computed by taking the encoder reading and subtracting the offset angle which is
        //      back-converted to encoder units, then taking the modulus of this result with the ratio
        //      to achieve a periodic function again from a continuous encoder wrap, then multiplying by the
        //      degrees per rev to translate the modulus result back into mechanism degrees and finally subtracting
        //      180 because the modulus results in a 0 to 360 result when -180 to +180 is desired
        dAngle = ((dEncoder_in - dOffset_in / dDegreesPerRev) % dRatio) * dDegreesPerRev - 180.0;
        return dAngle;
    }

    public static double limitVariable(double dMinValue, double dVariable, double dMaxValue) {
        double dValue;
        dValue = Math.max(dVariable, dMinValue);
        dValue = Math.min(dValue, dMaxValue);        
        return dValue;
    }

    /* Testing Position Idea */
    // private final Arm_subsystem objArm_subsystem = new Arm_subsystem();
    // private final Forearm_subsystem objForearm_subsystem = new Forearm_subsystem();
    // private final Wrist_subsystem objWrist_subsystem = new Wrist_subsystem();

    // public Command PickupPosition() {
    //     return new Arm_command(objArm_subsystem, -Constants.Arm.dArmSpeedManual, true, -10.5),                                //Doesn't like negative?
    //     new Forearm_command(objForearm_subsystem, -Constants.Forearm.dForearmSpeedManual, true, 117.52),
    //     new Wrist_command(objWrist_subsystem, -Constants.Wrist.dWristSpeedManual, true, 12.85);
    // }
} 
