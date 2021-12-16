/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  /**
   * Creates a new Intake.
   */
  public Intake() {
  }

  public static WPI_TalonFX intakeMotor = new WPI_TalonFX(Constants.intakeMotorPort);

  public static double intakeSpeed = 0.5;

  public static void runIntake(){
    intakeMotor.set(ControlMode.PercentOutput, intakeSpeed);
  }

  public static void reverseIntake(){
    intakeMotor.set(ControlMode.PercentOutput, -intakeSpeed);
  }

  public static void stopIntake(){
    intakeMotor.stopMotor();
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}