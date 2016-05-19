package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import main.Main;

public class NewMachineController {

	private Main main;

	public void setMain(Main main) {
		this.main = main;
	}

	public NewMachineController(Main main) {
		super();
		this.main = main;
	}

	public NewMachineController() {
		super();
	}

	@FXML
	RadioButton rbMealy;

	@FXML
	RadioButton rbMoore;

	@FXML
	TextField tfID;

	@FXML
	Spinner<Integer> spinnerState;

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
				main.getMealyMachines().add(new core.Mealy.Machine(tfID.getText()));
				if (spinnerState.getValue() > 0) {
					for (int i = 0; i < spinnerState.getValue(); i++)
						main.getMealyMachines().get(main.getMealyMachines().size() - 1).addState(i);
				}
			} else {
				main.getMooreMachines().add(new core.Moore.Machine(tfID.getText()));
				if (spinnerState.getValue() > 0) {
					for (int i = 0; i < spinnerState.getValue(); i++)
						main.getMooreMachines().get(main.getMooreMachines().size() - 1).addState(null, i);
					;
				}
			}

			handleButtonCancel(event);
		} catch (core.Moore.MachineException | core.Mealy.MachineException e) {
			// TODO
			// System.out.println(e.getMessage());
			main.showPopup(e.getMessage(), AlertType.WARNING);
		}
	}

	@FXML
	private void initialize() {
		spinnerState.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0, 1));
	}

}
