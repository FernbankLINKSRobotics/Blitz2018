package frc.team0000.robot.Auto.Actions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import frc.team0000.robot.Lib.Actions.Action;

public class FollowMP implements Action {
    
    public FollowMP(String file){
        try{
            BufferedReader br = new BufferedReader(new FileReader("Employee.csv"));
            String line = "";
            while ((line = br.readLine()) != null){
                String[] sPoint = line.split(",");
                double[] mPoint = null;
                for(int i=0; i < sPoint.length; i++){ 
                    mPoint[i] = Double.parseDouble(sPoint[i]);
                }
            }
        } catch (Exception e) {
            System.err.println("NO FILE FOUND");
        }
    }

    @Override public void start(){}
    @Override public void update(){}
    @Override public void done(){}

    @Override public boolean isFinished(){
        return false;
    }
}