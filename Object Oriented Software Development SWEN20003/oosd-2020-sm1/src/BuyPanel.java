import java.util.ArrayList;

import bagel.Font;
import bagel.Input;
import bagel.MouseButtons;
import bagel.Window;
import bagel.util.Point;

/** The buy panel contains the towers available for purchase (purchase items), 
 * a list of key binds, and the money the player currently has. 
 *
 */
public class BuyPanel extends Sprite {
	
	private static final String RES = "res/images/buypanel.png";
	private static final String FONT_RES = "res/fonts/DejaVuSans-Bold.ttf";
	private static final int FONT_SIZE = 20;
	private static final int HEIGHT = 50;
	private static final int WIDTH = Window.getWidth() / 2;
	
	private static final int PRICE_HEIGHT = 20;
	private static final int PRICE_WIDTH_DIFF = 20;
	
	private static final int DESCRIPTION_HEIGHT = 15;
	
	private static final int MONEY_WIDTH = 800;
	
	private static BuyPanel buyPanel = null;
	private final Font font;
	
	private BuyPanelItem placingImg = null;
	
	private ArrayList<BuyPanelItem> panelItems;

	private BuyPanel() {
		super(RES);
		setLocation(new Point(WIDTH, HEIGHT));
		panelItems = new ArrayList<BuyPanelItem>();
		panelItems.add(new TankPanelItem());
		panelItems.add(new SuperTankPanelItem());
		panelItems.add(new AirplanePanelItem());
		font = new Font(FONT_RES, FONT_SIZE);
	}
	
	/** Make buy panel as a singleton class
	 * @return
	 */
	public static BuyPanel getInstance() {
		return buyPanel = buyPanel == null ? new BuyPanel() : buyPanel; 
	}

	@Override
	public void update(Level lv, Input input) {
		// draw self first
		draw();
		
		// draw descriptions
		font.drawString("Key Binds:\n"
				+ "S - Start Wave\n"
				+ "L - Increase Timescale\n"
				+ "K - Decrease Timescale\n"
				+ "P - Secret Key", 
				WIDTH, DESCRIPTION_HEIGHT);
		
		// draw panel items
		panelItems.forEach(e -> {
			e.draw();
			font.drawString("$" + e.getPrice(), 
					e.getLocation().x - PRICE_WIDTH_DIFF, PRICE_HEIGHT);
		});
		font.drawString("$" + lv.getMoney(), MONEY_WIDTH, HEIGHT / 2);
		
		// if item was chosen or placed
		if (input.wasPressed(MouseButtons.LEFT)) {
			double x = input.getMouseX();
			double y = input.getMouseY();
			if (placingImg != null) {
				StatusPanel.getInstance().setPlacing();
				placeAtCurrentPosition(input, lv);
			}
			panelItems.forEach(e -> {
				if (e.intersects(x, y)) {
					placingImg = e;
				}
			});
		}
		// if it is not null, a placing action is performing
		if (placingImg != null) {
			placing(placingImg, input);
		}
		if (input.wasPressed(MouseButtons.RIGHT)) {
			placingImg = null;
		}
	}
	
	/** Place an panel item at current position
	 * @param input
	 * @param lv
	 */
	public void placeAtCurrentPosition(Input input, Level lv) {
		double x = input.getMouseX();
		double y = input.getMouseY();
		if (input.isDown(MouseButtons.LEFT)) {
			// money is not enough
			if (!lv.isEnoughMoney(placingImg.getPrice())) {
				return;
			}
			if (isValidPlace(input, lv)) {
				if (placingImg instanceof TankPanelItem) {
					lv.addSprite(new Tank(x, y));
				}else if (placingImg instanceof SuperTankPanelItem) {
					lv.addSprite(new SuperTank(x, y));
				}
			}
			
			if (placingImg instanceof AirplanePanelItem) {
				lv.addSprite(new Airplane(x, y));
			}
			lv.loseMoney(placingImg.getPrice());
		}
		
	}
	
	/** Check if current place is valid
	 * @param input
	 * @param lv
	 * @return
	 */
	private boolean isValidPlace(Input input, Level lv) {
		double x = input.getMouseX();
		double y = input.getMouseY();
		return !lv.getMap().hasProperty((int)x, (int)y, "blocked") && 
				!lv.isInSpriteBoundingBox(x, y) && 
				y > this.getImage().getHeight() + placingImg.getImage().getHeight();
	}
	
	/**  A copy of the panel item image is rendered at the user¡¯s cursor 
	 * so that they can have a visual indicator of where the tower is to 
	 * be placed.
	 * 
	 * @param item
	 * @param input
	 */
	private void placing(BuyPanelItem item, Input input) {
		double x = input.getMouseX();
		double y = input.getMouseY();
		item.getImage().draw(x, y);
	}

	

}
