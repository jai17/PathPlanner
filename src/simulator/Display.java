/*
 * Display
 * Created by: Neil Balaskandarajah
 * Last modified: 03/24/2019
 * Screen where robot movement occurs
 */
package simulator;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import simulator.field.Point;
import simulator.graphics.SpeedDisplay;
import simulator.graphics.TimerDisplay;
import simulator.motion.BezierPath;

public class Display extends JComponent implements Runnable {

	private int width; //width of window in feet
	private int height; //height of window in feet
	private DisplayState state; //display state of window
	
	private int PIXELS_PER_FOOT; //pixels per field foot
	private final int SCALE_FACTOR = 64;
	private final float STROKE_THICKNESS = 30.0F; //line thickness
	public static final int LOOPER_PERIOD = 20; //update rate in milliseconds
	
	private boolean displayGrid = false; //whether to display grid or not
	private boolean displayField = false; //whether to display field or not
	private boolean displayCurve = false; //whether to display curve or not
	
	private Robot robot; //robot to draw
	
	private static Display mInstance; //singular display
	
	private BufferedImage field; //image of the field
	
	private SpeedDisplay sd; //speed display for the robot
	private TimerDisplay td; //timer display for the robot
	private SpeedDisplay turnd; //turn speed display for the robot
	
	private BezierPath path; //path for robot to follow
	
	//display states
	public static enum DisplayState {
		CLIENT,
		DEBUG, 
		BAREBONES
	} //end enum
	
	/*
	 * Creates a display of a given width and height
	 * int width - width of the display in pixels
	 * int height - height of the display in pixels
	 * DisplayState state - state of the window
	 */
	private Display(int width, int height, DisplayState state) {
		super();
		
		PIXELS_PER_FOOT = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() / SCALE_FACTOR);
		
		this.width = width;
		this.height = height;
		this.setPreferredSize(new Dimension(width * PIXELS_PER_FOOT, height * PIXELS_PER_FOOT));
		
		this.state = state;
		switch(state) {
			//debug mode
			case DEBUG:
				displayField = true; //display field
				displayGrid = true; //display field grid
				break;
				
			//regular client mode
			case CLIENT:
				displayGrid = false; //don't display grid
				displayField = true; //still display field
				break;
				
			//i don't want to look at that field
			case BAREBONES:
				displayGrid = true;
				displayField = false;
				break;
		} //switch
		
		displayCurve = false;
		
		//add field image
		try {
			field = ImageIO.read(getClass().getResource("/resources/2019 Field Background.jpg"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
			displayField = false;
		}
	} //end constructor
	
	/*
	 * Creates a display
	 */
	private Display() {
		this(27, 27, DisplayState.BAREBONES);
	} //end constructor
	
	/*
	 * Return the singular instance of the Display class
	 */
	public static Display getInstance() {
		if (mInstance == null) {
			mInstance = new Display();
		}
		
		return mInstance;
	} //end getInstance
	
	/*
	 * Returns the width of the screen in pixels
	 */
	public int getWidth() {
		return width * PIXELS_PER_FOOT;
	} //end getWidth
	
	/*
	 * Returns the height of the screen in pixels
	 */
	public int getHeight() {
		return height * PIXELS_PER_FOOT;
	} //end getHeight
	
	/*
	 * Add a speed display to the display
	 * SpeedDisplay sd - speed display for robot
	 */
	public void addSpeedDisplay(SpeedDisplay sd) {
		this.sd = sd;
	} //end addSpeedDisplay
	
	public void addTimerDisplay(TimerDisplay td) {
		this.td = td;
		td.setTime(0);
	}
	
	public void addTurnDisplay(SpeedDisplay turnd) {
		this.turnd = turnd;
	}
	
	public void startTimer() {
		td.start();
	}
	
	public void stopTimer() {
		td.stop();
	}
	
	/*
	 * Draws the component to the screen
	 * Graphics g - Graphics object used for drawing
	 */
	public void paintComponent(Graphics g) { //OVERRIDEN
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(STROKE_THICKNESS / this.getWidth()));
		
		//draw field based on mode
		if (displayField) {
			displayField(g2);
		}
		
		//draw grid based on mode
		if (displayGrid) {
			displayGrid(g2);
		}
		
		//draw curve
		if (displayCurve) {
			drawCurve(g2);
		}
		
		//set scale for robot drawing
		drawRobot(g2);
			
		sd.setSpeed(robot.getSpeed());
		turnd.setSpeed(robot.getAngularVel());
		td.updateTime();
	} //end paintComponent
	
	/*
	 * Draws the current position of the robot
	 * Graphics2D g2 - Graphics2D object used for drawing
	 */
	private void drawRobot(Graphics2D g2) {
		g2.setColor(robot.getColor());
		g2.scale(getWidth() / 324, getHeight() / 324); 
		
		int topLeftX = (int) (robot.getX() - robot.getWidth()/2);
		int topLeftY = (int) (robot.getY() - robot.getHeight()/2);
		
		g2.rotate(robot.getHeadingRads(), robot.getX(), 324 - robot.getY());
		//robot
		g2.fillRoundRect(topLeftX, (324 - robot.getHeight()) - topLeftY, 
				robot.getWidth(), robot.getHeight(), 12, 12);
		//heading indicator
		g2.fillRect(topLeftX, (324 - 12) - topLeftY, robot.getWidth(), 12); 
		//rotate back
		g2.rotate(-robot.getHeadingRads(), robot.getX(), 324 - robot.getY());
	} //end drawRobot

	
	/**
	 * Draw the Bezier Curve to the screen
	 * @param g2	- Graphics2D object
	 */
	private void drawCurve(Graphics2D g2) {
		if (path != null) {
			g2.setStroke(new BasicStroke(7.0F));
//			g2.scale(getWidth() / 324, getWidth() / 324);
//			g2.scale(1, 1);
			
			int h = 324;
			double scale = 5.0;
			
//			g2.drawLine(0, 0, 300, 300);
			
			/*for (int i = 1; i < path.getResolution()-1; i++) {
				//center
				g2.setColor(Color.BLACK);
				g2.drawLine((int) (path.getX(i)*var), h - (int) (path.getY(i)*var), 
							(int) (path.getX(i+1)*var), h - (int) (path.getY(i+1))*var);
				
				//left
				g2.setColor(Color.BLUE);
				g2.drawLine((int) (path.getLeftX(i)*var), h - (int) (path.getLeftY(i)*var), 
						(int) (path.getLeftX(i+1)*var), h - (int) (path.getLeftY(i+1))*var);
				
				//right
				g2.setColor(Color.RED);
				g2.drawLine((int) (path.getRightX(i)*var), h - (int) (path.getRightY(i)*var), 
						(int) (path.getRightX(i+1)*var), h - (int) (path.getRightY(i+1))*var);
			} //loop
			*/
			g2.setColor(Color.BLACK);
			g2.drawPolyline(path.getXPoints(scale), path.getYPoints(h, scale), path.getResolution());
			
			g2.setColor(Color.BLUE);
			g2.drawPolyline(path.getLeftXPoints(scale), path.getLeftYPoints(h, scale), path.getResolution());

			g2.setColor(Color.RED);
			g2.drawPolyline(path.getRightXPoints(scale), path.getRightYPoints(h, scale), path.getResolution());
		} //if
	} //end drawCurve
	
	/*
	 * Draws a single waypoint
	 * Graphics2D g2 - Graphics2D object used for drawing
	 */
	private void drawWaypoint(Graphics2D g2, Point point) {
		int in = 6;
		int out = 4;
		
		g2.setColor(Color.BLACK);
		g2.fillOval((int) point.getX() - in/2, 324 - (int) (point.getY() - in/2), in, in);
		g2.setColor(Color.YELLOW);
		g2.fillOval((int) point.getX() - out/2, 324 - (int) (point.getY() - out), out, out);
		
	} //end drawWaypoint
	
	/*
	 * Sets the local robot to the one passed
	 * Robot newRobot - robot to be simulated
	 */
	public void addRobot(Robot newRobot) {
		robot = newRobot;
	} //end addRobot
	
	/**
	 * Add a path to the display
	 * @param path
	 */
	public void addPath(BezierPath path) {
		this.path = path;
	} //end addPath
	
	/*
	 * Displays a grid of the field, with lines at every foot
	 */
	private void displayGrid(Graphics2D g2) {
		//set scale for grid drawing
		g2.scale(super.getWidth() / width, super.getHeight() / height);
		g2.setStroke(new BasicStroke(40.0F / getWidth()));
		
		g2.setBackground(new Color(200,200,200)); //light black
		g2.clearRect(0, 0, width, height);
		g2.setColor(new Color(108,0,170)); //green 
		
		//draw vertical lines
		for(int x = 0; x <= width; x++) {
			g2.drawLine(x, 0, x, height);
		}
		
		//draw horizontal lines
		for(int y = 0; y <= height; y++) {
			g2.drawLine(0, y, width, y);
		}
		
		//revert scale
		double oldScaleWidth = (double) width / (double) super.getWidth();
		double oldScaleHeight = (double) height / (double) super.getHeight();
		
		g2.scale(oldScaleWidth, oldScaleHeight);
	} //end displayGrid
	
	/*
	 * Displays the field
	 */
	private void displayField(Graphics2D g2) {
		g2.drawImage(field, 0,0, null);
	}

	/*
	 * Updates the robot position and redraws it to the screen
	 */
	public void run() { //OVERRIDEN
			long startTime = System.currentTimeMillis();
			robot.update(); //update the robot's speeds and pose
			
			//print out all data of the robot
			if (state == DisplayState.DEBUG) {
				robot.outputData();
			}
			
			//repaint the screen
			repaint();
			
			//sleep for the looper period
			try {
				Thread.sleep(LOOPER_PERIOD - (System.currentTimeMillis() - startTime));
				//(System.currentTimeMillis() - startTime)
			} catch (InterruptedException e) {
				System.out.println();
				e.printStackTrace();
				System.out.println();
			}
	} //end run
	
	/*
	 * Update whether or not to display curves to the screen
	 * boolean bool - state for curve drawing
	 */
	public void setDisplayCurve(boolean bool) {
		displayCurve = bool;
	} //end setDisplayCurve
} //end class
