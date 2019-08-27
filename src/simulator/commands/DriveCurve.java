package simulator.commands;

import simulator.Display;
import simulator.Robot;
import simulator.field.Point;
import simulator.motion.BezierPath;
import simulator.motion.CurveTrajectory;

public class DriveCurve extends Command {
	private CurveTrajectory traj;
	private Robot r;
	private int index;
	private BezierPath path;
	
	public DriveCurve(Robot r, Point[] pts, double accDist, double decDist, double maxVel, double minVel) {
		this.r = r;
		traj = new CurveTrajectory(r, pts, accDist, decDist, maxVel, minVel);
		
		this.path = new BezierPath(pts, r.getTrackWidth());
		
		Display.getInstance().setDisplayCurve(true);
	}
	
	public DriveCurve(Robot r, BezierPath path, double accDist, double decDist, double maxVel, double minVel) {
		this.r = r;
		
		traj = new CurveTrajectory(r, path, accDist, decDist, maxVel, minVel);
		
		this.path = path;
	}
	
	@Override
	protected void initialize() {
		index = 0;
		
		Display.getInstance().setDisplayCurve(true);
	}

	@Override
	protected void execute() {
		r.setLeftSpeedIPS(traj.getLeftSpeed(index));
		r.setRightSpeedIPS(traj.getRightSpeed(index));
		
//		r.setPoint(path.getPoint(index));
//		r.setAngle(path.getHeading(index));
		
		index++;
	}

	@Override
	protected boolean isFinished() {
		if (index > traj.getLength()-1) {
//		if (index > path.getResolution()-1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void end() {
		r.setLeftSpeedIPS(0);
		r.setRightSpeedIPS(0);
		
		System.out.println("DriveCurve: ended");
	}

}
