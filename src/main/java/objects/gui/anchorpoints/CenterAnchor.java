package objects.gui.anchorpoints;

import javax.media.opengl.GL2;

import view.View;

public class CenterAnchor extends AnchorPoint {

	@Override
	public void setTranslation(GL2 gl) {
		gl.glTranslated(View.getScreenWidth()/2, View.getScreenHeight()/2, 0);
	}

	@Override
	public int getXComponent() {
		return View.getScreenWidth()/2;
	}

	@Override
	public int getYComponent() {
		return View.getScreenHeight()/2;
	}

}
