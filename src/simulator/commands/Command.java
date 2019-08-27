/*
w * Command
 * Created by: Neil Balaskandarajah
 * Last modified: 03/24/2019
 * Replicates the functionality of WPILib's Command class 
 */
package simulator.commands;

import java.io.File;

import simulator.Display;

public abstract class Command implements Runnable {
	private boolean isRunning;
	
	/*
	 * Runs once before command starts
	 */
	protected abstract void initialize();
	
	/*
	 * Runs while isFinished is false
	 */
	protected abstract void execute();
	
	/*
	 * Checks whether the command isFinished
	 */
	protected abstract boolean isFinished();
	
	/*
	 * Ends the command
	 */
	protected abstract void end();
	
	/*
	 * Runs a command until finished
	 */
	public void run() {
		isRunning = true;
		this.initialize();
		
		while(!this.isFinished()) {
			this.execute();
			
			Display.getInstance().run();
		}
		
		if (isFinished()) {
			this.end();	
			isRunning = false;
		}
	}
	
	/*
	 * Start the command by creating a thread to run it 
	 */
	public void start() {
		isRunning = true;
		Thread t = new Thread(this);
		t.start();
	}
	
	public boolean isRunning() {
		return isRunning;
	}
	
	protected void configFromFile(File f) {};
} //end class
