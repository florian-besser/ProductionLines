package view;

import helpers.GameModelLoader;
import helpers.Texture;

import java.nio.IntBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import model.Model;
import objects.scenery.SceneryObject;

public class SceneryObjectRenderer {

	private int sceneryVboHandler;
	private int sceneryIboHandler;
	private int indexesLength;

	public void renderScenery(GL2 gl) {
		if (indexesLength > 0) {
			gl.glColor4d(1, 1, 1, 1);

			gl.glBindTexture(GL.GL_TEXTURE_2D, Texture.WORLD.getHandlerId(gl));
			gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, sceneryVboHandler);
			gl.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, sceneryIboHandler);

			gl.glVertexPointer(3, GL2.GL_FLOAT, 8 * 4, 0); // 3 Vertices
			gl.glNormalPointer(GL2.GL_FLOAT, 8 * 4, 3 * 4); // 3 Normals
			gl.glTexCoordPointer(2, GL2.GL_FLOAT, 8 * 4, 6 * 4); // 2 Texture Coordinates

			gl.glPushMatrix();

			gl.glTranslated(0, -0.001, 0);
			gl.glDrawElements(GL2.GL_TRIANGLES, indexesLength, GL2.GL_UNSIGNED_INT, 0);

			gl.glPopMatrix();

			gl.glBindTexture(GL.GL_TEXTURE_2D, 0);
			gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, 0);
			gl.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, 0);
		}
	}

	public void transferSceneryToGpu(GL2 gl) {
		int pointsLength = 0;
		int indexesLength = 0;
		int texCoordsLength = 0;
		SceneryObject[] sceneryObjects = Model.getSceneryObjects();
		for (SceneryObject sceneryObject : sceneryObjects) {
			pointsLength += sceneryObject.getPoints().length;
			indexesLength += sceneryObject.getIndexes().length;
			texCoordsLength += sceneryObject.getTextCoords().length;
		}

		float[] points = new float[pointsLength];
		int[] indexes = new int[indexesLength];
		float[] texCoords = new float[texCoordsLength];

		System.out.println("Loading Scenery with " + points.length + " points, " + indexes.length + " indexes and " + texCoords.length + " texCoords.");

		fillArrays(points, indexes, texCoords, sceneryObjects);

		if (sceneryVboHandler >= 0) {
			deleteHandlers(gl);
		}

		int[] handlers = GameModelLoader.load(gl, points, indexes, texCoords);
		sceneryVboHandler = handlers[0];
		sceneryIboHandler = handlers[1];
		Model.setNoRedrawNecessary();
		Model.clearUpdatedSceneryObjects();
		this.indexesLength = indexesLength;
	}

	public void updateSceneryOnGpu(GL2 gl) {
		for (SceneryObject sceneryObject : Model.getUpdatedSceneryObjects()) {

			int pointsLength = sceneryObject.getPoints().length;
			int indexesLength = sceneryObject.getIndexes().length;
			int texCoordsLength = sceneryObject.getTextCoords().length;

			float[] points = new float[pointsLength];
			int[] indexes = new int[indexesLength];
			float[] texCoords = new float[texCoordsLength];

			fillArrays(points, indexes, texCoords, sceneryObject);

			int offset = Model.getPlayfieldDimensionX() * sceneryObject.getX() + sceneryObject.getY();
			GameModelLoader.update(gl, sceneryVboHandler, sceneryIboHandler, offset, points, indexes, texCoords);
			Model.removeUpdatedSceneryObjects(sceneryObject);
		}
	}

	private void fillArrays(float[] points, int[] indexes, float[] texCoords, SceneryObject... sceneryObjects) {
		int pointIndex = 0;
		int indexesIndex = 0;
		int texCoordsIndex = 0;
		for (SceneryObject sceneryObject : sceneryObjects) {
			int offset = Model.getPlayfieldDimensionX() * sceneryObject.getX() + sceneryObject.getY();
			int baseIndex = sceneryObject.getPoints().length * offset / 3;
			for (float point : sceneryObject.getPoints()) {
				if (pointIndex % 3 == 0) {
					point += sceneryObject.getX();
				} else if (pointIndex % 3 == 2) {
					point += sceneryObject.getY();
				}
				points[pointIndex] = point;
				pointIndex++;
			}
			for (int index : sceneryObject.getIndexes()) {
				index += baseIndex;
				indexes[indexesIndex] = index;
				indexesIndex++;
			}
			for (float texCoord : sceneryObject.getTextCoords()) {
				if (texCoordsIndex % 2 == 0) {
					float xOffset = sceneryObject.getTexture().getXOffset();
					float widthCoefficient = sceneryObject.getTexture().getWidthCoefficient();
					texCoord = xOffset + texCoord * widthCoefficient;
				} else {
					float yOffset = sceneryObject.getTexture().getYOffset();
					float heightCoefficient = sceneryObject.getTexture().getHeightCoefficient();
					texCoord = yOffset + texCoord * heightCoefficient;
				}
				texCoords[texCoordsIndex] = texCoord;
				texCoordsIndex++;
			}
		}
	}

	private void deleteHandlers(GL2 gl) {
		IntBuffer exHandlers = IntBuffer.allocate(2);
		exHandlers.put(sceneryVboHandler);
		exHandlers.put(sceneryIboHandler);
		exHandlers.rewind();
		gl.glDeleteBuffers(2, exHandlers);
	}
}
