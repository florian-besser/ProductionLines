package objects.gui.anchorpoints;

import javax.media.opengl.GL2;

import view.View;

public class TopCenterAnchor extends AnchorPoint {

	@Override
	public void setTranslation(GL2 gl) {
		gl.glTranslated(View.getScreenWidth() / 2, 0, 0);
	}

	@Override
	public int getXComponent() {
		return View.getScreenWidth() / 2;
	}

	@Override
	public int getYComponent() {
		return 0;
	}

}
