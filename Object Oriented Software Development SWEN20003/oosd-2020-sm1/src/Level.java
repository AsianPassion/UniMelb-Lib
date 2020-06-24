import java.util.ArrayList;

import bagel.Input;
import bagel.Window;
import bagel.map.TiledMap;
import bagel.util.Point;


/**
 * Controls actions of each level, including
 * map draw, panels, waves and each sprite
 *
 */
public class Level {

	private TiledMap map;
	private ArrayList<Sprite> objects;
	private WaveControl waveControl;
	private StatusPanel statusPanel;
	private BuyPanel buyPanel;
	private static final int TIMESCLALER_FACTOR = 1;
	private static final int INIT_LIVES = 25;
	private static final int INIT_MONEY = 500;
	private int timeScaler = 1;
	
	private int lives;
	private int money;
	
	private boolean startControl;

	public Level(int level) {
		map = new TiledMap("res/levels/" + level + ".tmx");
		objects = new ArrayList<Sprite>();
		lives = INIT_LIVES;
		money = INIT_MONEY;
		statusPanel = StatusPanel.getInstance();
		buyPanel = BuyPanel.getInstance();
		waveControl = new WaveControl();
		waveControl.readWaves();
		startControl = false;
	}
	
	public void addSprite(Sprite s) {
		objects.add(s);
	}

	public TiledMap getMap() {
		return map;
	}

	public void update(Input input) {
		// Draw background
		map.draw(0, 0, 0, 0, Window.getWidth(), Window.getHeight());
		
		// Draw panels
		statusPanel.setWaveNum(waveControl.getCurrentWave());
		statusPanel.update(this, input);
		buyPanel.update(this, input);
		
		// Update and draw each sprite
//		objects.forEach(e -> {e.update(this, input); e.draw();});
		for (int i=0; i<objects.size(); i++) {
			objects.get(i).update(this, input);
			objects.get(i).draw();
		}
		
		if (!startControl) {
			statusPanel.setAwaitingStart();
			return;
		}
		
		// Start wave
		waveControl.applyWave(this);
		
		// Remove unused sprite (sprite that has achieved the territory)
		objects.removeIf(obj -> !obj.isDraw());
		
		waveControl.update(timeScaler);
		
	}

	
	/** Increase timescale by scaling factor
	 * 
	 */
	public void increaseTimescale() {
		timeScaler = timeScaler>=5 ? 5 : timeScaler + TIMESCLALER_FACTOR;
	}
	
	/** Decrease timescale by scaling factor
	 * 
	 */
	public void decreaseTimescale() {
		timeScaler = timeScaler<=1 ? 1 : timeScaler - TIMESCLALER_FACTOR;
	}
	
	
	/** Check if this game is over
	 * @return
	 */
	public boolean isGameOver() {
		return (lives <= 0);
	}
	
	/** Check if the current level is over
	 * @return
	 */
	public boolean isLevelOver() {
		return waveControl.isLevelEnd();
	}
	
	/** Check if this point is in a sprite's bounding box
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isInSpriteBoundingBox(double x, double y) {
		for (Sprite s:objects) {
			if (s.getBoundingBox().intersects(new Point(x, y)))
				return true;
		}
		return false;
	}

	public int getTimeScaler() {
		return timeScaler;
	}

	public ArrayList<Sprite> getObjects() {
		return objects;
	}

	public int getLives() {
		return lives;
	}

	/** Reduce a life
	 * 
	 */
	public void loseLives() {
		this.lives = this.lives - 1;
	}

	public int getMoney() {
		return money;
	}
	
	/** Add money 
	 * @param val
	 */
	public void increaseMoney(int val) {
		money = money + val;
	}

	/** Check if money is enough to buy an item
	 * @param money
	 * @return
	 */
	public boolean isEnoughMoney(int money) {
		return this.money - money >= 0;
		
	}
	
	/** Reduce a specific number of money
	 * @param money
	 */
	public void loseMoney(int money) {
		this.money = this.money - money;
	}

	/** Start the level
	 * 
	 */
	public void start() {
		this.startControl = true;
	}
	
	/** Secret key action!!!
	 * 
	 */
	public void superPower() {
		this.lives = 999;
		this.money = 999999;
	}
	
	

}
