package frc.team0000.robot.Subs;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team0000.robot.Constants;
import frc.team0000.robot.Lib.Pair;
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

    private DifferentialDrive drive_ = new DifferentialDrive(left_, right_);

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

    public void setPower(Pair<Double, Double> s){
        leftPower_ = s.fst();
        rightPower_ = s.snd();
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

    @Override public void start(){
        shift_.set(Value.kForward);
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
        SmartDashboard.putBoolean("High gear", isHigh_);
    }
}