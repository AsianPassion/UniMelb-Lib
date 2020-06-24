import bagel.util.Point;

/**
 * Abstract class of all movable sprite
 */
public abstract class MovableSprite extends Sprite {
	
	private Point target;
	private double speed;

	public MovableSprite(String image) {
		super(image);
	}
	
	/** Check if it has achieved the target location
	 * @return true if it is at the destination
	 */
	public boolean isDestination(Level lv) {
		double d = this.getLocation().distanceTo(this.getTarget());
		return d <= (this.speed * lv.getTimeScaler()) && d >=0;
	}
	
	public Point getTarget() {
		return target;
	}

	public void setTarget(Point target) {
		this.target = target;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

}
