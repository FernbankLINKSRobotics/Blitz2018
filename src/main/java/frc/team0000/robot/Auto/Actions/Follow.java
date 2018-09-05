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

public class Follow implements Action {
    private ArrayList<Pose> xs_ = new ArrayList<Pose>();
    private ArrayList<Translation> ps_ = new ArrayList<Translation>();

    private PurePursuit pp_;

    private double points_ = 50;
    private double cruise_ = 1;
    private double decay_ = .5;
    private double gain_ = 1.5;

    private PIDFSignal turn_ = new PIDFSignal(1, 0, 1, 1);
    private PIDFSignal speed_ = new PIDFSignal(1, 0, 0, 1);

    public Follow(Pose pose ,Pose ... poses){
        xs_.add(pose);
        for (Pose p : poses) { xs_.add(p); }
    }

    @Override public void start(){
        for(int i=0; i < xs_.size()-1; i++){
            Pose p0 = Util.getClamped(xs_, i);
            Pose p1 = Util.getClamped(xs_, i+1);
            HermitePath h = new HermitePath(p0, p1);
            for(double x: Util.linspace(0.0, 1.0, points_)){
                ps_.add(h.point(x));
            }
        }

        pp_ = new PurePursuit(cruise_, decay_, gain_, turn_, speed_, ps_);
    }

    @Override public void update(){
        Pair<Double, Double> s = pp_.update(Robot.localization.getState(), 
                                            Robot.localization.dt());
        Robot.drivetrain.setPower(s);
    }

    @Override public boolean isFinished(){
        return (points_ * xs_.size()) == pp_.index();
    }

    @Override public void done(){
        //Robot.drivetrain.setPower(0, 0);
    }
}