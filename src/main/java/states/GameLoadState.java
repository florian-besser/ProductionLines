package states;

import helpers.FontEnum;

import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL2;

import model.Model;
import objects.gui.menu.MenuString;
import objects.gui.menu.PanelSelectTexts;
import objects.gui.menu.StartGameButton;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class GameLoadState extends AbstractGameState {

	@Override
	public void activate() {
		Model.getWriteLock();
		try {
			Model.clearGuiObjects();

			Font font = FontEnum.TewntyEightDaysLater.getFont().deriveFont(48f);
			List<String> existingMaps = new ArrayList<String>();
			findExistingMaps(existingMaps);
			Model.addGuiObject(new PanelSelectTexts("existingMaps", -250, -300, 500, 500, existingMaps, font));

			Model.addGuiObject(new MenuString(-250, 300, "Select Map", FontEnum.TewntyEightDaysLater.getFont().deriveFont(72f)));
			Model.addGuiObject(new StartGameButton(-250, 210, 500, 100));
		} finally {
			Model.relesaseWriteLock();
		}
	}

	private void findExistingMaps(List<String> existingMaps) {
		File folder = new File("levels/");
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile() && listOfFiles[i].getName().contains(".res")) {
				existingMaps.add(listOfFiles[i].getName().replace(".res", ""));
			}
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
