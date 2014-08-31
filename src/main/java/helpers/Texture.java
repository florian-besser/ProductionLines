package helpers;

import java.awt.Font;

import javax.media.opengl.GL2;

public enum Texture {
	DEBUG("DebugTexture.png"),
	DEBUG_SMALL("DebugTextureSmall.png", "Text", FontEnum.TewntyEightDaysLater.getFont().deriveFont(64f), 10, 100),
	BUTTON_START("BlueButton.png", "Start", FontEnum.TewntyEightDaysLater.getFont().deriveFont(64f), 90, 65),
	BUTTON_EXIT("BlueButton.png", "Exit", FontEnum.TewntyEightDaysLater.getFont().deriveFont(64f), 105, 65), 
	BUTTON_LEVEL_EDITOR("BlueButton.png", "Level Editor", FontEnum.TewntyEightDaysLater.getFont().deriveFont(64f), 5, 65), 
	BUTTON_LOAD_LEVEL("BlueButton.png", "Load Level", FontEnum.TewntyEightDaysLater.getFont().deriveFont(64f), 5, 65), 
	BUTTON_CREATE_LEVEL("BlueButton.png", "Create Level", FontEnum.TewntyEightDaysLater.getFont().deriveFont(64f), 5, 65), 
	GREY("Grey.png");
	
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
