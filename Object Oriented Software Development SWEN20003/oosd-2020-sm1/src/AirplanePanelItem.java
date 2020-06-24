import bagel.Input;

/**
 * Airplane item in the buy panel
 *
 */
public class AirplanePanelItem extends BuyPanelItem {
	
	private static final String RES = "res/images/airsupport.png";
	private static final int ITEM_SIZE = 64;
	private static final int GAP = 100;
	public static final int PRICE = 500;

	public AirplanePanelItem() {
		super(RES, ITEM_SIZE + GAP * 2, ITEM_SIZE, PRICE);
	}

	@Override
	public void update(Level lv, Input input) {
		// TODO Auto-generated method stub
		
	}

}
