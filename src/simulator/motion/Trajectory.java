package simulator.motion;
import simulator.Display;

/*
 * Trajectory
 * Created by: Neil Balaskandarajah
 * Last modified: 04/14/2019
 * A trajectory for the robot to follow
 */
public class Trajectory {
	// Attributes
	private double totalDist;
	private double accDist;
	private double decDist;

	private double totalLeftDist;
	private double totalRightDist;
	
	private double maxVel;
	private double minVel;

	private double accTime;
	private double decTime;
	private double totalTime;

	private Segment[] leftTraj;
	private Segment[] rightTraj;
	
	private int length;

	private final double PERIOD = Display.LOOPER_PERIOD / 1000.0;

	//Constructor
	public Trajectory(double totalDist, double accDist, double decDist, double maxVel, double minVel) {
		this.totalDist = totalDist;
		this.accDist = accDist;
		this.decDist = decDist;
		this.maxVel = maxVel;
		this.minVel = minVel;
		
		totalLeftDist = totalDist;
		totalRightDist = totalDist;

		calculateTimes();
		fillTrajectory();
	}

	private void calculateTimes() {
		accTime = accDist / (maxVel / 2);
		decTime = decDist / (maxVel / 2);
		totalTime = (accDist + decDist) / (maxVel / 2) + (totalDist - accDist - decDist) / maxVel;
	}

	private void fillTrajectory() {
		length = (int) Math.ceil(totalTime / PERIOD);

		leftTraj = new Segment[length];
		rightTraj = new Segment[length];

		calcSegments();
	}

	private void calcSegments() {
		double pos = 0;
		double t = 0;
		int index = 0;

		for (double time = 0; time <= totalTime; time += PERIOD) {
			t = time / totalTime;

			// putting time instead for straights
			double[] jp = jerkProfile(totalTime, accTime, decTime, maxVel, t);

			pos += jp[0] * PERIOD;

			leftTraj[index] = new Segment(pos, jp[0], jp[1]);
			rightTraj[index] = new Segment(pos, jp[0], jp[1]);

			index++;
		}
	}

	// 5-segment velocity and acceleration profile based off jerk
	// total distance, acc distance, dec distance, t value, cruise vel
	private double[] jerkProfile(double total, double acc, double dec, double cruiseVel, double t) {
		double outputs[] = new double[2];
		double velOut = -1, accOut = -1; // outputs

		// compute jerk constants
		double jA = cruiseVel / (0.75 * Math.pow(acc, 2));
		double jD = cruiseVel / (0.75 * Math.pow(dec, 2));

		// get current distance for profile segment selection
		double d = total * t;

		if (d >= 0 && d <= acc / 2) { // first segment, between 0 and half acc dist
			accOut = jA * d; // acceleration
			velOut = 0.5 * jA * Math.pow(d, 2) + accOut * d; // velocity

		} else if (d >= acc / 2 && d <= acc) { // between half acc dist and acc dist
			accOut = -jA * d + jA * acc; // acceleration
			velOut = accOut * (d - acc) - 0.5 * jA * Math.pow(d - acc, 2) + cruiseVel; // velocity

		} else if (d >= acc && d <= (total - dec)) { // cruising
			accOut = 0;
			velOut = cruiseVel;

		} else if (d >= (total - dec) && d <= (total - dec / 2)) { // between cruise dist and half dec dist
			accOut = -jD * (d - (total - dec));
			velOut = accOut * (d - (total - dec)) - 0.5 * jD * Math.pow(d - (total - dec), 2) + cruiseVel;

		} else if (d >= (total - dec / 2) && d <= total) { // between half dec dist and total
			accOut = jD * d - jD * total;
			velOut = accOut * (d - total) + 0.5 * jD * Math.pow(d - total, 2);
		}
		
		if (velOut < minVel)
			velOut = minVel;
		
		outputs[0] = velOut;
		outputs[1] = accOut;

		return outputs;
	}
	
	public double smoothT(double x) {
		double t;
		
		if (x >= 0 && x <= 0.5) {
			t = 2 * x*x;
		} else if (x >= 0.5 && x <= 1) {
			t = -2 * x*x + 4 * x - 1;
		} else {
			t = -1;
			throw new IllegalArgumentException("wtf r u doing");
		}
		
		return t;
	}
	
	public double getLeftPos(int i) {
		return leftTraj[i].getPos();
	}
	
	public double getLeftVel(int i) {
		return leftTraj[i].getVel();
	}
	
	public double getLeftAcc(int i) {
		return leftTraj[i].getAcc();
	}
	
	public double getRightPos(int i) {
		return rightTraj[i].getPos();
	}
	
	public double getRightVel(int i) {
		return rightTraj[i].getVel();
	}
	
	public double getRightAcc(int i) {
		return rightTraj[i].getAcc();
	}
	
	public int getLength() {
		return length;
	}
	
	public double getTimeSeconds() {
		return totalTime;
	}
	
	public double getTimeMillis() {
		return (long) (totalTime * 1000);
	}
	
	public double getTotalDist() {
		return totalDist;
	}
	
	public double getTotalLeftDist() {
		return totalLeftDist;
	}
	
	public double getTotalRightDist() {
		return totalRightDist;
	}
	
	
}
