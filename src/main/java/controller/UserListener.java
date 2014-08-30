package controller;

import helpers.Texture;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import objects.MenuButton;
import objects.Triangle;
import model.GameState;
import model.Model;

public class UserListener implements KeyListener, MouseListener, MouseMotionListener {
	
	private static final int forwardKey = KeyEvent.VK_W;
	private static final int backKey = KeyEvent.VK_S;
	private static final int leftKey = KeyEvent.VK_A;
	private static final int rightKey = KeyEvent.VK_D;
	private static final int upKey = KeyEvent.VK_SHIFT;
	private static final int downKey = KeyEvent.VK_CONTROL;


	public UserListener() {
		Model.addGuiObject(new MenuButton(0, -100, 500, 100, Texture.BUTTON_START));
		Model.addGuiObject(new MenuButton(0, 100, 500, 100, Texture.BUTTON_EXIT));
		Model.setState(GameState.MENU);
		
		Model.addGameObject(new Triangle(0, 0));
		Model.setState(GameState.RUNNING);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if (keyCode == KeyEvent.VK_ESCAPE || (keyCode == KeyEvent.VK_C) && e.isControlDown()) {
			System.out.println("Shutting Down...");
			
			Model.setState(GameState.EXIT);
		} else if (keyCode == KeyEvent.VK_R) {
			//TODO: Reinitialize?
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
