package controller;

import model.GameState;
import model.Model;
import objects.Triangle;

public class UserListener {

	public UserListener() {
		Model.add(new Triangle(0));
		//Model.add(new Triangle(0.5));
		
		Model.setState(GameState.RUNNING);
	}
}
