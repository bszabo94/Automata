package core.Mealy;

public class Translation {
	private Character input, output;
	private State parent, target;

	public Translation() {
		this.parent = null;
		this.target = null;
		this.input = null;
		this.output = null;
	}

	public Translation(Character input, Character output, State parent, State target) {
		this();
		this.input = input;
		this.output = output;
		this.target = target;
		this.parent = parent;
	}

	public State getParent() {
		return parent;
	}

	public void setParent(State parent) {
		this.parent = parent;
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
