package states;

import java.util.ArrayList;
import java.util.Collection;

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
		Collection<String> existingMaps = new ArrayList<String>();
		existingMaps.add("None found");
		Model.addGuiObject(new MenuSelect(-290, -50, 500, 500, existingMaps, FontEnum.TewntyEightDaysLater.getFont().deriveFont(48f)));
		
		Collection<String> newMaps = new ArrayList<String>();
		newMaps.add("100x100");
		newMaps.add("250x250");
		newMaps.add("500x500");
		newMaps.add("1000x1000");
		Model.addGuiObject(new MenuSelect(320, -50, 500, 500, newMaps, FontEnum.TewntyEightDaysLater.getFont().deriveFont(48f)));

		Model.addGuiObject(new MenuString(-540, 300, "Load Map", FontEnum.TewntyEightDaysLater.getFont().deriveFont(72f)));
		Model.addGuiObject(new MenuString(60, 300, "Create new Map", FontEnum.TewntyEightDaysLater.getFont().deriveFont(72f)));
		Model.addGuiObject(new LoadLevelButton(-290, 260, 500, 100));
		Model.addGuiObject(new CreateLevelButton(320, 260, 500, 100));

	}

}
