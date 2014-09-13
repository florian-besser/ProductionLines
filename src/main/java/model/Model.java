package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import objects.game.EmptyGameObject;
import objects.game.GameObject;
import objects.gui.EmptyGuiObject;
import objects.gui.GuiObject;
import objects.scenery.SceneryObject;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import states.AbstractGameState;
import states.MainMenuState;

public class Model {

	// Locking
	private static ReadWriteLock globalLock = new ReentrantReadWriteLock();
	private static Lock readLock = globalLock.readLock();
	private static Lock writeLock = globalLock.writeLock();

	// Store
	private static SceneryObject[][] sceneryObjects;
	private static Collection<SceneryObject> updatedSceneryObjects = new ArrayList<SceneryObject>();
	private static boolean redrawScenery = false;
	private static Collection<GameObject> gameObjects = new ArrayList<GameObject>();
	private static Collection<GuiObject> guiObjects = new ArrayList<GuiObject>();
	private static Vector3D camera = new Vector3D(0, 10, 0);
	private static Vector3D cameraDirection = new Vector3D(0, -5, -1);
	private static Vector3D cameraMovement = new Vector3D(0, 0, 0);
	private static int mouseX;
	private static int mouseY;
	private static AbstractGameState state = new MainMenuState();
	public static final double NEAR_CLIPPING = 1.0;
	public static final double FAR_CLIPPING = 1000.0;
	private static final double CAMERA_OFFSET_NEAR = 10;
	private static final double CAMERA_OFFSET_FAR = 100;
	private static GuiObject focusGuiObject = new EmptyGuiObject();

	public static void relesaseWriteLock() {
		writeLock.unlock();
	}

	public static void getWriteLock() {
		writeLock.lock();
	}

	public static void releaseReadLock() {
		readLock.unlock();
	}

	public static void getReadLock() {
		readLock.lock();
	}

	public static void addGameObject(GameObject object) {
		gameObjects.add(object);
	}

	public static void addGuiObject(GuiObject object) {
		guiObjects.add(object);
	}

	public static void addSceneryObject(SceneryObject object) {
		int x = object.getX();
		int y = object.getY();
		if (x >= 0 && x < sceneryObjects.length && y >= 0 && y < sceneryObjects.length) {
			sceneryObjects[x][y] = object;
			if (!redrawScenery) {
				updatedSceneryObjects.add(object);
			}
		}
	}

	public static void removeGameObject(GameObject object) {
		gameObjects.remove(object);
	}

	public static void removeGuiObject(GuiObject object) {
		guiObjects.remove(object);
	}

	public static void removeUpdatedSceneryObjects(SceneryObject object) {
		updatedSceneryObjects.remove(object);
	}

	public static void clearGuiObjects() {
		guiObjects.clear();
	}

	public static void clearGameObjects() {
		gameObjects.clear();
	}

	public static void clearSceneryObjects(int x, int y) {
		sceneryObjects = new SceneryObject[x][y];
		redrawScenery = true;
	}

	public static void clearUpdatedSceneryObjects() {
		updatedSceneryObjects.clear();
	}

	public static Collection<GameObject> getGameObjects() {
		return new ArrayList<GameObject>(gameObjects);
	}

	public static Collection<GuiObject> getGuiObjects() {
		return new ArrayList<GuiObject>(guiObjects);
	}

	public static SceneryObject[] getSceneryObjects() {
		return convertTo1DArray(sceneryObjects);
	}

	public static Collection<SceneryObject> getUpdatedSceneryObjects() {
		return new ArrayList<SceneryObject>(updatedSceneryObjects);
	}

	public static SceneryObject[] convertTo1DArray(SceneryObject[][] objects) {
		SceneryObject[] oneDArray = new SceneryObject[objects.length * objects[0].length];
		for (int i = 0; i < objects.length; i++) {
			for (int s = 0; s < objects[0].length; s++) {
				oneDArray[(i * objects[0].length) + s] = objects[i][s];
			}
		}
		return oneDArray;
	}

	public static boolean isRedrawScenery() {
		return redrawScenery;
	}

	public static void setRedrawNecessary() {
		redrawScenery = true;
	}

	public static void setNoRedrawNecessary() {
		redrawScenery = false;
	}

	public static void setState(AbstractGameState s) {
		state = s;
		s.activate();
	}

	public static AbstractGameState getState() {
		return state;
	}

	public static void setCameraMovementX(int x) {
		cameraMovement = new Vector3D(x, cameraMovement.getY(), cameraMovement.getZ());
	}

	public static void setCameraMovementY(int y) {
		cameraMovement = new Vector3D(cameraMovement.getX(), y, cameraMovement.getZ());
	}

	public static void setCameraMovementZ(int z) {
		cameraMovement = new Vector3D(cameraMovement.getX(), cameraMovement.getY(), z);
	}

	public static void addCameraY(double preciseWheelRotation) {
		moveCamera(new Vector3D(0, preciseWheelRotation, 0));
	}

	public static Vector3D getCamera() {
		return new Vector3D(camera.toArray());
	}

	public static Vector3D getCameraDirection() {
		return new Vector3D(cameraDirection.toArray());
	}

	public static Vector3D getCameraMovement() {
		// System.out.println("Camera moving in " + movement.x + " " + movement.y + " " + movement.z + ", delta is " + deltaInSeconds);
		return new Vector3D(cameraMovement.toArray());
	}

	public static void moveCamera(Vector3D cameraMovement) {
		Vector3D newCamera = camera.add(cameraMovement);
		if (newCamera.getY() < NEAR_CLIPPING + CAMERA_OFFSET_NEAR) {
			newCamera = new Vector3D(newCamera.getX(), NEAR_CLIPPING + CAMERA_OFFSET_NEAR, newCamera.getZ());
		} else if (newCamera.getY() > FAR_CLIPPING - CAMERA_OFFSET_FAR) {
			newCamera = new Vector3D(newCamera.getX(), FAR_CLIPPING - CAMERA_OFFSET_FAR, newCamera.getZ());
		}
		camera = newCamera;
		// System.out.println("Camera is now at " + camera.getX() + " " + camera.getY() + " " + camera.getZ());
	}

	public static GuiObject findGuiObject(int x, int y) {
		for (GuiObject guiObject : getGuiObjects()) {
			int objectX = guiObject.getX();
			int objectY = guiObject.getY();
			int objectWidth = guiObject.getWidth();
			int objectHeight = guiObject.getHeight();
			if (objectX <= x && x <= objectX + objectWidth && objectY <= y && y <= objectY + objectHeight) {
				return guiObject;
			}
		}

		return new EmptyGuiObject();
	}

	public static GameObject findGameObject(int x, int y) {
		for (GameObject gameObject : getGameObjects()) {
			int objectX = gameObject.getX();
			int objectY = gameObject.getY();
			int objectWidth = gameObject.getWidth();
			int objectHeight = gameObject.getHeight();
			if (objectX <= x && x < objectX + objectWidth && objectY <= y && y < objectY + objectHeight) {
				return gameObject;
			}
		}

		return new EmptyGameObject();
	}

	public static GuiObject findGuiObject(String string) {
		for (GuiObject guiObject : getGuiObjects()) {
			if (guiObject.getId().equals(string)) {
				return guiObject;
			}
		}
		return new EmptyGuiObject();
	}

	public static int getAbsoluteMouseX() {
		return mouseX;
	}

	public static int getAbsoluteMouseY() {
		return mouseY;
	}

	public static void setAbsoluteMouseX(int x) {
		mouseX = x;
	}

	public static void setAbsoluteMouseY(int y) {
		mouseY = y;
	}

	public static int getPlayfieldDimensionX() {
		return sceneryObjects.length;
	}

	public static int getPlayfieldDimensionY() {
		return sceneryObjects[0].length;
	}

	public static void setFocusGuiObject(GuiObject obj) {
		focusGuiObject = obj;
	}

	public static GuiObject getFocusGuiObject() {
		return focusGuiObject;
	}

}
