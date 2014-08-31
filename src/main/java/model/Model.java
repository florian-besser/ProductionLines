package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import objects.game.GameObject;
import objects.general.EmptyObject;
import objects.general.GeneralObject;
import objects.gui.GuiObject;
import states.GameState;

public class Model {

	// Locking
	private static ReadWriteLock globalLock = new ReentrantReadWriteLock();
	private static Lock readLock = globalLock.readLock();
	private static Lock writeLock = globalLock.writeLock();

	// Store
	private static Collection<GameObject> gameObjects = new ArrayList<GameObject>();
	private static Collection<GuiObject> guiObjects = new ArrayList<GuiObject>();
	private static Vector3D camera = new Vector3D(0, 5, 0);
	private static Vector3D cameraDirection = new Vector3D(1, -5, 0);
	private static Vector3D cameraMovement = new Vector3D(0, 0, 0);
	
	public static void addGameObject(GameObject object) {
		writeLock.lock();
		try{
			gameObjects.add(object);
		} finally {
			writeLock.unlock();
		}
	}

	public static void addGuiObject(GuiObject object) {
		writeLock.lock();
		try{
			guiObjects.add(object);
		} finally {
			writeLock.unlock();
		}
	}

	public static void removeGameObject(GameObject object) {
		writeLock.lock();
		try{
			gameObjects.remove(object);
		} finally {
			writeLock.unlock();
		}
	}

	public static void removeGuiObject(GuiObject object) {
		writeLock.lock();
		try{
			guiObjects.remove(object);
		} finally {
			writeLock.unlock();
		}
	}

	public static void clearGuiObjects() {
		writeLock.lock();
		try{
			guiObjects.clear();
		} finally {
			writeLock.unlock();
		}
	}
	public static Collection<GameObject> getGameObjects() {
		readLock.lock();
		try {
			return new ArrayList<GameObject>(gameObjects);
		} finally {
			readLock.unlock();
		}
	}

	public static Collection<GuiObject> getGuiObjects() {
		readLock.lock();
		try {
			return new ArrayList<GuiObject>(guiObjects);
		} finally {
			readLock.unlock();
		}
	}

	public static void setState(GameState s) {
		s.activate();
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

	public static Vector3D getCamera() {
		return new Vector3D(camera.toArray());
	}

	public static Vector3D getCameraDirection() {
		return new Vector3D(cameraDirection.toArray());
	}

	public static Vector3D getCameraMovement() {
		//System.out.println("Camera moving in " + movement.x + " " + movement.y + " " + movement.z + ", delta is " + deltaInSeconds);
		return new Vector3D(cameraMovement.toArray());
	}

	public static void moveCamera(Vector3D cameraMovement) {
		camera = camera.add(cameraMovement);
		//System.out.println("Camera is now at " + camera.x + " " + camera.y + " " + camera.z);
	}

	public static GeneralObject findObject(int x, int y) {
		for (GuiObject guiObject : getGuiObjects()) {
			int objectX = guiObject.getX();
			int objectY = guiObject.getY();
			int objectWidth = guiObject.getWidth();
			int objectHeight = guiObject.getHeight();
			if (objectX - objectWidth/2 <= x && x <= objectX + objectWidth/2
					&& objectY - objectHeight/2 <= y && y <= objectY + objectHeight/2) {
				return guiObject;
			}
		}
		
		//TODO: Game objects.
		
		return new EmptyObject();
	}
}
