package simulator.commands.routines;

import simulator.Robot;
import simulator.commands.CommandGroup;
import simulator.commands.DriveDistance;
import simulator.commands.DriveToGoal;
import simulator.commands.SetXY;
import simulator.commands.TurnToGoal;
import simulator.field.FieldPoints;

public class LeftCargoShip extends CommandGroup {

	public LeftCargoShip(Robot r) {
		super();

		add(new SetXY(r, FieldPoints.LEFT_LEVEL_2));
		add(new DriveDistance(r, 75+10, 10, 1));
//		add(new SetXY(r, FieldPoints.LEFT_OFF_PLATFORM));
		
		add(new DriveToGoal(r, FieldPoints.CLOSE_LEFT_CARGO_PRE_SCORE, 4, 1, false));
		add(new TurnToGoal(r, FieldPoints.CLOSE_LEFT_CARGO, 4, 1));
		add(new DriveDistance(r, 20+10, 10, 0.5));
		add(new DriveDistance(r, -30, 10, 0.5));

		add(new TurnToGoal(r, FieldPoints.LEFT_FEEDER, 30, 1));
		add(new DriveToGoal(r, FieldPoints.PRE_LEFT_FEEDER, 4, 1, false));
		add(new TurnToGoal(r, FieldPoints.LEFT_FEEDER, 3, 1));
		add(new DriveDistance(r, 45+10, 10, 0.3));
		
		add(new DriveToGoal(r, FieldPoints.MID_LEFT_CARGO_PRE_SCORE, 4, 1, true));
		add(new TurnToGoal(r, FieldPoints.MID_LEFT_CARGO, 4, 1));
		add(new DriveDistance(r, 25+10, 10, 0.5));
		add(new DriveDistance(r, -30, 10, 0.5));
	}
}

/*
 * add(new DriveDistance(r, 75+10, 10, 1));
		add(new SetXY(r, FieldPoints.LEFT_OFF_PLATFORM));
		
		add(new DriveToGoal(r, FieldPoints.CLOSE_LEFT_CARGO_PRE_SCORE, 4, 1, false));
		add(new TurnToGoal(r, FieldPoints.CLOSE_LEFT_CARGO, 4, 1));
		add(new DriveDistance(r, 20+10, 10, 0.5));
		
		add(new DriveToGoal(r, FieldPoints.PRE_FEEDER_LEFT, 4, 1, true));
		add(new TurnToGoal(r, FieldPoints.LEFT_FEEDER, 3, 1));
		add(new DriveDistance(r, 45+10, 10, 0.3));
		
		add(new DriveToGoal(r, FieldPoints.MID_LEFT_CARGO_PRE_SCORE, 4, 1, true));
		add(new TurnToGoal(r, FieldPoints.MID_LEFT_CARGO, 4, 1, true));
		add(new DriveDistance(r, 25+10, 10, 0.5));
		add(new DriveDistance(r, -30, 10, 0.5));
 */
