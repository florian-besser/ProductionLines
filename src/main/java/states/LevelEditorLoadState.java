package states;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import helpers.FontEnum;
import objects.gui.CreateLevelButton;
import objects.gui.LoadLevelButton;
import objects.gui.MenuSelect;
import objects.gui.MenuString;
import model.Model;


public class LevelEditorLoadState extends GameState {

	@Override
	public void activate() {
		Model.clearGuiObjects();
		
		Font font = FontEnum.TewntyEightDaysLater.getFont().deriveFont(48f);
		List<String> existingMaps = new ArrayList<String>();
		existingMaps.add("None found");
		Model.addGuiObject(new MenuSelect("existingMaps", -290, -50, 500, 500, existingMaps, font));
		
		List<String> newMaps = new ArrayList<String>();
		newMaps.add("10x10");
		newMaps.add("100x100");
		newMaps.add("250x250");
		newMaps.add("500x500");
		newMaps.add("1000x1000");
		Model.addGuiObject(new MenuSelect("newMaps", 320, -50, 500, 500, newMaps, font));

		Model.addGuiObject(new MenuString(-540, 300, "Load Map", FontEnum.TewntyEightDaysLater.getFont().deriveFont(72f)));
		Model.addGuiObject(new MenuString(60, 300, "Create new Map", FontEnum.TewntyEightDaysLater.getFont().deriveFont(72f)));
		Model.addGuiObject(new LoadLevelButton(-290, 260, 500, 100));
		Model.addGuiObject(new CreateLevelButton(320, 260, 500, 100));

	}

}
