package simulator.motion;

import simulator.field.FieldPositioning;
import simulator.field.Point;

/*
 * BezierCurve
 * Created by: Neil Balaskandarajah
 * Last modified; 05/01/2019
 * A path generated using a bezier curve
 */
public class BezierPath {
	// Attributes
	private Point[] controlPts;
	private double width;
	
	//path values
	private Point[] pathPts;
	private double[] headings;
	private double[] distances;
	private Point[] leftPts;
	private Point[] rightPts;
	private double[] leftDistances;
	private double[] rightDistances;
	private double[] radii;
	
	private int[] xPts;
	private int[] yPts;
	
	private int[] leftXPts;
	private int[] leftYPts;
	
	private int[] rightXPts;
	private int[] rightYPts;

	// Constants
	private final int RESOLUTION = 1000;
	private final double SEGMENT = 1.0 / RESOLUTION;
	
	public BezierPath(Point[] controlPts, double width) {
		this.controlPts = controlPts;
		
		pathPts = new Point[RESOLUTION];
		headings = new double[RESOLUTION];
		distances = new double[RESOLUTION];
		leftPts = new Point[RESOLUTION];
		rightPts = new Point[RESOLUTION];
		
		leftDistances = new double[RESOLUTION];
		rightDistances = new double[RESOLUTION];
		
		radii = new double[RESOLUTION];
		
		this.width = width;
		update();
	}

	public void updateControlPoints(Point[] controlPts) {
		this.controlPts = controlPts;
		update();
	}

	public void update() {
		if (controlPts[0] != null &&
				controlPts[1] != null &&
				controlPts[2] != null &&
				controlPts[3] != null &&
				controlPts[4] != null &&
				controlPts[5] != null ) {
			fillPoints();
		}
	}

	private void fillPoints() {
		double distance = 0; //distance at that point in the path
		Point prevPoint = controlPts[0]; //first point
		
		//left and right distances
		double leftDist = 0;
		double rightDist = 0;
		
		double initHeading = FieldPositioning.calcGoalYaw(controlPts[0], calcPoint(SEGMENT*2));
		headings[0] = initHeading;
		
		radii[0] = Double.POSITIVE_INFINITY;
		
		Point prevLeftPoint = transformLeftPoint(controlPts[0], initHeading); 
		Point prevRightPoint = transformRightPoint(controlPts[0], initHeading);
		
		//loop to add points to the arrays
		for (int index = 0; index < RESOLUTION; index++) {
//			double t = smoothT(index / (double) RESOLUTION);
			double t = (double) (index) / (double) RESOLUTION;
			
			//point
			pathPts[index] = calcPoint(t);
			
			//distance
			double dDist = Point.calcDistance(prevPoint, pathPts[index]);
			distance += dDist;
			distances[index] = distance;
			
			//heading
			if (t != 0) {
				headings[index] = FieldPositioning.calcGoalYaw(prevPoint, pathPts[index]);
				
				double dTheta = headings[index] - headings[index-1];
				radii[index] = (180 * dDist) / (dTheta * Math.PI);
			}
			
			//left and right points
			leftPts[index] = transformLeftPoint(pathPts[index], headings[index]);
			rightPts[index] = transformRightPoint(pathPts[index], headings[index]);
			
			//left and right distances 
			leftDist += Point.calcDistance(prevLeftPoint, leftPts[index]);
			rightDist += Point.calcDistance(prevRightPoint, rightPts[index]);
			
			leftDistances[index] = leftDist;
			rightDistances[index] = rightDist;
			
//			System.out.printf("%4.3f\n", headings[index]);
//			System.out.printf("%5.2f\t%5.2f\t%5.2f\n", leftDistances[index], rightDistances[index], distances[index]);
//			System.out.printf(index + "\t%4.3f\t%4.3f\t"+ (leftDistances[index] - rightDistances[index] +"\n"), t, index / (double) RESOLUTION);
//			System.out.printf("%5.2f\t%5.2f\t%5.2f\t%5.2f\t%5.2f\t%5.2f\n", 
//				leftPts[index].getX(),leftPts[index].getY(), rightPts[index].getX(),rightPts[index].getY(), pathPts[index].getX(),pathPts[index].getY());		
//			System.out.println(Point.calcDistance(prevPoint, pathPts[index]));
			
//			System.out.printf("%.2f\t%.2f\t%.2f\n", Point.calcDistance(prevLeftPoint, leftPts[index]), 
//								Point.calcDistance(prevRightPoint, rightPts[index]), Point.calcDistance(prevPoint, pathPts[index]));
			
			//prepare for next loop
			prevPoint = pathPts[index];
			prevLeftPoint = transformLeftPoint(prevPoint, headings[index]);
			prevRightPoint = transformRightPoint(prevPoint, headings[index]);
		} //loop
		
		/*double var = 0.55;
		System.out.printf("%5.2f %5.2f\n", rightPts[(int) (var * RESOLUTION)].getX(), rightPts[(int) (var * RESOLUTION)].getY());
		double var2 = var + 0.01;
		System.out.printf("%5.2f %5.2f\n", rightPts[(int) (var2 * RESOLUTION)].getX(), rightPts[(int) (var2 * RESOLUTION)].getY());*/

		/*System.out.println("post - " + pathPts.length);
		for (int i = 0; i < pathPts.length; i++) {
			double diff = Math.abs(rightPts[i].getX()) - Math.abs(leftPts[i].getX());
			if (diff < 0.1) {
				System.out.print(diff + " ");
				rightPts[i].print();
				System.out.print(" ");
				leftPts[i].print();
				System.out.println();
			}
		}*/
		
		System.out.println("--------");
		
	} //end fillPoints

	private double smoothT(double x) {
		double t;
		
		if (x >= 0 && x <= 0.5) {
			t = 2 * x*x;
		} else if (x >= 0.5 && x <= 1) {
			t = -2 * x*x + 4 * x - 1;
		} else {
			t = -1;
			throw new IllegalArgumentException("wtf r u doing");
		}
		
		return t;
	}
	
	public double distanceBetween(double t2, double t1) {		
		Point p1 = calcPoint(t1);
		Point p2 = calcPoint(t2);
		
		double distance = Point.calcDistance(p1, p2);
		
		return distance;
	}
	
	public double angleBetween(double t2, double t1) {
		Point p1 = calcPoint(t1);
		Point p2 = calcPoint(t2);
		
		double heading = FieldPositioning.calcGoalYaw(p1, p2);
		
		return heading;
	}
	
	private Point calcPoint(double t) {
		double x = 0;
		double y = 0;
		
		for (int i = 0; i <= 5; i++) {
			double binomial = fiveChooseI(i);
			double polynomial = Math.pow((1 - t), (5 - i)) * Math.pow(t, i);
			double xWeightage = controlPts[i].getX();
			double yWeightage = controlPts[i].getY();
			double xPointParamVal = binomial * polynomial * xWeightage;
			double yPointParamVal = binomial * polynomial * yWeightage;
			
			x += xPointParamVal;
			y += yPointParamVal;
		}
		
		return new Point(x,y);
	}
	
	private Point transformLeftPoint(Point p, double phi) {
		double r = width/2;
		
		double leftX = p.getX() - r*Math.cos(Math.toRadians(phi));
		double leftY = p.getY() + r*Math.sin(Math.toRadians(phi));
		
		Point left = new Point(leftX, leftY);
		
		return left;
	}
	
	private Point transformRightPoint(Point p, double phi) {		
		double r = width/2;
		
		double rightX = p.getX() + r*Math.cos(Math.toRadians(phi));
		double rightY = p.getY() - r*Math.sin(Math.toRadians(phi));
		
		Point right = new Point(rightX, rightY);
		
		return right;
	}

	// calculate the factorial of a number
	private int factorial(int number) {
		int factorial = 1;
		if (number == 0) {
			factorial = 1;
		} else {
			for (int i = (number - 1); i > 1; i--) {
				factorial = factorial * i;
			}
		}
		return factorial;
	}

	// calculate 5 choose I for bernstein polynomial
	private int fiveChooseI(int i) {
		int choose = 0;
		int bottom = factorial(i) * factorial((5 - i));
		choose = factorial(5) / bottom;
		return choose;
	}

	public double getX(int i) {
		return pathPts[i].getX();
	}
	
	public double getLeftX(int i) {
		return leftPts[i].getX();
	}
	
	public double getRightX(int i) {
		return rightPts[i].getX();
	}
	
	public double getY(int i) {
		return pathPts[i].getY();
	}
	
	public double getLeftY(int i) {
		return leftPts[i].getY();
	}
	
	public double getRightY(int i) {
		return rightPts[i].getY();
	}
	
	public double getHeading(int i) {
		return headings[i];
	}
	
	public double getDistance(int i) {
		return distances[i];
	}
	
	public double getLeftDistance(int i) {
		return leftDistances[i];
	}
	
	public double getRightDistance(int i) {
		return rightDistances[i];
	}
	
	public int getResolution() {
		return RESOLUTION;
	}

	public double getTotalDistance() {
		return distances[RESOLUTION-1];
	}
	
	public Point getPoint(int i) {
		return pathPts[i];
	}
	
	public double getRadius(int i) {
		return radii[i];
	}
	
	public double getWidth() {
		return width;
	}
	
	public int[] getXPoints(double scale) {
		if (xPts == null) {
			xPts = new int[RESOLUTION];
			
			for (int i = 0; i < RESOLUTION; i++) {
				xPts[i] = (int) (pathPts[i].getX() * scale);
			}
		}
		return xPts;
	}
	
	public int[] getYPoints(int h, double scale) {
		if (yPts == null) {
			yPts = new int[RESOLUTION];
			
			for (int i = 0; i < RESOLUTION; i++) {
				yPts[i] = (int) ((h - pathPts[i].getY()) * scale);
			}
		}
		return yPts;
	}
	
	public int[] getLeftXPoints(double scale) {
		if (leftXPts == null) {
			leftXPts = new int[RESOLUTION];
			
			for (int i = 0; i < RESOLUTION; i++) {
				leftXPts[i] = (int) (leftPts[i].getX() * scale);
			}
		}
		return leftXPts;
	}
	
	public int[] getLeftYPoints(int h, double scale) {
		if (leftYPts == null) {
			leftYPts = new int[RESOLUTION];
			
			for (int i = 0; i < RESOLUTION; i++) {
				leftYPts[i] = (int) ((h - leftPts[i].getY()) * scale);
			}
		}
		return leftYPts;
	}
	
	public int[] getRightXPoints(double scale) {
		if (rightXPts == null) {
			rightXPts = new int[RESOLUTION];
			
			for (int i = 0; i < RESOLUTION; i++) {
				rightXPts[i] = (int) (rightPts[i].getX() * scale);
			}
		}
		return rightXPts;
	}
	
	public int[] getRightYPoints(int h, double scale) {
		if (rightYPts == null) {
			rightYPts = new int[RESOLUTION];
			
			for (int i = 0; i < RESOLUTION; i++) {
				rightYPts[i] = (int) ((h - rightPts[i].getY()) * scale);
			}
		}
		return rightYPts;
	}
}