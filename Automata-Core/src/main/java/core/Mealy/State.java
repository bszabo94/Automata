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
import java.util.List;

/**
 * <h1>State of a Mealy Machine</h1>
 * <P>
 * Describes a State of a Mealy Machine. Each State consist of an {@code ID} and
 * a List of {@code Translations}, in which every Translation refers the
 * {@code State} as parent.
 * 
 * @author bszabo
 * @version 1.0
 * @see core.Mealy.Machine Mealy Machine
 *
 */
public class State {
	private String id;
	private List<Translation> translations;

	/**
	 * Constructor of the State.
	 * <P>
	 * A constructor which initalizes the {@code List} that contains the
	 * {@code Translations} of the State, and initailizes the state with a
	 * {@code null} ID.
	 */
	public State() {
		this.translations = new ArrayList<Translation>();
		this.id = null;
	}

	/**
	 * Constructor of the State.
	 * <P>
	 * A constructor which initalizes the {@code List} that contains the
	 * {@code Translations} of the State, and initailizes the state with a "qn"
	 * ID, where {@code n}. stands for the number given as paramter.
	 * 
	 * @param n
	 *            The number, from which the ID will be set.
	 */
	public State(int n) {
		this.translations = new ArrayList<Translation>();
		this.id = "q" + Integer.toString(n);
	}

	/**
	 * Constructor of the State.
	 * <P>
	 * A constructor which initalizes the {@code List} that contains the
	 * {@code Translations} of the State, and initailizes the state with the
	 * given {@code String} as ID.
	 * 
	 * @param s
	 *            The String which will serve as the ID of the {@code State}.
	 */
	public State(String s) {
		this.translations = new ArrayList<Translation>();
		this.id = s;
	}

	/**
	 * Adds a translation to the state.
	 * 
	 * <P>
	 * In the machines, all translations are stored in a List within their
	 * corresponding parent states. This method adds the {@code Translation}
	 * object given as paramter to the translations list of this state.
	 * 
	 * @param newTranslation
	 *            The {@code Translation} object to be added.
	 * @see core.Mealy.Translation Translation
	 */
	public void addTranslation(Translation newTranslation) {
		this.translations.add(newTranslation);
	}

	/**
	 * Gets the translations of the state.
	 * <P>
	 * Gets all {@code Translation} objects, that has the {@code State} as
	 * parent, and are contained by the State.
	 * 
	 * @return A {@code List} containing all of the state's translations.
	 */
	public List<Translation> getTranslations() {
		return this.translations;
	}

	/**
	 * Gets the ID of the State.
	 * 
	 * Gets the {@code ID} assigned to the {@code} object.
	 * 
	 * @return The ID of the State.
	 */
	public String getID() {
		return this.id;
	}

}
