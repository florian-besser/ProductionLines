package objects.game.gameModels;

import javax.media.opengl.GL2;

public enum GameModelEnum {
	
	SQUARE(new SquareModel());
	
	int handlerId = -1;
	private GameModel model;
	
	private GameModelEnum(GameModel model) {
		this.model = model;
	}

	public int getVboHandlerId(GL2 gl) {
		return model.getVboHandlerId(gl);
	}
	
	public int getIboHandlerId(GL2 gl) {
		return model.getIboHandlerId(gl);
	}

	public int getIndexLength() {
		return model.getIndexLength();
	}
}
