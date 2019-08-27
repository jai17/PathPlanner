package simulator.commands.routines;

import simulator.Robot;
import simulator.commands.CommandGroup;
import simulator.commands.DriveToGoal;
import simulator.field.FieldPoints;

public class DriveTest extends CommandGroup {

	public DriveTest(Robot r) {
		super();

//		add(new TurnToGoal(r, FieldPoints.MID_LEFT_CARGO, 4, 1, false));
//		add(new DriveDistance(r, 30, 3, 1));
		add(new DriveToGoal(r, FieldPoints.LEFT_FAR_ROCKET, 4, 1, true));
	}
} 
