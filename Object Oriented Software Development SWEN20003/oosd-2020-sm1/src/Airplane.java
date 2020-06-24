import bagel.Input;
import bagel.Window;
import bagel.util.Point;

public class Airplane extends MovableSprite {
	
	private static final String RES = "res/images/airsupport.png";
	private static final double DEFAULT_SPEED = 5;
	private static final int DROP_TIME_GAP = 3 * 60;
	
	private int timer;

	public Airplane(double x, double y) {
		super(RES);
		// TODO Auto-generated constructor stub
		this.setLocation(new Point(x, Window.getHeight()));
		this.setTarget(new Point(x, 0));
		timer = getRandomTime();
	}

	/** Generate an airplane, go through the map from bottom to top
	 * and drop an explosive boom in 0 ~ 3 seconds
	 */
	@Override
	public void update(Level lv, Input input) {
		this.setLocation(
				new Point(this.getLocation().x, 
						this.getLocation().y - DEFAULT_SPEED * 
						lv.getTimeScaler()));
		if (timer > 0) {
			timer = timer - lv.getTimeScaler();
		}else {
			lv.addSprite(new Explosive(this.getLocation()));
			timer = getRandomTime();
		}
		if (this.isDestination(lv)) {
			this.setDraw(false);
		}
	}
	
	/** Generate a random dropping time
	 * @return
	 */
	private int getRandomTime() {
	    return (int) (Math.random() * DROP_TIME_GAP);
	}

}
