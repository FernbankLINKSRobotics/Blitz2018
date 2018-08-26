package frc.team0000.robot.Lib;

public class Pair<X, Y> {
    private X fst_;
    private Y snd_;

    public Pair(X x, Y y){
        this.fst_ = x;
        this.snd_ = y;
    }

    public X fst() { return this.fst_; }
    public Y snd() { return this.snd_; }

    public void setFst(X x) { this.fst_ = x; }
    public void setSnd(Y y) { this.snd_ = y; }
}