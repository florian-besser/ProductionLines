package objects.gui;

import javax.media.opengl.GL2;

import objects.gui.anchorpoints.CenterAnchor;

public class EmptyGuiObject extends GuiObject {

	public EmptyGuiObject() {
		super("", new CenterAnchor(), 0, 0, 0, 0);
	}

	@Override
	public void internalRender(GL2 gl) {

	}

	@Override
	public void click(int x, int y) {
		
	}

}
