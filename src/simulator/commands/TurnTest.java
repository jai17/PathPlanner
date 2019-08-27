package simulator.commands;

import simulator.Display;
import simulator.Robot;

public class TurnTest extends Command {
	private Robot robot;
	private double initTime;
	private double prevAngle;
	
	public TurnTest(Robot r) {
		super();
		robot = r;
	}
	
	@Override
	protected void initialize() {
		double spd = 0.01;
		
		robot.setLeftSpeedPercent(spd);
		robot.setRightSpeedPercent(-spd);
		initTime = System.currentTimeMillis();
		prevAngle = robot.getAngle();
	}

	@Override
	protected void execute() {
		double angVel = (robot.getAngle() - prevAngle) / Robot.UPDATE_PERIOD;
		
		System.out.printf("%4.2f\n", angVel);
		
		prevAngle  = robot.getAngle();
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}
}
