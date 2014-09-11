package states;

import helpers.LevelHandler;
import helpers.Texture;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL2;

import model.Model;
import objects.gui.Panel;
import objects.gui.PanelContent;
import objects.gui.anchorpoints.TopCenterAnchor;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class GameState extends AbstractGameState {

	private List<PanelContent> menuOptions;
	private String levelName;

	public GameState(String levelName) {
		this.levelName = levelName;
		menuOptions = new ArrayList<PanelContent>();
		menuOptions.add(new PanelContent("Exit", 64, 64, Texture.EXIT));
	}

	@Override
	public void activate() {
		Model.getWriteLock();
		try {
			Model.clearGuiObjects();
			Model.clearGameObjects();
			Model.setRedrawNecessary();
			LevelHandler.loadLevel(levelName);
			Model.addGuiObject(new Panel("menuOptions", new TopCenterAnchor(), -64, 0, 128, 96, menuOptions));
		} finally {
			Model.relesaseWriteLock();
		}
	}

	@Override
	public void render(Vector3D pos, GL2 gl) {
		// TODO Auto-generated method stub
		handleExit();
	}

	private void handleExit() {
		Panel menuOptionsPanel = (Panel) Model.findGuiObject("menuOptions");
		int chosenAction = menuOptionsPanel.getChosen();

		if (chosenAction >= 0 && chosenAction < menuOptions.size()) {
			PanelContent chosen = menuOptions.get(chosenAction);
			if (chosen.getId().equals("Exit")) {
				Model.setState(new MainMenuState());
			}
		}
	}

	@Override
	public void click() {
		// TODO Auto-generated method stub

	}

}
