package objects.game;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.media.opengl.GL2;

import objects.general.GeneralObject;

public abstract class GameObject extends GeneralObject {
	
	private static Lock lock = new ReentrantLock();
	protected double x;
	protected double y;
	
	public GameObject(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void updateGraphicsAndRender(double deltaInSeconds, GL2 gl) {
		lock.lock();
		try {
			safeUpdateGraphics(deltaInSeconds);
			safeRender(gl);
		} finally {
			lock.unlock();
		}
	}
	
	public void tick() {
		lock.lock();
		try {
			safeTick();
		} finally {
			lock.unlock();
		}
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	protected abstract void safeUpdateGraphics(double delta);

	protected abstract void safeRender(GL2 gl);

	protected abstract void safeTick();
}
