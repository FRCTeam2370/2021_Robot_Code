/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2020 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.SwerveLibrary.SwerveDrive;
import frc.robot.SwerveLibrary.SwerveModule;

public class Drivetrain extends SubsystemBase {
  /**
   * Creates a new ExampleSubsystem.
   */
  public Drivetrain() {
    motorInit(frontLeftDrive);
    motorInit(frontRightDrive);
    motorInit(backLeftDrive);
    motorInit(backRightDrive);
    motorInit(frontLeftSpin);
    motorInit(frontRightSpin);
    motorInit(backLeftSpin);
    motorInit(backRightSpin);
    drive.resetGyro();
  }

  public static WPI_TalonFX frontLeftDrive = new WPI_TalonFX(Constants.Drivefl);
  public static WPI_TalonFX frontRightDrive = new WPI_TalonFX(Constants.Drivefr);
  public static WPI_TalonFX backLeftDrive = new WPI_TalonFX(Constants.Drivebl);
  public static WPI_TalonFX backRightDrive = new WPI_TalonFX(Constants.Drivebr);
  public static WPI_TalonFX frontLeftSpin = new WPI_TalonFX(Constants.Spinfl);
  public static WPI_TalonFX frontRightSpin = new WPI_TalonFX(Constants.Spinfr);
  public static WPI_TalonFX backLeftSpin = new WPI_TalonFX(Constants.Spinbl);
  public static WPI_TalonFX backRightSpin = new WPI_TalonFX(Constants.Spinbr);

  public static AHRS ahrs = new AHRS();

  public static SwerveModule frontLeftModule = new SwerveModule(-1, 1, frontLeftSpin, frontLeftDrive);
  public static SwerveModule frontRightModule = new SwerveModule(1, 1, frontRightSpin, frontRightDrive);
  public static SwerveModule backLeftModule = new SwerveModule(-1, -1, backLeftSpin, backLeftDrive);
  public static SwerveModule backRightModule = new SwerveModule(1, -1, backRightSpin, backRightDrive);
  
  public static SwerveDrive drive = new SwerveDrive(frontLeftModule, frontRightModule, backLeftModule, backRightModule, ahrs,0,0);

public static void resetMotors(){
  motorInit(frontLeftDrive);
  motorInit(frontRightDrive);
  motorInit(backLeftDrive);
  motorInit(backRightDrive);
  motorInit(frontLeftSpin);
  motorInit(frontRightSpin);
  motorInit(backLeftSpin);
  motorInit(backRightSpin);
  initSwerveRotation(frontLeftSpin);
  initSwerveRotation(frontRightSpin);
  initSwerveRotation(backLeftSpin);
  initSwerveRotation(backRightSpin);
}

  public static void motorInit(WPI_TalonFX motor){
    motor.configFactoryDefault();
    motor.config_kP(0, 0.1);
    motor.config_kI(0, 0);
    motor.config_kD(0, 0);
    motor.config_kF(0, 0);
    motor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor,0,0);
    motor.configAllowableClosedloopError(0, 0, 0);
    motor.configFeedbackNotContinuous(true, 0);
    motor.setNeutralMode(NeutralMode.Brake);
    motor.getSensorCollection().setIntegratedSensorPosition(0, 0);
  }

  public static void initSwerveRotation(WPI_TalonFX motor){
    motor.configPeakOutputForward(0.5);
    motor.configPeakOutputReverse(-0.5);
  }

  @Override
  public void periodic() {
    if(RobotState.isDisabled()){
      frontLeftDrive.setNeutralMode(NeutralMode.Coast);
      frontRightDrive.setNeutralMode(NeutralMode.Coast);
      backLeftDrive.setNeutralMode(NeutralMode.Coast);
      backRightDrive.setNeutralMode(NeutralMode.Coast);
      frontLeftSpin.setNeutralMode(NeutralMode.Coast);
      frontRightSpin.setNeutralMode(NeutralMode.Coast);
      backLeftSpin.setNeutralMode(NeutralMode.Coast);
      backRightSpin.setNeutralMode(NeutralMode.Coast);
    }else{
      frontLeftDrive.setNeutralMode(NeutralMode.Brake);
      frontRightDrive.setNeutralMode(NeutralMode.Brake);
      backLeftDrive.setNeutralMode(NeutralMode.Brake);
      backRightDrive.setNeutralMode(NeutralMode.Brake);
      frontLeftSpin.setNeutralMode(NeutralMode.Brake);
      frontRightSpin.setNeutralMode(NeutralMode.Brake);
      backLeftSpin.setNeutralMode(NeutralMode.Brake);
      backRightSpin.setNeutralMode(NeutralMode.Brake);
    }
      SmartDashboard.putNumber("FREncoder", frontRightSpin.getSensorCollection().getIntegratedSensorPosition());
      if(RobotContainer.Start2.get()){
        resetMotors();
      }

      /*frontLeftDrive.getSensorCollection().setIntegratedSensorPosition(0, 0);
      frontRightDrive.getSensorCollection().setIntegratedSensorPosition(0, 0);
      backLeftDrive.getSensorCollection().setIntegratedSensorPosition(0, 0);
      backRightDrive.getSensorCollection().setIntegratedSensorPosition(0, 0);
      frontLeftSpin.getSensorCollection().setIntegratedSensorPosition(0, 0);
      frontRightSpin.getSensorCollection().setIntegratedSensorPosition(0, 0);
      backLeftSpin.getSensorCollection().setIntegratedSensorPosition(0, 0);
      backRightSpin.getSensorCollection().setIntegratedSensorPosition(0, 0);*/
    
    // This method will be called once per scheduler run
    
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
