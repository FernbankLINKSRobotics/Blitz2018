package frc.team0000.robot.Subs;

import frc.team0000.robot.Lib.RobotState;
import frc.team0000.robot.Lib.Subsystem;
import edu.wpi.first.wpilibj.Timer;
import frc.team0000.robot.Constants;
import frc.team0000.robot.Robot;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.kauailabs.navx.frc.AHRS;

public class Localization implements Subsystem {
    private double pt_ = 0;
    private double dt_ = 0;
    private double x_ = 0;
    private double y_ = 0;

    private RobotState state_ = new RobotState();

    private Encoder leftEnc_ = new Encoder(
        Constants.leftEnc1, 
        Constants.leftEnc2, 
        Constants.leftEncInverted, 
        Encoder.EncodingType.k4X
    );
    
    private Encoder rightEnc_ = new Encoder(
            Constants.rightEnc1, 
            Constants.rightEnc2, 
            Constants.rightEncInverted,
            Encoder.EncodingType.k4X
    );

    private AHRS gyro = new AHRS(Constants.gyroPort);

    public Localization(){}

    public RobotState getState(){
        return state_;
    }

    public double getRadians(){
        return gyro.getYaw() * (Math.PI / 180);
    }

    public double getDegree(){
        return gyro.getYaw();
    }

    public double getRadiansPerSec(){
        return gyro.getRate() * (Math.PI / 180);
    }

    public double getDegreePerSec(){
        return gyro.getRate();
    }

    public double getVelocity(){
        return ((rightEnc_.getRate() + leftEnc_.getRate()) / 2) * Constants.distancePerPulse;
    }

    public double getDistance(){
        return ((rightEnc_.getDistance() + leftEnc_.getDistance()) / 2) * Constants.distancePerPulse;
    }

    public double getLeftDistance(){
        return leftEnc_.getDistance() * Constants.distancePerPulse;
    }

    public double getRightDistance(){
        return rightEnc_.getDistance() * Constants.distancePerPulse;
    }

    public double getLeftVelocity(){
        return leftEnc_.getRate() * Constants.distancePerPulse;
    }

    public double getRightVelocity(){
        return rightEnc_.getRate() * Constants.distancePerPulse;
    }

    public double dt(){
        return dt_;
    }

    public double x(){
        return x_;
    }

    public double y(){
        return y_;
    }

    public void reset(){
        pt_ = 0;
        x_ = 0;
        y_ = 0;
        leftEnc_.reset();
        rightEnc_.reset();
        gyro.reset();
    }

    @Override public void start(){
        reset();
    }

    @Override public void update(){
        double t = Timer.getFPGATimestamp();
        dt_ = t - pt_;
        pt_ = t;

        x_ += Math.cos(getRadians() - Math.PI/2) * getVelocity() * dt();
        y_ += Math.sin(getRadians() - Math.PI/2) * getVelocity() * dt();

        state_.set(x_, y_, getVelocity(), getRadians());
    }

    @Override public void stop(){}
    @Override public void log(){
        SmartDashboard.putNumber("Dead X", x());
        SmartDashboard.putNumber("Dead Y", y());
        SmartDashboard.putNumber("Left Distance", getLeftDistance());
        SmartDashboard.putNumber("Right Distance", getRightDistance());
        SmartDashboard.putNumber("Distance", getDistance());
        SmartDashboard.putNumber("Left Velocity", getLeftVelocity());
        SmartDashboard.putNumber("Right Velocity", getRightVelocity());
        SmartDashboard.putNumber("Velocity", getVelocity());
        SmartDashboard.putNumber("Angle", getDegree());
        SmartDashboard.putNumber("Anglular Velocity", getDegreePerSec());
    }
}