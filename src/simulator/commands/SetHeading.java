package simulator.commands;

import simulator.Robot;

public class SetHeading extends Command {
	private Robot r;
	private double heading;
	
	public SetHeading(Robot r, double heading) {
		this.r = r;
		this.heading = heading;
	}
	
	@Override
	protected void initialize() {
		r.setAngle(heading);
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub

	}

}
