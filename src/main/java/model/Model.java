package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.sun.javafx.geom.Vec3d;

import objects.GameObject;

public class Model {

	// Locking
	private static ReadWriteLock globalLock = new ReentrantReadWriteLock();
	private static Lock readLock = globalLock.readLock();
	private static Lock writeLock = globalLock.writeLock();

	// Store
	private static Collection<GameObject> objects = new ArrayList<GameObject>();
	private static Vec3d camera = new Vec3d(0, 5, 0);
	private static Vec3d cameraDirection = new Vec3d(1, -5, 0);
	private static Vec3d cameraMovement = new Vec3d(0, 0, 0);
	
	// State
	private static GameState state = GameState.MENU;

	public static void add(GameObject object) {
		writeLock.lock();
		try{
			objects.add(object);
		} finally {
			writeLock.unlock();
		}
	}

	public static void remove(GameObject object) {
		writeLock.lock();
		try{
			objects.remove(object);
		} finally {
			writeLock.unlock();
		}
	}

	public static Collection<GameObject> getObjects() {
		readLock.lock();
		try {
			return new ArrayList<GameObject>(objects);
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
