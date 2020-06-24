import bagel.DrawOptions;
import bagel.Image;
import bagel.Input;
import bagel.util.Point;
import bagel.util.Rectangle;

/** Basic abstract object in this game
 * Update needs to be implemented
 */
public abstract class Sprite {

	private Image object;
	private Point location;

	private double rotateAngle;
	private boolean draw;


	public Sprite(String image) {
		object = new Image(image);
		draw = true;
		rotateAngle = 0;
		
	}
	
	/** Draw the object with a rotating angle
	 * 
	 */
	public void draw() {
		if (!draw) {
			return;
		}
		DrawOptions d = new DrawOptions();
		d.setRotation(rotateAngle);
		object.draw(location.x, location.y, d);
	}
	
	/** Update each frame
	 * @param timescale
	 */
	public abstract void update(Level lv, Input input);
	
	/** Getters and Setters */
	
	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}
	
	public void setDraw(boolean draw) {
		this.draw = draw;
	}

	public boolean isDraw() {
		return draw;
	}


	public void setRotateAngle(double rotateAngle) {
		this.rotateAngle = rotateAngle;
	}

	public double getRotateAngle() {
		return rotateAngle;
	}
	
	public Rectangle getBoundingBox() {
		return object.getBoundingBoxAt(location);
	}

	public Image getImage() {
		return object;
	}
	
	

}
