package objects.gui.menu;

import helpers.Texture;


public class LoadLevelButton extends MenuButton {

	public LoadLevelButton(int x, int y, int width, int height) {
		super("LoadLevelButton", x, y, width, height);
		this.texture = Texture.BUTTON_LOAD_LEVEL;
	}

	@Override
	public void click(int x, int y) {
		// TODO Auto-generated method stub

	}

}
