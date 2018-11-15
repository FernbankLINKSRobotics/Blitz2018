package frc.team0000.robot.Lib;

import java.util.ArrayList;

public class Util<T> {
    public static<T> T getClamped(ArrayList<T> a, int ind){
        if(ind < 0){ ind = 0; }
        if(ind > a.size()) { ind = a.size()-1; }
        return a.get(ind);
    }

    public static<T> T getClamped(T[] a, int ind){
        if(ind < 0){ ind = 0; }
        if(ind > a.length) { ind = a.length-1; }
        return a[ind];
    }

    public static int clamp(int hi, int lo, int v){
        return Math.max(lo, Math.min(hi, v));
    }

    public static double clamp(double hi, double lo, double v){
        return Math.max(lo, Math.min(hi, v));
    }

    public static<T> T id(T t){
        return t;
    }

    public static double[] linspace(double start, double end, int points){
        double[] ret = new double[points];
        for (int i = 0; i < points-1; i++){  
            ret[i] = start + i * (start - end) / (points - 1);  
        }  
        return ret;
    }
}