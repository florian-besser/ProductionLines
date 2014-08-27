package objects;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

public class Triangle extends GameObject {

	private double theta;
	private double s;
	private double c;
	private double color = 1;

	public Triangle(double t) {
		this.theta = t;
	}
	
	@Override
	public void updateGraphics() {
		theta += 0.01;
		s = Math.sin(theta);
		c = Math.cos(theta);
	}

	@Override
	public void render(GL2 gl) {
		// draw a triangle filling the window
		gl.glBegin(GL.GL_TRIANGLES);
		gl.glColor3d(color, 0, 0);
		gl.glVertex2d(-c, -c);
		gl.glColor3d(0, color, 0);
		gl.glVertex2d(0, c);
		gl.glColor3d(0, 0, color);
		gl.glVertex2d(s, -s);
		gl.glEnd();
	}

	@Override
	public void tick() {
		color += 0.01;
		if (color > 1) {
			color--;
		}
	}
}
