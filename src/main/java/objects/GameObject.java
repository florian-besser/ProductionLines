package objects;

import javax.media.opengl.GL2;

public abstract class GameObject {
	
	public abstract void updateGraphics();

	public abstract void render(GL2 gl);

	public abstract void tick();
}
