package objects.gui.menu;

import java.awt.Font;

import javax.media.opengl.GL2;

import objects.gui.GuiObject;
import objects.gui.anchorpoints.CenterAnchor;

import com.jogamp.opengl.util.awt.TextRenderer;

import view.View;

public class MenuString extends GuiObject {

	private TextRenderer textrenderer;
	private String text;
	
	public MenuString(int x, int y, String text, Font font) {
		super(text, new CenterAnchor(), x, y, 0, 0);
		this.text = text;
		this.textrenderer = new TextRenderer(font);
	}
	
	@Override
	public void internalRender(GL2 gl) {
		int w = View.getScreenWidth();
		int h = View.getScreenHeight();
		textrenderer.beginRendering(w, h);
	    // optionally set the color
		textrenderer.setColor(1.0f, 0.2f, 0.2f, 0.8f);
		textrenderer.draw(text, w/2+x, h/2+y);
	    // ... more draw commands, color changes, etc.
		textrenderer.endRendering();
	}

	@Override
	public void click(int x, int y) {
		//Do nothing
	}

}
