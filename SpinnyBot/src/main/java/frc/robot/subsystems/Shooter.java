/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class Shooter extends SubsystemBase {
  /**
   * Creates a new Shooter.
   */
  public Shooter() {
    shooterMotor.configOpenloopRamp(0.5);
    configPID();
  }

  //limelight setup
  public static NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  public static NetworkTableEntry tx = table.getEntry("tx");
  public static NetworkTableEntry ty = table.getEntry("ty");
  public NetworkTableEntry ta = table.getEntry("ta");
  public static NetworkTableEntry LED = table.getEntry("ledMode");
  public static NetworkTableEntry Camera = table.getEntry("camMode");
  public static boolean operatorAlign; 
  public static double offsetRatio = 15;
  public static double offsetInDegrees = 3.3;
  
  public static double limelightAngle = 25;
  public static double totalTangent;
  public static double startingAngle;
  public static double adjustedHeight = 64.5;  // 98 - 33.5


  //old shooter code
  public static String ManualToggle = "none";
  private static int timeout = 30;
  private static int slotIdx = 0;
  public static double kF = .0522;//.0522
  public static double kP = 0.016;
  //public static double kI = 0.000005;//000005;
  public static double kD = 0.8;
  public static double speed = 1800;
  //don't touch these (\/) unless you know what you are doing
  public static double StartingBaseSpeed = 1975;
  public static double BaseSpeed = 1975;
  public static double scaling = 1.535;
  private boolean isSet;

  public static double getSpeed(){
    double raw = (shooterMotor.getSensorCollection().getIntegratedSensorVelocity() /2048)*600;
    return raw;
  }

  public static void configPID(){
    //Configure Motor 1
    shooterMotor.configFactoryDefault();
    shooterMotor.setSensorPhase(true);
    shooterMotor.configNominalOutputForward(0, timeout);
    shooterMotor.configNominalOutputReverse(0, timeout);
    shooterMotor.configPeakOutputForward(1, timeout);
    shooterMotor.configPeakOutputReverse(-1, timeout);
    shooterMotor.config_kF(slotIdx, kF, timeout);
    shooterMotor.config_kP(slotIdx, kP, timeout);
    //shooterMotor.config_kI(slotIdx, kI, timeout);
    shooterMotor.config_kD(slotIdx, kD, timeout);
  }


  private static double shooterSpeed = -0.5;

  public static WPI_TalonFX shooterMotor = new WPI_TalonFX(Constants.shooterMotorPort);

  public static void runShooter(){
    shooterMotor.set(ControlMode.PercentOutput, shooterSpeed);
  }


  //limelight functions
  public double distanceToTarget(){
    return (adjustedHeight) / (totalTangent);
  }

 public double getOffsetDegrees(){
   return offsetInDegrees;
 }
 public static double getLimelightXOffset() {
   double x = tx.getDouble(0.0);
   return x;
 }

 public static double getLimelightYOffset() {
   double y = ty.getDouble(0.0);
   return y;
 }

 public double getLimelightTargetArea() {
   double a = ta.getDouble(0.0);
   return a;
 }

 
 public boolean getOperatorAllign() {
  return operatorAlign;
}

public static double getRotation() {
  // Negetive is Left
  // Positive is Right
  return (getLimelightXOffset() - offsetInDegrees) / offsetRatio;
}

public void setLEDState(double value){
  LED.setNumber(value);
}

public void switchOperatorControl() {
  operatorAlign = !operatorAlign;
}

public void setCameraMode() {
  if (operatorAlign == false) {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1);
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(0);
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("snapshot").setNumber(0);
  } else {  
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);
    SmartDashboard.putNumber("limelight", NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").getDouble(0.0));
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(1);
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("snapshot").setNumber(1);
  }
}
public void sendToDashboard(){
  SmartDashboard.putString("IsThisConnected", NetworkTableInstance.getDefault().getTable("limelight").getSubTables().toString()); 
  SmartDashboard.putNumber("Limelight X", NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0.0));
  SmartDashboard.putNumber("Limelight Y", getLimelightYOffset());
  SmartDashboard.putBoolean("Operator Align", operatorAlign);
  //SmartDashboard.putNumber("Limelight A", getLimelightTargetArea());
}


  @Override
  public void periodic() {  
    if(getLimelightYOffset()<12 && getLimelightYOffset()>0){
      SmartDashboard.putBoolean("Shoot", true);
      RobotContainer.stick.setRumble(RumbleType.kLeftRumble, 0.6);
      RobotContainer.stick.setRumble(RumbleType.kRightRumble, 0.6);
      RobotContainer.stick2.setRumble(RumbleType.kLeftRumble, 0.6);
      RobotContainer.stick2.setRumble(RumbleType.kRightRumble, 0.6);
    } else{
      SmartDashboard.putBoolean("Shoot", false);
      RobotContainer.stick.setRumble(RumbleType.kLeftRumble, 0);
      RobotContainer.stick.setRumble(RumbleType.kRightRumble, 0);
      RobotContainer.stick2.setRumble(RumbleType.kLeftRumble, 0);
      RobotContainer.stick2.setRumble(RumbleType.kRightRumble, 0);
    }
    //SmartDashboard.putNumber("Shooter Speed", shooterMotor.getClosedLoopTarget());
    SmartDashboard.putNumber("limelight Y",Math.round(getLimelightYOffset()));
    SmartDashboard.putNumber("Shooter Error", shooterMotor.getClosedLoopError());
    SmartDashboard.putNumber("Actual", shooterMotor.getSensorCollection().getIntegratedSensorVelocity());
    SmartDashboard.putNumber("ShooterPercent", shooterMotor.getMotorOutputPercent());
    speed = Math.min(BaseSpeed * Math.pow(distanceToTarget(), 0.0525) + 0.5 * Math.pow(distanceToTarget(), scaling), 6100);
    // This method will be called once per scheduler run
    startingAngle = getLimelightYOffset() + limelightAngle;
    totalTangent = Math.tan(Math.toRadians(startingAngle));
    
    if(!RobotState.isDisabled()){ //|| RobotContainer.stick.getRawButton(6)){//|| RobotContainer.stick.getRawButton(3) || RobotContainer.stick.getRawButton(4)){
      operatorAlign = true;
    } else{
      operatorAlign = false;
    }


//    if(Climber.getClimbmode()){
  //    operatorAlign = false;
    //}

    setCameraMode();
    sendToDashboard();

  }
}