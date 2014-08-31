package objects.gui;

import java.awt.Font;
import java.util.Collection;

import helpers.Texture;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import com.jogamp.opengl.util.awt.TextRenderer;

import view.View;

public class MenuSelect extends GuiObject {

	protected Texture texture = Texture.DEBUG_SMALL;
	private Collection<String> lines;
	private TextRenderer textrenderer;
	
	public MenuSelect(int x, int y, int width, int height, Collection<String> lines, Font font) {
		super(x, y, width, height);
		this.texture = Texture.GREY;
		this.lines = lines;
		this.textrenderer = new TextRenderer(font);
	}

	@Override
	public void render(GL2 gl) {
		renderBackground(gl);
		
		renderLines();
	}

	private void renderBackground(GL2 gl) {
		gl.glBindTexture(GL.GL_TEXTURE_2D, texture.getHandlerId(gl));
		gl.glLoadIdentity();
		// translate to the right location and prepare to draw
		int w = View.getScreenWidth();
		int h = View.getScreenHeight();
		gl.glTranslated(w/2-width/2, h/2-height/2, 0);
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

	private void renderLines() {
		int w = View.getScreenWidth();
		int h = View.getScreenHeight();
		textrenderer.beginRendering(w, h);
	    // optionally set the color
		textrenderer.setColor(1.0f, 0.2f, 0.2f, 0.8f);
		
		int offset = -textrenderer.getFont().getSize();
		for (String text : lines) {
			textrenderer.draw(text, w/2-width/2 + 10 +x, h/2+height/2 - 10 -y + offset);
			offset -= textrenderer.getFont().getSize();
		}
	    // ... more draw commands, color changes, etc.
		textrenderer.endRendering();
	}

	@Override
	public void click() {
		// TODO Highlight clicked entry
		
	}

}
