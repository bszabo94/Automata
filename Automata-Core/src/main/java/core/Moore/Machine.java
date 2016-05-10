package core.Moore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Machine {
	private List<State> states;
	private String id;
	private Set<Character> iAlphabet, oAlphabet;
	private State currState;

	public Machine(String id) {
		this.states = new ArrayList<State>();
		this.id = id;
		this.currState = null;
	}

	public Machine(String id, Set<Character> iAlphabet, Set<Character> oAlphabet) throws MachineExpection {
		this.id = id;
		this.states = new ArrayList<State>();
		this.currState = null;
		this.init(iAlphabet, oAlphabet);
	}

	public List<State> getStates() {
		return states;
	}

	public void addState() {
		this.states.add(new State());
	}

	public void addState(Character output) {
		this.states.add(new State(output));
	}

	public State getCurrState() {
		return currState;
	}

	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}

	public void setiAlphabet(Set<Character> iAlphabet) {
		this.iAlphabet = iAlphabet;
	}

	public void setoAlphabet(Set<Character> oAlphabet) {
		this.oAlphabet = oAlphabet;
	}

	public void setCurrState(State currState) {
		this.currState = currState;
	}

	public Set<Character> getiAlphabet() {
		return iAlphabet;
	}

	public Set<Character> getoAlphabet() {
		return oAlphabet;
	}

	public void init(Set<Character> iAlphabet, Set<Character> oAlphabet) throws MachineExpection {
		if (iAlphabet.size() > oAlphabet.size())
			throw new MachineExpection("Input Alphabet must contain equal or less symbols than the Output Alphabet!");
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

		this.currState = this.states.get(0);

	}

	public boolean isValid() {

		if (this.currState == null) {
			return false;
		}

		Set<Character> checkIAlphabet = new HashSet<Character>();
		List<Character> checkOAlphabet = new ArrayList<Character>();

		for (State currState : this.states) {
			checkIAlphabet.clear();
			checkOAlphabet.clear();
			if (this.iAlphabet.size() != currState.getTranslations().size())
				return false;
			for (Translation currTranslation : currState.getTranslations()) {
				checkIAlphabet.add(currTranslation.getInput());
				checkOAlphabet.add(currTranslation.getTarget().getOutput());
			}
			if (!this.iAlphabet.equals(checkIAlphabet))
				return false;
			if (checkOAlphabet.size() != new HashSet<Character>(checkOAlphabet).size())
				return false;
			for (Character currChar : checkIAlphabet) {
				if (!this.iAlphabet.contains(currChar))
					return false;
			}
			for (Character currChar : checkOAlphabet) {
				if (!this.oAlphabet.contains(currChar))
					return false;
			}
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
		State temp = this.currState;
		String output = new String();
		for (int i = 0; i < input.length(); i++) {
			output += this.step(input.charAt(i), true);
		}

		this.currState = temp;

		return output;
	}

	public String decode(String input) {
		State temp = this.currState;
		String output = new String();
		for (int i = 0; i < input.length(); i++) {
			output += this.step(input.charAt(i), false);
		}

		this.currState = temp;

		return output;

	}

	public void processData(String data) throws MachineExpection {
		Set<Character> base = new HashSet<Character>();
		for (int i = 0; i < data.length(); i++) {
			base.add(data.charAt(i));
		}
		this.init(base, base);
	}

	@SuppressWarnings("unused")
	public core.Mealy.Machine toMealy() {
		core.Mealy.Machine m = new core.Mealy.Machine(this.getID());

		m.setiAlphabet(new HashSet<Character>(this.iAlphabet));
		m.setoAlphabet(new HashSet<Character>(this.oAlphabet));

		for (State currState : this.states)
			m.addState();

		m.setCurrState(m.getStates().get(this.states.indexOf(this.currState)));

		for (int i = 0; i < this.states.size(); i++) {
			for (Translation currTranslation : this.states.get(i).getTranslations()) {
				m.getStates().get(i)
						.addTranslation(new core.Mealy.Translation(currTranslation.getInput(),
								currTranslation.getTarget().getOutput(),
								m.getStates().get(this.states.indexOf((currTranslation.getTarget())))));
			}
		}

		return m;
	}

	public Set<Character> getSymbols(String sentence) {
		Set<Character> symbols = new HashSet<Character>();
		for (int i = 0; i < sentence.length(); i++) {
			symbols.add(sentence.charAt(i));
		}

		return symbols;
	}

	public Map<State, List<Translation>> getTranslations() {
		Map<State, List<Translation>> allTranslation = new HashMap<State, List<Translation>>();

		for (State currState : this.states) {
			allTranslation.put(currState, new ArrayList<Translation>());
			for (Translation currTranslation : currState.getTranslations()) {
				allTranslation.get(currState).add(currTranslation);
			}
		}

		return allTranslation;
	}

	@Override
	public String toString() {
		String output = new String();
		output += "Machine: " + this.id + "\n";
		output += "Input Alphabet: " + this.iAlphabet + "\n";
		output += "Output Alphabet: " + this.oAlphabet + "\n";
		for (State currState : this.states) {
			output += "---State " + this.states.indexOf(currState) + " | output: " + currState.getOutput() + "---\n";
			for (Translation currTranslation : currState.getTranslations()) {
				output += "[ " + currTranslation.getInput() + " ---> q"
						+ this.states.indexOf(currTranslation.getTarget()) + " ]\n";
			}
		}
		return output;
	}

}
