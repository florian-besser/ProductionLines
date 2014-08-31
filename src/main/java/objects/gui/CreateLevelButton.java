package objects.gui;

import helpers.Texture;


public class CreateLevelButton extends MenuButton {

	public CreateLevelButton(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.texture = Texture.BUTTON_CREATE_LEVEL;
	}

	@Override
	public void click() {
		// TODO Auto-generated method stub

	}

}
