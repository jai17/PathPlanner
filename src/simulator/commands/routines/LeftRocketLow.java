package simulator.commands.routines;

import simulator.Robot;
import simulator.commands.CommandGroup;
import simulator.commands.DriveDistance;
import simulator.commands.DriveToGoal;
import simulator.commands.SetXY;
import simulator.commands.TurnToGoal;
import simulator.field.FieldPoints;

public class LeftRocketLow extends CommandGroup {

	public LeftRocketLow(Robot r) {
		super();
		
		add(new SetXY(r, FieldPoints.LEFT_LEVEL_2));
		add(new DriveDistance(r, 65 + 30, 30, 1));
//		add(new SetXY(r, FieldPoints.LEFT_OFF_PLATFORM));
		add(new DriveToGoal(r, FieldPoints.LEFT_CLOSE_ROCKET, 4, 1, false));
		add(new TurnToGoal(r, FieldPoints.LEFT_ROCKET, 4, 1));
		add(new DriveDistance(r, 40+10, 10, 0.5));
		
		add(new DriveToGoal(r, FieldPoints.PRE_LEFT_FEEDER, 4, 1, true));
		add(new TurnToGoal(r, FieldPoints.LEFT_FEEDER, 3, 0.75));
		add(new DriveDistance(r, 45+10, 10, 0.5));
		
		add(new DriveToGoal(r, FieldPoints.LEFT_FAR_ROCKET, 4, 1, true));
		add(new TurnToGoal(r, FieldPoints.LEFT_ROCKET, 8, 1));
		add(new DriveDistance(r, 45+10, 10, 0.5));
		
		add(new DriveToGoal(r, FieldPoints.CLOSE_LEFT_CARGO_PRE_SCORE, 4, 0.8, true));
	}
}
