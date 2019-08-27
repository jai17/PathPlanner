/*
 * Main
 * Created by: Neil Balaskandarajah
 * Last modified: 03/24/2019
 * Simulates a robot's movement to a GUI window
 */
package simulator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import simulator.commands.TurnTest;
import simulator.commands.routines.CurveTest;
import simulator.commands.routines.DriveRadiusTest;
import simulator.commands.routines.LeftRocketLow;
import simulator.commands.routines.SlickD2GTest;
import simulator.field.FieldPoints;
import simulator.field.Point;
import simulator.graphics.JComponentFactory;
import simulator.graphics.SpeedDisplay;
import simulator.graphics.TimerDisplay;
import simulator.motion.BezierPath;

public class RoboSim {
	private static boolean starting;
	
	public static void main(String[] args) {
		starting = false;
		BezierPath path = FieldPoints.path2;
		
		//create frame and panel
		JFrame frame = JComponentFactory.frame("2019 Autonomous Simulator");
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		//create robot
		Robot shadow = new Robot(60,15, 36.5, 36.5);
		shadow.setPoint(new Point(324/2, 24)); //center of level 3
		
		//create display, add robot
		Display display = Display.getInstance();
		display.addRobot(shadow);
		panel.add(display, BorderLayout.CENTER);
		
		//velocity sliders
		JPanel speeds = new JPanel();
		speeds.setLayout(new BoxLayout(speeds, BoxLayout.X_AXIS));
		
		SpeedDisplay sd = new SpeedDisplay(200, 200, (int) NumberConstants.TOP_SPEED);
//		panel.add(sd, BorderLayout.EAST);
		speeds.add(sd);
		
		SpeedDisplay turnd = new SpeedDisplay(200, 200, (int) NumberConstants.TOP_TURN_SPEED);
		turnd.setColor(Color.ORANGE);
		speeds.add(turnd);
		
		panel.add(speeds, BorderLayout.EAST);
		
		display.addSpeedDisplay(sd);
		display.addTurnDisplay(turnd);
		
		//timer
		TimerDisplay td = new TimerDisplay(frame.getWidth(), 100, 96.0F);
		panel.add(td, BorderLayout.SOUTH);
		
		//parse command from file
//		FileCommandGroup fcg = new FileCommandGroup(shadow, new File("selectedCommand.txt"));
//		fcg.start();
		
		//start button
		JButton button = new JButton("START");
		button.setPreferredSize(new Dimension(200, 50));
		button.setFont(button.getFont().deriveFont(50.0F));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				starting = true;
			}
		});
		
		panel.add(button, BorderLayout.NORTH);
		
		display.addTimerDisplay(td);
		display.addPath(path);
		
		//set icon
		ImageIcon t6 = new ImageIcon("src/resources/Theory6Icon.jpg");
		frame.setIconImage(t6.getImage());
		
		//set up window
		frame.setContentPane(panel);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setFocusable(true);
		
		CurveTest ct = new CurveTest(shadow, path);
		DriveRadiusTest drt = new DriveRadiusTest(shadow, 50, 1);
		TurnTest tt = new TurnTest(shadow);
		SlickD2GTest sd2g = new SlickD2GTest(shadow);
		
		while (true) {
			System.out.print("");
			if (starting) {
				System.out.println("starting");
//				fcg.start();
//				ct.start();
				sd2g.start();
//				new LeftRocketLow(shadow).start();
//				drt.start();
//				tt.start();
				starting = false;
			}
		}
//		FileCommandGroup fcg = new FileCommandGroup(shadow, new File("selectedCommand.txt"));
//		fcg.start();
		
//		DriveProfile dp = new DriveProfile(shadow, 100, 12, 12, 50, 12);
//		dp.start();
	} //end main
	
} //end class
