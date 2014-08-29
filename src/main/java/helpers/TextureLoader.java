package helpers;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;

public class TextureLoader {

	private static final int BYTES_PER_PIXEL = 4;// 3 for RGB, 4 for RGBA

	public static int loadTextureWithText(String loc, String text, Font font,
			int xOffset, int yOffset, GL2 gl) {
		BufferedImage image = loadImage(loc);
		
		renderTextOnImage(image, text, font, xOffset, yOffset);
		
		return transferImageToGpu(gl, image);
	}

	public static int loadTexture(String loc, GL2 gl) {
		BufferedImage image = loadImage(loc);
				
		return transferImageToGpu(gl, image);
	}

	private static BufferedImage loadImage(String loc) {
		try {
			return ImageIO.read(new File("src/main/resources/" + loc));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return null;
	}

	private static void renderTextOnImage(BufferedImage image, String text, Font font, int x,
			int y) {
		Graphics2D g = image.createGraphics();
		g.setFont(font);
		g.drawString(text, x, y);
		g.dispose();
	}
	
	private static int transferImageToGpu(GL2 gl, BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();

		int[] pixels = new int[width * height];
		image.getRGB(0, 0, width, height, pixels, 0, width);

		ByteBuffer buffer = ByteBuffer.allocate(width * height
				* BYTES_PER_PIXEL);

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Color c = new Color(image.getRGB(x, y));
				buffer.put((byte) c.getRed()); // Red component
				buffer.put((byte) c.getGreen()); // Green component
				buffer.put((byte) c.getBlue()); // Blue component
				buffer.put((byte) c.getAlpha()); // Alpha component. Only for
													// RGBA
			}
		}

		buffer.flip();

		int textureID = generateTextureId(gl); // Generate texture ID
		gl.glBindTexture(GL.GL_TEXTURE_2D, textureID); // Bind texture ID

		// Setup wrap mode
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S,
				GL2.GL_CLAMP_TO_EDGE);
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T,
				GL2.GL_CLAMP_TO_EDGE);

		// Setup texture scaling filtering
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER,
				GL.GL_NEAREST);
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER,
				GL.GL_NEAREST);

		// Send texel data to OpenGL
		gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA8, width, height, 0,
				GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, buffer);

		// Return the texture ID so we can bind it later again
		return textureID;
	}

	private static int generateTextureId(GL2 gl) {
		int[] tmp = new int[1];
		gl.glGenBuffers(1, tmp, 0);
		return tmp[0];
	}

}
