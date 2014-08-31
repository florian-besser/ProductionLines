package objects.gui;

import states.ExitState;
import model.Model;
import helpers.Texture;

public class ExitButton extends MenuButton {

	public ExitButton(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.texture = Texture.BUTTON_EXIT;
	}

	@Override
	public void click() {
		Model.setState(new ExitState());
	}

}
