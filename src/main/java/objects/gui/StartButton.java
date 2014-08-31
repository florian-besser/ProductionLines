package objects.gui;

import helpers.Texture;

public class StartButton extends MenuButton {

	public StartButton(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.texture = Texture.BUTTON_START;
	}

	@Override
	public void click() {
		// TODO Auto-generated method stub

	}

}
