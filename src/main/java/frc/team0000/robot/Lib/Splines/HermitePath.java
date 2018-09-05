package frc.team0000.robot.Lib.Splines;

import frc.team0000.robot.Lib.Pair;
import frc.team0000.robot.Lib.Pose;
import frc.team0000.robot.Lib.Translation;

public class HermitePath {
    public class Quintic {
        private double a;
        private double b;
        private double c;
        private double d;
        private double e;
        private double f;

        public Quintic(Quintic q){
            this.a = q.a;
            this.b = q.b;
            this.c = q.c;
            this.d = q.d;
            this.e = q.e;
            this.f = q.f;
        }

        public Quintic(double a, double b, double c, double d, double e, double f){
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
            this.e = e;
            this.f = f;
        }
    }

    private Quintic x_ = new Quintic(0, 0, 0, 0, 0, 0);
    private Quintic y_ = new Quintic(0, 0, 0, 0, 0, 0);

    private double x0, x1, dx0, dx1, ddx0, ddx1;
    private double y0, y1, dy0, dy1, ddy0, ddy1;

    public HermitePath(Quintic x, Quintic y){ 
        this.x_ = x;
        this.y_ = y;
    }

    public HermitePath(Pose p0, Pose p1){
        double scale = 1.2 * p0.distance(p1);
        this.x0 = p0.x();
        this.x1 = p1.x();
        this.dx0 = Math.cos(p0.r()) * scale;
        this.dx1 = Math.cos(p1.r()) * scale;
        this.ddx0 = 0;
        this.ddx1 = 0;
        this.y0 = p0.y();
        this.y1 = p1.y();
        this.dy0 = Math.sin(p0.r()) * scale;
        this.dy1 = Math.sin(p0.r()) * scale;
        this.ddy0 = 0;
        this.ddy1 = 0;

        ceofficients(this.x_, this.x0, this.x1, this.dx0, this.dx1, this.ddx0, this.ddx1);
        ceofficients(this.y_, this.y0, this.y1, this.dy0, this.dy1, this.ddy0, this.ddy1);
    }

    private void ceofficients(Quintic q, double v0, double v1, double dv0, double dv1, double ddv0, double ddv1){
        q.a = -6 * v0 - 3 * dv0 - 0.5 * ddv0 + 0.5 * ddv1 - 3 * dv1 + 6 * v1;
        q.b = 15 * v0 + 8 * dv0 + 1.5 * ddv0 - ddv1 + 7 * dv1 - 15 * v1;
        q.c = -10 * v0 - 6 * dv0 - 1.5 * ddv0 + 0.5 * ddv1 - 4 * dv1 + 10 * v1;
        q.d = 0.5 * ddv0;
        q.e = dv0;
        q.f = v0;
    }

    private Quintic dt_impl(Quintic q){
        Quintic t = new Quintic(q); 
        t.b = 5 * q.a;
        t.c = 4 * q.b;
        t.d = 3 * q.c;
        t.e = 2 * q.d; 
        t.f = q.e;
        return t;
    }

    private double apply_impl(Quintic q, double t){
        return q.a * t * t * t * t * t + q.b * t * t * t * t + q.c * t * t * t + q.d * t * t + q.e * t + q.f;
    }
    
    public HermitePath dt(){
        return new HermitePath(dt_impl(x_), dt_impl(y_));
    }

    public double heading(double t){
        return Math.atan2(apply_impl(dt_impl(x_),t), apply_impl(dt_impl(y_),t));
    }

    public double getVelocity(double t) {
        return Math.hypot(apply_impl(dt_impl(x_),t), apply_impl(dt_impl(y_),t));
    }

    public Translation point(double t){
        return new Translation(apply_impl(x_,t), apply_impl(y_,t));
    }

    public Pose apply(double t){
        return new Pose(apply_impl(x_, t), apply_impl(y_, t), this.heading(t));
    }
}