package frc.team0000.robot.Subs;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team0000.robot.Constants;
import frc.team0000.robot.Lib.Subsystem;

public class Drivetrain implements Subsystem {
    private SpeedControllerGroup left_ = new SpeedControllerGroup(
        new VictorSP(Constants.leftPair),
        new VictorSP(Constants.leftBot)
    );

    private SpeedControllerGroup right_ = new SpeedControllerGroup(
        new VictorSP(Constants.rightPair), 
        new VictorSP(Constants.rightBot)
    );

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

    private DifferentialDrive drive_ = new DifferentialDrive(left_, right_);

    private AHRS gyro = new AHRS(Constants.gyroPort);

    private DoubleSolenoid shift_ = new DoubleSolenoid(Constants.shifterPort1, Constants.shifterPort2);

    private double leftPower_ = 0;
    private double rightPower_ = 0;

    private boolean isHigh_ = false;

    public Drivetrain(){
        left_.setInverted(true);
    }

    public void setPower(double l, double r){
        leftPower_ = l;
        rightPower_ = r;
    }

    public void setLeft(double l){
        leftPower_ = l;
    }

    public void setRight(double r){
        rightPower_ = r;
    }

    public void setHighGear(boolean h){ 
        isHigh_ = h;
    }

    public double getRadians(){
        return gyro.getYaw() * (Math.PI / 180);
    }

    public double getDegree(){
        return gyro.getYaw();
    }

    public double getVelocity(){
        return ((rightEnc_.getRate() + leftEnc_.getRate()) / 2) * Constants.distancePerPulse;
    }

    public double getLeftDistance(){
        return leftEnc_.getDistance() * Constants.distancePerPulse;
    }

    public double getRightDistance(){
        return rightEnc_.getDistance() * Constants.distancePerPulse;
    }

    @Override public void start(){
        shift_.set(Value.kForward);
        leftEnc_.reset();
        rightEnc_.reset();
        gyro.reset();
    }

    @Override public void update(){
        shift_.set(isHigh_ ? Value.kForward : Value.kReverse);
        drive_.tankDrive(leftPower_, rightPower_);
    }

    @Override public void stop(){
        shift_.set(Value.kForward);
        drive_.stopMotor();
    }

    @Override public void log(){
        SmartDashboard.putNumber("Left Power", leftPower_);
        SmartDashboard.putNumber("Right Power", rightPower_);
        SmartDashboard.putNumber("Left Distance", getLeftDistance());
        SmartDashboard.putNumber("Right Distance", getRightDistance());
        SmartDashboard.putNumber("Left Velocity", leftEnc_.getRate() * Constants.distancePerPulse);
        SmartDashboard.putNumber("Right Velocity", rightEnc_.getRate() * Constants.distancePerPulse);
        SmartDashboard.putNumber("Angle", gyro.getAngle());
        SmartDashboard.putNumber("Anglular Velocity", gyro.getRate());
        SmartDashboard.putBoolean("High gear", isHigh_);
    }
}