package objects.gui;

import states.LevelEditorState;
import model.Model;
import helpers.Texture;


public class CreateLevelButton extends MenuButton {

	public CreateLevelButton(int x, int y, int width, int height) {
		super("CreateLevelButton", x, y, width, height);
		this.texture = Texture.BUTTON_CREATE_LEVEL;
	}

	@Override
	public void click(int x, int y) {
		MenuSelect ms = (MenuSelect) Model.findGuiObject("newMaps");
		String levelSize = ms.getChosen();
		String[] dimensions = levelSize.split("x");
		int xDimension = Integer.valueOf(dimensions[0]);
		int yDimension = Integer.valueOf(dimensions[1]);
		Model.setState(new LevelEditorState(xDimension, yDimension));
	}

}
