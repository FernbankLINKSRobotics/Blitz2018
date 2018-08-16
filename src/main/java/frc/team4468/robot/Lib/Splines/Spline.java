package frc.team4468.robot.Lib.Splines;

import frc.team4468.robot.Lib.Pair;

public interface Spline {
    public abstract Pair<Double, Double> getPoint(double t);
    public abstract double getHeading(double t);
    public abstract double getCurvature(double t);
    public abstract double getDCurvature(double t);
    public abstract double getVelocity(double t);
}