package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import objects.general.GeneralObject;
import states.ExitState;
import states.MainMenuState;
import view.View;
import model.Model;

public class UserListener implements KeyListener, MouseListener, MouseMotionListener {
	
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
	public void keyTyped(KeyEvent arg0) {
		//Use keyPressed instead
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		//No functionality yet
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		//Irrelevant
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//Use mousePressed instead
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		//Irrelevant
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		//Irrelevant
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//System.out.println("Mouse pressed on " + e.getX() + " " + e.getY());
		GeneralObject obj = Model.findObject(e.getX() - View.getScreenWidth()/2, e.getY() - View.getScreenHeight()/2);
		obj.click();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//System.out.println("Mouse released on " + e.getX() + " " + e.getY());		
	}
}
