package frc.team4468.robot.Lib.Actions;

public abstract class OneTimeAction implements Action {
    @Override public boolean isFinished(){ return true; }
    @Override public void start (){ oneTime(); }
    @Override public void update(){ oneTime(); }
    @Override public void done  (){}

    public abstract void oneTime();
}