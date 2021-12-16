/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.ResourceBundle.Control;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class Spindexer extends SubsystemBase {
  /**
   * Creates a new Spindexer.
   */
  public Spindexer() {
    SpindexerMotor.configFactoryDefault();
    //SpindexerMotor.configOpenloopRamp(1);
  }

  public static WPI_TalonFX SpindexerMotor = new WPI_TalonFX(Constants.spindexerMotorPort);
  private static double speed = .20;
  private static double quickSpeed = .30;

  public static void spinIndexer(){
    SpindexerMotor.set(ControlMode.PercentOutput, speed);
  }

  public static void spinIndexerQuick(){
    SpindexerMotor.set(ControlMode.PercentOutput, quickSpeed);
  }

  public static void stopIndexer(){
    SpindexerMotor.stopMotor();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Indexer Current", SpindexerMotor.getSupplyCurrent());
    SmartDashboard.putBoolean("Indexer Jam", SpindexerMotor.getSupplyCurrent()<2.1);
    if(SpindexerMotor.getSupplyCurrent()>2.1){
      RobotContainer.stick.setRumble(RumbleType.kLeftRumble, 1);
      RobotContainer.stick.setRumble(RumbleType.kRightRumble, 1);
      RobotContainer.stick2.setRumble(RumbleType.kLeftRumble, 1);
      RobotContainer.stick2.setRumble(RumbleType.kRightRumble, 1);
    } else{
      RobotContainer.stick.setRumble(RumbleType.kLeftRumble, 0);
      RobotContainer.stick.setRumble(RumbleType.kRightRumble, 0);
      RobotContainer.stick2.setRumble(RumbleType.kLeftRumble, 0);
      RobotContainer.stick2.setRumble(RumbleType.kRightRumble, 0);
    }
    //1.3 is cutoff for jam
    // This method will be called once per scheduler run
  }
}
