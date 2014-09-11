package objects.gui.menu;

import helpers.Texture;
import model.Model;
import states.GameState;

public class StartGameButton extends MenuButton {

	public StartGameButton(int x, int y, int width, int height) {
		super("StartGameButton", x, y, width, height);
		this.texture = Texture.BUTTON_START_GAME;
	}

	@Override
	public void click(int x, int y) {
		PanelSelectTexts ms = (PanelSelectTexts) Model.findGuiObject("existingMaps");
		String levelName = ms.getChosen();
		Model.setState(new GameState(levelName));
	}

}
