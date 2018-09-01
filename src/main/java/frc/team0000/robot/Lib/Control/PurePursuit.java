package frc.team0000.robot.Lib.Control;


import java.util.ArrayDeque;

import frc.team0000.robot.Lib.Pair;
import frc.team0000.robot.Lib.RobotState;

public class PurePursuit {
    private double cruiseV_ = 0;
    private double forwardGain_ = 0;
    private double lookaheadDistance_ = 0;
    private double L = 0;

    private int targetInd_ = 0;
    private int ind_ = 0;
    private int length_ = 0;

    private double pSpeed_ = 0;
    private double pTurn_ = 0;

    Pair<Double, Double>[] points_;

    private PIDFSignal turnPID;
    private PIDFSignal speedPID;


    public PurePursuit(double cruiseV, double lookahead, double forwardGain, PIDFSignal speed, PIDFSignal turn, Pair<Double, Double>[] points){
        this.points_ = points;
        this.cruiseV_ = cruiseV;
        this.forwardGain_ = forwardGain;
        this.lookaheadDistance_ = lookahead;
        this.turnPID = turn;
        this.speedPID = speed;
    }

    public int index(RobotState state, Pair<Double, Double>[] ps){
        double[] d = null;
        for (int i=0; i< ps.length; i++){
            d[i] = (Math.hypot((state.x - ps[i].fst()), (state.y - ps[i].snd())));
        }


        int ind = 0;
        double min = d[ind];
        for (int j=0; j < d.length; j++) {
            if (d[j] < min) {
                min = d[j];
                ind = j;
            }
        }

        L = 0.0;
        double Lf = forwardGain_ * state.v + lookaheadDistance_;

        while((Lf > L) && (ind + 1 < ps.length)){
            double dx = ps[ind+1].fst() - ps[ind].fst();
            double dy = ps[ind+1].snd() - ps[ind].snd();
            L += Math.hypot(dx, dy);
            ind += 1;
        }

        return ind;
    }

    private double calcTurn(RobotState state, Pair<Double, Double>[] ps){
        double tx = 0;
        double ty = 0;
        int ind = index(state, ps);
        if(targetInd_ >= ind){ ind = targetInd_; }
        if(ind > ps.length){
            ind = ps.length - 1;
            tx = ps[ind].fst();
            ty = ps[ind].snd();
        }

        double alpha = Math.atan2(ty - state.y, tx - state.x) - state.r;
        if(state.v < 0){
            alpha = Math.PI - alpha;
        }
        double Lf = forwardGain_ * state.v + lookaheadDistance_;
        double delta = Math.atan2(2.0 * L * Math.sin(alpha) / Lf, 1.0);

        targetInd_ = ind;
        return delta;
    }

    public Pair<Double, Double> control(RobotState state, double speed, double delta, double dt){
        double base = (speedPID.P * (speed - state.v)) + (speedPID.F * speed) + (speedPID.D * ((state.v - pSpeed_) / 2));
        double turn = (turnPID.P * (delta - state.r)) + (turnPID.F * delta) + (turnPID.D * ((state.r - pTurn_) / 2));
        return new Pair<Double, Double>(base - turn, base + turn);
    }

    public Pair<Double, Double> update(RobotState state, double dt){

    }
}