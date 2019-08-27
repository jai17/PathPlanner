package simulator.commands;

import simulator.Robot;

public class WaitCommand extends Command {
	//Attributes
	private Robot r;
	private double delay;
	
	public WaitCommand(Robot r, double delay) {
		this.r = r;
		this.delay = delay;
	}
	
	@Override
	protected void initialize() {
		try {
			long delayTime = (long) (delay * 1000);
			Thread.sleep(delayTime);
		} catch (InterruptedException e) {
			
		}
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
