package objects.gui.anchorpoints;

import javax.media.opengl.GL2;

public abstract class AnchorPoint {
	
	public abstract void setTranslation(GL2 gl);

	public abstract int getXComponent();
	
	public abstract int getYComponent();
}
