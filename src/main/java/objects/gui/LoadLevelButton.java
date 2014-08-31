package objects.gui;

import helpers.Texture;


public class LoadLevelButton extends MenuButton {

	public LoadLevelButton(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.texture = Texture.BUTTON_LOAD_LEVEL;
	}

	@Override
	public void click() {
		// TODO Auto-generated method stub

	}

}
