package view;

import javax.media.opengl.*;

import model.Model;
import objects.GameObject;

public class View implements GLEventListener {

	// Looping
	long timer = System.currentTimeMillis();
	int frames = 0;

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		for (GameObject object : Model.getObjects()) {
			object.updateGraphics();
			object.render(gl);
		}

		logFps();
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

	private void logFps() {
		frames++;
		if (System.currentTimeMillis() - timer > 1000) {
			timer += 1000;
			System.out.println("FPS: " + frames);
			frames = 0;
		}
	}
}
