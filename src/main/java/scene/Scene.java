package scene;


import java.util.ArrayList;
import java.util.Collection;

import javax.media.opengl.*;

import objects.GameObject;

public class Scene implements GLEventListener {

	private Collection<GameObject> objects = new ArrayList<GameObject>();
	
	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		for (GameObject object : objects) {
			object.update();
			object.render(gl);
		}
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
	}

	@Override
	public void init(GLAutoDrawable drawable) {
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
	}
	
	public void add(GameObject object) {
		objects.add(object);
	}
	
	public void remove(GameObject object) {
		objects.remove(object);
	}
}