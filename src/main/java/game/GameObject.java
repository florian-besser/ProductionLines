package game;

import javax.media.opengl.GL2;

public abstract class GameObject {
	
	public abstract void update();

	public abstract void render(GL2 gl);
}
