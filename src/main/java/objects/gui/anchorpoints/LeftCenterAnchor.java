package objects.gui.anchorpoints;

import javax.media.opengl.GL2;

import view.View;

public class LeftCenterAnchor extends AnchorPoint {

	@Override
	public void setTranslation(GL2 gl) {
		gl.glTranslated(0, View.getScreenHeight() / 2, 0);
	}

	@Override
	public int getXComponent() {
		return 0;
	}

	@Override
	public int getYComponent() {
		return View.getScreenHeight() / 2;
	}

}
