package game;

import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;

import view.View;

import com.jogamp.opengl.util.FPSAnimator;

import controller.UserListener;
import controller.ModelUpdater;

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

		View view = new View();
		canvas.addGLEventListener(view);

		FPSAnimator animator = new FPSAnimator(canvas, 120);
		// animator.add(canvas);
		animator.start();
		
		Timer timer = new Timer();
		timer.schedule(new ModelUpdater(), 0, 50);
		
		UserListener userListener = new UserListener();
		canvas.addKeyListener(userListener);
		canvas.addMouseListener(userListener);
		canvas.addMouseMotionListener(userListener);
		
		canvas.requestFocus();
		canvas.requestFocusInWindow();
	}
}
