package simulator.commands;

import java.awt.Color;

import simulator.Display;
import simulator.Robot;
import simulator.Util;

public class DriveTurn extends Command {
	private Robot robot; //robot to drive
	private double endAngle; //angle to finish at
	private double tolerance;
	private double topSpeed;
	
	public DriveTurn(Robot robot, double endAngle, double tolerance, double topSpeed) {
		super();
		
		this.endAngle = endAngle;
		this.robot = robot;
		this.tolerance = tolerance;
		this.topSpeed = topSpeed;
	}

	@Override
	protected void initialize() {
		robot.regulatedTurnPID(endAngle, tolerance, topSpeed, true);
		Util.println(this.toString() + "INITIALIZED");
	}

	@Override
	protected void execute() {
//		Display.getInstance().run();
	}

	@Override
	protected boolean isFinished() {
		boolean isFinished = false;
		if (endAngle < 0) { //negative angle
			if (robot.getAngle() <= endAngle) {
				isFinished = true;
			} 
		} else if (endAngle > 0) { //positive angle
			if (robot.getAngle() >= endAngle) {
				isFinished = true;
			} 
		} else { //zero angle
			if (robot.getAngle() == 0) {
				isFinished = true;
			}
		}
		return isFinished;
	}

	@Override
	protected void end() {
		robot.setLeftSpeedPercent(0);
		robot.setRightSpeedPercent(0);
		robot.setColor(Color.RED);
		Util.println("END\n");
	}
}
