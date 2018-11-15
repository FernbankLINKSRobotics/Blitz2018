package frc.team0000.robot.Subs;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team0000.robot.Constants;
import frc.team0000.robot.Lib.Subsystem;

public class Shooter implements Subsystem {

    public SpeedControllerGroup shooter_ = new SpeedControllerGroup(
        new VictorSP(Constants.Shooter.p1), 
        new VictorSP(Constants.Shooter.p2)
    );

    private double speed_ = 0;

    public Shooter(){}

    public void setPower(double spd){
        speed_ = spd;
    }

    public void start(){}
    public void stop(){}

    public void update(){
        shooter_.set(speed_);
    }

    public void log(){
        SmartDashboard.putNumber("Shooter Speed", speed_);
    }
}
