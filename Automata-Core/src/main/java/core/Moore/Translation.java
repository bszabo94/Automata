package core.Moore;

public class Translation {
	private Character input;
	private State target, parent;

	public Translation(State parent, Character input, State target) {
		this.parent = parent;
		this.input = input;
		this.target = target;
	}
	
	public State getParent(){
		return parent;
	}

	public void setParent(State parent){
		this.parent = parent;
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
