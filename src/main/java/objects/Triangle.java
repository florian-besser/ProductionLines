package objects;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

public class Triangle extends GameObject {

	private double theta;
	private double s;
	private double c;
	
	public Triangle(double t) {
		this.theta = t;
	}
	
	@Override
	public void update() {
		theta += 0.01;
		s = Math.sin(theta);
		c = Math.cos(theta);
	}

	@Override
	public void render(GL2 gl) {
		// draw a triangle filling the window
		gl.glBegin(GL.GL_TRIANGLES);
		gl.glColor3f(1, 0, 0);
		gl.glVertex2d(-c, -c);
		gl.glColor3f(0, 1, 0);
		gl.glVertex2d(0, c);
		gl.glColor3f(0, 0, 1);
		gl.glVertex2d(s, -s);
		gl.glEnd();
	}
}
