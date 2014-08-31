package states;

import helpers.FontEnum;
import model.Model;
import objects.gui.ExitButton;
import objects.gui.LevelEditorButton;
import objects.gui.MenuString;
import objects.gui.StartButton;

public class MainMenuState extends GameState {

	@Override
	public void activate() {
		Model.clearGuiObjects();
		Model.addGuiObject(new StartButton(0, -100, 500, 100));
		Model.addGuiObject(new LevelEditorButton(0, 100, 500, 100));
		Model.addGuiObject(new ExitButton(0, 300, 500, 100));
		Model.addGuiObject(new MenuString(-240, 300, "ProductionLines", FontEnum.TewntyEightDaysLater.getFont().deriveFont(72f)));
	}

}
