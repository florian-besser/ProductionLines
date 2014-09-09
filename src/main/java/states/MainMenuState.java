package states;

import helpers.FontEnum;

import javax.media.opengl.GL2;

import model.Model;
import objects.gui.menu.ExitButton;
import objects.gui.menu.LevelEditorButton;
import objects.gui.menu.MenuString;
import objects.gui.menu.StartButton;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class MainMenuState extends GameState {

	@Override
	public void activate() {
		Model.clearGuiObjects();
		Model.addGuiObject(new StartButton(-250, -150, 500, 100));
		Model.addGuiObject(new LevelEditorButton(-250, 50, 500, 100));
		Model.addGuiObject(new ExitButton(-250, 250, 500, 100));
		Model.addGuiObject(new MenuString(-240, 300, "ProductionLines", FontEnum.TewntyEightDaysLater.getFont().deriveFont(72f)));
	}

	@Override
	public void render(Vector3D pos, GL2 gl) {

	}

	@Override
	public void click() {

	}

}
