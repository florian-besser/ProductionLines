package helpers;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import model.Model;
import objects.scenery.ScenerySquare;
import states.ExitState;

public class LevelLoader {

	public static void loadLevel(String name) {
		try (DataInputStream reader = new DataInputStream(new BufferedInputStream(new FileInputStream(name)))) {
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
	}
}
