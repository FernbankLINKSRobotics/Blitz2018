package frc.team0000.robot.Auto.Actions;

import java.util.ArrayList;

import frc.team0000.robot.Robot;
import frc.team0000.robot.Lib.Pair;
import frc.team0000.robot.Lib.Pose;
import frc.team0000.robot.Lib.Translation;
import frc.team0000.robot.Lib.Util;
import frc.team0000.robot.Lib.Actions.Action;
import frc.team0000.robot.Lib.Control.PIDFSignal;
import frc.team0000.robot.Lib.Control.PurePursuit;
import frc.team0000.robot.Lib.Splines.HermitePath;

public class FollowPP implements Action {
    private ArrayList<Pose> xs_ = new ArrayList<Pose>();
    private ArrayList<Translation> ps_ = new ArrayList<Translation>();

    private PurePursuit pp_;

    // Constants for the PP controller
    private int points_ = 50;
    private double cruise_ = 1;
    private double decay_ = .5;
    private double gain_ = 1.5;

    private PIDFSignal turn_ = new PIDFSignal(1, 0, 1, 1);
    private PIDFSignal speed_ = new PIDFSignal(1, 0, 0, 1);

    // Make sure there is at least one point
    public FollowPP(Pose pose ,Pose ... poses){
        xs_.add(pose);
        for (Pose p : poses) { xs_.add(p); }
    }

    @Override public void start(){
        // Iterates through all of the points
        for(int i=0; i < xs_.size()-1; i++){
            Pose p0 = Util.getClamped(xs_, i);
            Pose p1 = Util.getClamped(xs_, i+1);
            // Generates the hermite
            HermitePath h = new HermitePath(p0, p1);
            // Gets the points and adds them to the 
            for(double x: Util.linspace(0.0, 1.0, points_)){
                ps_.add(h.point(x));
            }
        }
        pp_ = new PurePursuit(cruise_, decay_, gain_, turn_, speed_, ps_);
    }

    @Override public void update(){
        // Gets the controller values and sets the DT
        Pair<Double, Double> s = pp_.update(Robot.localization.getState(), 
                                            Robot.localization.dt());
        Robot.drivetrain.setPower(s.fst(), s.snd());
    }

    @Override public boolean isFinished(){
        // When the target point is the final point
        return (points_ * xs_.size()) == pp_.index();
    }

    @Override public void done(){} // No end conditions at this time
}