import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import bagel.Input;
import bagel.util.Point;

/**
 * The explosive has an effect radius of 200px and detonates after 2 seconds 
 * of it being dropped. When an explosive detonates, it deals 500 damage to 
 * every enemy in its effect radius and is subsequently removed from the game.
 *
 */
public class Explosive extends Sprite {
	
	private static final String RES = "res/images/explosive.png";
	private static final int EXPLOSIVE_TIME_GAP = 2 * 60;
	private static final int DAMAGE = 500;
	private static final int EFFECT_RAD = 200;
	private int timer;

	public Explosive(Point p) {
		super(RES);
		this.setLocation(p);
		timer = 0;
	}

	@Override
	public void update(Level lv, Input input) {
		// TODO Auto-generated method stub
		timer++;
		if (timer >= EXPLOSIVE_TIME_GAP) {
			explosiveAction(lv);
			this.setDraw(false);
		}
	}
	
	private void explosiveAction(Level lv) {
		Predicate<Sprite> p = e 
				-> e.getLocation().distanceTo(this.getLocation()) < 
				EFFECT_RAD && e instanceof Slicer;
		List<Sprite> effectedSprite = 
				lv.getObjects()
				.stream()
				.filter(p)
				.collect(Collectors.toList());
		effectedSprite.forEach(e -> ((Slicer)e).reduceHP(DAMAGE, lv));
	}

}
