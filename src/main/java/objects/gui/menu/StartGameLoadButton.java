package objects.gui.menu;

import states.GameLoadState;
import helpers.Texture;
import model.Model;

public class StartGameLoadButton extends MenuButton {

	public StartGameLoadButton(int x, int y, int width, int height) {
		super("StartButton", x, y, width, height);
		this.texture = Texture.BUTTON_START;
	}

	@Override
	public void click(int x, int y) {
		Model.setState(new GameLoadState());
	}

}
