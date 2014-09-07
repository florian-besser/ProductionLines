package objects.scenery.sceneryModels;

public abstract class SceneryModel {

	private float[] points;
	private float[] texCoords;
	private int[] indexes;

	public float[] getPoints() {
		return points;
	}

	public void setPoints(float[] points) {
		this.points = points;
	}

	public float[] getTexCoords() {
		return texCoords;
	}

	public void setTexCoords(float[] texCoords) {
		this.texCoords = texCoords;
	}

	public int[] getIndexes() {
		return indexes;
	}

	public void setIndexes(int[] indexes) {
		this.indexes = indexes;
	}
	
}
