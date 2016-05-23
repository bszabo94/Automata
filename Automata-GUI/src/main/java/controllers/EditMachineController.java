// CHECKSTYLE:OFF
package controllers;



/*
 * #%L
 * Automata-GUI
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

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import main.Main;

public class EditMachineController {

	private Main main;

	public void setMain(Main main) {
		this.main = main;
	}

	public EditMachineController(Main main) {
		super();
		this.main = main;
	}

	public EditMachineController() {
		super();
	}

	@FXML
	TextField newMachineID;

	@FXML
	TextField newCurrStateID;

	@FXML
	TextField newSymbols;

	@FXML
	private void handleButtonCancel(ActionEvent event) {
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
		main.getPrimaryStage().toFront();
	}

	@FXML
	private void handleIAAdd(ActionEvent event) {
		try {
			if (main.getSelectedMealy() != null) {
				main.getSelectedMealy().addiAlphabet(newSymbols.getText());
			} else {
				main.getSelectedMoore().addiAlphabet(newSymbols.getText());
			}
			handleButtonCancel(event);
		} catch (core.Mealy.MachineException | core.Moore.MachineException e) {
			main.showPopup(e.getMessage(), AlertType.ERROR);
		}
	}

	@FXML
	private void handleOAAdd(ActionEvent event) {
		try {
			if (main.getSelectedMealy() != null) {
				main.getSelectedMealy().addoAlphabet(newSymbols.getText());
			} else {
				main.getSelectedMoore().addoAlphabet(newSymbols.getText());
			}
			handleButtonCancel(event);
		} catch (core.Mealy.MachineException | core.Moore.MachineException e) {
			main.showPopup(e.getMessage(), AlertType.ERROR);
		}
	}

	@FXML
	private void handleIARemove(ActionEvent event) {
		try {
			if (main.getSelectedMealy() != null) {
				main.getSelectedMealy().removeiAlphabet(newSymbols.getText());
			} else {
				main.getSelectedMoore().removeiAlphabet(newSymbols.getText());
			}
			handleButtonCancel(event);
		} catch (core.Mealy.MachineException | core.Moore.MachineException e) {
			main.showPopup(e.getMessage(), AlertType.ERROR);
		}
	}

	@FXML
	private void handleOARemove(ActionEvent event) {
		try {
			if (main.getSelectedMealy() != null) {
				main.getSelectedMealy().removeoAlphabet(newSymbols.getText());
			} else {
				main.getSelectedMoore().removeoAlphabet(newSymbols.getText());
			}
			handleButtonCancel(event);
		} catch (core.Mealy.MachineException | core.Moore.MachineException e) {
			main.showPopup(e.getMessage(), AlertType.ERROR);
		}
	}

	@FXML
	private void handleRenameMachine(ActionEvent event) {
		if (main.getSelectedMealy() != null) {
			main.getSelectedMealy().setID(newMachineID.getText());

			// TODO refresh
		} else {
			main.getSelectedMoore().setID(newMachineID.getText());
		}
	}

	@FXML
	private void handleSetCurrState(ActionEvent event) {
		try {
			if (main.getSelectedMealy() != null) {
				main.getSelectedMealy().setCurrStateById(newCurrStateID.getText());
			} else {
				main.getSelectedMoore().setCurrStateById(newCurrStateID.getText());
			}
		} catch (core.Mealy.MachineException | core.Moore.MachineException e) {
			main.showPopup(e.getMessage(), AlertType.ERROR);
		}
	}

}
