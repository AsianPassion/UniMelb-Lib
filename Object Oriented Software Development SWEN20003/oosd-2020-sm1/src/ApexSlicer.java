
import java.util.LinkedList;
import java.util.List;

import bagel.util.Point;

public class ApexSlicer extends SpawnableSlicer {
	
	private static final String RES = "res/images/apexslicer.png";
	private static final double DEFAULT_SPEED = 0.75 / 2;
	private static final int DEFAULT_PENALTY = 16;
	private static final int DEFAULT_REWARD = 150;
	private static final int DEFAULT_HEALTH = 25;
	
	private static final int NUM_CHILD_SPAWN = 4;
	
	public ApexSlicer(List<Point> route, int delay) {
		super(route, delay, RES);
		init(DEFAULT_HEALTH, DEFAULT_PENALTY, DEFAULT_REWARD, DEFAULT_SPEED);
	}
	
	public ApexSlicer(List<Point> route, Point p, Point t) {
		super(route, p, t, RES);
		init(DEFAULT_HEALTH, DEFAULT_PENALTY, DEFAULT_REWARD, DEFAULT_SPEED);
	}
	
	/** An apex slicer will spawn four mega silcer after it has been 
	 * eliminated
	 */
	@Override
	public void deadAction(Level lv) {
		for (int i=0; i<NUM_CHILD_SPAWN; i++) {
			lv.addSprite(new ApexSlicer(new LinkedList<>(this.getRoute()), 
					this.getLocation(), this.getTarget()));
		}
		this.setDraw(false);
	}
}
