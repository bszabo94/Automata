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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import main.Main;

public class ReadController {

	private Main main;
	private String data;

	public void setMain(Main main, String data) {
		this.main = main;
		this.data = data;
	}

	public ReadController(Main main) {
		super();
		this.main = main;
	}

	public ReadController() {
		super();
	}

	@FXML
	RadioButton rbMealy;

	@FXML
	RadioButton rbMoore;

	@FXML
	TextField tfID;

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
			if (rbMealy.isSelected()) {
				core.Mealy.Machine m = new core.Mealy.Machine(tfID.getText());
				m.processData(this.data);
				main.getMealyMachines().add(m);
				handleButtonCancel(event);

			} else {
				core.Moore.Machine m = new core.Moore.Machine(tfID.getText());
				m.processData(this.data);
				main.getMooreMachines().add(m);
				handleButtonCancel(event);
			}
		} catch (core.Mealy.MachineException | core.Moore.MachineException e) {
			main.showPopup(e.getMessage(), AlertType.ERROR);
		}
	}

}
