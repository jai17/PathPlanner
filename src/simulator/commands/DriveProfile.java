package simulator.commands;

import simulator.Robot;
import simulator.Util;
import simulator.motion.Trajectory;

public class DriveProfile extends Command {
	private Trajectory traj; //trajectory for the robot to follow
	
	private Robot r;
	private double initLeftDist, initRightDist;
	private long initTime;
	private int index;
	private double tolerance;
	
	public DriveProfile(Robot r, double totalDist, double accDist, double decDist, double maxVel, double minVel) {
		super();
		
		this.r = r;
		traj = new Trajectory(totalDist, accDist, decDist, maxVel, minVel);
	}
	
	@Override
	protected void initialize() {
		initLeftDist = r.getLeftPos();
		initRightDist = r.getRightPos();
		initTime = System.currentTimeMillis();
		
		index = 0;
		System.out.println("DriveProfile: initialized " + 
							traj.getTotalLeftDist() +" "+ traj.getTotalRightDist());
	}

	@Override
	protected void execute() {
		double deltaTime = System.currentTimeMillis() - initTime;
		
		index = (int) ((deltaTime / traj.getTimeMillis()) * (traj.getLength()-1));
		index = (int) Util.clampNum(index, 0, traj.getLength()-1);
		
		r.setLeftSpeedIPS(traj.getLeftVel(index));
		r.setRightSpeedIPS(traj.getRightVel(index));
	}

	@Override
	protected boolean isFinished() {
		double goalLeftDist = initLeftDist + traj.getTotalLeftDist() - tolerance;
		double goalRightDist = initRightDist + traj.getTotalRightDist() - tolerance;

		/*System.out.println("DriveProfile: executing " + index +" "+ 
							goalLeftDist +" "+ r.getLeftPos() +" "+
							goalRightDist +" "+ r.getRightPos());*/
		
		if (r.getLeftPos() > goalLeftDist && r.getRightPos() > goalRightDist) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void end() {
		r.setLeftSpeedPercent(0);
		r.setRightSpeedPercent(0);
		System.out.println("DRIVEPROFILE: finished\n");
	}

}
