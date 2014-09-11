package states;

import helpers.Texture;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL2;

import model.Model;
import objects.gui.Panel;
import objects.gui.PanelContent;
import objects.gui.anchorpoints.TopCenterAnchor;
import objects.scenery.ScenerySquare;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Game extends GameState {

	private List<PanelContent> menuOptions;

	public Game() {
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
			try (DataInputStream reader = new DataInputStream(new BufferedInputStream(new FileInputStream("level.res")))) {
				int xDimension = reader.readInt();
				int yDimension = reader.readInt();

				Model.clearSceneryObjects(xDimension, yDimension);
				for (int i = 0; i < xDimension * yDimension; i++) {
					int id = reader.readInt();
					int row = i / yDimension;
					Model.addSceneryObject(new ScenerySquare(row, i - row * yDimension, Texture.getById(id)));
				}
			} catch (IOException e) {
				e.printStackTrace();
				Model.setState(new ExitState());
				return;
			}
			Model.addGuiObject(new Panel("menuOptions", new TopCenterAnchor(), -64, 0, 128, 96, menuOptions));
		} finally {
			Model.relesaseWriteLock();
		}
	}

	@Override
	public void render(Vector3D pos, GL2 gl) {
		// TODO Auto-generated method stub

	}

	@Override
	public void click() {
		// TODO Auto-generated method stub

	}

}
