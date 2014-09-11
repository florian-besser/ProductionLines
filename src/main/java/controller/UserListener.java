package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import model.Model;
import objects.gui.EmptyGuiObject;
import objects.gui.GuiObject;
import states.ExitState;
import states.MainMenuState;

public class UserListener implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

	private static final int forwardKey = KeyEvent.VK_W;
	private static final int backKey = KeyEvent.VK_S;
	private static final int leftKey = KeyEvent.VK_A;
	private static final int rightKey = KeyEvent.VK_D;
	private static final int upKey = KeyEvent.VK_SHIFT;
	private static final int downKey = KeyEvent.VK_CONTROL;

	public UserListener() {
		Model.setState(new MainMenuState());
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();

		if (keyCode == KeyEvent.VK_ESCAPE || (keyCode == KeyEvent.VK_C) && e.isControlDown()) {
			System.out.println("Shutting Down...");

			Model.setState(new ExitState());
		} else if (keyCode == forwardKey) {
			Model.setCameraMovementX(1);
		} else if (keyCode == backKey) {
			Model.setCameraMovementX(-1);
		} else if (keyCode == leftKey) {
			Model.setCameraMovementZ(-1);
		} else if (keyCode == rightKey) {
			Model.setCameraMovementZ(1);
		} else if (keyCode == upKey) {
			Model.setCameraMovementY(1);
		} else if (keyCode == downKey) {
			Model.setCameraMovementY(-1);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();

		if (keyCode == forwardKey) {
			Model.setCameraMovementX(0);
		} else if (keyCode == backKey) {
			Model.setCameraMovementX(0);
		} else if (keyCode == leftKey) {
			Model.setCameraMovementZ(0);
		} else if (keyCode == rightKey) {
			Model.setCameraMovementZ(0);
		} else if (keyCode == upKey) {
			Model.setCameraMovementY(0);
		} else if (keyCode == downKey) {
			Model.setCameraMovementY(0);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		Model.getFocusGuiObject().writeChar(e.getKeyChar());
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// System.out.println("Mouse dragged on " + e.getX() + " " + e.getY());
		handleMouseMoveOrDrag(e);
		handleMouseClickOrDrag(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// System.out.println("Mouse moved on " + e.getX() + " " + e.getY());
		handleMouseMoveOrDrag(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// System.out.println("Mouse clicked on " + e.getX() + " " + e.getY());
		handleMouseClickOrDrag(e);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// Irrelevant
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// Irrelevant
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// System.out.println("Mouse pressed on " + e.getX() + " " + e.getY());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// System.out.println("Mouse released on " + e.getX() + " " + e.getY());
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// System.out.println("Mouse Wheel moved by " + e.getPreciseWheelRotation()+ " units.");
		Model.addCameraY(e.getPreciseWheelRotation());
	}

	private void handleMouseClickOrDrag(MouseEvent e) {
		GuiObject obj = Model.findGuiObject(e.getX(), e.getY());
		// System.out.println("Found object " + obj.getId() + " on " + obj.getX() + " " + obj.getY() + " with dimensions " + obj.getWidth() + " " + obj.getHeight());
		obj.click(e.getX() - obj.getX(), e.getY() - obj.getY());

		Model.setFocusGuiObject(obj);
		if (obj instanceof EmptyGuiObject) {
			Model.getState().click();
		}
	}

	private void handleMouseMoveOrDrag(MouseEvent e) {
		Model.setAbsoluteMouseX(e.getX());
		Model.setAbsoluteMouseY(e.getY());
	}
}
