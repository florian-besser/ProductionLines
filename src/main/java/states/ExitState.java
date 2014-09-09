package states;

import javax.media.opengl.GL2;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class ExitState extends GameState {

	@Override
	public void activate() {
		System.exit(0);
	}

	@Override
	public void render(Vector3D pos, GL2 gl) {

	}

	@Override
	public void click() {

	}

}
