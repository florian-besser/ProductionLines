package states;

import helpers.Texture;

import java.util.ArrayList;
import java.util.List;

import model.Model;
import objects.gui.Panel;
import objects.gui.PanelContent;
import objects.gui.anchorpoints.BottomCenterAnchor;
import objects.gui.anchorpoints.LeftCenterAnchor;
import objects.scenery.ScenerySquare;

public class LevelEditorState extends GameState {

	private int xDimension;
	private int yDimension;

	public LevelEditorState(int xDimension, int yDimension) {
		this.xDimension = xDimension;
		this.yDimension = yDimension;
	}

	@Override
	public void activate() {
		Model.clearGuiObjects();
		Model.clearSceneryObjects();
		Model.clearGameObjects();
		for (int x = 0; x < xDimension; x++) {
			for (int y = 0; y < yDimension; y++) {
				Model.addSceneryObject(new ScenerySquare(x, y));
			}
		}

		List<PanelContent> terrainTypes = new ArrayList<PanelContent>();
		terrainTypes.add(new PanelContent("Dirt", 64, 64, Texture.DIRT_SMALL));
		terrainTypes.add(new PanelContent("Grass", 64, 64, Texture.GRASS_SMALL));
		terrainTypes.add(new PanelContent("Blocked", 64, 64, Texture.BLOCKED_SMALL));
		Model.addGuiObject(new Panel("terrainTypes", new BottomCenterAnchor(), -250, -96, 500, 96, terrainTypes));

		List<PanelContent> brushSizes = new ArrayList<PanelContent>();
		brushSizes.add(new PanelContent("BigBrush", 64, 64, Texture.BIG_BRUSH));
		brushSizes.add(new PanelContent("MediumBigBrush", 64, 64, Texture.MEDIUM_BIG_BRUSH));
		brushSizes.add(new PanelContent("MediumBrush", 64, 64, Texture.MEDIUM_BRUSH));
		brushSizes.add(new PanelContent("SmallBrush", 64, 64, Texture.SMALL_BRUSH));
		Model.addGuiObject(new Panel("brushSizes", new LeftCenterAnchor(), 0, -250, 96, 500, brushSizes));
	}

}
