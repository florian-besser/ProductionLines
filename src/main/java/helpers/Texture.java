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
	BUTTON_START_GAME("KenneysUiPack/buttonLong_blue.png", "Start Game", FontEnum.TewntyEightDaysLater.getFont().deriveFont(32f), 10, 40),
	PANEL("KenneysUiPack/panel_blue.png"),
	PANEL_INSET("KenneysUiPack/panelInset_blue.png"),
	DIRT("WorldTexture.png", 256f / 2048, 128f / 2048, 128f / 2048, 128f / 2048),
	GRASS("WorldTexture.png", 256f / 2048, 0, 128f / 2048, 128f / 2048),
	BLOCKED("WorldTexture.png", 128f / 2048, 128f / 2048, 128f / 2048, 128f / 2048),
	STONE("WorldTexture.png", 0, 0, 128f / 2048, 128f / 2048),
	SAND("WorldTexture.png", 0, 384f / 2048, 128f / 2048, 128f / 2048),
	ORE("WorldTexture.png", 0, 256f / 2048, 128f / 2048, 128f / 2048),
	BLACK_ORE("WorldTexture.png", 256f / 2048, 256f / 2048, 128f / 2048, 128f / 2048),
	BLUE_ORE("WorldTexture.png", 256f / 2048, 384f / 2048, 128f / 2048, 128f / 2048),
	RED_ORE("WorldTexture.png", 384f / 2048, 384f / 2048, 128f / 2048, 128f / 2048),
	BIG_BRUSH("BigBrush.png"),
	MEDIUM_BIG_BRUSH("MediumBigBrush.png"),
	MEDIUM_BRUSH("MediumBrush.png"),
	SMALL_BRUSH("SmallBrush.png"),
	CHOSEN("Chosen.png"),
	WORLD("WorldTexture.png"),
	SAVE("save.png"),
	EXIT("KenneysUiPack/iconCross_brown.png"),
	BLACK_FLAG("WorldTexture.png", 9f / 16, 13f / 16, 1f / 16, 1f / 16),
	BLUE_FLAG("WorldTexture.png", 10f / 16, 13f / 16, 1f / 16, 1f / 16),
	RED_FLAG("WorldTexture.png", 9f / 16, 14f / 16, 1f / 16, 1f / 16);

	private String loc;
	private String text;
	private Font font;
	private int textXOffset;
	private int textYOffset;

	private int handlerId = -1;
	private int semiOpaqueHandlerId = -1;
	private float xOffset = 0;
	private float yOffset = 0;
	private float widthCoefficient = 1;
	private float heightCoefficient = 1;

	private Texture(String loc) {
		this.loc = loc;
	}

	private Texture(String loc, float xOffset, float yOffset, float widthCoefficient, float heightCoefficient) {
		this.loc = loc;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.widthCoefficient = widthCoefficient;
		this.heightCoefficient = heightCoefficient;
	}

	private Texture(String loc, String text, Font font, int xOffset, int yOffset) {
		this.loc = loc;
		this.text = text;
		this.font = font;
		this.textXOffset = xOffset;
		this.textYOffset = yOffset;
	}

	public int getHandlerId(GL2 gl) {
		if (handlerId == -1) {
			if (text != null) {
				handlerId = TextureLoader.loadTextureWithText(loc, text, font, textXOffset, textYOffset, gl);
			} else {
				handlerId = TextureLoader.loadTexture(loc, xOffset, yOffset, widthCoefficient, heightCoefficient, gl);
			}
			System.out.println("Loading Texture " + loc + " in handlerId " + handlerId);
		}
		return handlerId;
	}

	public int getSemiOpaqueHandlerId(GL2 gl) {
		if (semiOpaqueHandlerId == -1) {

			semiOpaqueHandlerId = TextureLoader.loadTextureWithReducedOpacity(loc, xOffset, yOffset, widthCoefficient, heightCoefficient, 100, gl);

			System.out.println("Loading Texture " + loc + " in handlerId " + semiOpaqueHandlerId);
		}
		return semiOpaqueHandlerId;
	}

	public float getXOffset() {
		return xOffset;
	}

	public float getYOffset() {
		return yOffset;
	}

	public float getWidthCoefficient() {
		return widthCoefficient;
	}

	public float getHeightCoefficient() {
		return heightCoefficient;
	}

	public int getId() {
		int i = 0;
		for (Texture t : Texture.values()) {
			if (t.equals(this)) {
				break;
			}
			i++;
		}
		return i;
	}

	public static Texture getById(int id) {
		int i = 0;
		for (Texture t : Texture.values()) {
			if (i == id) {
				return t;
			}
			i++;
		}
		return null;
	}
}
