package objects.gui.menu;

import helpers.Texture;

import java.awt.Font;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import objects.gui.GuiObject;
import objects.gui.anchorpoints.CenterAnchor;
import view.View;

import com.jogamp.opengl.util.awt.TextRenderer;

public class EntryField extends GuiObject {

	private static final int Y_OFFSET = 10;
	private static final int X_OFFSET = 30;

	private Texture texture;
	private TextRenderer textrenderer;
	private String text = "";

	public EntryField(String id, int x, int y, int width, int height, Font font) {
		super(id, new CenterAnchor(), x, y, width, height);
		this.texture = Texture.PANEL_INSET;
		this.textrenderer = new TextRenderer(font);
	}

	@Override
	protected void internalRender(GL2 gl) {
		renderBackground(gl);

		renderText(gl);
	}

	private void renderText(GL2 gl) {
		int w = View.getScreenWidth();
		int h = View.getScreenHeight();
		textrenderer.beginRendering(w, h);

		textrenderer.setColor(1.0f, 0.2f, 0.2f, 0.8f);

		int offset = textrenderer.getFont().getSize();
		textrenderer.draw(text, w / 2 + X_OFFSET + x, h / 2 - Y_OFFSET - y - offset);

		textrenderer.endRendering();
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
	public void writeChar(char keyChar) {
		if (Character.isAlphabetic(keyChar)) {
			text = text + keyChar;
		} else if (keyChar == '\b') {
			text = text.substring(0, text.length() - 1);
		}
	}

	public String getConent() {
		return text;
	}
}
