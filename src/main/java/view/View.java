package view;

import java.nio.FloatBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.fixedfunc.GLMatrixFunc;
import javax.media.opengl.glu.gl2.GLUgl2;

import model.Model;
import objects.game.GameObject;
import objects.gui.GuiObject;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import states.AbstractGameState;

import com.jogamp.opengl.util.gl2.GLUT;

public class View implements GLEventListener {

	// Looping
	private long timer = System.currentTimeMillis();
	private double lastTime = System.nanoTime();
	private double deltaInSeconds = 0;
	private int frames = 0;
	private GLUgl2 glu = new GLUgl2();
	private GLUT glut = new GLUT();
	private SceneryObjectRenderer sceneryObjectRenderer = new SceneryObjectRenderer();

	// Screen
	private static int screenWidth;
	private static int screenHeight;
	// ANGLES ARE IN DEGREES 0-2Pi
	private static final double VIEW_ANGLE = Math.PI / 4;
	private static final double RAD2ANG = 180.0 / Math.PI;
	private static final Vector3D up = new Vector3D(0, 1, 0);
	private double modelViewMatrix[] = new double[16];
	private double projectionMatrix[] = new double[16];
	private int viewport[] = new int[4];

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

		/*
		 * gl.glEnableClientState(GL2.GL_LINE_LOOP); gl.glEnableClientState(GL2.GL_VERTEX_ARRAY); gl.glEnableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
		 * gl.glEnable(GL2.GL_TEXTURE_2D);
		 */

		gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
		// gl.glEnableClientState(GL2.GL_NORMAL_ARRAY); //Crashes
		gl.glEnableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
		gl.glEnable(GL.GL_BLEND);
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

		double now = System.nanoTime();
		deltaInSeconds = (now - lastTime) / 1000000000;
		lastTime = now;

		moveCamera(deltaInSeconds);

		renderSceneryObjects(gl);

		render3dObjects(gl);

		AbstractGameState state = Model.getState();
		Vector3D oglPos = getOGLPos(Model.getAbsoluteMouseX(), Model.getAbsoluteMouseY(), gl);
		state.render(oglPos, gl);

		renderDebugWireCube(gl);

		render2dObjects(gl);

		logFps();

		gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
		// gl.glDisableClientState(GL2.GL_NORMAL_ARRAY);
		gl.glDisableClientState(GL2.GL_TEXTURE_COORD_ARRAY);

		/*
		 * gl.glDisableClientState(GL2.GL_LINE_LOOP); gl.glDisable(GL2.GL_TEXTURE_2D); gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
		 * gl.glDisableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
		 * 
		 * gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, 0); gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);
		 */
	}

	private void renderSceneryObjects(GL2 gl) {
		init3d(gl);

		Model.getWriteLock();
		try {
			if (Model.isRedrawScenery()) {
				sceneryObjectRenderer.transferSceneryToGpu(gl);
			} else {
				sceneryObjectRenderer.updateSceneryOnGpu(gl);
			}
		} finally {
			Model.relesaseWriteLock();
		}

		// long start = System.nanoTime();
		sceneryObjectRenderer.renderScenery(gl);
		// long finish = System.nanoTime();
		// System.out.println("Used " + (finish - start)/1000000 + " ms for Scenery rendering.");
	}

	private void render3dObjects(GL2 gl) {
		init3d(gl);

		// long start = System.nanoTime();
		for (GameObject object : Model.getGameObjects()) {
			object.updateGraphicsAndRender(deltaInSeconds, gl);
		}
		// long finish = System.nanoTime();
		// System.out.println("Used " + (finish - start)/1000000 + " ms for 3D rendering.");

	}

	private void render2dObjects(GL2 gl) {
		init2d(gl);
		for (GuiObject object : Model.getGuiObjects()) {
			object.render(gl);
		}
	}

	private void renderDebugWireCube(GL2 gl) {
		gl.glColor4d(1, 1, 1, 1);
		glut.glutWireCube((float) 1.0);
		gl.glFlush();
	}

	private void moveCamera(double deltaInSeconds) {
		Vector3D movement = Model.getCameraMovement();
		movement = movement.scalarMultiply(deltaInSeconds);
		// The higher the camera, the faster you move
		movement = movement.scalarMultiply(Model.getCamera().getY());
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

		screenWidth = width;
		screenHeight = height;
	}

	private void init3d(GL2 gl) {
		float h = ((float) screenWidth) / screenHeight;

		gl.glViewport(0, 0, screenWidth, screenHeight);
		gl.glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);

		gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
		gl.glGetDoublev(GL2.GL_MODELVIEW_MATRIX, modelViewMatrix, 0);

		gl.glLoadIdentity();
		glu.gluPerspective(VIEW_ANGLE * RAD2ANG, h, Model.NEAR_CLIPPING, Model.FAR_CLIPPING);
		gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
		gl.glGetDoublev(GL2.GL_PROJECTION_MATRIX, projectionMatrix, 0);
		gl.glLoadIdentity();
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glDepthFunc(GL.GL_LEQUAL);

		Vector3D camera = Model.getCamera();
		Vector3D cameraDirection = Model.getCameraDirection();
		glu.gluLookAt(camera.getX(), camera.getY(), camera.getZ(), camera.getX() + cameraDirection.getX(), camera.getY() + cameraDirection.getY(),
				camera.getZ() + cameraDirection.getZ(), up.getX(), up.getY(), up.getZ());
	}

	private void init2d(GL2 gl) {
		gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
		gl.glLoadIdentity();

		glu.gluOrtho2D(0.0f, screenWidth, screenHeight, 0.0f);

		gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
		gl.glLoadIdentity();

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

	public static int getScreenWidth() {
		return screenWidth;
	}

	public static int getScreenHeight() {
		return screenHeight;
	}

	private Vector3D getOGLPos(int x, int y, GL2 gl) {
		int realY = 0;// GL y coord pos
		double woorldCoordinates[] = new double[4];

		realY = viewport[3] - y;

		FloatBuffer winZ = FloatBuffer.allocate(1);
		gl.glReadPixels(x, realY, 1, 1, GL2.GL_DEPTH_COMPONENT, GL.GL_FLOAT, winZ);

		// System.out.println("Coordinates at cursor are (" + x + ", " + realy);
		glu.gluUnProject((double) x, (double) realY, winZ.get(0), modelViewMatrix, 0, projectionMatrix, 0, viewport, 0, woorldCoordinates, 0);
		// System.out.println("World coords at z=" + winZ.get(0) + " are (" + wcoord[0] + ", " + wcoord[1] + ", " + wcoord[2] + ")");

		return new Vector3D(woorldCoordinates[0], woorldCoordinates[1], woorldCoordinates[2]);
	}
}
