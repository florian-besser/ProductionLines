package states;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import helpers.FontEnum;
import objects.gui.menu.CreateLevelButton;
import objects.gui.menu.LoadLevelButton;
import objects.gui.menu.MenuString;
import objects.gui.menu.PanelSelectTexts;
import model.Model;


public class LevelEditorLoadState extends GameState {

	@Override
	public void activate() {
		Model.clearGuiObjects();
		
		Font font = FontEnum.TewntyEightDaysLater.getFont().deriveFont(48f);
		List<String> existingMaps = new ArrayList<String>();
		existingMaps.add("None found");
		Model.addGuiObject(new PanelSelectTexts("existingMaps", -540, -300, 500, 500, existingMaps, font));
		
		List<String> newMaps = new ArrayList<String>();
		newMaps.add("10x10");
		newMaps.add("100x100");
		newMaps.add("250x250");
		newMaps.add("500x500");
		newMaps.add("1000x1000");
		Model.addGuiObject(new PanelSelectTexts("newMaps", 70, -300, 500, 500, newMaps, font));

		Model.addGuiObject(new MenuString(-540, 300, "Load Map", FontEnum.TewntyEightDaysLater.getFont().deriveFont(72f)));
		Model.addGuiObject(new MenuString(60, 300, "Create new Map", FontEnum.TewntyEightDaysLater.getFont().deriveFont(72f)));
		Model.addGuiObject(new LoadLevelButton(-540, 210, 500, 100));
		Model.addGuiObject(new CreateLevelButton(70, 210, 500, 100));

	}

}
