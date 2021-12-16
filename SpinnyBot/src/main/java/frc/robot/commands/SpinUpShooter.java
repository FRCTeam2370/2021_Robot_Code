/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
//import frc.robot.subsystems.Magazine;
import frc.robot.subsystems.Shooter;

/**
 * An example command that uses an example subsystem.
 */
public class SpinUpShooter extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  

  private double targetV;
  /**
   * Creates a new Shoot.
   *
   * @param subsystem The subsystem used by this command.
   */
  public SpinUpShooter(Shooter subsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if(Shooter.getLimelightYOffset()<-13){
      targetV = 7700;
    } else if(Shooter.getLimelightYOffset()>-13){// && Shooter.getLimelightYOffset()<10){
      targetV = 7350;
    } else{
      targetV = 13500;
    }
    Shooter.shooterMotor.set(ControlMode.Velocity, targetV);
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    //targetV = /*Math.min(*/Shooter.speed/*, 6100)*/ * 2048 / 600;
//    targetV = 7700;
//    Shooter.shooterMotor.set(ControlMode.Velocity, targetV);
    //Shooter.shooterMotor2.set(ControlMode.Velocity, targetV);
    

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Shooter.shooterMotor.stopMotor();
    //Shooter.shooterMotor.set(ControlMode.Velocity, 0);
    //Shooter.shooterMotor2.set(ControlMode.Velocity, 0);
    }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(Shooter.shooterMotor.getClosedLoopError())<200 && Shooter.shooterMotor.getMotorOutputPercent()>0.35;
  }
}