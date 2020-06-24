import bagel.util.Point;

/** A super tank is an active tower that has an effect radius of 
 * 150px, does three times as much damage as a regular tank, and 
 * has a projectile cooldown of 500ms
 *
 */
public class SuperProjectile extends Projectile {
	
	private static final String RES = "res/images/supertank_projectile.png";
	private static final double SPEED = 10;
	
	public SuperProjectile(Sprite target, Point location, int dmg) {
		super(target, location, dmg, RES);
		this.setSpeed(SPEED);
	}
	

}
