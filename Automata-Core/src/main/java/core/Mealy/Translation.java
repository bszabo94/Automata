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

/**
 * <h1>Translation of a Mealy Machine</h1>
 * 
 * <P>
 * Translations describe unequivocal operations between the states of a machine.
 * Each translation consist of four elements: {@code parent}, {@code target},
 * {@code input} and {@code output}. Translations are stored in a list, within
 * the {@code State} the translation describes as it's parent.
 * 
 * For every input symbol, the Machine finds the correct translation: where the
 * parent state is the current state of the machine, and the input of the
 * translation is the same symbol given as input, and executes the operation the
 * translation describes: moves to the target state, and returns the output,
 * which was associated to the input by the translation.
 * 
 * @author bszabo
 * @version 1.0
 * @see core.Mealy.Machine Machine
 */
public class Translation {
	private Character input, output;
	private State parent, target;

	/**
	 * Empty constructor
	 * 
	 * <P>
	 * An empty constructor, used only by tests.
	 * 
	 * @see core.Mealy.Machine#Machine(String, java.util.Set, java.util.Set)
	 *      Machine(String, Set, Set)
	 */
	public Translation() {
		this.parent = null;
		this.target = null;
		this.input = null;
		this.output = null;
	}

	/**
	 * Constructor of Translation
	 * 
	 * The constructor, which provides a fully funcation {@code Translation}
	 * object.
	 * 
	 * @param input
	 *            The input symbol for the translation.
	 * @param output
	 *            The output symbol for the translation.
	 * @param parent
	 *            The parent {@code State} of the translation.
	 * @param target
	 *            The target {@code State} of the translation.
	 */
	public Translation(Character input, Character output, State parent, State target) {
		this();
		this.input = input;
		this.output = output;
		this.target = target;
		this.parent = parent;
	}

	/**
	 * Gets the parent.
	 * 
	 * <P>
	 * Gets the parent {@code State} of the translation.
	 * 
	 * @return The {@code State} object described as parent.
	 */
	public State getParent() {
		return parent;
	}

	/**
	 * Sets the parent.
	 * <P>
	 * Sets the parent state of the translation to the {@code State} object
	 * given as parameter.
	 * 
	 * @param parent
	 *            The {@code State} object that will be the new parent.
	 */
	public void setParent(State parent) {
		this.parent = parent;
	}

	/**
	 * Gets the input.
	 * <P>
	 * Gets the {@code Character} that is set as input for the translation.
	 * 
	 * @return The {@code Character} object stored in the translation as input.
	 */
	public Character getInput() {
		return input;
	}

	/**
	 * Sets the input.
	 * <P>
	 * Sets the {@code Character} given as parameter as the input of the
	 * translation.
	 * 
	 * @param input
	 *            The character that will be set as input.
	 */
	public void setInput(Character input) {
		this.input = input;
	}

	/**
	 * Gets the output.
	 * <P>
	 * Gets the {@code Character} that is set as output for the translation.
	 * 
	 * @return The {@code Character} object stored in the translation as output.
	 */
	public Character getOutput() {
		return output;
	}

	/**
	 * Sets the output.
	 * <P>
	 * Sets the {@code Character} given as parameter as the output of the
	 * translation.
	 * 
	 * @param output
	 *            The character that will be set as output.
	 */
	public void setOutput(Character output) {
		this.output = output;
	}

	/**
	 * Gets the target.
	 * 
	 * <P>
	 * Gets the target {@code State} of the translation.
	 * 
	 * @return The {@code State} object described as target.
	 */
	public State getTarget() {
		return target;
	}

	/**
	 * Sets the target.
	 * <P>
	 * Sets the target state of the translation to the {@code State} object
	 * given as parameter.
	 * 
	 * @param target
	 *            The {@code State} object that will be the new target.
	 */
	public void setTarget(State target) {
		this.target = target;
	}

}
