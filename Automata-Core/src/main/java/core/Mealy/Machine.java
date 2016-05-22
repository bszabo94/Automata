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
	 * The initial state of the Machine. This can only changed by the
	 * {@link core.Mealy.Machine#step(Character, boolean) step(Character,
	 * boolean)} method.
	 * 
	 * @see core.Mealy.Machine#step(Character, boolean) step(Character, boolean)
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
	 * Creates a new {@link core.Mealy.State State} object with the same id as
	 * the string given as parameter, and puts it to the
	 * {@link core.Mealy.Machine#states list}, which contains the states of this
	 * machine.
	 * 
	 * @param id
	 *            The string, which will be {@code id} of the new state;
	 * @throws MachineException
	 *             if the give {@code id} is empty or duplicate.
	 * @see core.Mealy.Machine#addState() addState()
	 */
	public void addState(String id) throws MachineException {
		if (id == null || id.equals(""))
			throw new MachineException("The ID must not be empty.");
		if (this.findState(id) != null)
			throw new MachineException("There is already a state in the machine with the ID: " + id + ".");
		this.states.add(new State(id));
	}

	/**
	 * Adds a new state to the machine without an id.
	 * 
	 * <P>
	 * Creates a new {@link core.Mealy.State State} object with an id of "qn",
	 * where n stands for the number given as parameter, and puts it to the
	 * {@link core.Mealy.Machine#states list}, which contains the states of this
	 * machine.
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

	public void setCurrStateById(String id) throws MachineException {
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
		if (this.states.size() > 0 || this.getTranslationsAsList().size() > 0)
			throw new MachineException(
					"Only empty Machines can be initialized. No state or translation must exists in the machine.");
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
	 * A machine is considered valid if:
	 * <ul>
	 * <li>it has an initial state (the reference does not point to null)</li>
	 * <li>each of it's states has unique id</li>
	 * <li>each states contains a translation for all of the characters of the
	 * input alphabet</li>
	 * <li>does not assign the same output to different inputs</li>
	 * <li>each state assign only one translation to each symbol of the input
	 * alphabet</li>
	 * <li>no translation contains symbol as input or output that is not a
	 * member of the corresponding set</li>
	 * </ul>
	 * <P>
	 * Following these, the machine will not be ambiguous, both encoding and
	 * decoding will be clear.
	 * 
	 * 
	 * @return True, if the machine is valid, and false otherwise.
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

	/**
	 * Moves the machine, according to the given input.
	 * 
	 * One unit of the encoding, or decoding process. Whether the
	 * {@code encoding} parameter is set to true or false, the method will
	 * simulate encoding or decoding in the machine.
	 * <ul>
	 * <li>If encoding, the method will iterate over the translations of the
	 * current state of the machine and, once found the translation with the
	 * same input as given in {@code input}, it will set the current state of
	 * the machine to the target state, and return the output symbol of the
	 * translation assigned to the given input.</li>
	 * <li>If decoding, the method will iterate over the translations of the
	 * current state of the machine and, once found the translation with the
	 * same output as given in {@code input}, it will set the current state of
	 * the machine to the target state, and return the input symbol of the
	 * translation assigned to the given output.</li>
	 * </ul>
	 * 
	 * @param input
	 *            The symbol we use as input.
	 * @param encoding
	 *            True will set the method to encoding, and false will set to
	 *            decoding.
	 * @return The symbol od the corresponding translation.
	 * @throws core.Mealy.MachineException
	 */
	public Character step(Character input, boolean encoding) throws MachineException {
		if (encoding) {
			if (!this.iAlphabet.contains(input))
				throw new MachineException(
						"The given symbol is not in the input alphabet: " + input + " in " + this.iAlphabet);
			for (Translation currTranslation : this.currState.getTranslations()) {
				if (currTranslation.getInput().equals(input)) {
					this.currState = currTranslation.getTarget();
					return currTranslation.getOutput();
				}
			}
		} else {
			if (!this.oAlphabet.contains(input))
				throw new MachineException("The given symbol is not in the output alphabet.");
			for (Translation currTranslation : this.currState.getTranslations()) {
				if (currTranslation.getOutput().equals(input)) {
					this.currState = currTranslation.getTarget();
					return currTranslation.getInput();
				}
			}
		}
		return null;
	}

	/**
	 * Encodes the given string.
	 * 
	 * Uses the given string as input, and encodes it with the usage of the
	 * machine. Throws {@link core.Mealy.MachineException MachineException} if
	 * one character of the given string is not included in the input alphabet
	 * of the machine.
	 * 
	 * @param input
	 *            The string it encodes.
	 * @return The output string after the encoding.
	 * @throws MachineException
	 */
	public String encode(String input) throws core.Mealy.MachineException {
		if (!this.isValid())
			throw new core.Mealy.MachineException("Must be a valid machine!");
		State temp = this.currState;
		String output = new String();
		for (int i = 0; i < input.length(); i++) {
			output += this.step(input.charAt(i), true);
		}

		// this.currState = this.states.get(0);
		this.currState = temp;

		return output;
	}

	/**
	 * Decodes the given string.
	 * 
	 * Uses the given string as input, and Decodes it with the usage of the
	 * machine. Throws {@link core.Mealy.MachineException MachineException} if
	 * one character of the given string is not included in the output alphabet
	 * of the machine.
	 * 
	 * @param input
	 *            The string it decodes.
	 * @return The output string after the decoding.
	 * @throws MachineException
	 *             if one character of the given string is not included in the
	 *             ouput alphabet.
	 */
	public String decode(String input) throws MachineException {
		if (!this.isValid())
			throw new MachineException("Must be a valid machine!");
		State temp = this.currState;
		String output = new String();
		for (int i = 0; i < input.length(); i++) {
			output += this.step(input.charAt(i), false);
		}

		// this.currState = this.states.get(0);
		this.currState = temp;

		return output;

	}

	/**
	 * Creates a machine that is capable of encoding and decoding the given
	 * text.
	 * 
	 * Initaializes a machine, which input and output symbols are equal. All
	 * symbols contained by the alphabets are characters of the given text in
	 * the {@code data} parameter.
	 * 
	 * @param data
	 *            The text the machine uses the characters of.
	 */
	public void processData(String data) {
		Set<Character> base = getSymbols(data);
		try {
			this.init(base, base);
		} catch (MachineException e) {
			// can never reach here
			e.printStackTrace();
		}

	}

	/**
	 * Converts the Mealy Machine to a Moore Machine.
	 * <P>
	 * Converts the Mealy Machine to a Moore Machine. The difference between the
	 * two, is that while the Mealy uses inputs given in the translations, the
	 * Moore stores the outputs in the states, and not the translations.
	 * Therefore all translations leading to the same state can return only the
	 * same output. If there are translations leading to the same state with
	 * different output in the initial Mealy Machine, then new states will be
	 * introduced in the new Moore Machine with the corresponding outputs, and
	 * reassigned new translations.
	 * 
	 * @return The Moore Machine converted from the Mealy Machine.
	 * @throws core.Moore.MachineException
	 */
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
				m.addState(currChar, currState.getID() + Integer.toString(i));
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

	/**
	 * Creates a Set containing the characters of the given string.
	 * 
	 * Extracts all characters from the given string, and creates a Set of
	 * Characters from the extracted symbols.
	 * 
	 * @param sentence
	 *            The sentence or text we extracts the smybols from.
	 * @return The set of characters, containing all the symbols found in the
	 *         given sentence or text.
	 */
	public static Set<Character> getSymbols(String sentence) {
		Set<Character> symbols = new HashSet<Character>();
		for (int i = 0; i < sentence.length(); i++) {
			symbols.add(sentence.charAt(i));
		}

		return symbols;
	}

	/**
	 * Creates a map for the state-translation connections of the machine.
	 * 
	 * <P>
	 * Creates a map where keys will be the states of the machine, and for each
	 * state assigns a list, that contains all the translations of the machine
	 * where the state used as key is the parent of the translations.
	 * 
	 * @return A Map object, defining all state-translation connections of the
	 *         machine.
	 */
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

	/**
	 * Creates a list for all translations of the machine.
	 * 
	 * <P>
	 * Creates a list that contains every translations of the machine,
	 * regardless of their parent state.
	 * 
	 * @return A List that contains all Translations that occur in the machine.
	 */
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

	/**
	 * Removes a state from the machine.
	 * 
	 * <P>
	 * Removes the state from the machine, that ID is equal to the given
	 * {@code id} parameter. Removing a state includes removing all translations
	 * from the machine where the removed state appears as either parent or
	 * target. Throws a {@link core.Mealy.MachineException MachineException} if
	 * there is no state in the machine with the given id.
	 * 
	 * @param id
	 *            The {@code ID} of the state to be removed.
	 * @throws MachineException
	 */
	public void removeState(String id) throws MachineException {
		State s = this.findState(id);
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
	 * @return The {@link core.Mealy.State State} with the same id, null if
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

	/**
	 * Adds a new translation to the machine.
	 * 
	 * <P>
	 * Initializes a new {@link core.Mealy.Translation Translation} object, and
	 * adds it to the translation list of the {@code parent} state given as
	 * parameter.
	 * 
	 * @param parentID
	 *            The ID of the state, which will be the parent state of the new
	 *            translation.
	 * @param targetID
	 *            The ID of the state, which will be the target state of the new
	 *            translation.
	 * @param input
	 *            The input symbol of the new translation.
	 * @param output
	 *            The output symbol of the new translation.
	 * @throws MachineException
	 *             If there is no state in the machine with the given ID either
	 *             given as parent or target. Also throws an exception when
	 *             input or output symbol is not included in the input or output
	 *             alaphabets.
	 */
	public void addTranslation(String parentID, String targetID, Character input, Character output)
			throws MachineException {
		State s = this.findState(parentID);
		if (s == null)
			throw new MachineException("There is no such state described as parent.");
		State t = this.findState(targetID);
		if (t == null)
			throw new MachineException("There is no such state described as target.");
		if (!iAlphabet.contains(input) || !oAlphabet.contains(output))
			throw new MachineException("Input and output symbols must be included in their corresponding alphabets.");
		s.addTranslation(new Translation(input, output, s, t));
	}

	/**
	 * Remove a translation from the machine.
	 * 
	 * <P>
	 * Removes the {@code t} translation given as parameter from the
	 * translations of it's parent state in the machine. Since translations are
	 * stored only in their parent states translations list it removes it from
	 * the machine completely.
	 * 
	 * @param t
	 *            The translations to be removed.
	 */
	public void removeTranslation(Translation t) {
		t.getParent().getTranslations().remove(t);
	}

	public String toString() {
		String output = new String();
		output += "Machine: " + this.id + "\n";
		output += "Input Alphabet: " + this.iAlphabet + "\n";
		output += "Output Alphabet: " + this.oAlphabet + "\n";
		for (State currState : this.states) {
			output += "---State " + currState.getID() + "---\n";
			for (Translation currTranslation : currState.getTranslations()) {
				output += "[ " + currTranslation.getParent().getID() + "/" + currTranslation.getInput() + " ---> "
						+ currTranslation.getOutput() + " / q" + currTranslation.getTarget().getID()
						+ " ]\n";
			}
		}
		return output;
	}

}
