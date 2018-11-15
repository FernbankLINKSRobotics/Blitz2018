package frc.team0000.robot.Auto.Routines;

import frc.team0000.robot.Auto.Actions.FollowPP;
import frc.team0000.robot.Lib.Pose;
import frc.team0000.robot.Lib.Actions.AutoEndedException;
import frc.team0000.robot.Lib.Actions.Routine;


public class LineTest extends Routine {
    public LineTest(){}

    @Override
    public void routine() throws AutoEndedException {
        addAction(new FollowPP(
            new Pose(0,0,Math.PI/2),
            new Pose(0,10,Math.PI/2)
            ));
    }
}