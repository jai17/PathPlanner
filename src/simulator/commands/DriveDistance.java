package simulator.commands;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import simulator.Robot;

public class DriveDistance extends Command {
	private Robot robot;
	private double endDistance;
	private double tolerance;
	private double topSpeed;
	private double setpoint;
	private double angle;
	
	/**
	 * Drive the robot forward a distance
	 * @param robot Robot to drive
	 * @param endDistance Distance to drive to
	 * @param tolerance Tolerance to be within when completed
	 * @param topSpeed Top speed to clamp to
	 */
	public DriveDistance(Robot robot, double endDistance, double tolerance, double topSpeed) {
		super();
		this.robot = robot;
		this.endDistance = endDistance;
		this.tolerance = tolerance;
		this.topSpeed = topSpeed;
	} //end constructor	

	@Override
	protected void initialize() {
		setpoint = endDistance + robot.getAveragePos();
		angle = robot.getAngle();
		robot.regulatedDrivePID(setpoint, angle, tolerance, topSpeed, 0, true);
		System.out.println("DRIVEDISTANCE INITIALIZED");
		System.out.println("setpoint: " + setpoint + " endDistance: " + endDistance 
							+ " averagePos(): " + robot.getAveragePos());
		System.out.println("angle: " + angle + " current: " + robot.getAngle());
	}

	@Override
	protected void execute() {
		robot.regulatedDrivePID(setpoint, angle, tolerance, topSpeed, 0, false);
	}

	@Override
	protected boolean isFinished() {
		boolean isFinished = false;
		
		if (Math.signum(endDistance) == 1) { //going forward
			if (robot.getAveragePos() > (setpoint - tolerance) && robot.slowerThanPercent(0.02)) {
				isFinished = true;
			}
		} else if (Math.signum(endDistance) == -1) {
			if (robot.getAveragePos() < (setpoint + tolerance) && robot.slowerThanPercent(0.02)) {
				isFinished = true;
			}
		}
		
		return isFinished;
	}

	@Override
	protected void end() {
		System.out.println("DRIVEDISTANCE FINISHED\n");
	}
} //end class
