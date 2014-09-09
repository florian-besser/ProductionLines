package objects.game;

import helpers.Texture;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.media.opengl.GL2;

public abstract class GameObject {

	private static Lock lock = new ReentrantLock();
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected Texture texture = Texture.DEBUG_SMALL;

	public GameObject(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
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

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Texture getTexture() {
		return texture;
	}

	protected abstract void safeUpdateGraphics(double delta);

	protected abstract void safeRender(GL2 gl);

	protected abstract void safeTick();

	public abstract void click();
}
