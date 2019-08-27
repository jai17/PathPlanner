package simulator.commands;

import simulator.Display;
import simulator.NumberConstants;
import simulator.Robot;
import simulator.field.FieldPositioning;
import simulator.field.Point;

public class TurnToGoal extends Command {
	private Point goalPoint; //goal point to drive to
	private double goalYaw; //goal angle to maintain; updated based on robot field position
	private double epsilon; //tolerance in degrees for final heading
	private double topSpeed; //top speed limit for PID
	private double goalAngle; //goal angle for the robot to turn to
	private double tolerance; 
	private Robot robot;
	
	public TurnToGoal(Robot r, Point goal, double epsilon, double topSpeed) {
		goalPoint = goal;
        this.epsilon = epsilon;
        this.topSpeed = topSpeed;
        robot = r;
	}
	
	@Override
	protected void initialize() {
		Point robotPoint = robot.getPoint(); //update robot point 
        goalYaw = FieldPositioning.calcGoalYaw(robotPoint, goalPoint); //yaw value
        
        //get change in angle from yaw

        double deltaAngle = 0;
        
        deltaAngle = goalYaw - robot.getYaw();
        if (Math.abs(deltaAngle) < (360 - Math.abs(deltaAngle))) {
        	goalAngle = deltaAngle;
        } else {
//        	goalAngle = Math.signum(deltaAngle)*(360 - deltaAngle);
        	goalAngle = (Math.signum(robot.getYaw())*180 - robot.getYaw())
        				- (Math.signum(goalYaw)*180 - goalYaw);
        }
        goalAngle = goalAngle + robot.getAngle();
        
        System.out.println(this.toString() + " INITIALIZED");
        System.out.println("deltaAngle: " + deltaAngle +
        					" goalAngle: " + goalAngle + " current: " + robot.getAngle()
        					+ " goalYaw: " + goalYaw + " current: " + robot.getYaw());
	}

	@Override
	protected void execute() {
//		robot.regulatedTurnPID(goalAngle, epsilon, topSpeed, reverse);
		robot.regulatedTurnPID(goalAngle + tolerance, epsilon, topSpeed, false);
		Display.getInstance().run();

	}

	@Override
	protected boolean isFinished() {
		if((robot.getAngle() >= (goalAngle - epsilon) && robot.getAngle() <= (goalAngle + epsilon)) && //if heading is in range
    			(Math.abs(robot.getRightSpeed()/NumberConstants.TOP_SPEED) <= 0.25)) { //and low oscillation on PID; getting side vel bc average vel when turning is 0 
			return true;    		
    	} else {
    		return false;
    	}
	}

	@Override
	protected void end() {
		System.out.println(this.toString() + " FINISHED\n");
	}
}
