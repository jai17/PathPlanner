/*
 * Util
 * Created by: Neil Balaskandarajah
 * Last modified: 03/24/2019
 * Utility class for useful methods and objects
 */
package simulator;

import javax.swing.JFrame;

public class Util {
	
	/*
	 * Round off a number to a number of digits
	 * double num - number to be rounded off
	 * int digits - digits to keep
	 */
	public static double roundOff(double num, int digits) {
		double newNum = Math.floor(num * Math.pow(10, digits)) / Math.pow(10, digits);
		return newNum;
	} //end roundOff
	
	/*
	 * Prints a double with a specified precision
	 * double num - number to print
	 * int prec - digit precision
	 */
	public static void printRoundedNum(double num, int prec) {
		System.out.printf("%." + prec + "f", num);
	} //end printRoundedNum
	
	/*
	 * Print a blank line to the screen
	 */
	public static void println() {
		System.out.println();
	} //end println
	
	/*
	 * Print a line of text to the screen
	 * String msg - message to print to screen
	 */
	public static void println(String msg) {
		System.out.println(msg);
	} //end println
	
	/*
	 * Clamp a number between two values
	 * double num - number to clamp
	 * double low - bottom value
	 * double high - high value
	 */
	public static double clampNum(double num, double low, double high) {
		double output;
		
		if (num > high) {
			output = high;
		} else if (num < low) {
			output = low;
		} else {
			output = num;
		}
		
		return output;
	} //end clampNum
	
	/*
	 * Pauses the thread for a specified amount of time
	 * long delay - time in milliseconds the pause the thread
	 */
	public static void pause(long delay) {
		try {
			Thread.sleep(delay);
		} catch (InterruptedException t) {
			t.printStackTrace();
		}
	} //end pause
}
