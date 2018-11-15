package frc.team0000.robot.Subs;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team0000.robot.Constants;
import frc.team0000.robot.Robot;
import frc.team0000.robot.Lib.Subsystem;
import frc.team0000.robot.Lib.Control.PIDF;

public class Drivetrain implements Subsystem {
    private SpeedControllerGroup left_ = new SpeedControllerGroup(
        new VictorSP(Constants.Drivetrain.leftPair),
        new VictorSP(Constants.Drivetrain.leftBot)
    );

    private SpeedControllerGroup right_ = new SpeedControllerGroup(
        new VictorSP(Constants.Drivetrain.rightPair), 
        new VictorSP(Constants.Drivetrain.rightBot)
    );

    private DifferentialDrive drive_ = new DifferentialDrive(left_, right_);

    private PIDF velLeftControl_ = new PIDF(
        Constants.Drivetrain.velP,
        Constants.Drivetrain.velI,
        Constants.Drivetrain.velD,
        Constants.Drivetrain.velF
    );

    private PIDF velRightControl_ = velLeftControl_.clone();

    enum DriveState {
        ClosedVelocity,
        OpenCurvature,
        OpenArcade,
        OpenPower,
        OpenTank,
    };

    private DriveState state_ = DriveState.ClosedVelocity;

    private double leftPower_ = 0;
    private double rightPower_ = 0;
    private double turn_ = 0;
    private double speed_ = 0;
    private double leftVel_ = 0;
    private double rightVel_ = 0;


    public Drivetrain(){
        right_.setInverted(true);
        drive_.setDeadband(Constants.IO.deadband);
    }

    public void setArcade(double spd, double trn){
        turn_ = trn;
        speed_ = spd;
        state_ = DriveState.OpenArcade;
    }

    public void setCurvature(double spd, double trn){
        turn_ = trn;
        speed_ = spd;
        state_ = DriveState.OpenCurvature;
    }

    public void setTank(double l, double r){
        leftPower_ = l;
        rightPower_ = r;
        state_ = DriveState.OpenTank;
    }

    public void setPower(double l, double r){
        leftPower_ = l;
        rightPower_ = r;
        state_ = DriveState.OpenPower;
    }

    public void setVelocity(double l, double r){
        leftVel_ = l;
        rightVel_ = r;
        state_ = DriveState.ClosedVelocity;
    }

    @Override public void start(){}

    @Override public void update(){
        /*
        switch(state_){
            case ClosedVelocity: {
                velLeftControl_ .setSetpoint(leftVel_);
                velRightControl_.setSetpoint(rightVel_);
                left_ .set(velLeftControl_ .calculate(Robot.localization.getLeftVelocity() ));
                right_.set(velRightControl_.calculate(Robot.localization.getRightVelocity()));
            };
            case OpenPower: {
                left_ .set(leftPower_);
                right_.set(rightPower_);
            };
            case OpenCurvature: drive_.curvatureDrive(speed_, turn_, false);
            case OpenArcade:    drive_.arcadeDrive(speed_, turn_);
            case OpenTank:      drive_.tankDrive(leftPower_, rightPower_);
        }
        */
        drive_.arcadeDrive(speed_, turn_);
    }

    @Override public void stop(){
        drive_.stopMotor();
    }

    @Override public void log(){
        SmartDashboard.putNumber("Left Power", leftPower_);
        SmartDashboard.putNumber("Right Power", rightPower_);
    }
}