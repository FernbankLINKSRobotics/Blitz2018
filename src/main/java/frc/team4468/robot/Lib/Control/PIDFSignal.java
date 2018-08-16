package frc.team4468.robot.Lib.Control;

public class PIDFSignal {
    public double P;
    public double I;
    public double D;
    public double F;

    public PIDFSignal(double p, double d){
        P = p;
        I = 0;
        D = d;
        F = 0;
    }

    public PIDFSignal(double p, double i, double d){
        P = p;
        I = i;
        D = d;
        F = 0;
    }

    public PIDFSignal(double p, double i, double d, double f){
        P = p;
        I = i;
        D = d;
        F = f;
    }
}