package objects.gui;

import javax.media.opengl.GL2;

import objects.gui.anchorpoints.AnchorPoint;

public abstract class GuiObject {
	
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	String id;
	protected AnchorPoint anchorPoint;
	
	public GuiObject(String id, AnchorPoint anchorPoint, int x, int y, int width, int height) {
		this.id = id;
		this.anchorPoint = anchorPoint;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void render(GL2 gl) {
		preRender(gl);
		internalRender(gl);
	}
	
	public void preRender(GL2 gl) {
		gl.glLoadIdentity();
		anchorPoint.setTranslation(gl);
	}
	
	protected abstract void internalRender(GL2 gl);
		
	public int getX() {
		return x + anchorPoint.getXComponent();
	}
	
	public int getY() {
		return y + anchorPoint.getYComponent();
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
