package simulator.commands;

import simulator.Robot;

public class DriveRadius extends Command {
	private Robot r;
	private double radius;
	
	private double inSpeed;
	private double outSpeed;
	
	public DriveRadius(Robot r, double radius, double outSpeed) {
		this.r = r;
		this.radius = radius;
		this.outSpeed = outSpeed;
	}

	@Override
	protected void initialize() {
		double sign = Math.signum(radius);
		inSpeed = outSpeed * ((radius - sign * r.getTrackWidth()/2) / (radius + sign * r.getTrackWidth()/2));
	}
	
	@Override
	protected void execute() {
		if (radius > 0) { //+ radius is turning right
			r.setLeftSpeedPercent(outSpeed);
			r.setRightSpeedPercent(inSpeed);
			
		} else if (radius < 0) { //- radius is turning left
			r.setLeftSpeedPercent(inSpeed);
			r.setRightSpeedPercent(outSpeed);
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub

	}

}
