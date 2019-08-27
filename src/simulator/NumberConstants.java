package simulator;

public class NumberConstants {
	
    //Drive PID constants
    //driving constants

    public static final double pDrive = 0.02; //0.02
    public static final double dDrive = 0.08; //0.08

    //turning constants
    public static final double pTurn = 0.03; //0.03
    public static final double dTurn = 0; //0.0

    //heading correction constants
    public static final double pDriveTurn = 0.02; //0.02
    public static final double dDriveTurn = 0.09; //0.09
	
	public static final double TOP_SPEED = 96; //top speed in inches per second
	public static final double WHEEL_RAD = 2; //wheel radius in inches
	public static final double TOP_TURN_SPEED = 301.4; //top turning speed in degrees per second
	
	public static final double LOOKAHEAD_DIST = 12;
}
