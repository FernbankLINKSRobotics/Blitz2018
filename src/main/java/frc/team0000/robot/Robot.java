package frc.team0000.robot;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {
    @Override
    public void robotInit(){}

    @Override
    public void disabledInit(){}
    
    @Override
    public void disabledPeriodic(){}

    @Override
    public void autonomousInit(){}

    @Override
    public void autonomousPeriodic(){}

    @Override
    public void teleopInit(){}

    @Override
    public void teleopPeriodic(){}

    @Override
    public void testInit(){
        System.out.print("TESTING");
    }

    @Override
    public void testPeriodic(){
        System.out.print("IN TESTING");
    }
}