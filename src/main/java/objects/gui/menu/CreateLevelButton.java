package objects.gui.menu;

import helpers.Texture;
import model.Model;

import org.apache.commons.lang3.StringUtils;

import states.LevelEditorState;

public class CreateLevelButton extends MenuButton {

	public CreateLevelButton(int x, int y, int width, int height) {
		super("CreateLevelButton", x, y, width, height);
		this.texture = Texture.BUTTON_CREATE_LEVEL;
	}

	@Override
	public void click(int x, int y) {
		PanelSelectTexts newMapsSelect = (PanelSelectTexts) Model.findGuiObject("newMaps");
		EntryField levelNameField = (EntryField) Model.findGuiObject("LevelName");

		String levelName = levelNameField.getConent();
		if (StringUtils.isNotBlank(levelName)) {
			String levelSize = newMapsSelect.getChosen();
			String[] dimensions = levelSize.split("x");
			int xDimension = Integer.valueOf(dimensions[0]);
			int yDimension = Integer.valueOf(dimensions[1]);
			Model.setState(new LevelEditorState(xDimension, yDimension, levelName));
		}
	}

}
