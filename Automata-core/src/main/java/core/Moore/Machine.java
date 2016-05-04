package core.Moore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Machine {
	private List<State> states;
	private String name;
	private Set<Character> iAlphabet, oAlphabet;
	private State currState;

	public Machine(String name) {
		this.states = new ArrayList<State>();
		this.name = name;
		this.currState = null;
	}

	public Machine(String name, Set<Character> iAlphabet, Set<Character> oAlphabet) throws MachineExpection {
		this.name = name;
		this.states = new ArrayList<State>();
		this.currState = null;
		this.createMachine(iAlphabet, oAlphabet);
	}

	public void addState() {
		this.states.add(new State());
	}

	public void addState(Character output) {
		this.states.add(new State(output));
	}

	public State getState(int n) {
		return this.states.get(n);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Character> getiAlphabet() {
		return iAlphabet;
	}

	public Set<Character> getoAlphabet() {
		return oAlphabet;
	}

	public void createMachine(Set<Character> iAlphabet, Set<Character> oAlphabet) throws MachineExpection {
		if (iAlphabet.size() != oAlphabet.size())
			throw new MachineExpection("Input and Output Alphabets must contain the same amount of letters!");
		this.iAlphabet = iAlphabet;
		this.oAlphabet = oAlphabet;

		List<Character> inputAlphabet = new ArrayList<Character>(iAlphabet);
		List<Character> outputAlphabet = new ArrayList<Character>(oAlphabet);
		List<Integer> range = IntStream.range(0, iAlphabet.size()).boxed().collect(Collectors.toList());

		for (int i = 0; i < oAlphabet.size(); i++)
			this.addState(outputAlphabet.get(i));

		for (State currState : this.states) {
			Collections.shuffle(range);

			for (int i = 0; i < iAlphabet.size(); i++) {
				currState.addTranslation(new Translation(inputAlphabet.get(i), this.states.get(range.get(i))));
			}
		}

		this.currState = this.getState(0);

	}

	public boolean isValid() {
		if (this.iAlphabet.size() != this.oAlphabet.size() || this.currState == null)
			return false;

		Set<Integer> compareRange = IntStream.range(0, this.iAlphabet.size()).boxed().collect(Collectors.toSet());

		Set<Character> checkIAlphabet = new HashSet<Character>();
		Set<Character> checkOAlphabet = new HashSet<Character>();
		Set<Integer> checkRange = new HashSet<Integer>();

		for (State currState : this.states) {
			if (currState.getTranslations().size() != this.states.size())
				return false;

			checkIAlphabet.clear();
			checkRange.clear();

			for (Translation currTranslation : currState.getTranslations()) {
				checkIAlphabet.add(currTranslation.getInput());
				checkRange.add(this.states.indexOf(currTranslation.getTarget()));
			}

			if (!checkIAlphabet.equals(this.iAlphabet) || !checkRange.equals(compareRange))
				return false;

			checkOAlphabet.add(currState.getOutput());
		}

		if (!checkOAlphabet.equals(this.oAlphabet)) {
			// System.out.println("debug");
			return false;
		}

		return true;
	}

	public Character step(Character input, boolean encoding) {
		if (encoding) {
			for (Translation currTranslation : this.currState.getTranslations()) {
				if (currTranslation.getInput().equals(input)) {
					this.currState = currTranslation.getTarget();
					return currState.getOutput();
				}
			}
		} else {
			for (Translation currTranslation : this.currState.getTranslations()) {
				if (currTranslation.getTarget().getOutput().equals(input)) {
					this.currState = currTranslation.getTarget();
					return currTranslation.getInput();
				}
			}
		}
		return null;
	}

	public String encode(String input) {
		String output = new String();
		for (int i = 0; i < input.length(); i++) {
			output += this.step(input.charAt(i), true);
		}

		this.currState = this.getState(0);

		return output;
	}

	public String decode(String input) {
		String output = new String();
		for (int i = 0; i < input.length(); i++) {
			output += this.step(input.charAt(i), false);
		}

		this.currState = this.getState(0);

		return output;

	}

	public void processData(String data) throws MachineExpection {
		Set<Character> base = new HashSet<Character>();
		for (int i = 0; i < data.length(); i++) {
			base.add(data.charAt(i));
		}
		this.createMachine(base, base);
	}

	@Override
	public String toString() {
		String output = new String();
		output += "Machine: " + this.name + "\n";
		for (State currState : this.states) {
			output += "---State " + this.states.indexOf(currState) + " | output: " + currState.getOutput() + "---\n";
			for (Translation currTranslation : currState.getTranslations()) {
				output += "[ " + currTranslation.getInput() + " ---> "
						+ this.states.indexOf(currTranslation.getTarget()) + " ]\n";
			}
		}
		return output;
	}

}
