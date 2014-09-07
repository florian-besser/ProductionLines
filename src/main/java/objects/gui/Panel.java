package objects.gui;

import helpers.Texture;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import objects.gui.anchorpoints.AnchorPoint;

public class Panel extends GuiObject {

	protected Texture texture = Texture.DEBUG_SMALL;
	
	public Panel(String id, AnchorPoint anchorPoint, int x, int y, int width, int height) {
		super(id, anchorPoint, x, y, width, height);
		this.texture = Texture.PANEL;
	}

	@Override
	public void internalRender(GL2 gl) {
		gl.glBindTexture(GL.GL_TEXTURE_2D, texture.getHandlerId(gl));
		gl.glColor3d(1, 1, 1);
		
		// draw a quad textured to match the sprite
		gl.glBegin(GL2.GL_QUADS);
		
		gl.glTexCoord2f(0, 0);
		gl.glVertex2d(x, y);
		gl.glTexCoord2f(1, 0);
		gl.glVertex2d(x+width, y);
		gl.glTexCoord2f(1, 1);
		gl.glVertex2d(x+width, y+height);
		gl.glTexCoord2f(0, 1);
		gl.glVertex2d(x, y+height);
		
		gl.glEnd();
		
		gl.glBindTexture(GL.GL_TEXTURE_2D, 0);
	}

	@Override
	public void click(int x, int y) {
		//Ignore
	}
}
