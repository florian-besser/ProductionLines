package objects.gui.menu;

import states.ExitState;
import model.Model;
import helpers.Texture;

public class ExitButton extends MenuButton {

	public ExitButton(int x, int y, int width, int height) {
		super("ExitButton", x, y, width, height);
		this.texture = Texture.BUTTON_EXIT;
	}

	@Override
	public void click(int x, int y) {
		Model.setState(new ExitState());
	}

}
