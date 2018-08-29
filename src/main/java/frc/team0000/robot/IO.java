package frc.team0000.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class IO{
    public XboxController driver;

    public IO(){
        driver = new XboxController(Constants.driveController);
    }

    public void update(){
        Robot.drivetrain.setPower(driver.getY(Hand.kLeft), driver.getY(Hand.kRight));
        if(driver.getBumperPressed(Hand.kLeft)) { Robot.drivetrain.setHighGear(false); }
        if(driver.getBumperPressed(Hand.kRight)){ Robot.drivetrain.setHighGear(true);  }
    }
}