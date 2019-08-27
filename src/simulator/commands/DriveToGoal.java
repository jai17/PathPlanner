package simulator.commands;

import simulator.Display;
import simulator.NumberConstants;
import simulator.Robot;
import simulator.field.FieldPositioning;
import simulator.field.Point;

public class DriveToGoal extends Command {
	private Point goalPoint; //goal point to drive to
	private double epsilon; //tolerance; radius of circle about goal point which you can exist in
	private double topSpeed; //top speed limit for PID
	private double minSpeed; //minimum speed to finish driving at
	private boolean reverse; //whether the robot is driving in reverse or not

	private double goalAngle; //goal angle to maintain; updated based on robot field position
	private Point robotPoint; //point for robot position
	private double currentSetpoint; //current distance you are tracking right now
	private double totalDist; //total distance for PID to drive
	private double initTotalDist; //initial total distance (1st hypotenuse of first current point - now)
	private double lookahead; //lookahead distance value to end moving faster
	
	private Robot robot; //robot that is driving to goal
	
	public DriveToGoal(Robot r, Point goal, double epsilon, double topSpeed, double minSpeed, boolean reverse) {
		this.goalPoint = goal;
		this.epsilon = epsilon;
		this.topSpeed = topSpeed;
		this.minSpeed = minSpeed;
		this.reverse = reverse;
		robot = r;
	}
	
	public DriveToGoal(Robot r, Point goal, double epsilon, double topSpeed, boolean reverse) {
		this(r, goal, epsilon, topSpeed, 0, reverse);
	}
	
	@Override
	protected void initialize() {
		totalDist = Point.calcDistance(robot.getPoint(), goalPoint);
		initTotalDist = totalDist;
		
		double goalYaw = FieldPositioning.calcGoalYaw(robot.getPoint(), goalPoint);
		
		//if driving in reverse
		if (reverse) {
			//change setpoint to reverse
			if (Math.signum(goalYaw) == 1){
				goalYaw -= 180; 
			} else {
				goalYaw += 180;
			}

			//make distance setpoints negative
			totalDist = -totalDist;
			initTotalDist = -initTotalDist;
		}
		
		lookahead = (minSpeed / NumberConstants.pDrive) + NumberConstants.LOOKAHEAD_DIST;
		lookahead *= Math.signum(totalDist);
		
		System.out.println(this.toString() +" INITIALIZED " + lookahead);
		/*System.out.println("deltaAngle: " + deltaAngle +
							" goalAngle: " + goalAngle + " current: " + robot.getAngle()
							+ " goalYaw: " + goalYaw + " current: " + robot.getYaw());*/
	}

	@Override
	protected void execute() {
		double goalYaw = 0;
		robotPoint = robot.getPoint();
		
		//(1) live update distance
		totalDist = Point.calcDistance(robotPoint, goalPoint); //live update distance when far from point
		if (reverse) {
			totalDist = -totalDist; 
		}
		
		//(2) when close to point, maintain current heading, update point
    	if(Math.abs(totalDist) >= 6) { //if point distance is greater than 6" (not too close to point)
			goalYaw = FieldPositioning.calcGoalYaw(robot.getPoint(), goalPoint);
			
			if (reverse) {
				goalYaw = -Math.signum(goalYaw)*180 + goalYaw;
			}
			
			double deltaAngle = goalYaw - robot.getYaw();
	        if (Math.abs(deltaAngle) < (360 - Math.abs(deltaAngle))) {
	        	goalAngle = deltaAngle;
	        } else {
	        	goalAngle = (Math.signum(robot.getYaw())*180 - robot.getYaw())
	        				- (Math.signum(goalYaw)*180 - goalYaw);
	        }
	        goalAngle += robot.getAngle();
			
			currentSetpoint = robot.getAveragePos() + totalDist + lookahead;
		}
		
    	double dA = Math.abs(goalAngle - robot.getAngle());
    	double scale = calcScale(dA);
    	robot.regulatedDrivePID(currentSetpoint, goalAngle, epsilon, topSpeed*scale, minSpeed, false);
    }

	@Override
	protected boolean isFinished() {
		if (Point.isWithinBounds(goalPoint, robot.getPoint(), epsilon)) { //within radius of circle
        	return true;
        } else {
        	return false;
        }
	}

	@Override
	protected void end() {
		robot.outputData();
		System.out.println(this.toString() +" FINISHED\n");
	}
	
	private double calcScale(double dA) {
		if (dA > 90) {
			return 0;
		} else {
			double coeff = (1 / Math.pow(90, 2));
			double output = coeff * Math.pow(dA - 90, 2);
			
			return output;
		}
	}

}
