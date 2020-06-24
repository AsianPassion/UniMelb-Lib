import bagel.Input;

/**
 * Super tank item in the buy panel
 *
 */
public class SuperTankPanelItem extends BuyPanelItem {
	
	private static final String RES = "res/images/supertank.png";
	private static final int ITEM_SIZE = 64;
	private static final int GAP = 100;
	public static final int PRICE = 600;

	public SuperTankPanelItem() {
		super(RES, ITEM_SIZE + GAP, ITEM_SIZE, PRICE);
	}

	@Override
	public void update(Level lv, Input input) {
		// TODO Auto-generated method stub
		
	}

}
