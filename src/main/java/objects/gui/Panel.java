package objects.gui;

import helpers.Texture;

import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import objects.gui.anchorpoints.AnchorPoint;

public class Panel extends GuiObject {

	private static final int MARGIN = 16;
	protected Texture texture = Texture.DEBUG_SMALL;
	private List<PanelContent> content;
	private int chosen = -1;

	public Panel(String id, AnchorPoint anchorPoint, int x, int y, int width, int height, List<PanelContent> content) {
		super(id, anchorPoint, x, y, width, height);
		this.texture = Texture.PANEL;
		this.content = content;
	}

	@Override
	public void internalRender(GL2 gl) {
		renderBackground(gl);

		int xOffset = 0;
		int yOffset = 0;
		if (width >= height) {
			xOffset = -width / 2 + MARGIN * 2;
			yOffset = y + MARGIN;
		} else {
			xOffset = MARGIN;
			yOffset = -height / 2 + MARGIN * 2;
		}
		int i = 0;
		for (PanelContent cont : content) {
			cont.setX(xOffset);
			cont.setY(yOffset);
			if (i == chosen) {
				cont.setChosen();
			} else {
				cont.setNotChosen();
			}

			cont.internalRender(gl);

			if (width >= height) {
				xOffset += cont.getWidth() + MARGIN;
			} else {
				yOffset += cont.getHeight() + MARGIN;
			}
			i++;
		}
	}

	private void renderBackground(GL2 gl) {
		gl.glBindTexture(GL.GL_TEXTURE_2D, texture.getHandlerId(gl));
		gl.glColor4d(1, 1, 1, 1);

		// draw a quad textured to match the sprite
		gl.glBegin(GL2.GL_QUADS);

		gl.glTexCoord2f(0, 0);
		gl.glVertex2d(x, y);
		gl.glTexCoord2f(1, 0);
		gl.glVertex2d(x + width, y);
		gl.glTexCoord2f(1, 1);
		gl.glVertex2d(x + width, y + height);
		gl.glTexCoord2f(0, 1);
		gl.glVertex2d(x, y + height);

		gl.glEnd();

		gl.glBindTexture(GL.GL_TEXTURE_2D, 0);
	}

	@Override
	public void click(int x, int y) {
		if (content.size() == 0) {
			return;
		}

		if (width >= height) {
			x -= MARGIN * 2;
			chosen = x / (content.get(0).getWidth() + MARGIN);
		} else {
			y -= MARGIN * 2;
			chosen = y / (content.get(0).getHeight() + MARGIN);
		}
	}

	public int getChosen() {
		return chosen;
	}
}
