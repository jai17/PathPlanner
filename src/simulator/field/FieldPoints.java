package simulator.field;

import simulator.motion.BezierPath;

/*
 * FieldPoints
 * Created by: Neil Balaskandarajah
 * Last modified: 04/24/2019
 * Static class holding all key points and paths
 */
public class FieldPoints {
	//POINTS//
    //level 2
    public static final Point RIGHT_LEVEL_2 = new Point(207, 40);
    public static final Point LEFT_LEVEL_2 = new Point(117, 40);

    //off platform
    public static final Point RIGHT_OFF_PLATFORM = new Point(207, 115);
    public static final Point LEFT_OFF_PLATFORM = new Point(117, 115);

    //rocket
    public static final Point RIGHT_CLOSE_ROCKET = new Point(283,165);
    public static final Point RIGHT_FAR_ROCKET = new Point(264,285);

    public static final Point RIGHT_ROCKET = new Point(312,230);
    public static final Point CLOSE_RIGHT_ROCKET_SCORE = new Point(299, 195);

    public static final Point LEFT_CLOSE_ROCKET = new Point(41,165);
    public static final Point LEFT_FAR_ROCKET = new Point(60, 295);
    public static final Point LEFT_ROCKET = new Point(12,230);
    public static final Point CLOSE_LEFT_ROCKET_SCORE = new Point(25, 195);

    //feeder
    public static final Point PRE_RIGHT_FEEDER = new Point(300, 60);
    public static final Point RIGHT_FEEDER = new Point(300, 0);

    public static final Point PRE_LEFT_FEEDER = new Point(24, 60);
    public static final Point LEFT_FEEDER = new Point(24, 18); //24,0

    //ejection distances
    public static final double CARGO_SHIP_EJECT_DIST = 6.5;
    public static final double ROCKET_EJECT_DIST = 3;

    //cargo ship
    public static final Point CLOSE_RIGHT_CARGO_PRE_SCORE = new Point(230, 260);
    public static final Point MID_RIGHT_CARGO_PRE_SCORE = new Point(230, 286);
    public static final Point PRE_CLOSE_RIGHT_CARGO = new Point(134, 235);
    public static final Point CLOSE_RIGHT_CARGO = new Point(176, 254);
    public static final Point MID_RIGHT_CARGO = new Point(176, 280);

    public static final Point CLOSE_LEFT_CARGO_PRE_SCORE = new Point(94, 260);
    public static final Point MID_LEFT_CARGO_PRE_SCORE = new Point(94, 286);
    public static final Point PRE_CLOSE_LEFT_CARGO = new Point(190, 235);
    public static final Point CLOSE_LEFT_CARGO = new Point(148, 254);
    public static final Point MID_LEFT_CARGO = new Point(148, 280);
    
    //PATHS//
    public static BezierPath testPath = new BezierPath(new Point[] {
    		new Point(33.0,307.0),
    		new Point(13.0,104.0),
    		new Point(102.0,150.0),
    		new Point(198.0,219.0),
    		new Point(69.0,230.0),
    		new Point(313.0,13.0)
    		}, 30);
    
    public static BezierPath childhood = new BezierPath(new Point[] {
    		new Point(23.0,57.0),
    		new Point(23.0,107.0),
    		new Point(23.0,157.0),
    		new Point(23.0,207.0),
    		new Point(23.0,257.0),
    		new Point(306.0,269.0)
    		}, 30);
    
    public static BezierPath path = new BezierPath(new Point[] {
    		new Point(71.0,96.0),
    		new Point(89.0,169.0),
    		new Point(57.0,281.0),
    		new Point(206.0,285.0),
    		new Point(314.0,188.0),
    		new Point(251.0,95.0)
    		}, 30);
    
    public static BezierPath path2 = new BezierPath(new Point[] {
    		new Point(101.0,58.0),
    		new Point(126.0,129.0),
    		new Point(144.0,246.0),
    		new Point(156.0,146.0),
    		new Point(310.0,177.0),
    		new Point(248.0,281.0)
    		}, 30);
}