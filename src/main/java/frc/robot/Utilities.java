package frc.robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


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
        dAngle = dAngle + 360;
        }

        SmartDashboard.putNumber("Angle", dAngle);
                                        
        return dAngle;
    }



} 
