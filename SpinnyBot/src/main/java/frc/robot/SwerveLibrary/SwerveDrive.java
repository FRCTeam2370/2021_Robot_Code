/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.SwerveLibrary;

import com.kauailabs.navx.frc.AHRS;

/**
 * Add your docs here.
 */
public class SwerveDrive {
    SwerveModule fl;
    SwerveModule fr;
    SwerveModule bl;
    SwerveModule br;
    private boolean fieldOriented = false;
    private AHRS gyro;
    public SwerveDrive(SwerveModule fl, SwerveModule fr, SwerveModule bl, SwerveModule br, AHRS gyro, double originX, double originY){
        this.fl = fl;
        this.fr = fr;
        this.bl = bl;
        this.br = br;
        this.gyro=gyro;
    }

    public void setDriveMode(boolean driveMode){
        fieldOriented=driveMode;
    }

    public void driveSwerve(double x, double y, double z){
        double xFinal;
        double yFinal;
        double zFinal;
        if(fieldOriented){
            double radians = gyro.getAngle() * Math.PI / 180;
            double cos = Math.cos(radians);
            double sin = Math.sin(radians);
            double fieldX = (x * cos - y * sin);
            double fieldY = (x * sin + y * cos);
            xFinal=fieldX;
            yFinal=fieldY;
            zFinal=z;
        } else{
            xFinal = x;
            yFinal = y;
            zFinal = z;
        }

        fl.setRotationAngle(xFinal, yFinal, zFinal);
        fl.setDriveSpeed(xFinal, yFinal, zFinal);
        
        fr.setRotationAngle(xFinal, yFinal, zFinal);
        fr.setDriveSpeed(xFinal, yFinal, zFinal);
        
        bl.setRotationAngle(xFinal, yFinal, zFinal);
        bl.setDriveSpeed(xFinal, yFinal, zFinal);
        
        br.setRotationAngle(xFinal, yFinal, zFinal);
        br.setDriveSpeed(xFinal, yFinal, zFinal);
    
    }

    public void resetEncoders(){
        fl.resetEncoders();
        fr.resetEncoders();
        bl.resetEncoders();
        br.resetEncoders();
    }

    public void resetGyro(){
        gyro.reset();
    }

    public void setOrigin(double x, double y){
        fl.setOrigin(x, y);
        fr.setOrigin(x, y);
        bl.setOrigin(x, y);
        br.setOrigin(x, y);
    }

}
