package core.Mealy;

public class Translation {
	private Character input, output;
	private State target;

	public Translation(Character input, Character output, State target) {
		this.input = input;
		this.output = output;
		this.target = target;
	}

	public Character getInput() {
		return input;
	}

	public void setInput(Character input) {
		this.input = input;
	}

	public Character getOutput() {
		return output;
	}

	public void setOutput(Character output) {
		this.output = output;
	}

	public State getTarget() {
		return target;
	}

	public void setTarget(State target) {
		this.target = target;
	}

}
