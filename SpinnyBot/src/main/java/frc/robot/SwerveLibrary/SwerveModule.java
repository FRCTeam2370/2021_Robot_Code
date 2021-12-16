/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.SwerveLibrary;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class SwerveModule {
private double positionX,positionY;
private WPI_TalonFX rotationMotor,speedMotor;
private static double ticksToDegrees = 0.01017265251;
private static boolean reversed;

double originX = 0;
double originY = 0;

    public SwerveModule(double positionX, double positionY, WPI_TalonFX rotationMotor, WPI_TalonFX speedMotor){
        this.positionX=positionX;
        this.positionY=positionY;
        this.rotationMotor = rotationMotor;
        this.speedMotor = speedMotor;
        this.originX = originX;
        this.originY = originY;
    }

    public void setOrigin(double x, double y){
        originX = x;
        originY = y;
    }

    public void setRotationAngle(double x, double y, double z){
        double Wxi = x+ z*positionY+originY;
        double Wyi = y-z*positionX+originX;

        double encoderAngle = (rotationMotor.getSensorCollection().getIntegratedSensorPosition()*ticksToDegrees);
        double encoderAngle360 = encoderAngle>0? encoderAngle%360: (encoderAngle%360)+360;
        double rawAngle = ((180/Math.PI)*Math.atan2(Wxi, Wyi)+180);    
    
        

        double distance = (rawAngle - encoderAngle360);

        if(Math.abs(distance)>180){
            distance = distance>0? -360+distance: 360+distance;
        }

        if(Math.abs(distance)>90){
            distance = distance<0?distance+180 %360:distance-180%360;
            reversed=true;
        }else{
            reversed =false;
        }

        double newTarget = distance+encoderAngle;
        
        SmartDashboard.putNumber("raw target", rawAngle);
        SmartDashboard.putNumber("position", encoderAngle);
        SmartDashboard.putNumber("position360", encoderAngle360);
        SmartDashboard.putNumber("target", newTarget);
        SmartDashboard.putNumber("Xposition", positionX);
        SmartDashboard.putNumber("Yposition", positionY);
        SmartDashboard.putNumber("distance", distance);
        SmartDashboard.putNumber("Wxi", Wxi);
        SmartDashboard.putNumber("Wyi", Wyi);
        SmartDashboard.putNumber("x", x);
        SmartDashboard.putNumber("y", y);
        SmartDashboard.putNumber("z", z);
        SmartDashboard.putBoolean("reversed", reversed);

        rotationMotor.set(ControlMode.Position, newTarget*(1/ticksToDegrees));

    }

    public void setDriveSpeed(double x, double y, double z){
        double Wxi = x+ z*positionY;
        double Wyi = y-z*positionX;

        double speed = Math.sqrt((Wxi*Wxi)+(Wyi*Wyi));

        if(reversed){
            speed = speed*-1;
        }
        SmartDashboard.putNumber("speed", speed);
        speedMotor.set(ControlMode.PercentOutput, speed);
    }

    public void resetEncoders(){
        rotationMotor.getSensorCollection().setIntegratedSensorPosition(0, 0);
        speedMotor.getSensorCollection().setIntegratedSensorPosition(0, 0);
    }




    /*public static void setMotorAngle(SwerveModule s, double angle){
        s.rotationMotor.set(ControlMode.Position, angle*(1/ticksToDegrees));
    }

    public static void setMotorSpeed(SwerveModule s, double speed){
        s.speedMotor.set(ControlMode.PercentOutput, speed);
    }*/

}
