import java.util.List;

import bagel.util.Point;

/** An abstract class for spawnable slicers
 *
 */
public abstract class SpawnableSlicer extends Slicer {
	
	// usual constructor
	public SpawnableSlicer(List<Point> route, int delay, String res) {
		super(route, delay, res);
		
	}
	
	// create a slicer at a specific point
	public SpawnableSlicer(List<Point> route, Point p, Point t, String res) {
		super(route, p, t, res);
	}
	
	public void init(int health, int penalty, int reward, double speed) {
		this.setHealthPoint(health);
		this.setPenalty(penalty);
		this.setReward(reward);
		this.setSpeed(speed);
	}

}
