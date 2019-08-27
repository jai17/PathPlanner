package simulator.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import simulator.Robot;
import simulator.field.Point;

public class FileCommandGroup extends CommandGroup {
	//Attributes
	private Robot r;
	private File f;
	
	public FileCommandGroup(Robot r, File f) {
		super();
		
		this.r = r;
		this.f = f;
		
		create();
	}
	
	private void create() {
		try {		
			Scanner s = new Scanner(f);
			
			while (s.hasNextLine()) {
				String line = s.nextLine();
				
				if (line.charAt(0) != '#') { //if not commented
					createCommand(line);
					System.out.println("Creating command");
				}
			} //loop
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Could not read file!");
		} //try-catch
			
	} //end create
	
	private void createCommand(String s) {
		StringTokenizer tokens = new StringTokenizer(s, " ");
		String firstTkn = tokens.nextToken();
		
		//drive distancce
		if (firstTkn.equals("dd")) {
			System.out.println(firstTkn);
			if (tokens.countTokens() == 3) { //3 parameters (loses from nextToken())
				try {
					double dist = Double.parseDouble(tokens.nextToken());
					double tol = Double.parseDouble(tokens.nextToken());
					double spd = Double.parseDouble(tokens.nextToken());
					
					this.add(new DriveDistance(r, dist, tol, spd));
					System.out.println("Created drive distance");
				} catch (NumberFormatException n) {
					n.printStackTrace();
				} //try-catch
			} //if 
			
		//drive to goal
		} else if (firstTkn.equals("d2g")) {
			System.out.println(firstTkn);
			if (tokens.countTokens() == 6) {
				try {
					double x = Double.parseDouble(tokens.nextToken());
					double y = Double.parseDouble(tokens.nextToken());
					double tol = Double.parseDouble(tokens.nextToken());
					double topSpd = Double.parseDouble(tokens.nextToken());
					double minSpd = Double.parseDouble(tokens.nextToken());
					boolean rev = Boolean.parseBoolean(tokens.nextToken());
					
					this.add(new DriveToGoal(r, new Point(x,y), tol, topSpd, minSpd, rev));
				} catch (NumberFormatException n) {
					n.printStackTrace();
				} //try-catch
			} else if (tokens.countTokens() == 5) {
				try {
					double x = Double.parseDouble(tokens.nextToken());
					double y = Double.parseDouble(tokens.nextToken());
					double tol = Double.parseDouble(tokens.nextToken());
					double topSpd = Double.parseDouble(tokens.nextToken());
					boolean rev = Boolean.parseBoolean(tokens.nextToken());
					
					this.add(new DriveToGoal(r, new Point(x,y), tol, topSpd, 0, rev));
				} catch (NumberFormatException n) {
					n.printStackTrace();
				} //try-catch
			}
			
		//drive turn
		} else if (firstTkn.equals("dt")) {
			System.out.println(firstTkn);
			if (tokens.countTokens() == 3) {
				try {
					double angle = Double.parseDouble(tokens.nextToken());
					double tol = Double.parseDouble(tokens.nextToken());
					double spd = Double.parseDouble(tokens.nextToken());
					
					this.add(new DriveTurn(r, angle, tol, spd));
					System.out.println("Created drive distance");
				} catch (NumberFormatException n) {
					n.printStackTrace();
				} //try-catch
			}
			
		//turn to goal
		} else if (firstTkn.equals("t2g")) {
			System.out.println(firstTkn);
			if (tokens.countTokens() == 4) {
				try {
					double x = Double.parseDouble(tokens.nextToken());
					double y = Double.parseDouble(tokens.nextToken());
					double tol = Double.parseDouble(tokens.nextToken());
					double spd = Double.parseDouble(tokens.nextToken());
					
					this.add(new TurnToGoal(r, new Point(x,y), tol, spd));
				} catch (NumberFormatException n) {
					n.printStackTrace();
				} //try-catch
			} //if
			
		//set xy
		} else if (firstTkn.equals("xy")) {
			System.out.println(firstTkn);
			if (tokens.countTokens() == 2) {
				try {
					double x = Double.parseDouble(tokens.nextToken());
					double y = Double.parseDouble(tokens.nextToken());
					
					this.add(new SetXY(r, new Point(x,y)));
				} catch (NumberFormatException n) {
					n.printStackTrace();
				} //try-catch
			} //if
			
		//wait
		} else if (firstTkn.equals("wait")) {
			System.out.println(firstTkn);
			if (tokens.countTokens() == 1) {
				try {
					double delay = Double.parseDouble(tokens.nextToken());
					
					this.add(new WaitCommand(r, delay));
				} catch (NumberFormatException n) {
					n.printStackTrace();
				} //try-catch
			} //if
			
		//drive profile
		} else if (firstTkn.equals("dmp")) {
			System.out.println(firstTkn);
			if (tokens.countTokens() == 5) {
				try {
					double total = Double.parseDouble(tokens.nextToken());
					double acc = Double.parseDouble(tokens.nextToken());
					double dec = Double.parseDouble(tokens.nextToken());
					double top = Double.parseDouble(tokens.nextToken());
					double min = Double.parseDouble(tokens.nextToken());
					
					this.add(new DriveProfile(r, total, acc, dec, top, min));
				} catch (NumberFormatException n) {
					n.printStackTrace();
				} //try-catch
			} //if
		}
	} //end createCommand
}
