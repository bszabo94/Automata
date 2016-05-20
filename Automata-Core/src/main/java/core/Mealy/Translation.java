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

public class Translation {
	private Character input, output;
	private State parent, target;

	public Translation() {
		this.parent = null;
		this.target = null;
		this.input = null;
		this.output = null;
	}

	public Translation(Character input, Character output, State parent, State target) {
		this();
		this.input = input;
		this.output = output;
		this.target = target;
		this.parent = parent;
	}

	public State getParent() {
		return parent;
	}

	public void setParent(State parent) {
		this.parent = parent;
	}

	public Character getInput() {
		return input;
	}

	public void setInput(Character input) {
		this.input = input;
	}

	public Character getOutput() {
		return output;
	}

	public void setOutput(Character output) {
		this.output = output;
	}

	public State getTarget() {
		return target;
	}

	public void setTarget(State target) {
		this.target = target;
	}

}
