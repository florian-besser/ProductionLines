package objects.scenery;

import helpers.Texture;
import objects.scenery.sceneryModels.SceneryModelEnum;

public class ScenerySquare extends SceneryObject {

	public ScenerySquare(int x, int y, Texture texture) {
		super(x, y, 1, 1);
		model = SceneryModelEnum.SQUARE;
		this.texture = texture;
	}

}
