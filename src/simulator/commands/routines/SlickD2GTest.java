package simulator.commands.routines;

import simulator.Robot;
import simulator.commands.CommandGroup;
import simulator.commands.DriveToGoal;
import simulator.commands.SetXY;
import simulator.field.FieldPoints;
import simulator.field.Point;
import simulator.motion.BezierPath;

public class SlickD2GTest extends CommandGroup {
	
	public SlickD2GTest(Robot r) {
		BezierPath path = FieldPoints.path2;
		
		add(new SetXY(r, path.getPoint(0)));
		
		for (int i = 0; i < 10; i++) {
			double randX = Math.random() * 264 + 60;
			double randY = Math.random() * 264 + 60;
			
			double out;
			double min;
			boolean rev;
			if (i == 0 || i == 9) {
				out = 0.5;
				min = 0;
			}  else {
				out = 1;
				min = 0.5;
			}
			
			if (i % 2 == 0) {
				rev = true;
			} else {
				rev = false;
			}
			
			add(new DriveToGoal(r, new Point(randX,randY), 4, out, min, rev));
		} //loop
		
		/*for (int i = 0; i < path.getResolution(); i += path.getResolution() / 10) {
			double max;
			double min;
			
			if (i == 0 || i == path.getResolution() * 9 / 10) {
				max = 0.5;
				min = 0;
			}  else {
				max = 0.75;
				min = 0.75;	
			}
			
			add(new DriveToGoal(r, path.getPoint(i), 4, max, min, false));
		}*/
	} 

}
