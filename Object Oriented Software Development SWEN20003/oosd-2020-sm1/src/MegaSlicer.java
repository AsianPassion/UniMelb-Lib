import java.util.LinkedList;
import java.util.List;

import bagel.util.Point;

/** A mega slicer moves at the same rate as a super slicer, 
 *  has double the health of a super slicer, has a reward of $10,
 *  and has a penalty equivalent to the sum of its child slicers¡¯ 
 *  penalties. When a mega slicer is eliminated, it spawns two 
 *  super slicers at the location it was eliminated.
 *
 */
public class MegaSlicer extends SpawnableSlicer {
	
	private static final String RES = "res/images/megaslicer.png";
	private static final double DEFAULT_SPEED = 0.75;
	private static final int DEFAULT_PENALTY = 4;
	private static final int DEFAULT_REWARD = 10;
	private static final int DEFAULT_HEALTH = 2;
	
	private static final int NUM_CHILD_SPAWN = 2;

	
	public MegaSlicer(List<Point> route, int delay) {
		super(route, delay, RES);
		init(DEFAULT_HEALTH, DEFAULT_PENALTY, DEFAULT_REWARD, DEFAULT_SPEED);
	}
	
	public MegaSlicer(List<Point> route, Point p, Point t) {
		super(route, p, t, RES);
		init(DEFAULT_HEALTH, DEFAULT_PENALTY, DEFAULT_REWARD, DEFAULT_SPEED);
	}
	
	@Override
	public void deadAction(Level lv) {
		for (int i=0; i<NUM_CHILD_SPAWN; i++) {
			lv.addSprite(new SuperSlicer(new LinkedList<>(this.getRoute()), 
					this.getLocation(), this.getTarget()));
		}
		this.setDraw(false);
	}
}
