package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import objects.GameObject;

public class Model {

	// Locking
	private static ReadWriteLock globalLock = new ReentrantReadWriteLock();
	private static Lock readLock = globalLock.readLock();
	private static Lock writeLock = globalLock.writeLock();

	// Store
	private static Collection<GameObject> objects = new ArrayList<GameObject>();
	
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
			return objects;
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
}
