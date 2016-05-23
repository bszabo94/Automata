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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import main.Main;

public class AddStateController {

	private Main main;

	public void setMain(Main main) {
		this.main = main;
		if (main.getSelectedMealy() != null) {
			labelOutput.setVisible(false);
			// labelOutput.setDisable(true);
			addOutput.setVisible(false);
			// addOutput.setDisable(true);
		}
	}

	public AddStateController(Main main) {
		super();
		this.main = main;

	}

	public AddStateController() {
		super();
	}

	@FXML
	TextField addID;

	@FXML
	Label addTitle;

	@FXML
	TextField addOutput;

	@FXML
	Label labelOutput;

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
			if (main.getSelectedMealy() != null) {
				main.getSelectedMealy().addState(addID.getText());
			}
			if (main.getSelectedMoore() != null) {
				main.getSelectedMoore().addState(addOutput.getText(), addID.getText());
			}
			handleButtonCancel(event);
		} catch (Exception e) {
			main.showPopup(e.getMessage(), AlertType.ERROR);
		}

	}

}
