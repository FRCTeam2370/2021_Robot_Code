/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Turret extends SubsystemBase {
  /**
   * Creates a new Turret.
   */
  public Turret() {
    resetEncoder();
    turretMotor.configFactoryDefault();
    turretMotor.config_kP(0, kP);
    turretMotor.config_kI(0, kI);
    turretMotor.config_kD(0, kD);
    turretMotor.config_kF(0, kF);
    turretMotor.configClosedLoopPeakOutput(1, maxSpeed);
    turretMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor,0,0);
    turretMotor.configForwardSoftLimitThreshold(Math.round(90*ticksperDegree()));
    turretMotor.configReverseSoftLimitThreshold(Math.round(-90*ticksperDegree()));
    turretMotor.configPeakOutputForward(0.15);
    turretMotor.configPeakOutputReverse(-0.15);
  }

  private static double limeLightX = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
  private static double limeLightY = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
  private static double cameraMode = NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").getDouble(0);
  

  public void toggleLimelightMode(){
    if(cameraMode == 0){
      //switch to driver mode
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1);
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
    } else{
      //switch to vision mode
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);      
    }

  }

  public static void setLimelightMode(int setting){
    if(setting == 0){
      //driver mode
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1);
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
    } else{
      //vision mode
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);      
    }

  }


  private static double maxSpeed = 0.5;
  public static WPI_TalonFX turretMotor = new WPI_TalonFX(Constants.turretMotorPort);
  private static double kP = 0.2;
  private static double kI = 0;
  private static double kD = 0;
  private static double kF = 0;
  private static double measurement;
  private static float gearRatio = 11.875f;
  private static float encoderTicks = 2048;
  
  
  private static float ticksperRotation() {
    return gearRatio * encoderTicks;
  }

  private static float ticksperDegree() {
    return ticksperRotation() / 360;
  }

  private double getDegrees() {
    double raw = turretMotor.getSensorCollection().getIntegratedSensorPosition();
    return raw / ticksperDegree();
  }

  public static void resetEncoder() {
    turretMotor.getSensorCollection().setIntegratedSensorPosition(0, 0);
  }

  public static double getTarget() {
    return limeLightX;
  }

  public static void turnTurret(double degrees) {
    //double setpoint = degrees * ticksperDegree();
    turretMotor.set(ControlMode.PercentOutput, degrees);
  }

  public static void stopTurret(){
    turretMotor.stopMotor();
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
