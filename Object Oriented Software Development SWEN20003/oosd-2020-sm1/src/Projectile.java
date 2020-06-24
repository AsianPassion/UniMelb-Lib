import bagel.Input;
import bagel.util.Point;

/** A tank is an active tower that has an effect radius of 100px, 
 * does 1 unit of damage per projectile, and has a projectile 
 * cooldown of 1000ms
 *
 */
public class Projectile extends MovableSprite {
	
	private static final String RES = "res/images/tank_projectile.png";
	private static final double SPEED = 10;
	private double speed;
	private Sprite target;
	private int damage;
	
	public Projectile(Sprite target, Point location, int dmg) {
		this(target, location, dmg, RES);
	}
	
	public Projectile(Sprite target, Point location, int dmg, String res) {
		super(res);
		this.target = target;
		this.setLocation(location);
		damage = dmg;
		speed = SPEED;
	}

	@Override
	public void update(Level lv, Input input) {
		setTarget(target.getLocation());
		double diffX = this.getTarget().x - this.getLocation().x;
		double diffY = this.getTarget().y - this.getLocation().y;
		this.setRotateAngle(Math.atan(diffY/diffX));
		moveToNextPoint(diffX, diffY, lv.getTimeScaler());
		if (!target.isDraw()) {
			this.setDraw(false);
		}
		if (this.isDestination()) {
			((Slicer) target).reduceHP(damage, lv);
			this.setDraw(false);
		}

	}
	
	/** Move to next point
	 * @param diffX
	 * @param diffY
	 */
	private void moveToNextPoint(double diffX, double diffY, int timeScaler) {
		double angle = Math.atan(Math.abs(diffY)/Math.abs(diffX));
		double angleX = Math.cos(angle) * speed * timeScaler;
		double angleY = Math.sin(angle) * speed * timeScaler;
		boolean moveRight = diffX >= 0;
		boolean moveDown = diffY >= 0;
		double futureX, futureY;
		if (moveRight) {
			futureX = angleX + this.getLocation().x;
		} else {
			futureX = - angleX + this.getLocation().x;
		}
		if (moveDown) {
			futureY = angleY + this.getLocation().y;
		} else {
			futureY = -angleY + this.getLocation().y;
		}
		this.setLocation(new Point(futureX, futureY));
	}
	
	/** Check if a projectile has reached the destination
	 * @return
	 */
	public boolean isDestination() {
		return target.getBoundingBox().intersects(getLocation());
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	
	

}
