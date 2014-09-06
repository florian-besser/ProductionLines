package objects.gui;

import javax.media.opengl.GL2;

public abstract class GuiObject {
	
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	String id;
	
	public GuiObject(String id, int x, int y, int width, int height) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public abstract void render(GL2 gl);
		
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}	

	public int getHeight() {
		return height;
	}
	
	public abstract void click(int x, int y);

	public String getId() {
		return id;
	}
}
