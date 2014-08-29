package objects;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

public class Triangle extends GameObject {
	private double theta;
	private double s;
	private double c;
	private double color = 1;
	
	public Triangle(double x, double y) {
		super(x, y);
		theta = 0;
	}
	
	@Override
	public void safeUpdateGraphics(double deltaSeconds) {
		theta += deltaSeconds;
		s = Math.sin(theta);
		c = Math.cos(theta);
	}

	@Override
	public void safeRender(GL2 gl) {
		// draw a triangle
		gl.glBegin(GL.GL_TRIANGLES);
		gl.glColor3d(color, 0, 0);
		gl.glVertex3d(-c, 0, -c);
		gl.glColor3d(0, color, 0);
		gl.glVertex3d(0, 0, c);
		gl.glColor3d(0, 0, color);
		gl.glVertex3d(s, 0, -s);
		gl.glEnd();
	}

	@Override
	public void safeTick() {
		color += 0.01;
		if (color > 1) {
			color--;
		}
	}
}
