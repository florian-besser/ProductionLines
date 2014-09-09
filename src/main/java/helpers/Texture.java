package helpers;

import java.awt.Font;

import javax.media.opengl.GL2;

public enum Texture {
	DEBUG("DebugTexture.png"),
	DEBUG_SMALL("DebugTextureSmall.png", "Text", FontEnum.TewntyEightDaysLater.getFont().deriveFont(64f), 10, 100),
	BUTTON_START("KenneysUiPack/buttonLong_blue.png", "Start", FontEnum.TewntyEightDaysLater.getFont().deriveFont(32f), 55, 40),
	BUTTON_EXIT("KenneysUiPack/buttonLong_blue.png", "Exit", FontEnum.TewntyEightDaysLater.getFont().deriveFont(32f), 70, 40),
	BUTTON_LEVEL_EDITOR("KenneysUiPack/buttonLong_blue.png", "Level Editor", FontEnum.TewntyEightDaysLater.getFont().deriveFont(32f), 15, 40),
	BUTTON_LOAD_LEVEL("KenneysUiPack/buttonLong_blue.png", "Load Level", FontEnum.TewntyEightDaysLater.getFont().deriveFont(32f), 10, 40),
	BUTTON_CREATE_LEVEL("KenneysUiPack/buttonLong_blue.png", "Create Level", FontEnum.TewntyEightDaysLater.getFont().deriveFont(32f), 10, 40),
	PANEL("KenneysUiPack/panel_blue.png"),
	DIRT("dirt_tiled512.jpg"),
	DIRT_SMALL("dirt_tiled128.jpg"),
	GRASS("grass_tiled512.jpg"),
	GRASS_SMALL("grass_tiled128.jpg"),
	BLOCKED_SMALL("blocked_tiled128.png"),
	BIG_BRUSH("BigBrush.png"),
	MEDIUM_BIG_BRUSH("MediumBigBrush.png"),
	MEDIUM_BRUSH("MediumBrush.png"),
	SMALL_BRUSH("SmallBrush.png"),
	CHOSEN("Chosen.png");

	private String loc;
	private String text;
	private Font font;
	private int xOffset;
	private int yOffset;

	private int handlerId = -1;
	private int semiOpaqueHandlerId = -1;

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

	public int getSemiOpaqueHandlerId(GL2 gl) {
		if (semiOpaqueHandlerId == -1) {
			if (text != null) {
				semiOpaqueHandlerId = TextureLoader.loadTextureWithText(loc, text, font, xOffset, yOffset, gl);
			} else {
				semiOpaqueHandlerId = TextureLoader.loadTextureWithReducedOpacity(loc, 100, gl);
			}
			System.out.println("Loading Texture " + loc + " in handlerId " + semiOpaqueHandlerId);
		}
		return semiOpaqueHandlerId;
	}
}
