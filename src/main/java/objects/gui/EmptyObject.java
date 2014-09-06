package objects.gui;

import javax.media.opengl.GL2;


public class EmptyObject extends GuiObject {

	public EmptyObject() {
		super("", 0, 0, 0, 0);
	}

	@Override
	public void render(GL2 gl) {

	}

	@Override
	public void click(int x, int y) {
		
	}

}
