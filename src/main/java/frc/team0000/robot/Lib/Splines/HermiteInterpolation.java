package frc.team0000.robot.Lib.Splines;

public class HermiteInterpolation {
    public class Cubic{
        private double a;
        private double b;
        private double c;
        private double d;

        public Cubic(Cubic c){
            this.a = c.a;
            this.b = c.b;
            this.c = c.c;
            this.d = c.d;
        }

        public Cubic(double a, double b, double c, double d){
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }
    }

    public Cubic c_ = new Cubic(0, 0, 0, 0);

    public HermiteInterpolation(Cubic c){ c_ = c; }

    public HermiteInterpolation(double A, double B, double C, double D){
        c_.a = -A/2.0 + (3.0*B)/2.0 - (3.0*C)/2.0 + D/2.0;
        c_.b = A - (5.0*B)/2.0 + 2.0*C - D / 2.0;
        c_.c = -A/2.0 + C/2.0;
        c_.d = B;
    }

    public Cubic eq(){
        return c_;
    }

    public HermiteInterpolation dt(){
        Cubic t_ = new Cubic(c_); 
        t_.d = c_.c;
        t_.c = 2 * c_.b;
        t_.b = 3 * c_.a;
        t_.a = 0;
        return new HermiteInterpolation(t_);
    }

    public double apply(double t){
        return ((c_.a*(t * t * t)) + (c_.b*(t * t)) + (c_.c*t) + c_.d);
    }
}
