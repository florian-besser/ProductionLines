package helpers;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

public class FontLoader {

	public static Font loadFont(String loc) {
		try {
			return Font.createFont(Font.TRUETYPE_FONT, new File(
					"src/main/resources/" + loc));
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		return null;
	}

}
