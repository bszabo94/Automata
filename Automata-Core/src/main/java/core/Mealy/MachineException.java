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
 * A Machine Exception
 * 
 * <P>
 * MachineException mainly serves to control the creation and modification of
 * the machines to be syntactially correct, clear and transparent. Makes
 * possible to avoid any incorrect form of addition or delition from the machine
 * that would cause logical problems and errors later.
 * 
 * @author bszabo
 * @version 1.0
 * @see core.Mealy.Machine Machine
 */
@SuppressWarnings("serial")
public class MachineException extends Exception {

	/**
	 * Constructor
	 * <P>
	 * Constructor for the exception. Most of the times initialized with an
	 * error message to be informative.
	 * 
	 * @param message
	 *            The error message.
	 */
	public MachineException(String message) {
		super(message);
	}

}
