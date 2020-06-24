
import java.util.LinkedList;
import java.util.List;

import bagel.util.Point;

/** A super slicer moves at 3/4 the rate of a regular slicer, has the
 *  same health as a regular slicer, has a reward of $15, and has a penalty
 *  equivalent to the sum of its child slicers¡¯ penalties. When a super
 *  slicer is eliminated, it spawns two regular slicers at the 
 *  location it was eliminated.
 *
 */
public class SuperSlicer extends SpawnableSlicer {
	
	private static final String RES = "res/images/superslicer.png";
	private static final double DEFAULT_SPEED = 0.75;
	private static final int DEFAULT_PENALTY = 2;
	private static final int DEFAULT_REWARD = 15;
	private static final int DEFAULT_HEALTH = 1;
	
	private static final int NUM_CHILD_SPAWN = 2;

	public SuperSlicer(List<Point> route, int delay) {
		super(route, delay, RES);
		init(DEFAULT_HEALTH, DEFAULT_PENALTY, DEFAULT_REWARD, DEFAULT_SPEED);
	}
	
	public SuperSlicer(List<Point> route, Point p, Point t) {
		super(route, p, t, RES);
		init(DEFAULT_HEALTH, DEFAULT_PENALTY, DEFAULT_REWARD, DEFAULT_SPEED);
	}
	
	@Override
	public void deadAction(Level lv) {
		for (int i=0; i<NUM_CHILD_SPAWN; i++) {
			lv.addSprite(new Slicer(new LinkedList<>(this.getRoute()), 
					this.getLocation(), this.getTarget()));
		}
		this.setDraw(false);
	}
}
