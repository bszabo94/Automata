package core.Mealy;

/*
 * #%L
 * Automata-Core
 * %%
 * Copyright (C) 2016 Faculty of Informatics, University of Debrecen
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.beans.property.SimpleStringProperty;

/**
 * <h1>Mealy Machine</h1>
 * <P>
 * Describes the representation of a Mealy Machine, a deterministic finite-state
 * machine.
 * 
 * @author bszabo
 * @version 1.0
 * @see <a href="https://en.wikipedia.org/wiki/Mealy_machine">Mealy Machines on
 *      Wikipedia</a>
 *
 */
public class Machine {

	/**
	 * A list that contains the states of the Mealy Machine.
	 * 
	 * @see core.Mealy.State State
	 */
	private List<State> states;

	/**
	 * The ID or name of the Machine.
	 * 
	 * @see core.Mealy.Machine#getID() getID()
	 * @see core.Mealy.Machine#setID() setID()
	 */
	private String id;

	/**
	 * Input Alphabet of the Machine.
	 * <P>
	 * A finite set of characters, which the Machine can use as input.
	 * 
	 * @see core.Mealy.Machine#getiAlphabet() getiAlphabet()
	 * @see core.Mealy.Machine#setiAlphabet(Set) setiAlphabet(Set)
	 */
	private Set<Character> iAlphabet;

	/**
	 * Output Alphabet of the Machine.
	 * <P>
	 * A finite set of characters, which the Machine can use as output.
	 * 
	 * @see core.Mealy.Machine#getoAlphabet() getoAlphabet()
	 * @see core.Mealy.Machine#setoAlphabet(Set) setoAlphabet(Set)
	 */
	private Set<Character> oAlphabet;

	/**
	 * Current State of the Machine.
	 * <P>
	 * The initial state of the Machine. This can only change TODO
	 * 
	 * @see core.Mealy.Machine#getoAlphabet() getoAlphabet()
	 * @see core.Mealy.Machine#setoAlphabet(Set) setoAlphabet(Set)
	 */
	private State currState;

	/**
	 * <h1>Constructor of Machine</h1>
	 * 
	 * <P>
	 * Creates a Machine object with empty input and output alphabets, and
	 * without states and translations. Throws
	 * {@link core.Mealy.MachineException MachineException} if the given id is
	 * an empty string.
	 * 
	 * @param id
	 *            The value of the {@code id} will be the ID of the Machine.
	 * 
	 * @throws core.Mealy.MachineException
	 * @see core.Mealy.Machine#Machine(String, Set, Set) Machine(String, Set,
	 *      Set)
	 */
	public Machine(String id) throws MachineException {
		if (id == null || id.equals(""))
			throw new MachineException("The Machine must have an ID.");
		this.states = new ArrayList<State>();
		this.id = id;
		this.currState = null;
		this.iAlphabet = new HashSet<Character>();
		this.oAlphabet = new HashSet<Character>();
	}

	/**
	 * <h1>Constructor of Machine</h1>
	 * 
	 * <P>
	 * Creates a Machine object with the given id, input and output alphabets,
	 * and without states and translations. Throws
	 * {@link core.Mealy.MachineException MachineException} if the given id is
	 * an empty string.
	 * 
	 * @param id
	 *            The value of the {@code id} will be the ID of the Machine.
	 * @param iAlphabet
	 *            The set of characters, which will be used as input alphabet
	 *            for the machine.
	 * @param oAlphabet
	 *            The set of characters, which will be used as output alphabet
	 *            for the machine.
	 * @see core.Mealy.Machine#Machine(String) Machine(String)
	 * @throws core.Mealy.MachineException
	 */
	public Machine(String id, Set<Character> iAlphabet, Set<Character> oAlphabet) throws MachineException {
		if (id == null)
			throw new MachineException("The Machine must have an ID.");
		this.id = id;
		this.states = new ArrayList<State>();
		this.currState = null;
		this.init(iAlphabet, oAlphabet);
	}

	public String getType() {
		return "Mealy";
	}

	public List<State> getStates() {
		return states;
	}

	public State getCurrState() {
		return currState;
	}

	/**
	 * Adds a new state to the machine without an id.
	 * 
	 * <P>
	 * Creates a new {@link core.Mealy.State State} object without and id, and
	 * puts it to the list, which contains the states of this machine.
	 * 
	 * @see core.Mealy.Machine#addState(int) addState(int)
	 */
	public void addState() {
		this.states.add(new State());
	}

	/**
	 * Adds a new state to the machine without an id.
	 * 
	 * <P>
	 * Creates a new {@link core.Mealy.State State} object with an id of "qn",
	 * where n stands for the number given as parameter, and puts it to the
	 * list, which contains the states of this machine.
	 * 
	 * @param n
	 *            A number, which determines the generated {@code id} of the new
	 *            state;
	 * @see core.Mealy.Machine#addState() addState()
	 */
	public void addState(int n) {
		this.states.add(new State(n));
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

	/**
	 * Initializes the machine.
	 * 
	 * <P>
	 * Initializes the machine with the given input and output alphabets. Sets
	 * the input and output alphabets of the machine to be equal to the ones
	 * given as parameters, as well as creates as many states as the number of
	 * characters the input alphabet contains, then fills up with random
	 * translations, assigning a translation to each input character in every
	 * state. Throws a {@link core.Mealy.MachineException MachineException} if
	 * the input alphabet contains more character than the output alphabet. The
	 * machine initialized with this method is considered valid.
	 * 
	 * @param iAlphabet
	 *            The set of characters, that will be used as input alphabet.
	 * @param oAlphabet
	 *            The set of characters, that will be used as output alphabet.
	 * @throws core.Mealy.MachineException
	 */
	public void init(Set<Character> iAlphabet, Set<Character> oAlphabet) throws MachineException {
		if (iAlphabet.size() > oAlphabet.size())
			throw new MachineException("Input Alphabet must contain equal or less symbols than the Output Alphabet!");
		this.iAlphabet = iAlphabet;
		this.oAlphabet = oAlphabet;

		List<Character> inputAlphabet = new ArrayList<Character>(iAlphabet);
		List<Character> outputAlphabet = new ArrayList<Character>(oAlphabet);
		List<Integer> range = IntStream.range(0, iAlphabet.size()).boxed().collect(Collectors.toList());

		for (int i = 0; i < iAlphabet.size(); i++)
			this.addState(i);

		for (State currState : this.states) {
			Collections.shuffle(outputAlphabet);
			Collections.shuffle(range);

			for (int i = 0; i < iAlphabet.size(); i++) {
				currState.addTranslation(new Translation(inputAlphabet.get(i), outputAlphabet.get(i), currState,
						this.states.get(range.get(i))));
			}
		}

		this.currState = this.states.get(0);

	}

	/**
	 * Checks whether the machine is valid or not.
	 * 
	 * <P>
	 * A machine is considered valid if it has an initial state.
	 * @returns True, if the machine is valid, and false otherwise.
	 */
	public boolean isValid() {
		if (this.currState == null) {
			return false;
		}

		Set<Character> checkIAlphabet = new HashSet<Character>();
		List<Character> checkOAlphabet = new ArrayList<Character>();
		Set<String> checkStateID = new HashSet<String>();

		for (State currState : this.states) {
			if (checkStateID.contains(currState.getID()))
				return false;
			checkStateID.add(currState.getID());
			checkIAlphabet.clear();
			checkOAlphabet.clear();
			if (this.iAlphabet.size() != currState.getTranslations().size())
				return false;
			for (Translation currTranslation : currState.getTranslations()) {
				checkIAlphabet.add(currTranslation.getInput());
				checkOAlphabet.add(currTranslation.getOutput());
			}
			if (!this.iAlphabet.equals(checkIAlphabet))
				return false;
			if (checkOAlphabet.size() != new HashSet<Character>(checkOAlphabet).size())
				return false;
			for (Character currChar : checkOAlphabet) {
				if (!this.oAlphabet.contains(currChar))
					return false;
			}
			for (Character currChar : checkIAlphabet) {
				if (!this.iAlphabet.contains(currChar))
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
					return currTranslation.getOutput();
				}
			}
		} else {
			for (Translation currTranslation : this.currState.getTranslations()) {
				if (currTranslation.getOutput().equals(input)) {
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

		// this.currState = this.states.get(0);
		this.currState = temp;

		return output;
	}

	public String decode(String input) {
		State temp = this.currState;
		String output = new String();
		for (int i = 0; i < input.length(); i++) {
			output += this.step(input.charAt(i), false);
		}

		// this.currState = this.states.get(0);
		this.currState = temp;

		return output;

	}

	public void processData(String data) throws MachineException {
		Set<Character> base = new HashSet<Character>();
		for (int i = 0; i < data.length(); i++) {
			base.add(data.charAt(i));
		}
		this.init(base, base);
	}

	public core.Moore.Machine toMoore() throws core.Moore.MachineException {
		core.Moore.Machine m = new core.Moore.Machine(this.id + " --> Moore");
		m.setiAlphabet(new HashSet<Character>(this.iAlphabet));
		m.setoAlphabet(new HashSet<Character>(this.oAlphabet));

		Map<State, Set<Character>> symbolDistributor = new HashMap<State, Set<Character>>();
		for (State currState : this.states) {
			symbolDistributor.put(currState, new HashSet<Character>());
		}

		for (State currState : this.states) {
			for (Translation currTranslation : currState.getTranslations()) {
				symbolDistributor.get(currTranslation.getTarget()).add(currTranslation.getOutput());
			}
		}

		Map<State, Map<Character, core.Moore.State>> translationDistributor = new HashMap<State, Map<Character, core.Moore.State>>();

		for (State currState : symbolDistributor.keySet()) {
			translationDistributor.put(currState, new HashMap<Character, core.Moore.State>());
		}

		int i = 0;

		for (State currState : symbolDistributor.keySet()) {
			for (Character currChar : symbolDistributor.get(currState)) {
				m.addState(currChar, i);
				i++;
				translationDistributor.get(currState).put(currChar, m.getStates().get(m.getStates().size() - 1));
				if (m.getCurrState() == null && this.currState == currState) {
					m.setCurrState(m.getStates().get(m.getStates().size() - 1));
				}
			}
		}

		for (State currState : this.states) {
			for (Translation currTranslation : currState.getTranslations()) {
				for (core.Moore.State tempState : translationDistributor.get(currState).values()) {
					tempState.addTranslation(new core.Moore.Translation(tempState, currTranslation.getInput(),
							translationDistributor.get(currTranslation.getTarget()).get(currTranslation.getOutput())));
				}
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

	public List<Translation> getTranslationsAsList() {
		List<Translation> translations = new ArrayList<Translation>();

		for (State currState : this.getTranslations().keySet()) {
			for (Translation currTranslation : this.getTranslations().get(currState)) {
				translations.add(currTranslation);
				currTranslation.setParent(currState);
			}
		}

		return translations;
	}

	public void removeState(String id) throws MachineException {
		State s = null;
		for (State currState : this.states)
			if (id.equals(currState.getID())) {
				s = currState;
				break;
			}
		if (s == null)
			throw new MachineException("There is no State with the given ID.");

		for (State currState : this.states) {
			for (int i = 0; i < currState.getTranslations().size(); i++) {
				if (currState.getTranslations().get(i).getParent() == s
						|| currState.getTranslations().get(i).getTarget() == s) {
					currState.getTranslations().remove(currState.getTranslations().get(i));
					i--;
				}
			}
		}
		this.states.remove(s);
	}

	public String toString() {
		String output = new String();
		output += "Machine: " + this.id + "\n";
		output += "Input Alphabet: " + this.iAlphabet + "\n";
		output += "Output Alphabet: " + this.oAlphabet + "\n";
		for (State currState : this.states) {
			output += "---State " + this.states.indexOf(currState) + "---\n";
			for (Translation currTranslation : currState.getTranslations()) {
				output += "[ " + currTranslation.getParent().getID() + "/" + currTranslation.getInput() + " ---> "
						+ currTranslation.getOutput() + " / q" + this.states.indexOf(currTranslation.getTarget())
						+ " ]\n";
			}
		}
		return output;
	}

}
