package controllers;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AutomataController {

	@FXML
	Button buttonNew, buttonDelete, buttonLoad, buttonSave;

	@FXML
	TableView<core.Mealy.Machine> tableMealy;

	@FXML
	TableView<core.Moore.Machine> tableMoore;

	@FXML
	TableColumn<core.Mealy.Machine, String> tableMealyID, tableMealyStates;

	@FXML
	TableColumn<core.Moore.Machine, String> tableMooreID, tableMooreStates;

	@FXML
	private void initialize() {
//		tableMealyID.setCellValueFactory(new PropertyValueFactory<core.Mealy.Machine, String>("id"));
//		tableMealyStates.setCellValueFactory(new PropertyValueFactory<core.Mealy.Machine, String>("states"));
		tableMealyID.setCellValueFactory(cellData -> StringProperty.stringExpression(cellData.getValue().getID()));
//		tableMooreID.setCellValueFactory(new PropertyValueFactory<core.Moore.Machine, String>("id"));
//		tableMooreStates.setCellValueFactory(new PropertyValueFactory<core.Moore.Machine, String>("states"));
	}

}
