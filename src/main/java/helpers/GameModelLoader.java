package helpers;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import com.jogamp.common.nio.Buffers;

public class GameModelLoader {

	public static int[] load(GL2 gl, float[] points, int[] indexes, float[] texCoords) {
		int[] handlers = new int[2];
		gl.glGenBuffers(2, handlers, 0);
		int vboId = handlers[0];
		int iboId = handlers[1];

		// System.out.println("Loading Model with vboId " + vboId + " and iboId " + iboId);

		int myPointSize = computePointSize(points, texCoords);
		int myIndexSize = computeIndexSize(indexes);

		FloatBuffer pointBuffer = Buffers.newDirectFloatBuffer(myPointSize);
		IntBuffer indexBuffer = Buffers.newDirectIntBuffer(myIndexSize);

		preAllocateBuffers(gl, vboId, iboId, pointBuffer, indexBuffer);

		fillPointBuffer(points, texCoords, pointBuffer);

		fillIndexBuffer(indexes, indexBuffer);

		pointBuffer.rewind();
		indexBuffer.rewind();

		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vboId);
		gl.glBufferSubData(GL.GL_ARRAY_BUFFER, 0, pointBuffer.capacity() * 4, pointBuffer);

		gl.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, iboId);
		gl.glBufferSubData(GL.GL_ELEMENT_ARRAY_BUFFER, 0, indexBuffer.capacity() * 4, indexBuffer);

		return handlers;
	}

	public static void update(GL2 gl, int vboId, int iboId, int offset, float[] points, int[] indexes, float[] texCoords) {
		// System.out.println("Updating Model with vboId " + vboId + " and iboId " + iboId + ", offset is " + offset);

		int myPointSize = computePointSize(points, texCoords);
		int myIndexSize = computeIndexSize(indexes);

		FloatBuffer pointBuffer = Buffers.newDirectFloatBuffer(myPointSize);
		IntBuffer indexBuffer = Buffers.newDirectIntBuffer(myIndexSize);

		fillPointBuffer(points, texCoords, pointBuffer);

		fillIndexBuffer(indexes, indexBuffer);

		pointBuffer.rewind();
		indexBuffer.rewind();

		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vboId);
		gl.glBufferSubData(GL.GL_ARRAY_BUFFER, myPointSize * 4 * offset, pointBuffer.capacity() * 4, pointBuffer);

		gl.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, iboId);
		gl.glBufferSubData(GL.GL_ELEMENT_ARRAY_BUFFER, myIndexSize * 4 * offset, indexBuffer.capacity() * 4, indexBuffer);
	}

	private static int computePointSize(float[] points, float[] texCoords) {
		int myPointSize = 0;
		myPointSize += points.length;
		myPointSize *= 2; // For Normals
		myPointSize += texCoords.length;
		return myPointSize;
	}

	private static int computeIndexSize(int[] indexes) {
		int myIndexSize = 0;
		myIndexSize += indexes.length;
		return myIndexSize;
	}

	private static void preAllocateBuffers(GL2 gl, int vboId, int iboId, FloatBuffer pointBuffer, IntBuffer indexBuffer) {
		int numBytes = pointBuffer.capacity() * 4;
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vboId);
		gl.glBufferData(GL.GL_ARRAY_BUFFER, numBytes, null, GL.GL_DYNAMIC_DRAW);
		numBytes = indexBuffer.capacity() * 4;
		gl.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, iboId);
		gl.glBufferData(GL.GL_ELEMENT_ARRAY_BUFFER, numBytes, null, GL.GL_DYNAMIC_DRAW);
	}

	private static void fillPointBuffer(float[] points, float[] texCoords, FloatBuffer pointBuffer) {
		for (int j = 0; j < points.length / 3; j++) {
			pointBuffer.put(points[3 * j]); // + start_x
			pointBuffer.put(points[3 * j + 1]); // + start_y
			pointBuffer.put(points[3 * j + 2]); // + start_z

			float U_x = 0, U_y = 0, U_z = 0, V_x = 0, V_y = 0, V_z = 0;

			if (3 * j + 5 < points.length) {
				U_x = (points[3 * j + 3] - points[3 * j]);
				U_y = (points[3 * j + 4] - points[3 * j + 1]);
				U_z = (points[3 * j + 5] - points[3 * j + 2]);
			}
			if (3 * j + 8 < points.length) {
				V_x = (points[3 * j + 6] - points[3 * j]);
				V_y = (points[3 * j + 7] - points[3 * j + 1]);
				V_z = (points[3 * j + 8] - points[3 * j + 2]);
			}

			pointBuffer.put((U_y * V_z) - (U_z * V_y)); // Normals
			pointBuffer.put((U_z * V_x) - (U_x * V_z));
			pointBuffer.put((U_x * V_y) - (U_y * V_x));

			pointBuffer.put(texCoords[2 * j]); // Texture
			pointBuffer.put(texCoords[2 * j + 1]);
		}
	}

	private static void fillIndexBuffer(int[] indexes, IntBuffer indexBuffer) {
		for (int j = 0; j < indexes.length; j++) {
			indexBuffer.put(indexes[j]);
		}
	}

}
