import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import bagel.Input;
import bagel.util.Point;

/** A tank is an active tower that has an effect radius of 
 * 100px, does 1 unit of damage per projectile, and has a 
 * projectile cooldown of 1000ms. Its image is tank.png, 
 * and its projectile¡¯s image is tank projectile.png.
 *
 */
public class Tank extends Tower {
	
	private static final String RES = "res/images/tank.png";
	
	private static final int RAD = 100;
	private static final int TIME_GAP = 1000 / 1000 * 60;
	private static final int DAMAGE = 1;
	
	private int rad;
	private int timeGap;
	private int damage;
	private int timer;
	
	private Sprite tempTarget;
	

	public Tank(double x, double y) {
		super(RES);
		setLocation(new Point(x, y));
		rad = RAD;
		timeGap = TIME_GAP;
		damage = DAMAGE;
		tempTarget = null;
	}
	
	public Tank(double x, double y, String res) {
		super(res);
		setLocation(new Point(x, y));
	}

	@Override
	public void update(Level lv, Input input) {
		// TODO Auto-generated method stub
		draw();
		// 1. find closest target
		// 2. face it
		// 3. project a bullet
		
		Predicate<Sprite> withinEffect = s 
				-> s.getLocation().distanceTo(this.getLocation()) < rad && 
				s instanceof Slicer;
		List<Sprite> filteredList = lv.getObjects()
				.stream()
				.filter(withinEffect)
				.collect(Collectors.toList());
		if (filteredList.size() > 0) {
			if (tempTarget == null || !filteredList.contains(tempTarget)) {
				tempTarget = filteredList.get((int) (Math.random() * filteredList.size()));
			}
			
			
			if (timer >= timeGap) {
				timer = 0;
				buildProjectile(tempTarget, lv);
			}
			
			// angle is wrong
			double diffX = tempTarget.getLocation().x - this.getLocation().x;
			double diffY = tempTarget.getLocation().y - this.getLocation().y;
			
			boolean xLarger = diffX > 0;
			boolean yLarger = diffY > 0;
			double theta = Math.atan(Math.abs(diffY/diffX));
			if(xLarger) {
				if (yLarger) {
					this.setRotateAngle(Math.PI * 1 / 2 + theta);
				}else {
					this.setRotateAngle(Math.PI * 1 / 2 - theta);
				}
			}else {
				if (yLarger) {
					this.setRotateAngle(Math.PI * 3 / 2 - theta);
				}else {
					this.setRotateAngle(Math.PI * 3 / 2 + theta);
				}
			}

		}
		timer = timer + lv.getTimeScaler();
	}
	
	/** Generate a projectile to a sprite at current position
	 * @param target
	 * @param lv
	 */
	public void buildProjectile(Sprite target, Level lv) {
		lv.addSprite(new Projectile(target, this.getLocation(), damage));
	}

	public void setRad(int rad) {
		this.rad = rad;
	}

	public void setTimeGap(int timeGap) {
		this.timeGap = timeGap;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getDamage() {
		return damage;
	}
	
	

}
