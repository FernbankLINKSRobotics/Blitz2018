package frc.team0000.robot.Lib;

public class RobotState {
    public double x = 0;
    public double y = 0;
    public double r = 0;
    public double v = 0;

    public RobotState(){}

    public RobotState(double x, double y, double v){
        this.x = x;
        this.y = v;
        this.v = v;
    }

    public RobotState(double x, double y, double v, double r){
        this.x = x;
        this.y = v;
        this.v = v;
        this.r = r;
    }
}