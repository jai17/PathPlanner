package simulator.commands.routines;

import simulator.Robot;
import simulator.commands.CommandGroup;
import simulator.commands.DriveDistance;
import simulator.commands.DriveToGoal;
import simulator.commands.DriveTurn;
import simulator.commands.SetXY;
import simulator.commands.TurnToGoal;
import simulator.field.FieldPoints;

public class RightRocketLow extends CommandGroup {

	public RightRocketLow(Robot r) {
		super();
		
		add(new SetXY(r, FieldPoints.RIGHT_LEVEL_2));
		add(new DriveDistance(r, 75 + 30, 30, 1));
//		add(new SetXY(r, FieldPoints.RIGHT_OFF_PLATFORM));
		add(new DriveToGoal(r, FieldPoints.RIGHT_CLOSE_ROCKET, 4, 1, false));
		add(new TurnToGoal(r, FieldPoints.RIGHT_ROCKET, 4, 1));
		add(new DriveDistance(r, 40+10, 10, 0.5));
		
		add(new DriveToGoal(r, FieldPoints.PRE_RIGHT_FEEDER, 4, 1, true));
		add(new TurnToGoal(r, FieldPoints.RIGHT_FEEDER, 3, 0.75));
		add(new DriveDistance(r, 45+10, 10, 0.5));
		
		add(new DriveToGoal(r, FieldPoints.RIGHT_FAR_ROCKET, 4, 1, true));
		add(new TurnToGoal(r, FieldPoints.RIGHT_ROCKET, 8, 1));
		add(new DriveDistance(r, 35+10, 10, 0.5));
		
		add(new DriveToGoal(r, FieldPoints.CLOSE_RIGHT_CARGO_PRE_SCORE, 4, 0.8, true));
	}
}
