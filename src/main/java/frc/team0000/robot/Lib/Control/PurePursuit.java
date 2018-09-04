package frc.team0000.robot.Lib.Control;

import java.util.ArrayList;
import java.util.stream.Collectors;

import frc.team0000.robot.Lib.RobotState;
import frc.team0000.robot.Lib.Pair;
import frc.team0000.robot.Lib.Translation;

public class PurePursuit {
    private double gain_ = 0;
    private double decay_ = 0;
    private double cruise_ = 0;
    private ArrayList<Translation> ps_;
    private int len_ = 0;

    private double pTurn_ = 0;

    private PIDFSignal turnPID_;
    private PIDFSignal speedPID_;

    public PurePursuit(double cruise, double decay, double gain, PIDFSignal turnPID, PIDFSignal speedPID, ArrayList<Translation> ps){
        gain_ = gain;
        decay_ = decay;
        cruise_ = cruise;
        turnPID_ = turnPID;
        speedPID_ = speedPID;
        ps_ = ps;
        len_ = ps.size();
    }

    public int find(RobotState state, double dt){
        // Calculates dead reckon estimations
        double nx = Math.cos(state.r - Math.PI/2) * state.v * dt;
        double ny = Math.sin(state.r - Math.PI/2) * state.v * dt;
        // Calculates lookahead based on velocity
        double look = gain_ * state.v;
        
        ArrayList<Double> d = ps_.stream() // Make stream for streamlined syntax
                                // finds the distance to all of the points and sees if they are within the look ahead dist
                                .map(p -> Math.hypot(state.x - p.fst(), state.y - p.snd()) - look)
                                // amkes into an Arraylist
                                .collect(Collectors.toCollection(ArrayList::new));

        int ind = 0;
        double min = Double.POSITIVE_INFINITY; // Numbers will be less than
        for(int i = 0; i < ps_.size(); i++){
            // variable to make the life easy
            double p = d.get(i);
            // Sees the cloest point to the expected future point
            double v = (p + look) + Math.hypot(state.x - ps_.get(i).fst(), state.y - ps_.get(i).snd());
            if((p > 0) // if the distance - look is more than 0
            && (v < min)){ // if the summed distance is the least
                min = v;
                ind = i;
            }
        }
        return ind;
    }

    public Pair<Double, Double> control(RobotState state, int ind, double dt){
        Translation p = ps_.get(ind);
        // The difference in the heading and the target
        double alpha = Math.atan2(state.x - p.fst(), state.y - p.snd()) - state.r;
        if(state.v < 0){ alpha = Math.PI - alpha; } // Reverse if needed
        double L = Math.hypot(state.x - p.fst(), state.y - p.snd()); // Distance
        // Calculates the curvature of the path to the target
        double delta = Math.atan2((2*L*Math.sin(alpha)) / (gain_ * state.v), 1);
        // QaD half velocity profile
        double speed = Math.min(cruise_, decay_ * (ind - len_));
        // Returns the outputs of the files
        return PIDF(state, delta, speed, dt);
    }

    private Pair<Double, Double> PIDF(RobotState state, double delta, double speed, double dt){
        // FP controller: speed doesnt need a lot of feedback
        double base = (speedPID_.P * (speed - state.v)) + (speedPID_.F * speed);
        // FPD Controller: the angle does require more control but I shouldnt be required may move to FP
        double turn = (turnPID_.P * (delta - state.r)) + (turnPID_.F * delta) + (turnPID_.D * ((state.r - pTurn_) / 2));
        pTurn_ = delta;
        // Return and make sure that the values are within range
        return new Pair<Double, Double>(clamp(1, -1, base - turn), clamp(1, -1, base + turn));
    }

    private double clamp(double hi, double lo, double v){
        return Math.max(lo, Math.min(hi, v));
    }

    public Pair<Double, Double> update(RobotState state, double dt){
        int ind = find(state, dt);
        return control(state, ind, dt);
    }
}