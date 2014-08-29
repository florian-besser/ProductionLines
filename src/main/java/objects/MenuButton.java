package objects;

import helpers.Texture;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

public class MenuButton extends GuiObject {

	private Texture texture;
	
	public MenuButton(int x, int y, int width, int height, Texture texture) {
		super(x, y, width, height);
		this.texture = texture;
	}

	@Override
	public void render(GL2 gl) {
		gl.glBindTexture(GL.GL_TEXTURE_2D, texture.getHandlerId(gl));
        // translate to the right location and prepare to draw
		//gl.glTranslatef(20, 20, 0);
		//gl.glColor3d(1, 1, 1);

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
		// draw a triangle filling the window
		/*gl.glColor3d(1, 1, 1);
		
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex2d(2*x, 2*y);
		gl.glVertex2d(2*x+width, 2*y);
		gl.glVertex2d(2*x+width, 2*y+height);
		gl.glVertex2d(2*x, 2*y+height);
		
		gl.glEnd();*/
	}

}
