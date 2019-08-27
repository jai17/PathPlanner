package simulator.motion;

import simulator.Display;
import simulator.NumberConstants;
import simulator.Robot;
import simulator.Util;
import simulator.field.Point;

public class CurveTrajectory {
	// Attributes
	private double[] left; //left speeds
	private double[] right; //right speeds
	private BezierPath path; //path to follow
	private Robot r; //robot to follow 
	private double accDist; //acceleration distance
	private double decDist; //deceleration distance
	private double maxVel; //max velocity
	private double minVel; //min velocity

	// Constructor
	public CurveTrajectory(Robot r, Point[] pts, double accDist, double decDist, double maxVel, double minVel) {
		path = new BezierPath(pts, r.getTrackWidth());

		this.r = r;
		this.accDist = accDist;
		this.decDist = decDist;
		this.maxVel = maxVel;
		this.minVel = minVel;
		
		calcSpeeds();
	}
	
	public CurveTrajectory(Robot r, BezierPath path, double accDist, double decDist, double maxVel, double minVel) {
		this.path = path;

		this.r = r;
		this.accDist = accDist;
		this.decDist = decDist;
		this.maxVel = maxVel;
		this.minVel = minVel;
		
		calcSpeeds();
	}
	
	public void calcSpeeds() {
		Trajectory traj = new Trajectory(path.getTotalDistance(), accDist, decDist, maxVel, minVel);
		double totalTime = traj.getTimeSeconds();
		
		int length = (int) Math.ceil(totalTime / Robot.UPDATE_PERIOD);
		left = new double[length];
		right = new double[length];
		
		double t = 0;
		double prevT = 0;
		
		double oldHeading = 0;
		
		double progress = 0;
		boolean isOver = false;
		
//		System.out.println("CURVE TRAJECTORY");
		for (int index = 0; index < length-1; index++) {
			progress = (double) index / (double) (length);
			
			t = traj.smoothT(progress);
			
//			System.out.printf("%4.3f\t%4.3f\n", progress, t);
//			System.out.printf("%4.2f\n", t - prevT);
			
			if (index != 0) { //every other time
				double dA = path.angleBetween(t, prevT) - oldHeading;
				double angOut = ((dA / Robot.UPDATE_PERIOD) / NumberConstants.TOP_TURN_SPEED)
								 * NumberConstants.TOP_SPEED;
				double linOut = (path.distanceBetween(t, prevT)) / Robot.UPDATE_PERIOD;
				
				if (linOut > NumberConstants.TOP_SPEED) {
					isOver = true;
				}
				
				double left = linOut + angOut;
				double right = linOut - angOut;
				
//				System.out.printf("%4.2f\t%4.2f\n", left, right);
				
				this.left[index] = left;
				this.right[index] = right;
			}
			
			oldHeading = path.angleBetween(t, prevT);
			prevT = t;
		}
		
		System.out.println("isOver: " + isOver);
	}
	
	public double getLeftSpeed(int i) {
		return left[i];
	}
	
	public double getRightSpeed(int i) {
		return right[i];
	}
	
	public int getLength() {
		return left.length;
	}
}
