/*
 * Robot
 * Created by: Neil Balaskandarajah
 * Last modified; 03/24/2019
 * Robot object placed in the display to simulate
 */
package simulator;

import java.awt.Color;

import simulator.field.Point;
import simulator.motion.PIDController;

public class Robot {

	private double x; // center x of robot
	private double y; // center y of robot
	private double width; // width of robot
	private double height; // height of robot
	private double wheelRad; //radius of drive wheels
	private Color color; // color of robot to represent states

	private double heading; // heading of robot
	private double angSpeed; // angular speed of the robot
	private double yaw; // yaw of robot

	private double leftPos; // left wheel position
	private double rightPos; // right wheel position
	private double averagePos; // average wheel position

	private double leftSpeed; // speed of left side
	private double rightSpeed; // speed of right side
	private double speed; // translational speed of robot

	private Point point; // point representing robot's position

	public static final double UPDATE_PERIOD = (double) (Display.LOOPER_PERIOD) / 1000; // update period
	
	private final PIDController drivePID; //driving PID controller 
	private final PIDController turnPID; //turning PID controller

	/*
	 * Create a robot of a given width and height 
	 * int centerX - x coordinate of robot center 
	 * int centerY - y coordinate of robot center 
	 * int width - width ofrobot 
	 * int height - height of robot
	 */
	public Robot(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		this.heading = 0;
		this.yaw = 0;

		this.leftSpeed = 0;
		this.rightSpeed = 0;
		updateSpeed();

		this.leftPos = 0;
		this.rightSpeed = 0;
		updatePos();

		this.point = new Point(0, 0);

		drivePID = new PIDController(NumberConstants.pDrive, 0, NumberConstants.dDrive);
		turnPID = new PIDController(NumberConstants.pTurn, 0, NumberConstants.dTurn);

		color = new Color(249, 128, 29);
		
		this.wheelRad = 2; //TO-DO: not hard code this
	} // end constructor

	// ROBOT PARAMETERS //
	
	/*
	 * Get the track width (distance between wheel centers) of robot
	 */
	public double getTrackWidth() {
		return width - 7.0;
	} //end getTrackWidth
	
	/*
	 * Get the radius of the drive wheels
	 */
	public double getWheelRad() {
		return wheelRad;
	} //end getWheelRadius
	
	
	// FIELD RELATIVE POSITIONING//

	/*
	 * Get the X value of the robot
	 */
	public double getX() {
		return point.getX();
		// return x;
	} // end getX

	/*
	 * Set the x value of the robot 
	 * double newX - new center X value
	 */
	public void setX(double newX) {
		// x = newX;
		point.setX(newX);
	} // end setX

	/*
	 * Get the y value of the robot
	 */
	public double getY() {
		return point.getY();
	} // end getY

	/*
	 * Set the y value of the robot 
	 * int newY - new center Y of the robot
	 */
	public void setY(double newY) {
		point.setY(newY);
	} // end setY

	// ROBOT DIMENSIONS//

	/*
	 * Get the width of the robot in inches
	 */
	public int getWidth() {
		return (int) width;
	} // end getWidth

	/*
	 * Get the height of the robot in inches
	 */
	public int getHeight() {
		return (int) height;
	} // end getHeight

	// ROBOT SPEED//

	/*
	 * Set the left speed of the robot 
	 * double newLeftSpeed - new speed for the left side of the robot between -1 & 1
	 */
	public void setLeftSpeedPercent(double newLeftSpeed) {
		newLeftSpeed = Util.clampNum(newLeftSpeed, -1, 1);

		leftSpeed = NumberConstants.TOP_SPEED * newLeftSpeed;
	} // end setLeftSpeed
	
	/*
	 * Set the left speed of the robot
	 * double newLeftSpeed - new speed for the left side of the robot in inches per second
	 */
	public void setLeftSpeedIPS(double newLeftSpeed) {
		leftSpeed = Util.clampNum(newLeftSpeed, -NumberConstants.TOP_SPEED, NumberConstants.TOP_SPEED);
	} //end setLeftSpeedIPS

	/*
	 * Get the speed of the left side of the robot
	 */
	public double getLeftSpeed() {
		return leftSpeed;
	} // end getLeftSpeed

	/*
	 * Set the right speed of the robot 
	 * double newRightSpeed - new speed for the right side of the robot between -1 & 1
	 */
	public void setRightSpeedPercent(double newRightSpeed) {
		newRightSpeed = Util.clampNum(newRightSpeed, -1, 1);

		rightSpeed = NumberConstants.TOP_SPEED * newRightSpeed;
	} // end setRightSpeed
	
	/*
	 * Set the right speed of the robot
	 * double newRightSpeed - new speed for the right side of the robot in inches per second
	 */
	public void setRightSpeedIPS(double newRightSpeed) {
		rightSpeed = Util.clampNum(newRightSpeed, -NumberConstants.TOP_SPEED, NumberConstants.TOP_SPEED);
	} //end setRightSpeedIPS


	/*
	 * Get the right speed of the robot
	 */
	public double getRightSpeed() {
		return rightSpeed;
	} // end getRightSpeed

	/*
	 * Update the linear speed of the robot by averaging the left and right speeds
	 */
	private void updateSpeed() {
		speed = (leftSpeed + rightSpeed) / 2.0;
	} // end updateSpeed

	/*
	 * Update and get the linear speed of the robot in inches per second
	 */
	public double getSpeed() {
		updateSpeed();
		return speed;
	} // end getSpeed
	
	/*
	 * Update and get the angular velocity of the robot in degrees
	 */
	public double getAngularVel() {
		updateAngularSpeed();
		return Math.toDegrees(angSpeed);
	}

	/**
	 * Whether the robot is slower than a percentage of its top speed
	 * @param speedPercent
	 * @return if speed is less than the value passed (absolutes)
	 */
	public boolean slowerThanPercent(double speedPercent) {
//		System.out.println(Math.abs(speed) +" "+ Math.abs(NumberConstants.TOP_SPEED * speedPercent));
		return Math.abs(speed) < Math.abs(NumberConstants.TOP_SPEED * speedPercent);
	} //end slowerThanPercent
	
	// PID//

	/*
	 * Drive the robot using a regulated PID
	 */
	public void regulatedDrivePID(double distSetpoint, double angleSetpoint, double epsilon, double topSpeed,
									double minSpeed, boolean relative) {
	    //change to use angles
	    turnPID.changePIDGains(NumberConstants.pDriveTurn, 0, NumberConstants.dDriveTurn);
	    
	    double currentVal = getAngle();

	    double driveOut = drivePID.calcPIDDrive(distSetpoint, getAveragePos(), epsilon);
	    
	    // limit driving PID output to top speed
	    driveOut = Util.clampNum(Math.abs(driveOut), Math.signum(driveOut) * minSpeed, Math.signum(driveOut) * topSpeed);
	    
	    double angleOut = turnPID.calcPIDDrive(angleSetpoint, currentVal, epsilon);

	    double leftOut = driveOut + angleOut;
	    
	    double rightOut = driveOut - angleOut;
	    
	    setLeftSpeedPercent(leftOut);
	    setRightSpeedPercent(rightOut);
	} //end regulatedDrivePID

	/*
	 * Turn the robot using a regulated turn PID
	 */
	public void regulatedTurnPID(double angleSetpoint, double epsilon, double topSpeed, boolean relative) {
	  	turnPID.changePIDGains(NumberConstants.pTurn, 0, NumberConstants.dTurn);

	    double currentVal;
	    currentVal = getAngle();

	    double angleOut = turnPID.calcPIDDrive(angleSetpoint, currentVal, epsilon);
	    
	    // limit turning PID output to top speed
	    if (angleOut > 0) // driving forward
	      angleOut = Math.min(angleOut, topSpeed);
	    else if (angleOut < 0) // driving backward
	      angleOut = Math.max(angleOut, -topSpeed);

    	setLeftSpeedPercent(angleOut);
	    setRightSpeedPercent(-angleOut);
	  }

	/*
	 * Stops the robot by setting wheel speeds to zero
	 */
	public void stop() {
		leftSpeed = 0;
		rightSpeed = 0;
	} //end stop
	
	// ROBOT POSITION//

	/*
	 * Get the position of the left side of the robot
	 */
	public double getLeftPos() {
		return leftPos;
	} // end getLeftPos

	/*
	 * Get the position of the right side of the robot
	 */
	public double getRightPos() {
		return rightPos;
	} // end getRightPos

	/*
	 * Get the average position of the robot
	 */
	public double getAveragePos() {
		updatePos();
		return averagePos;
	} // end getAveragePos

	/*
	 * Update the wheel position of the robot
	 */
	private void updatePos() {
		averagePos = (leftPos + rightPos) / 2;
	} // end updatePos

	// ROBOT HEADING//

	/*
	 * Get the heading of the robot in radians
	 */
	public double getHeadingRads() {
		return heading;
	} // end getHeadingRads

	/*
	 * Get the heading of the robot in degrees
	 */
	public double getAngle() {
		return Math.toDegrees(heading);
	} // end getHeadingDegs

	/*
	 * Set the heading of the robot
	 * double newHdg - new robot heading in degrees
	 */
	public void setAngle(double newHdg) {
		heading = Math.toRadians(newHdg);
		updateHeading();
	} //end setAngle
	
	/*
	 * Get the yaw of the robot in degrees
	 */
	public double getYaw() {
		yaw = getAngle();

		// normalize to 0-360 degree range
		if (yaw > 360) {
			while (yaw > 360) {
				yaw -= 360;
			}
		} else if (yaw < 0) {
			while (yaw < 0) {
				yaw += 360;
			}
		}

		// set to negative if greater than 180
		if (yaw > 180) {
			yaw -= 360;
		}

		return yaw;
	} // end getYaw

	/*
	 * Update the angular speed of the robot in radians
	 */
	private void updateAngularSpeed() {
		angSpeed = (rightSpeed - leftSpeed) / width;
	} // end updateAngularSpeed

	/*
	 * Update the heading of the robot
	 */
	private void updateHeading() {
		updateAngularSpeed();
		heading -= angSpeed * UPDATE_PERIOD;
	} // end updateHeading

	// POSE//

	/*
	 * Update the pose, position and speed of the robot and change its color
	 */
	public void update() {
		updateSpeed();
		updateHeading();
		updatePos();

		leftPos += leftSpeed * UPDATE_PERIOD;
		rightPos += rightSpeed * UPDATE_PERIOD;

		point.setX(getX() + calcDeltaX());
		point.setY(getY() + calcDeltaY());
		
		setVelColor();
	} // end update

	/*
	 * Calculate the change in x in the given time interval
	 */
	private double calcDeltaX() {
		double dx = this.speed * Math.sin(heading) * UPDATE_PERIOD;
		return dx;
	} // end calcDeltaX

	/*
	 * Calculate the change in y in the given time interval
	 */
	private double calcDeltaY() {
		double dy = this.speed * Math.cos(heading) * UPDATE_PERIOD;
		return dy;
	} // end calcDeltaY

	/*
	 * Get the robot's coordinates as a point
	 */
	public Point getPoint() {
		return point;
	} // end getPoint

	/*
	 * Set the robot's coordinates as a point 
	 * Point pt - the new point to set the robot too
	 */
	public void setPoint(Point pt) {
		point = pt;
	} // end setPoint

	// OUTPUTS//

	/*
	 * Print the pose and velocity of the robot
	 */
	public void outputData() { // OVERRIDEN
		System.out.printf("(%.2f", point.getX());
		System.out.printf(",%.2f)", point.getY());
		System.out.printf("(%.2f,", yaw);
		System.out.printf("%.2f)", getAngle());
		System.out.printf("(%.3f", leftPos);
		System.out.printf(",%.3f,", rightPos);
		System.out.printf("%.3f)", averagePos);
		System.out.printf("(%.3f", leftSpeed);
		System.out.printf(",%.3f,", rightSpeed);
		System.out.printf("%.3f)", speed);
		
		System.out.println();
	} // end toString

	/*
	 * Change the color of the robot 
	 * Color clr - new color of the robot
	 */
	public void setColor(Color clr) {
		color = clr;
	} // end setColor

	/*
	 * Get the color of the robot
	 */
	public Color getColor() {
		return color;
	} // end getColor
	
	/*
	 * Change color based on speed
	 */
	public void setVelColor() {
		double modifier = Math.abs(speed) / NumberConstants.TOP_SPEED;
		int val = 127 + (int) (128 * modifier);
		
		color = new Color(0, val, 0);	
	}
} // end class
