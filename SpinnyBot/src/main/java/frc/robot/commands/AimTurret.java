/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Turret;

public class AimTurret extends CommandBase {
  /**
   * Creates a new AimTurret.
   */
  public AimTurret(Turret turret) {
    addRequirements(turret);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Turret.resetEncoder();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    SmartDashboard.putNumber("Targets?", NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0));
    if(NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0) == 1 ){//&& Math.abs(Shooter.getLimelightXOffset())>0.5){
    Turret.turnTurret((Shooter.getLimelightXOffset()+1)*0.02 + (Math.signum(Shooter.getLimelightXOffset())*0.03));
    SmartDashboard.putNumber("TriggerInput", Shooter.getRotation());
    SmartDashboard.putNumber("Motor Output", Turret.turretMotor.getMotorOutputPercent());
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Turret.stopTurret();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
