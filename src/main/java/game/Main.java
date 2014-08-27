package game;

import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;

import com.jogamp.opengl.util.FPSAnimator;

public class Main {
	
	private static GraphicsDevice device = GraphicsEnvironment
			.getLocalGraphicsEnvironment().getDefaultScreenDevice();

	public static void main(String[] args) {
		GLProfile glp = GLProfile.getDefault();
		GLCapabilities caps = new GLCapabilities(glp);
		GLCanvas canvas = new GLCanvas(caps);

		Frame frame = new Frame("AWT Window Test");
		frame.add(canvas);
		frame.setUndecorated(true);
		frame.setResizable(false);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		device.setFullScreenWindow(frame);

		Scene scene = new Scene();
		canvas.addGLEventListener(scene);

		FPSAnimator animator = new FPSAnimator(canvas, 60);
		// animator.add(canvas);
		animator.start();
		
		Logic logic = new Logic(scene);
		logic.start();
	}
}
