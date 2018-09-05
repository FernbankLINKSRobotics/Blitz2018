package frc.team0000.robot.Auto.Actions;

import frc.team0000.robot.Robot;
import frc.team0000.robot.Lib.Actions.OneTimeAction;

public class Break extends OneTimeAction {
    private boolean target_ = false;

    public Break(boolean target){
        target_ = target;
    }

    @Override public void oneTime(){
        Robot.arm.setBreak(target_);
    }
}