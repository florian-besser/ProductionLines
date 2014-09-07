package objects.gui.menu;

import java.awt.Font;
import java.util.List;

import helpers.Texture;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import objects.gui.GuiObject;
import objects.gui.anchorpoints.CenterAnchor;

import com.jogamp.opengl.util.awt.TextRenderer;

import view.View;

public class PanelSelectTexts extends GuiObject {

	private static final int Y_OFFSET = 30;
	private static final int X_OFFSET = 30;
	protected Texture texture = Texture.DEBUG_SMALL;
	private List<String> lines;
	private int chosen = 0;
	private TextRenderer textrenderer;
	
	public PanelSelectTexts(String id, int x, int y, int width, int height, List<String> lines, Font font) {
		super(id, new CenterAnchor(), x, y, width, height);
		this.texture = Texture.PANEL;
		this.lines = lines;
		this.textrenderer = new TextRenderer(font);
	}

	@Override
	public void internalRender(GL2 gl) {
		renderBackground(gl);
		
		renderLines();
	}

	private void renderBackground(GL2 gl) {
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

	private void renderLines() {
		int w = View.getScreenWidth();
		int h = View.getScreenHeight();
		textrenderer.beginRendering(w, h);
		
		int i = 0;
		for (String text : lines) {
			if (i == chosen) {
				textrenderer.setColor(1.0f, 0.2f, 0.2f, 0.8f);
			} else {
				textrenderer.setColor(0.2f, 0.2f, 1.0f, 0.8f);
			}
			
			int offset = -(i+1) * textrenderer.getFont().getSize();
			textrenderer.draw(text, w/2 + X_OFFSET +x, h/2 - Y_OFFSET -y + offset);
			i++;
		}
	    // ... more draw commands, color changes, etc.
		textrenderer.endRendering();
	}

	@Override
	public void click(int x, int y) {
		y -= Y_OFFSET;
		chosen = y / textrenderer.getFont().getSize();
	}
	
	public String getChosen() {
		return lines.get(chosen);
	}

}
