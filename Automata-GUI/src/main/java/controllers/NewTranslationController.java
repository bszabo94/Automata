package controllers;

import core.Mealy.MachineException;

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
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.Main;

public class NewTranslationController {

	private Main main;

	public void setMain(Main main) {
		this.main = main;
		mealy = main.getSelectedMealy();
		moore = main.getSelectedMoore();

		if (moore != null) {
			newTranslationLabelOutput.setVisible(false);
			oSymbol.setVisible(false);
		}
	}

	public NewTranslationController(Main main) {
		super();
		this.main = main;
	}

	public NewTranslationController() {
		super();
	}

	@FXML
	Label newTranslationLabelOutput;

	@FXML
	TextField parentID;

	@FXML
	TextField targetID;

	@FXML
	TextField iSymbol;

	@FXML
	TextField oSymbol;

	private core.Mealy.Machine mealy;
	private core.Moore.Machine moore;

	@FXML
	private void handleButtonCancel(ActionEvent event) {
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
		main.getPrimaryStage().toFront();
	}

	@FXML
	private void handleButtonCreate(ActionEvent event) {
		try {
			if (iSymbol.getText().length() > 1 || oSymbol.getText().length() > 1) {
				main.showPopup("Input and output smybols must be a single characters.", AlertType.ERROR);
				return;
			}
			if (mealy != null) {
				mealy.addTranslation(parentID.getText(), targetID.getText(), iSymbol.getText().charAt(0),
						oSymbol.getText().charAt(0));
				handleButtonCancel(event);
			} else {
				moore.addTranslation(parentID.getText(), targetID.getText(), iSymbol.getText().charAt(0));
			}
		} catch (core.Mealy.MachineException | core.Moore.MachineException e) {
			main.showPopup(e.getMessage(), AlertType.ERROR);
		}

	}

}
