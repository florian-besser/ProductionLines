package objects.gui;

import javax.media.opengl.GL2;

import objects.general.GeneralObject;

public abstract class GuiObject extends GeneralObject {
	
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	
	public GuiObject(int x, int y, int width, int height) {
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
}
