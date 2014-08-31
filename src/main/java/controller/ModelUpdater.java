package controller;

import java.util.TimerTask;

import model.Model;
import objects.game.GameObject;

public class ModelUpdater extends TimerTask {

	//Looping
	private static final double AMOUNT_OF_TICKS_PER_SECOND = 10.0;
	private static final double NS_PER_TICK = 1000000000 / AMOUNT_OF_TICKS_PER_SECOND;
	private long lastTime = System.nanoTime();
	private long timer = System.currentTimeMillis();
	private double deltaTicks = 0;
	private int updates = 0;

	@Override
	public void run() {
		long now = System.nanoTime();
		deltaTicks += (now - lastTime) / NS_PER_TICK;
		lastTime = now;
		while(deltaTicks >= 1){
			for (GameObject object : Model.getGameObjects()) {
				object.tick();
			}
			updates++;
			deltaTicks--;
		}
				
		logTicks();
		
	}

	private void logTicks() {
		if(System.currentTimeMillis() - timer > 1000){
			timer += 1000;
			System.out.println("TICKS/s: " + updates);
			updates = 0;
		}
	}
}
