package states;

import objects.scenery.ScenerySquare;
import model.Model;


public class LevelEditorState extends GameState {

	private int xDimension;
	private int yDimension;

	public LevelEditorState(int xDimension, int yDimension) {
		this.xDimension = xDimension;
		this.yDimension = yDimension;
	}

	@Override
	public void activate() {
		Model.clearGuiObjects();
		Model.clearGameObjects();
		for (int x = 0; x < xDimension; x++) {
			for (int y = 0; y < yDimension; y++) {
				Model.addSceneryObject(new ScenerySquare(x, y));
			}
		}
	}

}
