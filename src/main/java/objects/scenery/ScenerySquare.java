package objects.scenery;

import objects.scenery.sceneryModels.SceneryModelEnum;

public class ScenerySquare extends SceneryObject {

	public ScenerySquare(int x, int y) {
		super(x, y, 1, 1);
		model = SceneryModelEnum.SQUARE;
	}
	
}
