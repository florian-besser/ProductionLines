package objects;

import javax.media.opengl.GL2;

public abstract class GuiObject {
	
	protected double x;
	protected double y;
	protected double width;
	protected double height;
	
	public GuiObject(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public abstract void render(GL2 gl);
		
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}	
}
