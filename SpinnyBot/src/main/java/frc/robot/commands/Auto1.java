/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019-2020 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterLoader;
import frc.robot.subsystems.Spindexer;
import frc.robot.subsystems.Turret;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Auto1 extends SequentialCommandGroup {
  /**
   * Creates a new Auto1.
   */
  public Auto1(Turret turret, Shooter shoot, Spindexer spin, Drivetrain drive, ShooterLoader shootload) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new AutoDrive(0, -0.3, 0, drive).withTimeout(1.3),new AimTurret(turret).withTimeout(0.5), new SpinUpShooter(shoot), new ParallelCommandGroup(new Shoot2(shoot), new SpindexerShoot(spin), new LoadShooter(shootload)).withTimeout(5));
  }
}
