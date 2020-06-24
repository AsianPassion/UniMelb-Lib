
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import bagel.Input;
import bagel.util.Point;

/** A regular slicer moves at a rate of 2px/frame, has a health of 1 unit, 
 * a reward of $2, and a penalty of 1 life.
 *
 */
public class Slicer extends MovableSprite {
	
	private Queue<Point> route;
	private static final String RES = "res/images/slicer.png";
	private static final double DEFAULT_SPEED = 1;
	private static final int DEFAULT_PENALTY = 1;
	private static final int DEFAULT_REWARD = 2;
	private static final int DEFAULT_HEALTH = 1;
	
	private int healthPoint;
	private double delayTime;
	private int reward;
	private int penalty;

	public Slicer(List<Point> route, int delay) {
		super(RES);
		init(route, delay);
		this.setLocation(this.nextPoint());
		this.setTarget(this.nextPoint());
	}
	
	// spawn a slicer with different image
	public Slicer(List<Point> route, int delay, String res) {
		super(res);
		init(route, delay);
		this.setLocation(this.nextPoint());
		this.setTarget(this.nextPoint());
	}
	
	// spawn a slicer immediately at a point
	public Slicer(List<Point> route, Point p, Point t) {
		super(RES);
		init(route, 0);
		this.setLocation(p);
		this.setTarget(t);
	}
	
	public Slicer(List<Point> route, Point p, Point t, String res) {
		super(res);
		init(route, 0);
		this.setLocation(p);
		this.setTarget(t);
	}
	
	private void init(List<Point> route, int delayTime) {
		this.route = new LinkedList<>(route);
		this.setHealthPoint(DEFAULT_HEALTH);
		this.setPenalty(DEFAULT_PENALTY);
		this.setReward(DEFAULT_REWARD);
		this.setSpeed(DEFAULT_SPEED);
		this.delayTime = msToFrame(delayTime);
	}

	private double msToFrame(int delay) {
		return delay / 1000 * 60;
	}

	/** Update the slicer's position and rotating angle
	 *
	 */
	@Override
	public void update(Level lv, Input input) {
		
		
		if (delayTime > 0) {
			delayTime = delayTime - lv.getTimeScaler();
			return;
		}

		if (!this.isDraw()) {
			return;
		}
		double diffX = this.getTarget().x - this.getLocation().x;
		double diffY = this.getTarget().y - this.getLocation().y;
		this.setRotateAngle(Math.atan(diffY/diffX));
		moveToNextPoint(diffX, diffY, lv);
		if (this.isDestination(lv)) {
			Point temp = this.nextPoint();
			if (temp != null) {
				this.setTarget(temp);
			} else {
				lv.loseLives();
				this.setDraw(false);
			}
			
		}
		
	}
	
	/** Move object to the next point it supposed to be
	 * @param diffX
	 * @param diffY
	 */
	private void moveToNextPoint(double diffX, double diffY, Level lv) {
		double angle = Math.atan(Math.abs(diffY)/Math.abs(diffX));
		double angleX = Math.cos(angle) * this.getSpeed() * lv.getTimeScaler();
		double angleY = Math.sin(angle) * this.getSpeed() * lv.getTimeScaler();
		boolean moveRight = diffX >= 0;
		boolean moveDown = diffY >= 0;
		double futureX, futureY;
		if (moveRight) {
			futureX = angleX + this.getLocation().x;
		} else {
			futureX = - angleX + this.getLocation().x;
			this.setRotateAngle(this.getRotateAngle() - Math.PI);
		}

		
		futureY = moveDown ? 
				angleY + this.getLocation().y : -angleY + this.getLocation().y;
		this.setLocation(new Point(futureX, futureY));
	}
	
	/** Get next target point on the map
	 * @return
	 */
	private Point nextPoint() {
		return route.poll();
	}
	
	public boolean isDelaying() {
		return delayTime > 0;
	}
	
	/** Reduce hp of the slicer
	 * @param value
	 * @param lv
	 */
	public void reduceHP(int value, Level lv) {
		healthPoint = healthPoint - value;
		if (healthPoint <= 0) {
			lv.increaseMoney(this.getReward());
			deadAction(lv);
		}
	}
	
	/** Action if the slicer is eliminated
	 * @param lv
	 */
	public void deadAction(Level lv) {
		this.setDraw(false);
	}

	public int getHealthPoint() {
		return healthPoint;
	}

	public void setHealthPoint(int healthPoint) {
		this.healthPoint = healthPoint;
	}

	public int getReward() {
		return reward;
	}

	public void setReward(int reward) {
		this.reward = reward;
	}

	public int getPenalty() {
		return penalty;
	}

	public void setPenalty(int penalty) {
		this.penalty = penalty;
	}

	public Queue<Point> getRoute() {
		return route;
	}

	



}
