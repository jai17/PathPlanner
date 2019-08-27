package simulator.commands.routines;

import simulator.Robot;
import simulator.commands.CommandGroup;
import simulator.commands.DriveRadius;
import simulator.commands.SetXY;
import simulator.field.Point;

public class DriveRadiusTest extends CommandGroup {

	public DriveRadiusTest(Robot r, double radius, double outSpeed) {
		add(new SetXY(r, new Point(80,80)));
		add(new DriveRadius(r, radius, outSpeed));
	}

}
