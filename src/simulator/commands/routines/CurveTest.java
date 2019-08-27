package simulator.commands.routines;

import simulator.Robot;
import simulator.commands.CommandGroup;
import simulator.commands.DriveCurve;
import simulator.commands.SetHeading;
import simulator.commands.SetXY;
import simulator.commands.WaitCommand;
import simulator.field.FieldPoints;
import simulator.field.Point;
import simulator.motion.BezierPath;

public class CurveTest extends CommandGroup {

	public CurveTest(Robot r, BezierPath path) {
		super();
		
		add(new SetXY(r, path.getPoint(0)));
		add(new SetHeading(r, path.getHeading(0)));
//		add(new WaitCommand(r, 1));
		add(new DriveCurve(r, path, path.getTotalDistance()/20.0, path.getTotalDistance()/20.0, 50, 0.0));
	}
}
