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

public class State {
	private String id;
	private List<Translation> translations;

	public State() {
		this.translations = new ArrayList<Translation>();
		this.id = null;
	}

	public State(int n) {
		this.translations = new ArrayList<Translation>();
		this.id = "q" + Integer.toString(n);
	}

	public void addTranslation(Translation newTranslation) {
		this.translations.add(newTranslation);
	}

	public List<Translation> getTranslations() {
		return this.translations;
	}

	public String getID() {
		return this.id;
	}

}
