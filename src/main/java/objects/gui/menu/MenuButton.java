package objects.gui.menu;

import helpers.Texture;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import objects.gui.GuiObject;
import objects.gui.anchorpoints.CenterAnchor;

public abstract class MenuButton extends GuiObject {

	protected Texture texture = Texture.DEBUG_SMALL;
	
	public MenuButton(String id, int x, int y, int width, int height) {
		super(id, new CenterAnchor(), x, y, width, height);
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

}
