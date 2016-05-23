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
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;

/**
 * <h1>Moore Machine</h1>
 * <P>
 * Describes the representation of a Moore Machine, a deterministic finite-state
 * machine.
 * 
 * @author bszabo
 * @version 1.0
 * @see <a href="https://en.wikipedia.org/wiki/Moore_machine">Moore Machines on
 *      Wikipedia</a>
 *
 */
public class Machine {
	private List<State> states;
	private String id;
	private Set<Character> iAlphabet, oAlphabet;
	private State currState;

	/**
	 * <h1>Constructor of Machine</h1>
	 * 
	 * <P>
	 * Creates a Machine object with empty input and output alphabets, and
	 * without states and translations.
	 * 
	 * @param id
	 *            The value of the {@code id} will be the ID of the Machine.
	 * 
	 * @throws core.Moore.MachineException
	 *             if the given id is an empty string.
	 * @see core.Moore.Machine#Machine(String, Set, Set) Machine(String, Set,
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
	 * and without states and translations.
	 * 
	 * @param id
	 *            The value of the {@code id} will be the ID of the Machine.
	 * @param iAlphabet
	 *            The set of characters, which will be used as input alphabet
	 *            for the machine.
	 * @param oAlphabet
	 *            The set of characters, which will be used as output alphabet
	 *            for the machine.
	 * 
	 * @throws core.Moore.MachineException
	 *             if the given id is an empty string.
	 * 
	 * @see core.Moore.Machine#Machine(String) Machine(String)
	 */
	public Machine(String id, Set<Character> iAlphabet, Set<Character> oAlphabet) throws MachineException {
		if (id == null || id.equals(""))
			throw new MachineException("The ID must not be empty.");
		this.id = id;
		this.states = new ArrayList<State>();
		this.currState = null;
		this.init(iAlphabet, oAlphabet);
	}

	/**
	 * Provides a String indicating the type of the machine.
	 * 
	 * @return The "Moore" {@code String}.
	 */
	public String getType() {
		return "Moore";
	}

	/**
	 * Gets all states of the machine.
	 * 
	 * @return A {@code List} containg all {@code State}s of the machine.
	 */
	public List<State> getStates() {
		return states;
	}

	/**
	 * Adds a new state to the machine without an id.
	 * 
	 * <P>
	 * Creates a new {@link core.Moore.State State} object without and id or
	 * output, and puts it to the list, which contains the states of this
	 * machine.
	 * 
	 * @see core.Moore.Machine#addState(Character) addState(Character)
	 */
	public void addState() {
		this.states.add(new State());
	}

	/**
	 * Adds a new state to the machine without an id.
	 * 
	 * <P>
	 * Creates a new {@link core.Moore.State State} object without an id and
	 * with the given output, and puts it to the list, which contains the states
	 * of this machine.
	 * 
	 * @param output
	 *            The output of the new state.
	 */
	public void addState(Character output) {
		this.states.add(new State(output));
	}

	/**
	 * Adds a new state to the machine without an id.
	 * 
	 * <P>
	 * Creates a new {@link core.Moore.State State} object with the same output
	 * as the string given as parameter, and "qn" id where n stands for the
	 * number given as paramter, and adds it to the
	 * {@link core.Moore.Machine#states list}, which contains the states of this
	 * machine.
	 * 
	 * @param output
	 *            The character, which will be the output of the state.
	 * @param n
	 *            The number which will be used for id genration.
	 * 
	 * @see core.Mealy.Machine#addState() addState()
	 */
	public void addState(Character output, int n) {
		this.states.add(new State(output, "q" + Integer.toString(n)));
	}

	/**
	 * Adds a new state to the machine without an id.
	 * 
	 * <P>
	 * Creates a new {@link core.Moore.State State} object with the same output
	 * as the string given as parameter, and the same id as the one given as
	 * parameter, and adds it to the {@link core.Moore.Machine#states list},
	 * which contains the states of this machine.
	 * 
	 * @param output
	 *            The character, which will be the output of the state.
	 * @param id
	 *            The string, which will be {@code id} of the new state.
	 * @throws MachineException
	 *             if the give {@code id} is empty or duplicate.
	 * @see core.Mealy.Machine#addState() addState()
	 */
	public void addState(Character output, String id) throws MachineException {
		if (id == null || id.equals(""))
			throw new MachineException("The ID must not be empty.");
		if (this.findState(id) != null)
			throw new MachineException("There is already a state in the machine with the ID: " + id);
		this.states.add(new State(output, id));
	}

	/**
	 * Adds a new state to the machine without an id.
	 * 
	 * <P>
	 * Creates a new {@link core.Moore.State State} object with the same output
	 * as the string given as parameter, and the same id as the one given as
	 * parameter, and adds it to the {@link core.Moore.Machine#states list},
	 * which contains the states of this machine.
	 * 
	 * @param output
	 *            The character, which will be the output of the state.
	 * @param id
	 *            The string, which will be {@code id} of the new state.
	 * @throws MachineException
	 *             if the give {@code id} is empty or duplicate, or the given
	 *             output is longer than one character.
	 * @see core.Mealy.Machine#addState() addState()
	 */
	public void addState(String output, String id) throws MachineException {
		if (id == null || id.equals(""))
			throw new MachineException("The ID must not be empty.");
		if (this.findState(id) != null)
			throw new MachineException("There is already a state in the machine with the ID: " + id + ".");
		if (output.length() > 1)
			throw new MachineException("Output is too large. It must be a single Character of Symbol.");
		this.states.add(new State(output.charAt(0), id));
	}

	/**
	 * Gets the State which marked as "current state" in the machine.
	 * 
	 * @return The {@code State} object referred as current state.
	 */
	public State getCurrState() {
		return currState;
	}

	/**
	 * Gets the {@code ID} of the machine.
	 * 
	 * @return The {@code String} used as ID for the machine.
	 */
	public String getID() {
		return id;
	}

	/**
	 * Sets a new {@code ID} for the machine.
	 * 
	 * @param id
	 *            The new {@code ID} of the machine.
	 */
	public void setID(String id) {
		this.id = id;
	}

	/**
	 * Sets a new input alphabet.
	 * <P>
	 * Changes the input alphabet of the machine for the new one given as
	 * paramter.
	 * 
	 * @param iAlphabet
	 *            A {@code Set} of {@code Characters} serving as new input
	 *            alphabet.
	 */
	public void setiAlphabet(Set<Character> iAlphabet) {
		this.iAlphabet = iAlphabet;
	}

	/**
	 * Sets a new output alphabet.
	 * <P>
	 * Changes the output alphabet of the machine for the new one given as
	 * paramter.
	 * 
	 * @param oAlphabet
	 *            A {@code Set} of {@code Characters} serving as new output
	 *            alphabet.
	 */
	public void setoAlphabet(Set<Character> oAlphabet) {
		this.oAlphabet = oAlphabet;
	}

	/**
	 * Extends the input alphabet.
	 * <P>
	 * Extends the input alphabet with all charachters of the given
	 * {@code String}.
	 * 
	 * @param i
	 *            The string containing all the new characters.
	 * @throws MachineException
	 *             If founds a character in the string, which is already
	 *             included in the input alphabet.
	 */
	public void addiAlphabet(String i) throws MachineException {
		for (Character currChar : i.toCharArray()) {
			if (this.iAlphabet.contains(currChar))
				throw new MachineException(currChar + "is already in the Input Alhpabet.");
			this.iAlphabet.add(currChar);
		}
	}

	/**
	 * Extends the output alphabet.
	 * <P>
	 * Extends the output alphabet with all charachters of the given
	 * {@code String}.
	 * 
	 * @param i
	 *            The string containing all the new characters.
	 * @throws MachineException
	 *             If founds a character in the string, which is already
	 *             included in the output alphabet.
	 */
	public void addoAlphabet(String i) throws MachineException {
		for (Character currChar : i.toCharArray()) {
			if (this.oAlphabet.contains(currChar))
				throw new MachineException(currChar + "is already in the Output Alhpabet.");
			this.oAlphabet.add(currChar);
		}
	}

	/**
	 * Removes characters from the input alphabet
	 * <P>
	 * Removes all of characters of a given string from the input alphabet of
	 * the machine.
	 * 
	 * @param i
	 *            The string containing all characters to be removed.
	 * @throws MachineException
	 *             If it reaches a character in the string which is not included
	 *             in the input alphabet of the machine.
	 */
	public void removeiAlphabet(String i) throws MachineException {
		for (Character currChar : i.toCharArray()) {
			if (!this.iAlphabet.contains(currChar))
				throw new MachineException(currChar + "is not in the Input Alhpabet.");
			this.iAlphabet.remove(currChar);
		}
	}

	/**
	 * Removes characters from the output alphabet
	 * <P>
	 * Removes all of characters of a given string from the output alphabet of
	 * the machine.
	 * 
	 * @param i
	 *            The string containing all characters to be removed.
	 * @throws MachineException
	 *             If it reaches a character in the string which is not included
	 *             in the output alphabet of the machine.
	 */
	public void removeoAlphabet(String i) throws MachineException {
		for (Character currChar : i.toCharArray()) {
			if (!this.oAlphabet.contains(currChar))
				throw new MachineException(currChar + "is not in the Output Alhpabet.");
			this.oAlphabet.remove(currChar);
		}
	}

	/**
	 * Sets a new current state.
	 * <P>
	 * Sets the given {@code State} object as new current state of the machine-
	 * 
	 * @param currState
	 *            The state, that will be the new current state.
	 */
	public void setCurrState(State currState) {
		this.currState = currState;
	}

	/**
	 * Sets a new current state.
	 * <P>
	 * Finds the {@code State} with the given id in the states of the machine,
	 * and sets it as current state.
	 * 
	 * @param id
	 *            The id of the new current state.
	 * @throws MachineException
	 *             If no state with the given id was found.
	 */
	public void setCurrStateById(String id) throws MachineException {
		State s = this.findState(id);
		if (s == null)
			throw new MachineException("There is no state with the id: " + id + ".");
		this.currState = s;
	}

	/**
	 * Gets the input alphabet of the machine.
	 * 
	 * @return The {@code Set} of {@code Characters} serving as input alphabet
	 *         for the machine.
	 */
	public Set<Character> getiAlphabet() {
		return iAlphabet;
	}

	/**
	 * Gets the output alphabet of the machine.
	 * 
	 * @return The {@code Set} of {@code Characters} serving as output alphabet
	 *         for the machine.
	 */
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
	 * state.
	 * 
	 * @param iAlphabet
	 *            The set of characters, that will be used as input alphabet.
	 * @param oAlphabet
	 *            The set of characters, that will be used as output alphabet.
	 * @throws core.Moore.MachineException
	 *             if the input alphabet contains more character than the output
	 *             alphabet. The machine initialized with this method is
	 *             considered valid.
	 */
	public void init(Set<Character> iAlphabet, Set<Character> oAlphabet) throws MachineException {
		if (iAlphabet.size() > oAlphabet.size())
			throw new MachineException("Input Alphabet must contain equal or less symbols than the Output Alphabet!");
		this.iAlphabet = new HashSet<Character>(iAlphabet);
		this.oAlphabet = new HashSet<Character>(oAlphabet);

		List<Character> inputAlphabet = new ArrayList<Character>(iAlphabet);
		List<Character> outputAlphabet = new ArrayList<Character>(oAlphabet);
//		Edit to exclude stream(), as Coberture misses the proper java8 support.
//		List<Integer> range = IntStream.range(0, iAlphabet.size()).boxed().collect(Collectors.toList());

		List<Integer> range = new ArrayList<Integer>();
		for(int i = 0; i < iAlphabet.size(); i++)
			range.add(i);

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
	 * <li>no translation contains symbol as input that is not a member of the
	 * input alphabet</li>
	 * <li>no state contains symbol as output that is not a member of the output
	 * alphabet</li>
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
	 * the machine to the target state, and return the output symbol of that
	 * state.</li>
	 * <li>If decoding, the method will iterate over the translations of the
	 * current state of the machine and, once found the translation leading to
	 * the state with the same output as given in {@code input}, it will set the
	 * current state of the machine to the target state, and return the input
	 * symbol of the translation assigned to the given output.</li>
	 * </ul>
	 * 
	 * @param input
	 *            The symbol we use as input.
	 * @param encoding
	 *            True will set the method to encoding, and false will set to
	 *            decoding.
	 * @return The symbol of the corresponding translation.
	 * @throws core.Moore.MachineException
	 *             if the given character is not included in it's corresponding
	 *             alphabet.
	 */
	public Character step(Character input, boolean encoding) throws MachineException {
		if (encoding) {
			if (!this.iAlphabet.contains(input))
				throw new MachineException(
						"The given symbol is not in the input alphabet: " + input + " in " + this.iAlphabet);
			for (Translation currTranslation : this.currState.getTranslations()) {
				if (currTranslation.getInput().equals(input)) {
					this.currState = currTranslation.getTarget();
					return currState.getOutput();
				}
			}
		} else {
			if (!this.oAlphabet.contains(input))
				throw new MachineException("The given symbol is not in the output alphabet.");
			for (Translation currTranslation : this.currState.getTranslations()) {
				if (currTranslation.getTarget().getOutput().equals(input)) {
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
	 * machine.
	 * 
	 * @param input
	 *            The string it encodes.
	 * @return The output string after the encoding.
	 * @throws MachineException
	 *             if one character of the given string is not included in the
	 *             input alphabet of the machine.
	 */
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

	/**
	 * Decodes the given string.
	 * 
	 * Uses the given string as input, and Decodes it with the usage of the
	 * machine. Throws {@link core.Moore.MachineException MachineException} if
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
		try {
			Set<Character> base = getSymbols(data);
			this.init(base, base);
		} catch (MachineException e) {
			// never reaches here
			e.printStackTrace();
		}
	}

	/**
	 * Converts the Moore Machine to a Mealy Machine.
	 * <P>
	 * Converts the Moore Machine to a Mealy Machine. The difference between the
	 * two, is that while the Moore stores the outputs in the states, the Mealy
	 * uses inputs given in the translations. Creates a new
	 * {@code Mealy Machine}, with the same amount of states and translations as
	 * the initial Moore Machine, and separates all the outputs from the states,
	 * assigning them to each translation leading to that state.
	 * 
	 * @return The Mealy Machine converted from the Moore Machine.
	 * @throws core.Mealy.MachineException
	 *             when the source machine contains syntactical errors.
	 */
	public core.Mealy.Machine toMealy() throws core.Mealy.MachineException {
		core.Mealy.Machine m = new core.Mealy.Machine(this.id + " --> Mealy");

		m.setiAlphabet(new HashSet<Character>(this.iAlphabet));
		m.setoAlphabet(new HashSet<Character>(this.oAlphabet));

		for (State currState : this.states)
			m.addState(currState.getID());

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
			for (Translation currTranslation : currState.getTranslations()) {
				translations.add(currTranslation);
				// currTranslation.setParent(currState);
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
	 * target.
	 * 
	 * @param id
	 *            The {@code ID} of the state to be removed.
	 * @throws MachineException
	 *             if there is no state in the machine with the given id.
	 */
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

	/**
	 * Adds a new translation to the machine.
	 * 
	 * <P>
	 * Initializes a new {@link core.Moore.Translation Translation} object, and
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
	 * @throws MachineException
	 *             If there is no state in the machine with the given ID either
	 *             given as parent or target. Also throws an exception when
	 *             input symbol is not included in the input alaphabet.
	 */
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

	@SuppressWarnings("checkstyle:javadocmethod")
	@Override
	public String toString() {
		String output = new String();
		output += "Machine: " + this.id + "\n";
		output += "Input Alphabet: " + this.iAlphabet + "\n";
		output += "Output Alphabet: " + this.oAlphabet + "\n";
		for (State currState : this.states) {
			output += "---State " + currState.getID() + " | output: " + currState.getOutput() + "---\n";
			for (Translation currTranslation : currState.getTranslations()) {
				output += "[ " + currTranslation.getParent().getID() + "/" + currTranslation.getInput() + " ---> q"
						+ currTranslation.getTarget().getID() + " ]\n";
			}
		}
		return output;
	}

}
