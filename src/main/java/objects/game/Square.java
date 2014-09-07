package objects.game;

import helpers.Texture;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import objects.game.gameModels.GameModelEnum;

public class Square extends GameObject {

	protected Texture texture = Texture.DIRT;
	protected GameModelEnum model = GameModelEnum.SQUARE;

	public Square(int x, int y) {
		super(x, y, 1, 1);
	}

	@Override
	protected void safeUpdateGraphics(double delta) {
		// Do nothing
	}

	@Override
	protected void safeRender(GL2 gl) {
		gl.glColor3d(1, 1, 1);

		gl.glBindTexture(GL.GL_TEXTURE_2D, texture.getHandlerId(gl));
		gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, model.getVboHandlerId(gl));
		gl.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, model.getIboHandlerId(gl));

		gl.glVertexPointer(3, GL2.GL_FLOAT, 8 * 4, 0); // 3 Vertices
		gl.glNormalPointer(GL2.GL_FLOAT, 8 * 4, 3 * 4); // 3 Normals
		gl.glTexCoordPointer(2, GL2.GL_FLOAT, 8 * 4, 6 * 4); // 2 Texture Coordinates

		gl.glPushMatrix();
		gl.glTranslated(x, 0, y);

		int indexes = model.getIndexLength();
		gl.glDrawElements(GL2.GL_TRIANGLES, indexes, GL2.GL_UNSIGNED_INT, 0);

		gl.glPopMatrix();

		gl.glBindTexture(GL.GL_TEXTURE_2D, 0);
		gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, 0);
		gl.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, 0);
	}

	@Override
	protected void safeTick() {
		// Do nothing
	}

	@Override
	public void click() {
		// Do nothing
	}

}
