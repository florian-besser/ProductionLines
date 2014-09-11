package states;

import helpers.FontEnum;

import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL2;

import model.Model;
import objects.gui.menu.CreateLevelButton;
import objects.gui.menu.EntryField;
import objects.gui.menu.LoadLevelButton;
import objects.gui.menu.MenuString;
import objects.gui.menu.PanelSelectTexts;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class LevelEditorLoadState extends AbstractGameState {

	@Override
	public void activate() {
		Model.getWriteLock();
		try {
			Model.clearGuiObjects();

			Font font = FontEnum.TewntyEightDaysLater.getFont().deriveFont(48f);
			List<String> existingMaps = new ArrayList<String>();
			findExistingMaps(existingMaps);
			Model.addGuiObject(new PanelSelectTexts("existingMaps", -540, -300, 500, 500, existingMaps, font));

			List<String> newMaps = new ArrayList<String>();
			newMaps.add("10x10");
			newMaps.add("100x100");
			newMaps.add("250x250");
			newMaps.add("500x500");
			newMaps.add("1000x1000");
			Model.addGuiObject(new PanelSelectTexts("newMaps", 70, -300, 500, 300, newMaps, font));

			Model.addGuiObject(new MenuString(-540, 300, "Load Map", FontEnum.TewntyEightDaysLater.getFont().deriveFont(72f)));
			Model.addGuiObject(new MenuString(60, 300, "Create new Map", FontEnum.TewntyEightDaysLater.getFont().deriveFont(72f)));
			Model.addGuiObject(new MenuString(60, -90, "Name", FontEnum.TewntyEightDaysLater.getFont().deriveFont(72f)));
			EntryField nameField = new EntryField("LevelName", 60, 90, 500, 100, FontEnum.TewntyEightDaysLater.getFont().deriveFont(72f));
			Model.addGuiObject(nameField);
			Model.setFocusGuiObject(nameField);
			Model.addGuiObject(new LoadLevelButton(-540, 210, 500, 100));
			Model.addGuiObject(new CreateLevelButton(70, 210, 500, 100));
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

	}

	@Override
	public void click() {

	}
}
