package game;

public class Logic extends Thread {

	private Scene scene;

	public Logic(Scene scene) {
		this.scene = scene;
	}

	@Override
	public void run() {
		scene.add(new Triangle(0));
		scene.add(new Triangle(0.5));
	}

}
