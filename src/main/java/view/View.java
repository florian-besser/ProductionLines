package view;

import helpers.GameModelLoader;
import helpers.Texture;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.fixedfunc.GLMatrixFunc;
import javax.media.opengl.glu.gl2.GLUgl2;

import model.Model;
import objects.game.GameObject;
import objects.gui.GuiObject;
import objects.scenery.SceneryObject;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import com.jogamp.opengl.util.gl2.GLUT;

public class View implements GLEventListener {

	// Looping
	private long timer = System.currentTimeMillis();
	private double lastTime = System.nanoTime();
	private double deltaInSeconds = 0;
	private int frames = 0;
	private GLUgl2 glu = new GLUgl2();
	private GLUT glut = new GLUT();
	private int sceneryVboHandler;
	private int sceneryIboHandler;
	private int indexesLength;

	// Screen
	private static int screenWidth;
	private static int screenHeight;
	// ANGLES ARE IN DEGREES 0-2Pi
	private static final double VIEW_ANGLE = Math.PI / 4;
	private static final double RAD2ANG = 180.0 / Math.PI;
	private static final Vector3D up = new Vector3D(0, 1, 0);

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

		double now = System.nanoTime();
		deltaInSeconds = (now - lastTime) / 1000000000;
		lastTime = now;

		moveCamera(deltaInSeconds);

		renderSceneryObjects(gl);

		render3dObjects(gl);

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

		if (Model.isRedrawScenery()) {
			int pointsLength = 0;
			int indexesLength = 0;
			int texCoordsLength = 0;
			for (SceneryObject sceneryObject : Model.getSceneryObjects()) {
				pointsLength += sceneryObject.getPoints().length;
				indexesLength += sceneryObject.getIndexes().length;
				texCoordsLength += sceneryObject.getTextCoords().length;
			}

			float[] points = new float[pointsLength];
			int pointIndex = 0;
			int[] indexes = new int[indexesLength];
			int indexesIndex = 0;
			float[] texCoords = new float[texCoordsLength];
			int texCoordsIndex = 0;

			System.out.println("Loading Scenery with " + points.length + " points, " + indexes.length + " indexes and " + texCoords.length + " texCoords.");

			for (SceneryObject sceneryObject : Model.getSceneryObjects()) {
				int i = 0;
				int baseIndex = pointIndex / 3;
				for (float point : sceneryObject.getPoints()) {
					if (i % 3 == 0) {
						point += sceneryObject.getX();
					} else if (i % 3 == 2) {
						point += sceneryObject.getY();
					}
					points[pointIndex] = point;
					pointIndex++;
					i++;
				}
				for (int index : sceneryObject.getIndexes()) {
					index += baseIndex;
					indexes[indexesIndex] = index;
					indexesIndex++;
				}
				for (float texCoord : sceneryObject.getTextCoords()) {
					texCoords[texCoordsIndex] = texCoord;
					texCoordsIndex++;
				}
			}

			int[] handlers = GameModelLoader.load(gl, points, indexes, texCoords);
			sceneryVboHandler = handlers[0];
			sceneryIboHandler = handlers[1];
			this.indexesLength = indexesLength;
			Model.setNoRedrawNecessary();
		}

		if (indexesLength > 0) {
			// long start = System.nanoTime();

			gl.glColor3d(1, 1, 1);

			gl.glBindTexture(GL.GL_TEXTURE_2D, Texture.DIRT_SMALL.getHandlerId(gl));
			gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, sceneryVboHandler);
			gl.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, sceneryIboHandler);

			gl.glVertexPointer(3, GL2.GL_FLOAT, 8 * 4, 0); // 3 Vertices
			gl.glNormalPointer(GL2.GL_FLOAT, 8 * 4, 3 * 4); // 3 Normals
			gl.glTexCoordPointer(2, GL2.GL_FLOAT, 8 * 4, 6 * 4); // 2 Texture Coordinates

			gl.glPushMatrix();

			gl.glDrawElements(GL2.GL_TRIANGLES, indexesLength, GL2.GL_UNSIGNED_INT, 0);

			gl.glPopMatrix();

			gl.glBindTexture(GL.GL_TEXTURE_2D, 0);
			gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, 0);
			gl.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, 0);

			// long finish = System.nanoTime();
			// System.out.println("Used " + (finish - start)/1000000 + " ms for Scenery rendering.");
		}
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
		gl.glColor3d(1.0, 1.0, 1.0);
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
		gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(VIEW_ANGLE * RAD2ANG, h, Model.NEAR_CLIPPING, Model.FAR_CLIPPING);
		gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glDepthFunc(GL.GL_LEQUAL);

		Vector3D camera = Model.getCamera();
		Vector3D cameraDirection = Model.getCameraDirection();
		glu.gluLookAt(camera.getX(), camera.getY(), camera.getZ(), camera.getX() + cameraDirection.getX(), camera.getZ() + cameraDirection.getY(),
				camera.getZ() + cameraDirection.getZ(), up.getX(), up.getY(), up.getZ());
	}

	private void init2d(GL2 gl) {
		gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
		gl.glLoadIdentity();

		glu.gluOrtho2D(0.0f, screenWidth, screenHeight, 0.0f);

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

	public static int getScreenWidth() {
		return screenWidth;
	}

	public static int getScreenHeight() {
		return screenHeight;
	}
}
