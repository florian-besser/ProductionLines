package objects;

import java.awt.Font;

import javax.media.opengl.GL2;

import com.jogamp.opengl.util.awt.TextRenderer;

import view.View;

public class MenuString extends GuiObject {

	private TextRenderer textrenderer;
	private String text;
	
	public MenuString(int x, int y, String text, Font font) {
		super(x, y);
		this.text = text;
		this.textrenderer = new TextRenderer(font);
	}
	
	@Override
	public void render(GL2 gl) {
		int w = View.getScreenWidth();
		int h = View.getScreenHeight();
		textrenderer.beginRendering(w, h);
	    // optionally set the color
		textrenderer.setColor(1.0f, 0.2f, 0.2f, 0.8f);
		textrenderer.draw(text, w/2+x, h/2+y);
	    // ... more draw commands, color changes, etc.
		textrenderer.endRendering();
	}

}
