package helpers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import model.Model;
import objects.scenery.SceneryObject;
import objects.scenery.ScenerySquare;
import states.ExitState;

public class LevelHandler {

	public static void loadLevel(String name) {
		try (DataInputStream reader = new DataInputStream(new BufferedInputStream(new FileInputStream("levels/" + name + ".res")))) {
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

	public static void saveLevel(String name) {
		try (DataOutputStream writer = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("levels/" + name + ".res")))) {
			writer.writeInt(Model.getPlayfieldDimensionX());
			writer.writeInt(Model.getPlayfieldDimensionY());
			for (SceneryObject obj : Model.getSceneryObjects()) {
				writer.writeInt(obj.getTexture().getId());
			}
		} catch (IOException e) {
			e.printStackTrace();
			Model.setState(new ExitState());
		}

	}
}
