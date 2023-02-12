package frc.robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.lang.Math;


public class Utilities {

    public static double correctAngle(double dEncoder_in, double dOffset_in, double dDegreesPerRev_in) {
        double dValue;
        double dAngle;
        dValue = dEncoder_in;
        dAngle = dValue;
        SmartDashboard.putNumber("value", dValue);
        
        dAngle = (dAngle * dDegreesPerRev_in);   
        dAngle = dAngle - dOffset_in;       //makes the percents aligned with the table horizontally
        dAngle = dAngle % 360;
        if((dAngle % 360) < 0) {
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


} 
