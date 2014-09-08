package objects.gui;

import helpers.Texture;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import objects.gui.anchorpoints.CenterAnchor;

public class PanelContent extends GuiObject {

	private Texture normalTexture;
	private Texture chosenTexture = Texture.CHOSEN;
	private boolean chosen;

	public PanelContent(String id, int width, int height, Texture normalTexture) {
		super(id, new CenterAnchor(), 0, 0, width, height);
		this.normalTexture = normalTexture;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	protected void internalRender(GL2 gl) {
		gl.glBindTexture(GL.GL_TEXTURE_2D, normalTexture.getHandlerId(gl));

		drawQuad(gl);

		if (chosen) {
			gl.glBindTexture(GL.GL_TEXTURE_2D, chosenTexture.getHandlerId(gl));
			drawQuad(gl);
		}

		gl.glBindTexture(GL.GL_TEXTURE_2D, 0);
	}

	private void drawQuad(GL2 gl) {
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
	}

	@Override
	public void click(int x, int y) {
		// Do nothing
	}

	public void setChosen() {
		chosen = true;
	}

	public void setNotChosen() {
		chosen = false;
	}

}
