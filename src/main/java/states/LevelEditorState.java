package states;

import helpers.Texture;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL2;

import model.Model;
import objects.game.GameObject;
import objects.game.SemiOpaqueSquare;
import objects.gui.Panel;
import objects.gui.PanelContent;
import objects.gui.anchorpoints.BottomCenterAnchor;
import objects.gui.anchorpoints.LeftCenterAnchor;
import objects.scenery.ScenerySquare;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class LevelEditorState extends GameState {

	private int xDimension;
	private int yDimension;
	private List<PanelContent> terrainTypes;
	private List<PanelContent> brushSizes;
	private List<GameObject> previewObjects = new ArrayList<GameObject>();

	public LevelEditorState(int xDimension, int yDimension) {
		this.xDimension = xDimension;
		this.yDimension = yDimension;

		terrainTypes = new ArrayList<PanelContent>();
		terrainTypes.add(new PanelContent("Dirt", 64, 64, Texture.DIRT_SMALL));
		terrainTypes.add(new PanelContent("Grass", 64, 64, Texture.GRASS_SMALL));
		terrainTypes.add(new PanelContent("Blocked", 64, 64, Texture.BLOCKED_SMALL));

		brushSizes = new ArrayList<PanelContent>();
		brushSizes.add(new PanelContent("BigBrush", 64, 64, Texture.BIG_BRUSH));
		brushSizes.add(new PanelContent("MediumBigBrush", 64, 64, Texture.MEDIUM_BIG_BRUSH));
		brushSizes.add(new PanelContent("MediumBrush", 64, 64, Texture.MEDIUM_BRUSH));
		brushSizes.add(new PanelContent("SmallBrush", 64, 64, Texture.SMALL_BRUSH));
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

		Model.addGuiObject(new Panel("terrainTypes", new BottomCenterAnchor(), -250, -96, 500, 96, terrainTypes));
		Model.addGuiObject(new Panel("brushSizes", new LeftCenterAnchor(), 0, -250, 96, 500, brushSizes));
	}

	@Override
	public void render(Vector3D pos, GL2 gl) {
		for (GameObject gameObj : previewObjects) {
			Model.removeGameObject(gameObj);
		}
		previewObjects.clear();

		Panel terrainTypesPanel = (Panel) Model.findGuiObject("terrainTypes");
		Panel brushSizesPanel = (Panel) Model.findGuiObject("brushSizes");

		int chosenTerrain = terrainTypesPanel.getChosen();
		int chosenBrush = brushSizesPanel.getChosen();

		if (chosenTerrain >= 0 && chosenBrush >= 0) {
			int radius = brushSizes.size() - chosenBrush;
			for (int x = -radius + 1; x < radius; x++) {
				for (int y = -(radius - Math.abs(x)) + 1; y < (radius - Math.abs(x)); y++) {
					previewObjects.add(new SemiOpaqueSquare((int) pos.getX() + x, (int) pos.getZ() + y, terrainTypes.get(chosenTerrain).getTexture()));
				}
			}
		}

		for (GameObject gameObj : previewObjects) {
			Model.addGameObject(gameObj);
		}
	}
}
