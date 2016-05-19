package controllers;

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
