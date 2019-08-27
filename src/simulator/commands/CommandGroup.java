package simulator.commands;

import java.util.ArrayList;

import simulator.Display;

public abstract class CommandGroup {
	private ArrayList<Command> commands; //all commands to be run
	private boolean isRunning = false; //whether the command is running
	
	/*
	 * Create a command group
	 */
	public CommandGroup() {
		initialize();
	} //end constructor
	
	/*
	 * Create a command group from a file
	 */
	
	/*
	 * Initialize the command group by creating the command array
	 */
	private void initialize() {
		commands = new ArrayList<Command>();
	} //end initialize
	
	/*
	 * Add a command to the list
	 * Command c - command to be added
	 */
	public void add(Command c) {
		commands.add(c);
	} //end add
	
	/*
	 * Runs the entire command group
	 */
	public void start() {
		isRunning = true;
		Display.getInstance().startTimer();
		
		long startTime = System.currentTimeMillis();
		
		System.out.println(commands.size());
		
		for (int i = 0; i < commands.size(); i++) {
			commands.get(i).start();
			System.out.println(i + " has started");
			while (commands.get(i).isRunning()) {
				System.out.print("");
			}
			System.out.println(i + " has finished");	
		}

		Display.getInstance().stopTimer();
		long duration = System.currentTimeMillis() - startTime;
		
		System.out.println("COMPLETED: " + (double) (duration)/1000 + " seconds");
		
		isRunning = false;
	} //end start
	
	/*
	 * Return whether the command is running or not
	 */
	public boolean isRunning() {
		return isRunning;
	}
	
	
}
