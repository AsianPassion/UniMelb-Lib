
/** A super tank is an active tower that has an effect radius of 
 * 150px, does three times as much damage as a regular tank, and 
 * has a projectile cooldown of 500ms. Its image is supertank.png, 
 * and its projectile¡¯s image is supertank projectile.png.
 *
 */
public class SuperTank extends Tank {
	
	private static final String RES = "res/images/supertank.png";
	
	private static final int RAD = 150;
	private static final int TIME_GAP = 500 * 60 / 1000;
	private static final int DAMAGE = 3;

	public SuperTank(double x, double y) {
		super(x, y, RES);
		setRad(RAD);
		setTimeGap(TIME_GAP);
		setDamage(DAMAGE);
	}
	
	@Override
	public void buildProjectile(Sprite target, Level lv) {
		lv.addSprite(new SuperProjectile(target, 
				this.getLocation(), this.getDamage()));
	}

}
