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

public class RemoveStateController {

	private Main main;

	public void setMain(Main main) {
		this.main = main;
	}

	public RemoveStateController(Main main) {
		super();
		this.main = main;
	}

	public RemoveStateController() {
		super();
	}

	@FXML
	TextField removeID;

	@FXML
	Label removeTitle;

	@FXML
	private void handleButtonCancel(ActionEvent event) {
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
		main.getPrimaryStage().toFront();
	}

	@FXML
	private void handleButtonRemove(ActionEvent event) {
		try {
			if (main.getSelectedMealy() != null) {
				main.getSelectedMealy().removeState(removeID.getText());
			}
			if (main.getSelectedMoore() != null) {
				main.getSelectedMoore().removeState(removeID.getText());
			}
			handleButtonCancel(event);
		} catch (Exception e) {
			main.showPopup(e.getMessage(), AlertType.WARNING);
		}

	}

	@FXML
	private void initialize() {
		//TODO
//		if (main.getSelectedMealy() != null) {
//			removeTitle.setText("Remove State from " + main.getSelectedMealy().getID());
//		}
//		if (main.getSelectedMoore() != null) {
//			removeTitle.setText("Remove State from " /*+ main.getSelectedMoore().getID()*/);
//		}
//		System.out.println(main.getSelectedMealy().getID());
	}

}
