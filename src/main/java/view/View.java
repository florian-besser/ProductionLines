package view;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.fixedfunc.GLMatrixFunc;
import javax.media.opengl.glu.gl2.GLUgl2;

import model.Model;
import objects.GameObject;
import objects.GuiObject;

import com.jogamp.opengl.util.gl2.GLUT;
import com.sun.javafx.geom.Vec3d;

public class View implements GLEventListener {

	// Looping
	private long timer = System.currentTimeMillis();
	private double lastTime = System.nanoTime();
	private double deltaInSeconds = 0;
	private int frames = 0;
	private GLUgl2 glu = new GLUgl2();
	private GLUT glut = new GLUT();

	//Screen
	private int SCREEN_WIDTH, SCREEN_HEIGHT;
	// ANGLES ARE IN DEGREES 0-2Pi
	private static final double VIEW_ANGLE = Math.PI / 4;
	private static final double RAD2ANG = 180.0 / Math.PI;
	private static final double NEAR_CLIPPING = 1.0;
	private static final double FAR_CLIPPING = 1000.0;
	private static final Vec3d up = new Vec3d(0, 1, 0);


	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
		/*gl.glEnableClientState(GL2.GL_LINE_LOOP);
		gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
		gl.glEnable(GL2.GL_TEXTURE_2D);*/

		double now = System.nanoTime();
		deltaInSeconds = (now - lastTime) / 1000000000;
		lastTime = now;

		moveCamera(deltaInSeconds);

		render3dObjects(gl);
		
		renderDebugWireCobe(gl);
		
		render2dObjects(gl);

		logFps();
		
		/*gl.glDisableClientState(GL2.GL_LINE_LOOP);
		gl.glDisable(GL2.GL_TEXTURE_2D);
		gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
		
		gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, 0);
		gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);*/
	}

	private void render3dObjects(GL2 gl) {
		init3d(gl);
		Vec3d camera = Model.getCamera();
		Vec3d cameraDirection = Model.getCameraDirection();
		glu.gluLookAt(camera.x, camera.y, camera.z, camera.x + cameraDirection.x, camera.z + cameraDirection.y, camera.z + cameraDirection.z, up.x, up.y, up.z);

		for (GameObject object : Model.getGameObjects()) {
			object.updateGraphicsAndRender(deltaInSeconds, gl);
		}
	}

	private void render2dObjects(GL2 gl) {
		init2d(gl);
		for (GuiObject object : Model.getGuiObjects()) {
			object.render(gl);
		}
	}
	
	private void renderDebugWireCobe(GL2 gl) {
		gl.glColor3d (1.0, 1.0, 1.0);
		glut.glutWireCube((float) 1.0);
		gl.glFlush ();
	}
	
	private void moveCamera(double deltaInSeconds) {
		Vec3d movement = Model.getCameraMovement();
		movement.mul(deltaInSeconds);
		Model.moveCamera(movement);
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
	}

	@Override
	public void init(GLAutoDrawable drawable) {

	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		System.out.println("reshape called");

		SCREEN_WIDTH = width;
		SCREEN_HEIGHT = height;
	}

	private void init3d(GL2 gl) {
		float h = ((float)SCREEN_WIDTH) / SCREEN_HEIGHT;

		gl.glViewport(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(VIEW_ANGLE * RAD2ANG, h, NEAR_CLIPPING, FAR_CLIPPING);
		gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glDepthFunc(GL.GL_LEQUAL);

	}
	
	private void init2d(GL2 gl) {
		gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
		gl.glLoadIdentity();

	    glu.gluOrtho2D(0.0f, SCREEN_WIDTH, SCREEN_HEIGHT, 0.0f);

	    gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
	    gl.glLoadIdentity();
	    gl.glTranslated(0.375, 0.375, 0.0);

	    gl.glDisable(GL.GL_DEPTH_TEST);
		gl.glEnable(GL2.GL_TEXTURE_2D);
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
