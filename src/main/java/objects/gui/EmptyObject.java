package objects.gui;

import javax.media.opengl.GL2;

import objects.gui.anchorpoints.CenterAnchor;

public class EmptyObject extends GuiObject {

	public EmptyObject() {
		super("", new CenterAnchor(), 0, 0, 0, 0);
	}

	@Override
	public void internalRender(GL2 gl) {

	}

	@Override
	public void click(int x, int y) {
		
	}

}
