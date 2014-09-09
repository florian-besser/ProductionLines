package objects.game;

import javax.media.opengl.GL2;

public class EmptyGameObject extends GameObject {

	public EmptyGameObject() {
		super(0, 0, 0, 0);
	}

	@Override
	protected void safeUpdateGraphics(double delta) {

	}

	@Override
	protected void safeRender(GL2 gl) {

	}

	@Override
	protected void safeTick() {

	}

	@Override
	public void click() {

	}

}
