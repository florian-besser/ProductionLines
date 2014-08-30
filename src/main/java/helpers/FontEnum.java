package helpers;

import java.awt.Font;

public enum FontEnum {
	TewntyEightDaysLater("28DaysLater.ttf");
	
	private String loc;
	private Font loaded;

	private FontEnum(String loc) {
		this.loc = loc;
	}
	
	public Font getFont() {
		if (loaded == null) {
			loaded = FontLoader.loadFont(loc);
			System.out.println("Loading Font " + loc);
		}
		return loaded;
	}
}
