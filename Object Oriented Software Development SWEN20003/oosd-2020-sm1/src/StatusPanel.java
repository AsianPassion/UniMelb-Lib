import bagel.Font;
import bagel.Input;
import bagel.Window;
import bagel.util.Point;

/** The status panel shows the state of the game at any given time.
 *  The state of the game can be described by: the current wave 
 *  (either the wave currently in progress or the wave we are waiting 
 *  to start), the current time scale, current ¡°status¡±, and the number
 *  of lives left. The player initially starts with 25 lives.
 *
 */
public class StatusPanel extends Sprite {
	
	private static final String RES = "res/images/statuspanel.png";
	private static final String FONT_RES = "res/fonts/DejaVuSans-Bold.ttf";
	private static final int FONT_SIZE = 20;
	private static final int STRING_GAP = 200;
	private static final int HEIGHT = Window.getHeight() - 5;
	private static final int WIDTH = Window.getWidth() / 2;
	private static StatusPanel sp = null;
	private final Font font;
	private int waveNum;
	private String status;
	

	private StatusPanel() {
		super(RES);
		setLocation(new Point(WIDTH, HEIGHT));
		waveNum = 1;
		font = new Font(FONT_RES, FONT_SIZE);
	}
	
	public static StatusPanel getInstance() {
		return sp = sp == null ? new StatusPanel() : sp; 
	}

	@Override
	public void update(Level lv, Input input) {
		draw();
		font.drawString("Wave: " + waveNum, 0, HEIGHT);
		font.drawString("TimeScale: " + lv.getTimeScaler(), 
				STRING_GAP, HEIGHT);
		font.drawString("Status: " + status, WIDTH, HEIGHT);
		font.drawString("Lives: " + lv.getLives(), 
				WIDTH * 2 - STRING_GAP, HEIGHT);
	}

	public void setWaveNum(int waveNum) {
		this.waveNum = waveNum;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setWinner() {
		this.status = "Winner!";
	}
	
	public void setPlacing() {
		this.status = "Placing";
	}

	public void setInProgress() {
		this.status = "Wave In Progress ";
	}
	
	public void setAwaitingStart() {
		this.status = "Awaiting Start ";
	}



}
