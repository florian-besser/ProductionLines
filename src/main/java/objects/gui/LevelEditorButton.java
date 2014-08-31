package objects.gui;

import model.Model;
import states.LevelEditorLoadState;
import helpers.Texture;

public class LevelEditorButton extends MenuButton {

	public LevelEditorButton(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.texture = Texture.BUTTON_LEVEL_EDITOR;
	}

	@Override
	public void click() {
		Model.setState(new LevelEditorLoadState());
	}

}
