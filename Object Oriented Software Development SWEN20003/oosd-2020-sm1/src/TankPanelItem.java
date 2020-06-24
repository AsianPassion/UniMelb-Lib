import bagel.Input;

/**
 * Tank item in the buy panel
 *
 */
public class TankPanelItem extends BuyPanelItem {
	
	private static final String RES = "res/images/tank.png";
	private static final int ITEM_SIZE = 64;
	public static final int PRICE = 250;

	public TankPanelItem() {
		super(RES, ITEM_SIZE, ITEM_SIZE, PRICE);
	}

	@Override
	public void update(Level lv, Input input) {
		// TODO Auto-generated method stub
		
	}

}
