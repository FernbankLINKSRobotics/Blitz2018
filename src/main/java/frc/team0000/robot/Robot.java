package frc.team0000.robot;

import java.rmi.registry.LocateRegistry;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.team0000.robot.Auto.Routines.LineTest;
import frc.team0000.robot.Lib.SubsystemManager;
import frc.team0000.robot.Lib.Actions.Routine;
import frc.team0000.robot.Lib.Actions.RoutineExecutor;
import frc.team0000.robot.Subs.*;

public class Robot extends IterativeRobot {

    public static Localization localization;
    public static Drivetrain drivetrain;
    public static Shooter shooter;
    public static IO io;

    private SendableChooser<Routine> chooser_;
    private RoutineExecutor executor_ = null;
    private Routine routine_;

    private SubsystemManager sm;

    @Override
    public void robotInit(){
        localization = new Localization();
        drivetrain = new Drivetrain();
        shooter = new Shooter();
        io = new IO();
        sm = new SubsystemManager(
            localization,
            drivetrain,
            shooter
        );

        /*
        chooser_.addDefault("Base line",  new LineTest());
        SmartDashboard.putData("Autonomous Chooser", chooser_);
        */
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
        executor_ = new RoutineExecutor(new LineTest());
        executor_.start();
    }

    @Override
    public void autonomousPeriodic(){
        sm.log();
    }

    @Override
    public void teleopInit(){
        sm.start();
    }

    @Override
    public void teleopPeriodic(){
        io.update();
        sm.log();
        sm.update();
    }

    @Override
    public void testInit(){}

    @Override
    public void testPeriodic(){
        shooter.setPower(1);
    }
}