package frc.team0000.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import frc.team0000.robot.Lib.SubsystemManager;
import frc.team0000.robot.Subs.Arm;
import frc.team0000.robot.Subs.Drivetrain;
import frc.team0000.robot.Subs.Localization;

public class Robot extends IterativeRobot {

    public static Localization localization;
    public static Drivetrain drivetrain;
    public static Arm arm;
    public static IO io;

    private SubsystemManager sm;

    @Override
    public void robotInit(){
        localization = new Localization();
        drivetrain = new Drivetrain();
        arm = new Arm();
        io = new IO();
        sm = new SubsystemManager(
            localization,
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
        
        System.out.println("X: " + localization.x());
        System.out.println("Y: " + localization.y()); 
        
        /*
        System.out.println(drivetrain.getDegree());
        System.out.println(drivetrain.getVelocity());

        System.out.println("Left: " + drivetrain.getLeftDistance());
        System.out.println("Right: " + drivetrain.getRightDistance());
        */

        //sm.log();
    }

    @Override
    public void testInit(){
        System.out.println("HIIIII");
    }

    @Override
    public void testPeriodic(){
        //System.out.println("Update");
        //sm.log();
    }
}