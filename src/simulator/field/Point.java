package simulator.field;

/**
 * Point
 * @author Neil
 * 05/10/2018
 *  Class used to define (x,y) positions of the robot on the field.
 */
public class Point {

	//x coordinate and y coordinate
	private double xPos, yPos;
	
	//create new point with x and y position
	public Point(double xPos, double yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	//creates a blank bezier point
	public Point() {
		xPos = 0;
		yPos = 0;
	}

	//get x pos
	public double getX() {
		return xPos;
	}
	
	//set x pos
	public void setX(double xPos) {
		this.xPos = xPos;
	}
	
	//get y pos
	public double getY() {
		return yPos;
	}
	
	//set y pos
	public void setY(double yPos) {
		this.yPos = yPos;
	}
	
	//pythag to calculate distance between points
	public static double calcDistance(Point p1, Point p2) {
		return Math.hypot(p2.getX() - p1.getX(), p2.getY() - p1.getY());
	}
	
	//return angle between 2 points in degrees
	public static double calcAngleDeg(Point p1, Point p2) {
		double deltaX = p2.getX() - p1.getX();
		double deltaY = p2.getY() - p1.getY();
		double theta = 0;
		
		if(deltaX == 0) {
			if (deltaY > 0)
				theta = 0;
			else if (deltaY < 0)
				theta = 180;
		} else if (deltaY == 0) {
			if (deltaX > 0) 
				theta = 90;
			else if (deltaX < 0)
				theta = -90;
		} else
			theta = Math.toDegrees(Math.atan(deltaX/deltaY)); 
		
		return theta;
	}
	
	//return angle formed between 2 points in radians
	public static double calcAngleRad(Point p1, Point p2) {
		double deltaX = p2.getX() - p1.getX();
		double deltaY = p2.getY() - p1.getY();
		double theta = 0;
		
		if(deltaX == 0) {
			if (deltaY > 0)
				theta = 0;
			else if (deltaY < 0)
				theta = 180;
		} else if (deltaY == 0) {
			if (deltaX > 0) 
				theta = 90;
			else if (deltaX < 0)
				theta = -90;
		} else
			theta = Math.atan(deltaY/deltaX); 
		
		return theta;
	}
	
	//check if 2 points are "within bounds" of one another
	public static boolean isWithinBounds(Point target, Point current, double range) {
		double xDiff = Math.abs(target.getX() - current.getX());
		double yDiff = Math.abs(target.getY() - current.getY());
		
		if(xDiff < range && yDiff < range) {
			return true;
		} else {
			return false;
		}
	}

	//return coordinates as a string in "x,y" format
	public String toString() {
		String coords = this.getX() +","+ this.getY();
		return coords;
	}
	
	public void print() {
		System.out.printf("%5.2f\t%5.2f", xPos,yPos);
	}
	
	public void println() {
		System.out.printf("%5.2f\t%5.2f\n", xPos,yPos);
	}
}
