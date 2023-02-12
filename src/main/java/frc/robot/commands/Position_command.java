// package frc.robot.commands;

// import edu.wpi.first.wpilibj2.command.CommandBase;

// import edu.wpi.first.wpilibj2.command.Command;
// import frc.robot.Constants;

// // import frc.robot.commands.Arm_command;
// // import frc.robot.commands.Forearm_command;
// // import frc.robot.commands.Wrist_command;

// import frc.robot.subsystems.*;

// public class Position_command extends CommandBase{

//     private final Arm_subsystem objArm_subsystem = new Arm_subsystem();
//     private final Forearm_subsystem objForearm_subsystem = new Forearm_subsystem();
//     private final Wrist_subsystem objWrist_subsystem = new Wrist_subsystem();
    
//     /* Example Position */
//     // public void examplePosition() {
//     //     new Arm_command(objArm_subsystem, -Constants.Arm.dArmSpeedManual, true, 0.0);
//     //     new Forearm_command(objForearm_subsystem, -Constants.Forearm.dForearmSpeedManual, true, 0.0);
//     //     new Wrist_command(objWrist_subsystem, -Constants.Wrist.dWristSpeedManual, true, 0.0);
//     // }

//     public Position_command() {         //This is so dumb

//     }

//     public void PickupPosition() {
//         new Arm_command(objArm_subsystem, -Constants.Arm.dArmSpeedManual, true, -10.5);                                    //Doesn't like negative?
//         new Forearm_command(objForearm_subsystem, -Constants.Forearm.dForearmSpeedManual, true, 117.52);
//         new Wrist_command(objWrist_subsystem, -Constants.Wrist.dWristSpeedManual, true, 12.85);
//     }

//     public void middleCubePosition() {
//         new Arm_command(objArm_subsystem, -Constants.Arm.dArmSpeedManual, true, -12.9);                                     //Doesn't like negative?
//         new Forearm_command(objForearm_subsystem, -Constants.Forearm.dForearmSpeedManual, true, 78.6);
//         new Wrist_command(objWrist_subsystem, -Constants.Wrist.dWristSpeedManual, true, 0.3);
//     }

//     public void topCubePosition() {
//         new Arm_command(objArm_subsystem, -Constants.Arm.dArmSpeedManual, true, 9.8);
//         new Forearm_command(objForearm_subsystem, -Constants.Forearm.dForearmSpeedManual, true, -68.2);                     //Doesn't like negative?
//         new Wrist_command(objWrist_subsystem, -Constants.Wrist.dWristSpeedManual, true, -7.8);                              //Doesn't like negative?
//     }

//     public void middleConePosition() {
//         new Arm_command(objArm_subsystem, -Constants.Arm.dArmSpeedManual, true, -18.9);                                     //Doesn't like negative?
//         new Forearm_command(objForearm_subsystem, -Constants.Forearm.dForearmSpeedManual, true, 55.4);
//         new Wrist_command(objWrist_subsystem, -Constants.Wrist.dWristSpeedManual, true, 7.38);
//     }

//     public void topConePosition() {
//         new Arm_command(objArm_subsystem, -Constants.Arm.dArmSpeedManual, true, 34.121);
//         new Forearm_command(objForearm_subsystem, -Constants.Forearm.dForearmSpeedManual, true, -8.4);                      //Doesn't like negative?
//         new Wrist_command(objWrist_subsystem, -Constants.Wrist.dWristSpeedManual, true, -110.5);                            //Doesn't like negative?
//     }

//     // public void withinRobotPosition() {
//     //     new Arm_command(objArm_subsystem, -Constants.Arm.dArmSpeedManual, true, 0.0);                        //Angle Value Unknown
//     //     new Forearm_command(objForearm_subsystem, -Constants.Forearm.dForearmSpeedManual, true, 0.0);        //Angle Value Unknown
//     //     new Wrist_command(objWrist_subsystem, -Constants.Wrist.dWristSpeedManual, true, 0.0);                //Angle Value Unknown
//     // }

// }