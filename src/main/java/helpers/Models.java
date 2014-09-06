package helpers;

import javax.media.opengl.GL2;

public enum Models {
	
	SQUARE(new SquareModel());
	
	int handlerId = -1;
	private Model model;
	
	private Models(Model model) {
		this.model = model;
	}

	public int getVboHandlerId(GL2 gl) {
		return model.getVboHandlerId(gl);
	}
	
	public int getIboHandlerId(GL2 gl) {
		return model.getIboHandlerId(gl);
	}

	public int getIndexLength() {
		return model.getIndexLength();
	}
}
