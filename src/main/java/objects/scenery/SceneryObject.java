package objects.scenery;

import helpers.Texture;
import objects.scenery.sceneryModels.SceneryModelEnum;

public abstract class SceneryObject {

	protected int x;
	protected int y;
	protected int width;
	protected int height;

	protected SceneryModelEnum model;
	protected Texture texture;

	public SceneryObject(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public float[] getPoints() {
		return model.getPoints();
	}

	public int[] getIndexes() {
		return model.getIndexes();
	}

	public float[] getTextCoords() {
		return model.getTexCoords();
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

}
