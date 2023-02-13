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
