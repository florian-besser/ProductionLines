package objects.gui.menu;

import helpers.Texture;
import model.Model;
import states.Game;

public class StartButton extends MenuButton {

	public StartButton(int x, int y, int width, int height) {
		super("StartButton", x, y, width, height);
		this.texture = Texture.BUTTON_START;
	}

	@Override
	public void click(int x, int y) {
		Model.setState(new Game());
	}

}
