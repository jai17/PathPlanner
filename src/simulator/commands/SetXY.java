package simulator.commands;

import simulator.Robot;
import simulator.field.Point;

public class SetXY extends Command {
	private Robot robot;
	private Point xy;
	
	public SetXY(Robot r, Point xy) {
		robot = r;
		this.xy = xy;
	}
	
	public SetXY(Robot r, double x, double y) {
		robot = r;
		this.xy = new Point(x,y);
	}
	
	@Override
	protected void initialize() {
		robot.setPoint(xy);
	}

	@Override
	protected void execute() {}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
		System.out.println();
	}

}
