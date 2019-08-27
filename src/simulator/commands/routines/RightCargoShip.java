package simulator.commands.routines;

import simulator.Robot;
import simulator.commands.CommandGroup;
import simulator.commands.DriveDistance;
import simulator.commands.DriveToGoal;
import simulator.commands.SetXY;
import simulator.commands.TurnToGoal;
import simulator.field.FieldPoints;

public class RightCargoShip extends CommandGroup {
	
	public RightCargoShip(Robot r) {
		super();

		add(new SetXY(r, FieldPoints.RIGHT_LEVEL_2));
		add(new DriveDistance(r, 75+10, 10, 1));
//		add(new SetXY(r, FieldPoints.RIGHT_OFF_PLATFORM));
		
		add(new DriveToGoal(r, FieldPoints.CLOSE_RIGHT_CARGO_PRE_SCORE, 4, 1, false));
		add(new TurnToGoal(r, FieldPoints.CLOSE_RIGHT_CARGO, 4, 1));
		add(new DriveDistance(r, 20+10, 10, 0.5));
		add(new DriveDistance(r, -30, 10, 0.5));

		add(new TurnToGoal(r, FieldPoints.RIGHT_FEEDER, 30, 1));
		add(new DriveToGoal(r, FieldPoints.PRE_RIGHT_FEEDER, 4, 1, false));
		add(new TurnToGoal(r, FieldPoints.RIGHT_FEEDER, 3, 1));
		add(new DriveDistance(r, 45+10, 10, 0.3));
		
		add(new DriveToGoal(r, FieldPoints.MID_RIGHT_CARGO_PRE_SCORE, 4, 1, true));
		add(new TurnToGoal(r, FieldPoints.MID_RIGHT_CARGO, 4, 1));
		add(new DriveDistance(r, 25+10, 10, 0.5));
		add(new DriveDistance(r, -30, 10, 0.5));
	}
}
