package simulator.motion;

/*
 * Segment
 * Created by: Neil Balaskandarajah
 * Last modified: 04/14/2019
 * Trajectory segment holding position, velocity and acceleration values
 */
public class Segment {
	//Attributes
	private double pos;
	private double vel;
	private double acc;
	
	//Constructors
	public Segment(double pos, double vel, double acc) {
		this.pos = pos;
		this.vel = vel;
		this.acc = acc;
	}
	
	public double getPos() {
		return pos;
	}
	
	public double getVel() {
		return vel;
	}
	
	public double getAcc() {
		return acc;
	}
}
