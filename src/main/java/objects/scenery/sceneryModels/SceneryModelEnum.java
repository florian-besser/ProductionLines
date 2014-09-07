package objects.scenery.sceneryModels;

public enum SceneryModelEnum {

	SQUARE(new SquareModel());
	
	private SceneryModel model;

	private SceneryModelEnum(SceneryModel model) {
		this.model = model;
	}

	public float[] getPoints() {
		return model.getPoints();
	}

	public int[] getIndexes() {
		return model.getIndexes();
	}

	public float[] getTexCoords() {
		return model.getTexCoords();
	}

}
