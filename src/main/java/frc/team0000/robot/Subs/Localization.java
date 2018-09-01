package frc.team0000.robot.Subs;

import frc.team0000.robot.Lib.Subsystem;
import edu.wpi.first.wpilibj.Timer;
import frc.team0000.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Localization implements Subsystem {
    public Localization(){}

    private double pt_ = 0;
    private double dt_ = 0;
    private double x_ = 0;
    private double y_ = 0;

    public double dt(){
        return dt_;
    }

    public double x(){
        return x_;
    }

    public double y(){
        return y_;
    }

    //private 

    @Override public void start(){
        pt_ = 0;
        x_ = 0;
        y_ = 0;
    }

    @Override public void update(){
        double t = Timer.getFPGATimestamp();
        dt_ = t - pt_;
        pt_ = t;

        x_ += Math.cos(Robot.drivetrain.getRadians() - Math.PI/2) * Robot.drivetrain.getVelocity() * dt();
        y_ += Math.sin(Robot.drivetrain.getRadians() - Math.PI/2) * Robot.drivetrain.getVelocity() * dt();
    }

    @Override public void stop(){}
    @Override public void log(){
        SmartDashboard.putNumber("Dead X", x());
        SmartDashboard.putNumber("Dead Y", y());
    }
}
