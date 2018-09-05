package frc.team0000.robot.Subs;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team0000.robot.Constants;
import frc.team0000.robot.Lib.Subsystem;

public class Arm implements Subsystem {
    private VictorSP motor_ = new VictorSP(Constants.lifterPort1);

    private AnalogPotentiometer pot_ = new AnalogPotentiometer(
        Constants.potPort,
        Constants.potRange,
        Constants.potOff
    );

    private DoubleSolenoid clamp_ = new DoubleSolenoid(
        Constants.BreakClampPort1, 
        Constants.BreakClampPort2
    );

    private double armPower_ = 0;
    private double armAngle_ = 0;
    private boolean clamping_ = false;
    private boolean prevClamping_ = false;
    private boolean isClosedLoop_ = false;

    public Arm(){}

    public void setAngle(double theta){
        isClosedLoop_ = true;
        armAngle_ = theta;
    }

    public void setPower(double p){
        isClosedLoop_ = false;
        armPower_ = p;
    }

    public void setBreak(boolean clamp){
        clamping_ = clamp;
    }

    @Override public void start(){
        isClosedLoop_ = false;
        armAngle_ = 0;
        armPower_ = 0;
        clamp_.set(Value.kForward);
    }

    @Override public void update(){
        if(isClosedLoop_){
            clamping_ = false;
            // TODO: implement PIDS and stuff
        }
        motor_.set(armPower_);
        if(prevClamping_ != clamping_){
            clamp_.set(clamping_ ? Value.kForward : Value.kReverse);
        }
        prevClamping_ = clamping_;
    }

    @Override public void stop(){
        start();
        motor_.stopMotor();
    }

    @Override public void log(){
        SmartDashboard.putNumber("Arm angle", pot_.get());
        SmartDashboard.putNumber("Arm power", armPower_);
        SmartDashboard.putBoolean("Clamped", clamp_.get() == Value.kForward);
    }
}