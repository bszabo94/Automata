package core.Moore;

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



public class Machine {
	private List<State> states;
	private String id;
	private Set<Character> iAlphabet, oAlphabet;
	private State currState;

	public Machine(String id) throws MachineException {
		if (id == null || id.equals(""))
			throw new MachineException("The Machine must have an ID.");
		this.states = new ArrayList<State>();
		this.id = id;
		this.currState = null;
		this.iAlphabet = new HashSet<Character>();
		this.oAlphabet = new HashSet<Character>();
	}

	public Machine(String id, Set<Character> iAlphabet, Set<Character> oAlphabet) throws MachineException {
		if (id == null || id.equals(""))
			throw new MachineException("The ID must not be empty.");
		this.id = id;
		this.states = new ArrayList<State>();
		this.currState = null;
		this.init(iAlphabet, oAlphabet);
	}

	public String getType() {
		return "Moore";
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

	public void addState(Character output, int n) {
		this.states.add(new State(output, "q" + Integer.toString(n)));
	}

	public void addState(Character output, String id) throws MachineException {
		if (id == null || id.equals(""))
			throw new MachineException("The ID must not be empty.");
		if (this.findState(id) != null)
			throw new MachineException("There is already a state in the machine with the ID: " + id);
		this.states.add(new State(output, id));
	}

	public void addState(String output, String id) throws MachineException {
		if (this.findState(id) != null)
			throw new MachineException("There is already a state in the machine with the ID: " + id + ".");
		if (output.length() > 1)
			throw new MachineException("Output is too large. It must be a single Character of Symbol.");
		this.states.add(new State(output.charAt(0), id));
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

	public void addiAlphabet(String i) throws MachineException{
		for(Character currChar: i.toCharArray()){
			if(this.iAlphabet.contains(currChar))
				throw new MachineException(currChar + "is already in the Input Alhpabet.");
			this.iAlphabet.add(currChar);
		}
	}
	
	public void addoAlphabet(String i) throws MachineException{
		for(Character currChar: i.toCharArray()){
			if(this.oAlphabet.contains(currChar))
				throw new MachineException(currChar + "is already in the Output Alhpabet.");
			this.oAlphabet.add(currChar);
		}
	}

	public void removeiAlphabet(String i) throws MachineException{
		for(Character currChar: i.toCharArray()){
			if(!this.iAlphabet.contains(currChar))
				throw new MachineException(currChar + "is not in the Input Alhpabet.");
			this.iAlphabet.remove(currChar);
		}
	}
	
	public void removeoAlphabet(String i) throws MachineException{
		for(Character currChar: i.toCharArray()){
			if(!this.oAlphabet.contains(currChar))
				throw new MachineException(currChar + "is not in the Output Alhpabet.");
			this.oAlphabet.remove(currChar);
		}
	}
	
	public void setCurrState(State currState) {
		this.currState = currState;
	}
	
	public void setCurrStateById(String id) throws MachineException{
		State s = this.findState(id);
		if (s == null)
			throw new MachineException("There is no state with the id: " + id + ".");
		this.currState = s;
	}

	public Set<Character> getiAlphabet() {
		return iAlphabet;
	}

	public Set<Character> getoAlphabet() {
		return oAlphabet;
	}

	public void init(Set<Character> iAlphabet, Set<Character> oAlphabet) throws MachineException {
		if (iAlphabet.size() > oAlphabet.size())
			throw new MachineException("Input Alphabet must contain equal or less symbols than the Output Alphabet!");
		this.iAlphabet = iAlphabet;
		this.oAlphabet = oAlphabet;

		List<Character> inputAlphabet = new ArrayList<Character>(iAlphabet);
		List<Character> outputAlphabet = new ArrayList<Character>(oAlphabet);
		List<Integer> range = IntStream.range(0, iAlphabet.size()).boxed().collect(Collectors.toList());

		for (int i = 0; i < oAlphabet.size(); i++)
			this.addState(outputAlphabet.get(i), i);

		for (State currState : this.states) {
			Collections.shuffle(range);

			for (int i = 0; i < iAlphabet.size(); i++) {
				currState.addTranslation(
						new Translation(currState, inputAlphabet.get(i), this.states.get(range.get(i))));
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

	public String encode(String input) throws MachineException {
		if (!this.isValid())
			throw new MachineException("Must be a valid machine!");
		State temp = this.currState;
		String output = new String();
		for (int i = 0; i < input.length(); i++) {
			output += this.step(input.charAt(i), true);
		}

		this.currState = temp;

		return output;
	}

	public String decode(String input) throws MachineException {
		if (!this.isValid())
			throw new MachineException("Must be a valid machine!");
		State temp = this.currState;
		String output = new String();
		for (int i = 0; i < input.length(); i++) {
			output += this.step(input.charAt(i), false);
		}

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

	@SuppressWarnings("unused")
	public core.Mealy.Machine toMealy() throws core.Mealy.MachineException {
		core.Mealy.Machine m = new core.Mealy.Machine(this.id + " --> Mealy");

		m.setiAlphabet(new HashSet<Character>(this.iAlphabet));
		m.setoAlphabet(new HashSet<Character>(this.oAlphabet));

		for (State currState : this.states)
			m.addState();

		m.setCurrState(m.getStates().get(this.states.indexOf(this.currState)));

		for (int i = 0; i < this.states.size(); i++) {
			for (Translation currTranslation : this.states.get(i).getTranslations()) {
				m.getStates().get(i)
						.addTranslation(new core.Mealy.Translation(currTranslation.getInput(),
								currTranslation.getTarget().getOutput(), m.getStates().get(i),
								m.getStates().get(this.states.indexOf((currTranslation.getTarget())))));
			}
		}

		return m;
	}

	public static Set<Character> getSymbols(String sentence) {
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
			for (Translation currTranslation : currState.getTranslations()) {
				translations.add(currTranslation);
				// currTranslation.setParent(currState);
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
		if (currState == s)
			currState = null;
	}

	/**
	 * Finds a state by ID.
	 * 
	 * Iterates over the states of the machine, and returns the state which has
	 * an {@code id} equal to the one given as parameter. Returns null if there
	 * is no such state.
	 * 
	 * @param id
	 *            The id the method searches for.
	 * @return The {@link core.Moore.State State} with the same id, null if
	 *         there is no such state.
	 */
	public State findState(String id) {
		State s = null;

		for (State currState : this.states) {
			if (currState.getID().equals(id))
				s = currState;
		}

		return s;
	}

	public void addTranslation(String parentID, String targetID, Character input) throws MachineException {
		State s = this.findState(parentID);
		if (s == null)
			throw new MachineException("There is no such state described as parent.");
		State t = this.findState(targetID);
		if (t == null)
			throw new MachineException("There is no such state described as target.");
		if (!iAlphabet.contains(input))
			throw new MachineException("Input symbol must be included in the input alphabet.");
		s.addTranslation(new Translation(s, input, t));
	}

	public void removeTranslation(Translation t) {
		t.getParent().getTranslations().remove(t);
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
				output += "[ " + currTranslation.getParent().getID() + "/" + currTranslation.getInput() + " ---> q"
						+ this.states.indexOf(currTranslation.getTarget()) + " ]\n";
			}
		}
		return output;
	}

}
