package frc.team0000.robot.Lib.Splines;

public class CubicHermite {
    public class Cubic{
        private double a;
        private double b;
        private double c;
        private double d;

        public Cubic(double a, double b, double c, double d){
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }
    }

    public Cubic c_ = new Cubic(0, 0, 0, 0);

    public CubicHermite(Cubic c){ c_ = c; }

    public CubicHermite(double A, double B, double C, double D){
        c_.a = -A/2.0 + (3.0*B)/2.0 - (3.0*C)/2.0 + D/2.0;
        c_.b = A - (5.0*B)/2.0 + 2.0*C - D / 2.0;
        c_.c = -A/2.0 + C/2.0;
        c_.d = B;
    }
    public CubicHermite dt(){

        c_.d = c_.c;
        c_.c = 2 * c_.b;
        c_.b = 3 * c_.a;
        c_.a = 0;
        return new CubicHermite(c_);
    }

    public double apply(double t){
        return ((c_.a*(t * t * t)) + (c_.b*(t * t)) + (c_.c*t) + c_.d);
    }
}