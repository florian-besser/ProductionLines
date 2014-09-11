package helpers;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.net.URL;

public class FontLoader {

	public static Font loadFont(String loc) {
		try {
			URL resource = FontLoader.class.getClassLoader().getResource(loc);
			return Font.createFont(Font.TRUETYPE_FONT, resource.openStream());
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		return null;
	}

}
