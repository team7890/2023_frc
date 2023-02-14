// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants;
// import frc.robot.commands.Autos;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

// //Arm Imports
// import frc.robot.subsystems.Arm_subsystem;
// //ForeArm Imports
// import frc.robot.subsystems.Forearm_subsystem;
// //Wrist Imports
// import frc.robot.subsystems.Wrist_subsystem;
//Begginning of Swerve Imports
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
//Already Imported: import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.autos.*;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
//End of Swerve Imports

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  private final Arm_subsystem objArm_subsystem = new Arm_subsystem();
  private final Forearm_subsystem objForearm_subsystem = new Forearm_subsystem();
  private final Wrist_subsystem objWrist_subsystem = new Wrist_subsystem();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_CoPilotController = new CommandXboxController(Constants.Controllers.iCoPilot);
  
  private final Joystick obj_ButtonBox = new  Joystick(Constants.Controllers.iButtonBox);
  JoystickButton ButtonOne = new JoystickButton(obj_ButtonBox, 1);

  //For Swerve
  /* Controllers */
  // private final Joystick m_DriverController = new Joystick(Constants.Controllers.iDriver);            //This is a change from team:364 code because we used CommandXboxController
  private final CommandXboxController m_DriverController = new CommandXboxController(Constants.Controllers.iDriver);    //Amalan How do we do this with CommandXboxController?

  /* Drive Controls */
  // private final int translationAxis = XboxController.Axis.kLeftY.value;            //This is a change from team:364 code because we used CommandXboxController
  // private final int strafeAxis = XboxController.Axis.kLeftX.value;            //This is a change from team:364 code because we used CommandXboxController
  // private final int rotationAxis = XboxController.Axis.kRightX.value;            //This is a change from team:364 code because we used CommandXboxController

  /* Driver Buttons */
  // private final JoystickButton zeroGyro = new JoystickButton(m_DriverController, XboxController.Button.kY.value);            //This is a change from team:364 code because we used CommandXboxController
  // private final JoystickButton robotCentric = new JoystickButton(m_DriverController, XboxController.Button.kLeftBumper.value);            //This is a change from team:364 code because we used CommandXboxController

  /* Subsystems */
  private final Swerve_subsystem s_Swerve = new Swerve_subsystem();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    //Swerve Stuff
    
          //This is a change from team:364 code because we used CommandXboxController
    // s_Swerve.setDefaultCommand(              //Amalan How do we do this with CommandXboxController?
    //   new TeleopSwerve(
    //     s_Swerve, 
        // () -> -m_DriverController.getRawAxis(translationAxis), 
    //     () -> -m_DriverController.getRawAxis(strafeAxis), 
    //     () -> -m_DriverController.getRawAxis(rotationAxis), 
    //     () -> robotCentric.getAsBoolean()
    //   )
    // );

    s_Swerve.setDefaultCommand(
      new TeleopSwerve(
        s_Swerve, 
        () -> -m_DriverController.getLeftY(), 
        () -> -m_DriverController.getLeftX(),
        () -> -m_DriverController.getRightX(),
        () -> m_DriverController.leftBumper().getAsBoolean()
      )
    );


    
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    //m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());

    m_CoPilotController.x().whileTrue(new Arm_command(objArm_subsystem, Constants.Arm.dArmSpeedManual, false, 0.0));         
    m_CoPilotController.y().whileTrue(new Arm_command(objArm_subsystem, -Constants.Arm.dArmSpeedManual, false, 0.0));        
    // m_CoPilotController.rightBumper().whileTrue(new Arm_command(objArm_subsystem, -Constants.Arm.dArmSpeedManual, true, -16.7));                    //Ask about - infront of Constants.Arm.dArmSpeedManual 

    m_CoPilotController.back().whileTrue(new Forearm_command(objForearm_subsystem, Constants.Forearm.dSpeedManual, false, 0.0));           // Back Button = Left button
    m_CoPilotController.start().whileTrue(new Forearm_command(objForearm_subsystem, -Constants.Forearm.dSpeedManual, false, 0.0));         // Start Button = Right button
    // m_CoPilotController.leftBumper().whileTrue(new Forearm_command(objForearm_subsystem, -Constants.Forearm.dSpeedManual, true, 0.0));     //Also has the negative -
    
    m_CoPilotController.a().whileTrue(new Wrist_command(objWrist_subsystem, Constants.Wrist.dSpeedManual, false, 0.0));
    m_CoPilotController.b().whileTrue(new Wrist_command(objWrist_subsystem, -Constants.Wrist.dSpeedManual, false, 0.0));
    // m_CoPilotController.rightTrigger().whileTrue(new Wrist_command(objWrist_subsystem, -Constants.Wrist.dWristSpeedManual, true, 0.0));       //Also has the negative -

    // ButtonBox Stuff?
    // ButtonOne.onTrue(SignalLights_subsystem.changeLightColor());

    /* Testing New Command System */      //Looks Nice I Guess?
    // m_CoPilotController.leftTrigger().whileTrue(new Mech_command(objArm_subsystem, objForearm_subsystem, objWrist_subsystem, , ,));

    //Straight Up
    m_CoPilotController.rightTrigger().whileTrue(new Mech_command(objArm_subsystem, objForearm_subsystem, objWrist_subsystem, 0.0, 0.0, 0.0));
    
    //Pick Up Verticle Cone 
    m_CoPilotController.leftTrigger().whileTrue(new Mech_command(objArm_subsystem, objForearm_subsystem, objWrist_subsystem, -9.1, -118.28, -47.69));

    //Drop Cone on Top Cone Level
    // m_CoPilotController.rightBumper().whileTrue(new Mech_command(objArm_subsystem, objForearm_subsystem, objWrist_subsystem, 22.0, 27.0, 11.72));

    //Cube to Middle from front
    m_CoPilotController.leftBumper().whileTrue(new Mech_command(objArm_subsystem, objForearm_subsystem, objWrist_subsystem, -22.0, 114.0, 4.2));

    //Cube to Top from front
    m_CoPilotController.rightBumper().whileTrue(new Mech_command(objArm_subsystem, objForearm_subsystem, objWrist_subsystem, 9.1, 70.6, -2.8));


    /* Driver Buttons */    //For Swerve
    // zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));            //This is a change from team:364 code because we used CommandXboxController
    m_DriverController.leftTrigger().onTrue(new InstantCommand(s_Swerve::zeroGyro, s_Swerve));                                  //Amalan How do we do this with CommandXboxController?

    m_DriverController.a().onTrue(SignalLights_subsystem.changeLightColor());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    //   // An example command will be run in autonomous
    //   // return Autos.exampleAuto(m_exampleSubsystem);
  
    // An ExampleCommand will run in autonomous
    return new exampleAuto(s_Swerve);
  }

  
}
