package states;

import javax.media.opengl.GL2;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public abstract class GameState {

	public abstract void activate();

	public abstract void render(Vector3D pos, GL2 gl);

}
