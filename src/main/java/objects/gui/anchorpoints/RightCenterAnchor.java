package objects.gui.anchorpoints;

import javax.media.opengl.GL2;

import view.View;

public class RightCenterAnchor extends AnchorPoint {

	@Override
	public void setTranslation(GL2 gl) {
		gl.glTranslated(View.getScreenWidth(), View.getScreenHeight() / 2, 0);
	}

	@Override
	public int getXComponent() {
		return View.getScreenWidth();
	}

	@Override
	public int getYComponent() {
		return View.getScreenHeight() / 2;
	}

}
