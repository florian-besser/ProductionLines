package objects.gui.menu;

import helpers.Texture;
import model.Model;
import states.LevelEditorState;

public class LoadLevelButton extends MenuButton {

	public LoadLevelButton(int x, int y, int width, int height) {
		super("LoadLevelButton", x, y, width, height);
		this.texture = Texture.BUTTON_LOAD_LEVEL;
	}

	@Override
	public void click(int x, int y) {
		PanelSelectTexts ms = (PanelSelectTexts) Model.findGuiObject("existingMaps");
		String chosen = ms.getChosen();
		Model.setState(new LevelEditorState(chosen + ".res"));
	}

}
