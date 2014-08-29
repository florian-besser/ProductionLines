package helpers;

import java.awt.Font;

import javax.media.opengl.GL2;

public enum Texture {
	DEBUG("DebugTexture.png"),
	DEBUG_SMALL("DebugTextureSmall.png", "Text", new Font("sansserif", Font.BOLD, 64), 10, 100);
	
	private String loc;
	private String text;
	private Font font;
	private int xOffset;
	private int yOffset;
		
	private int handlerId = -1;

	private Texture(String loc) {
		this.loc = loc;
	}
	
	private Texture(String loc, String text, Font font, int xOffset, int yOffset) {
		this.loc = loc;
		this.text = text;
		this.font = font;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	public int getHandlerId(GL2 gl) {
		if (handlerId == -1) {
			if (text != null) {
				handlerId = TextureLoader.loadTextureWithText(loc, text, font, xOffset, yOffset, gl);
			} else {
				handlerId = TextureLoader.loadTexture(loc, gl);
			}
			System.out.println("Loading Texture " + loc + " in handlerId " + handlerId);
		}
		return handlerId;
	}
}
