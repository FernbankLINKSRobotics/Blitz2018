package frc.team0000.robot;

public class Constants{

    public class Drivetrain {
        // Left Side
        public static final int leftPair = 9;
        public static final int leftBot  = 8;
        // Right Side
        public static final int rightPair = 7;
        public static final int rightBot  = 6;
        // Both Sides
        public static final double velP = .95;
        public static final double velI = 0.0;
        public static final double velD = 0.0;
        public static final double velF = 0.0;
    }
        
    
    public class IO {
        public static final int driveController = 0;
        public static final int operatorController = 1;
        public static final double deadband = 0.1;
    }
        
    public class Encoder {
        // Constants
        public static final double pulsesPerRev = 128.0;
        public static final double distancePerPulse = 0.000396745; //wheelCircumference / (pulsesPerRev * (stageRatio * encoderRatio));
        // Left Encoder
        public static final boolean leftInverted = false;
        public static final int left2 = 9;
        public static final int left1 = 7;
        // Right Encoder
        public static final boolean rightInverted = false;
        public static final int right2 = 6;
        public static final int right1 = 8;
    }

    public class Gyro {
        public static final double P = 0.012;
        public static final double I = 0.0;
        public static final double D = 0.0;
        public static final double F = 0.0;
    }

    public class Shooter {
        public static final int p1 = 3;
        public static final int p2 = 2;
    }
}