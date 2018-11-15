package frc.team0000.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class IO{
    public XboxController driver;
    public XboxController operator;

    public IO(){
        driver = new XboxController(Constants.IO.driveController);
        operator = new XboxController(Constants.IO.operatorController);
    }

    public void update(){
        Robot.drivetrain.setArcade(-driver.getX(Hand.kLeft), driver.getY(Hand.kRight));
        //Robot.drivetrain.setPower(driver.getY(Hand.kLeft), -driver.getY(Hand.kRight));
        
        if(driver.getAButton()){
            Robot.shooter.setPower(.45);
        } else if (driver.getBButton()) {
            Robot.shooter.setPower(.6);
        } else if (driver.getYButton()) {
            Robot.shooter.setPower(.7);
        } else if (driver.getXButton()) {
            Robot.shooter.setPower(1);
        } else {
            Robot.shooter.setPower(0);
        }
    }
}