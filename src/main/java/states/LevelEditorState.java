package states;

import helpers.LevelHandler;
import helpers.Texture;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL2;

import model.Model;
import objects.game.GameObject;
import objects.game.Square;
import objects.gui.Panel;
import objects.gui.PanelContent;
import objects.gui.anchorpoints.BottomCenterAnchor;
import objects.gui.anchorpoints.LeftCenterAnchor;
import objects.gui.anchorpoints.TopCenterAnchor;
import objects.scenery.ScenerySquare;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class LevelEditorState extends AbstractGameState {

	private int xDimension;
	private int yDimension;
	private List<PanelContent> terrainTypes;
	private List<PanelContent> brushSizes;
	private List<PanelContent> menuOptions;
	private List<GameObject> previewObjects = new ArrayList<GameObject>();
	private String levelName;

	public LevelEditorState(int xDimension, int yDimension, String levelName) {
		this.xDimension = xDimension;
		this.yDimension = yDimension;
		this.levelName = levelName;

		menuOptions = new ArrayList<PanelContent>();
		menuOptions.add(new PanelContent("Save", 64, 64, Texture.SAVE));
		menuOptions.add(new PanelContent("Exit", 64, 64, Texture.EXIT));

		terrainTypes = new ArrayList<PanelContent>();
		terrainTypes.add(new PanelContent("Dirt", 64, 64, Texture.DIRT));
		terrainTypes.add(new PanelContent("Grass", 64, 64, Texture.GRASS));
		terrainTypes.add(new PanelContent("Stone", 64, 64, Texture.STONE));
		terrainTypes.add(new PanelContent("Sand", 64, 64, Texture.SAND));
		terrainTypes.add(new PanelContent("Ore", 64, 64, Texture.ORE));
		terrainTypes.add(new PanelContent("Black Ore", 64, 64, Texture.BLACK_ORE));
		terrainTypes.add(new PanelContent("Blue Ore", 64, 64, Texture.BLUE_ORE));
		terrainTypes.add(new PanelContent("Red Ore", 64, 64, Texture.RED_ORE));
		terrainTypes.add(new PanelContent("Blocked", 64, 64, Texture.BLOCKED));

		brushSizes = new ArrayList<PanelContent>();
		brushSizes.add(new PanelContent("BigBrush", 64, 64, Texture.BIG_BRUSH));
		brushSizes.add(new PanelContent("MediumBigBrush", 64, 64, Texture.MEDIUM_BIG_BRUSH));
		brushSizes.add(new PanelContent("MediumBrush", 64, 64, Texture.MEDIUM_BRUSH));
		brushSizes.add(new PanelContent("SmallBrush", 64, 64, Texture.SMALL_BRUSH));
	}

	public LevelEditorState(String levelName) {
		this(0, 0, levelName);
	}

	@Override
	public void activate() {
		Model.getWriteLock();
		try {
			Model.clearGuiObjects();
			Model.clearSceneryObjects(xDimension, yDimension);
			Model.clearGameObjects();
			Model.setRedrawNecessary();
			if (xDimension == 0 || yDimension == 0) {
				LevelHandler.loadLevel(levelName);
			} else {
				for (int x = 0; x < xDimension; x++) {
					for (int y = 0; y < yDimension; y++) {
						Model.addSceneryObject(new ScenerySquare(x, y, Texture.DIRT));
					}
				}
			}

			Model.addGuiObject(new Panel("menuOptions", new TopCenterAnchor(), -104, 0, 208, 96, menuOptions));
			Model.addGuiObject(new Panel("terrainTypes", new BottomCenterAnchor(), -384, -96, 768, 96, terrainTypes));
			Model.addGuiObject(new Panel("brushSizes", new LeftCenterAnchor(), 0, -184, 96, 368, brushSizes));
		} finally {
			Model.relesaseWriteLock();
		}

	}

	@Override
	public void click() {
		Model.getWriteLock();
		try {
			// System.out.println("LevelEditorState.click called.");
			for (GameObject gameObj : previewObjects) {
				ScenerySquare square = new ScenerySquare(gameObj.getX(), gameObj.getY(), gameObj.getTexture());
				Model.addSceneryObject(square);
			}
			// System.out.println("Added " + previewObjects.size() + " SceneryObjects.");
		} finally {
			Model.relesaseWriteLock();
		}
	}

	@Override
	public void render(Vector3D pos, GL2 gl) {
		Model.getWriteLock();
		try {
			removePreviewObjects();

			setPreviewObjects(pos);

			addPreviewObjects();

			handleSaveAndExit();
		} finally {
			Model.relesaseWriteLock();
		}
	}

	private void removePreviewObjects() {
		for (GameObject gameObj : previewObjects) {
			Model.removeGameObject(gameObj);
		}
		previewObjects.clear();
	}

	private void setPreviewObjects(Vector3D pos) {
		Panel terrainTypesPanel = (Panel) Model.findGuiObject("terrainTypes");
		Panel brushSizesPanel = (Panel) Model.findGuiObject("brushSizes");

		int chosenTerrain = terrainTypesPanel.getChosen();
		int chosenBrush = brushSizesPanel.getChosen();

		if (chosenTerrain >= 0 && chosenBrush >= 0) {
			Texture chosenTexture = terrainTypes.get(chosenTerrain).getTexture();
			int radius = brushSizes.size() - chosenBrush;
			for (int x = -radius + 1; x < radius; x++) {
				for (int y = -(radius - Math.abs(x)) + 1; y < (radius - Math.abs(x)); y++) {
					Square square = new Square((int) pos.getX() + x, (int) pos.getZ() + y, chosenTexture, true);
					previewObjects.add(square);
				}
			}
		}
	}

	private void addPreviewObjects() {
		Model.getWriteLock();
		try {
			for (GameObject gameObj : previewObjects) {
				Model.addGameObject(gameObj);
			}
		} finally {
			Model.relesaseWriteLock();
		}

	}

	private void handleSaveAndExit() {
		Panel menuOptionsPanel = (Panel) Model.findGuiObject("menuOptions");
		int chosenAction = menuOptionsPanel.getChosen();

		if (chosenAction >= 0 && chosenAction < menuOptions.size()) {
			PanelContent chosen = menuOptions.get(chosenAction);
			if (chosen.getId().equals("Exit")) {
				Model.setState(new MainMenuState());
			} else if (chosen.getId().equals("Save")) {
				LevelHandler.saveLevel(levelName);
				Model.setState(new MainMenuState());
			}
		}
	}

}
