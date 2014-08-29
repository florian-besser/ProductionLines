package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.sun.javafx.geom.Vec3d;

import objects.GameObject;
import objects.GuiObject;

public class Model {

	// Locking
	private static ReadWriteLock globalLock = new ReentrantReadWriteLock();
	private static Lock readLock = globalLock.readLock();
	private static Lock writeLock = globalLock.writeLock();

	// Store
	private static Collection<GameObject> gameObjects = new ArrayList<GameObject>();
	private static Collection<GuiObject> guiObjects = new ArrayList<GuiObject>();
	private static Vec3d camera = new Vec3d(0, 5, 0);
	private static Vec3d cameraDirection = new Vec3d(1, -5, 0);
	private static Vec3d cameraMovement = new Vec3d(0, 0, 0);
	
	// State
	private static GameState state = GameState.MENU;

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

	public static GameState getState() {
		return state;
	}
	public static void setState(GameState s) {
		state = s;
	}

	public static void setCameraMovementX(int x) {
		cameraMovement.x = x;
	}
	public static void setCameraMovementY(int y) {
		cameraMovement.y = y;
	}
	public static void setCameraMovementZ(int z) {
		cameraMovement.z = z;
	}

	public static Vec3d getCamera() {
		return new Vec3d(camera);
	}

	public static Vec3d getCameraDirection() {
		return new Vec3d(cameraDirection);
	}

	public static Vec3d getCameraMovement() {
		//System.out.println("Camera moving in " + movement.x + " " + movement.y + " " + movement.z + ", delta is " + deltaInSeconds);
		return new Vec3d(cameraMovement);
	}

	public static void moveCamera(Vec3d cameraMovement) {
		camera.add(cameraMovement);
		//System.out.println("Camera is now at " + camera.x + " " + camera.y + " " + camera.z);
	}
}
