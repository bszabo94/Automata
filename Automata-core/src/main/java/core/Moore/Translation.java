package core.Moore;

public class Translation {
	private Character input;
	private State target;

	public Translation(Character input, State target) {
		this.input = input;
		this.target = target;
	}

	public Character getInput() {
		return input;
	}

	public void setInput(Character input) {
		this.input = input;
	}

	public State getTarget() {
		return target;
	}

	public void setTarget(State target) {
		this.target = target;
	}

}
