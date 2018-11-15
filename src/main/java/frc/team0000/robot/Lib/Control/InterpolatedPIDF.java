package frc.team0000.robot.Lib.Control;

import java.util.ArrayList;

public class InterpolatedPIDF extends PIDF {
    private double min_ = 0;
    private double max_ = 0;
    private double points_ = 0;

    private ArrayList<PIDFSignal> sigs_ = new ArrayList<PIDFSignal>();

    private ArrayList<PIDFSignal> xs = new ArrayList<PIDFSignal>();

    public InterpolatedPIDF(double maxIn, double minIn, double points, PIDFSignal sig, PIDFSignal ... sigs){
        super(0.0,0.0,0.0);
        min_ = minIn;
        max_ = maxIn;
        points_ = points;
        sigs_.add(sig);
        for (PIDFSignal s : sigs) {
            sigs_.add(s);
        }


    }
}