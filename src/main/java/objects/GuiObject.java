package objects;

import javax.media.opengl.GL2;

public abstract class GuiObject {
	
	protected int x;
	protected int y;
	
	public GuiObject(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public abstract void render(GL2 gl);
		
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}	
}
