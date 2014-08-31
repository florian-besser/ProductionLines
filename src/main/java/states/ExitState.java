package states;

public class ExitState extends GameState {
	
		@Override
		public void activate() {
			System.exit(0);
		}
}
