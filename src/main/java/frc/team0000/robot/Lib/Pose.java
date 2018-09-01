package frc.team0000.robot.Lib;

public class Pose {
    private double r_ = 0;
    private double x_ = 0;
    private double y_ = 0;

    public Pose(){}

    public Pose(double r){
        r_ = r;
    }

    public Pose(double x, double y){
        x_ = x;
        y_ = y;
    }

    public Pose(double x, double y, double r){
        x_ = x;
        y_ = y;
        r_ = r;
    }

    public double r(){
        return r_;
    }

    public double x(){
        return x_;
    }

    public double y(){
        return y_;
    }

    public double distance(final Pose p){
        return Math.hypot(x_ - p.x(), y_ - p.y());
    }
}