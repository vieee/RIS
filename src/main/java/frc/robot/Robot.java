// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

/**
 * This is a demo program showing the use of the DifferentialDrive class. Runs
 * the motors with
 * arcade steering.
 */
public class Robot extends TimedRobot {
  private CANSparkMax leftMotorf;
  private CANSparkMax leftMotorb;
  private CANSparkMax rightMotorf;
  private CANSparkMax rightMotorb;
  private MotorControllerGroup left, right;
  private CANSparkMax intakeRoller;
  private CANSparkMax shooter;
  private CANSparkMax intakeAngle;
  private CANSparkMax indexing;
  private DifferentialDrive drive;
  private Joystick joy;

  @Override
  public void robotInit() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    this.joy = new Joystick(0);

    // drive motors
    leftMotorf = new CANSparkMax(1, MotorType.kBrushless);
    leftMotorb = new CANSparkMax(2, MotorType.kBrushless);
    rightMotorf = new CANSparkMax(3, MotorType.kBrushless);
    rightMotorb = new CANSparkMax(4, MotorType.kBrushless);

    left = new MotorControllerGroup(leftMotorf, leftMotorb);
    right = new MotorControllerGroup(rightMotorf, rightMotorb);
    right.setInverted(true);

    drive = new DifferentialDrive(left, right);

    // intake motors
    intakeRoller = new CANSparkMax(6, MotorType.kBrushless);
    intakeAngle = new CANSparkMax(32, MotorType.kBrushless);

    // indexing
    indexing = new CANSparkMax(7, MotorType.kBrushless);

    // shooter
    shooter = new CANSparkMax(8, MotorType.kBrushed);
  }

  @Override
  public void teleopPeriodic() {
    // Drive with arcade drive.
    // That means that the Y axis drives forward
    // and backward, and the X turns left and right.
    double speed = -joy.getRawAxis(1) * 0.6;
    double turn = joy.getRawAxis(0) * 0.6;
    double intakeRuns = -joy.getRawAxis(2) * 0.6;

    drive.arcadeDrive(speed, turn);
    intakeAngle.set(intakeRuns);

    if (joy.getRawButton(1)) {
      shooter.set(-0.4);
    } else {
      shooter.set(0.0);
    }

    if (joy.getRawButton(2)) {
      indexing.set(-0.4);
    } else {
      indexing.set(0.0);
    }

    if (joy.getRawButton(3)) {
      intakeRoller.set(-0.4);
    } else {
      intakeRoller.set(0.0);
    }

  }
}
