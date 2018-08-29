package frc.team0000.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import frc.team0000.robot.Lib.SubsystemManager;
import frc.team0000.robot.Subs.Arm;
import frc.team0000.robot.Subs.Drivetrain;

public class Robot extends IterativeRobot {

    public static Drivetrain drivetrain;
    public static Arm arm;
    public static IO io;

    private SubsystemManager sm;

    @Override
    public void robotInit(){
        drivetrain = new Drivetrain();
        arm = new Arm();
        io = new IO();
        sm = new SubsystemManager(
            drivetrain,
            arm
        );
    }

    @Override
    public void disabledInit(){
        sm.stop();
    }
    
    @Override
    public void disabledPeriodic(){}

    @Override
    public void autonomousInit(){
        sm.start();
    }

    @Override
    public void autonomousPeriodic(){
        sm.log();
    }

    @Override
    public void teleopInit(){}

    @Override
    public void teleopPeriodic(){
        io.update();
        sm.update();
        sm.log();
    }

    @Override
    public void testInit(){
    }

    @Override
    public void testPeriodic(){
        sm.log();
    }
}