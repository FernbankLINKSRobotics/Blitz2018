package frc.team0000.robot.Lib;

import java.util.ArrayDeque;
import java.util.Deque;

public class SubsystemManager implements Subsystem {
    Deque<Subsystem> subs_ = new ArrayDeque<Subsystem>();

    public SubsystemManager(Subsystem ... sub){
        for(Subsystem s : sub){ subs_.add(s); }
    }

    @Override public void start()  { subs_.forEach(s -> s.start());  }
    @Override public void update() { subs_.forEach(s -> s.update()); }
    @Override public void stop()   { subs_.forEach(s -> s.stop());   }
    @Override public void log()    { subs_.forEach(s -> s.log());    }
}