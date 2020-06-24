import bagel.util.Point;

/** Item in buy panel
 *
 */
public abstract class BuyPanelItem extends Sprite {
	
	private int price;

	public BuyPanelItem(String image, double x, double y, int price) {
		super(image);
		this.setLocation(new Point(x, y));
		this.price = price;
	}
	
	public boolean intersects(double x, double y) {
		return this.getBoundingBox().intersects(new Point(x, y));
	}

	public int getPrice() {
		return price;
	}

}
