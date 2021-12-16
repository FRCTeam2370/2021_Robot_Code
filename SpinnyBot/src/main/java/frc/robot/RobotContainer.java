/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.AimTurret;
import frc.robot.commands.Auto1;
import frc.robot.commands.Drive;
import frc.robot.commands.Index;
import frc.robot.commands.LoadShooter;
import frc.robot.commands.LoadShooterSpin;
import frc.robot.commands.ResetSwerveMotors;
import frc.robot.commands.ReverseIndexer;
import frc.robot.commands.ReverseIntake;
import frc.robot.commands.RunIntake;
import frc.robot.commands.Shoot2;
import frc.robot.commands.SpinUpShooter;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterLoader;
import frc.robot.subsystems.Spindexer;
import frc.robot.subsystems.Turret;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  //private final Drivetrain drive = new Drivetrain();

  private final Drivetrain swerve = new Drivetrain();

  private final Turret m_turret = new Turret();

   private final ShooterLoader m_ShooterLoader = new ShooterLoader();

   private final Shooter m_shooter = new Shooter();
   
   private final Spindexer m_Spindexer = new Spindexer();

   private final Intake m_intake = new Intake();

   private final Auto1 m_autoCommand = new Auto1(m_turret, m_shooter, m_Spindexer, swerve, m_ShooterLoader);

   public static Joystick stick = new Joystick(0);

   public static JoystickButton Y = new JoystickButton(stick, 4);

   public static JoystickButton A = new JoystickButton(stick, 1);

  public static JoystickButton B = new JoystickButton(stick, 2);

  public static JoystickButton X = new JoystickButton(stick, 3);

   public static JoystickButton RB = new JoystickButton(stick, 6);

   public static JoystickButton LB = new JoystickButton(stick, 5);

   public static JoystickButton Start = new JoystickButton(stick, 8);


   public static Joystick stick2 = new Joystick(1);

   public static JoystickButton Y2 = new JoystickButton(stick2, 4);

   public static JoystickButton A2 = new JoystickButton(stick2, 1);

  public static JoystickButton B2 = new JoystickButton(stick2, 2);

  public static JoystickButton X2 = new JoystickButton(stick2, 3);

   public static JoystickButton RB2 = new JoystickButton(stick2, 6);

   public static JoystickButton LB2 = new JoystickButton(stick2, 5);

   public static JoystickButton Start2 = new JoystickButton(stick2, 8);

   public static JoystickButton Select2 = new JoystickButton(stick2, 7);

   
  public static double getLX(){
    double x = stick.getRawAxis(0);
    x=Math.abs(x)<0.05? 0: x;
    return x*-1;
  }

  public static double getLY(){
    double y = stick.getRawAxis(1);
    y=Math.abs(y)<0.05? 0: y;
    return y*1;
  }

  public static double getRX(){
    double rx = stick.getRawAxis(4);
    rx=Math.abs(rx)<0.05? 0: rx;
    return rx*-1;
  }

  public static double getLeftTrigger(){
    return stick.getRawAxis(2);
  }
  public static double getRightTrigger(){
    return stick.getRawAxis(3);
  }
  public static double getBothTriggers(){
    return getRightTrigger() - getLeftTrigger();
  }



   public static double leftStickAngle(){
    double x = getLX();
    double y = getLY();
    double out = Math.atan2(x, -y);
    double outDegrees = Math.toDegrees(out);
    SmartDashboard.putNumber("Joystick Angle", outDegrees);
    return outDegrees;
   }

   public static double leftStickDistance(){
     double x = getLX();
     double y = getLY();
     double out = Math.sqrt((x*x)+(y*y));
     return out;
   }


  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    //controller 1
    swerve.setDefaultCommand(new Drive(swerve));
    RB.whenHeld(new SequentialCommandGroup(new ParallelRaceGroup(new AimTurret(m_turret), new SpinUpShooter(m_shooter)), new ParallelCommandGroup(new Shoot2(m_shooter),new LoadShooterSpin(m_ShooterLoader,m_Spindexer))));
    A.toggleWhenPressed(new RunIntake(m_intake));
    Y.toggleWhenPressed(new Index(m_Spindexer));

    //controller 2
    A2.whenHeld(new ReverseIntake(m_intake));
    RB2.whenHeld(new AimTurret(m_turret));
    Y2.toggleWhenPressed(new ReverseIndexer(m_Spindexer));
    X2.whenHeld(new LoadShooter(m_ShooterLoader));
    Start2.whenPressed(new ResetSwerveMotors());
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
     //An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
