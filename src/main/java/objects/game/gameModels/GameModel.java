package objects.game.gameModels;

import helpers.GameModelLoader;


import javax.media.opengl.GL2;


public abstract class GameModel {

	private int vboId = -1;
	private int iboId = -1;
	protected float[] points;
	protected float[] texCoords;
	protected int[] indexes;

	public int getVboHandlerId(GL2 gl) {
		if (vboId == -1) {
			setInternals(gl);
			load(gl);
		}
		
		return vboId;
	}

	public int getIboHandlerId(GL2 gl) {
		if (iboId == -1) {
			setInternals(gl);
			load(gl);
		}
		
		return iboId;
	}
		
	protected abstract void setInternals(GL2 gl);
	
	public int getIndexLength() {
		return indexes.length;
	}

	private void load(GL2 gl) {
		int[] handlers = GameModelLoader.load(gl, points, indexes, texCoords);
		vboId = handlers[0];
		iboId = handlers[1];
	}
}
