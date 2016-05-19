package controllers;

import java.io.File;

import io.XML.XMLHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import main.Main;

public class AutomataController {

	private Main main;

	public void setMain(Main main) {
		this.main = main;
		tableMealy.setItems(main.getMealyMachines());
		tableMealy.setPlaceholder(new Label("There are no Mealy Machines."));
		tableMoore.setItems(main.getMooreMachines());
		tableMoore.setPlaceholder(new Label("There are no Moore Machines."));
		mealyTranslationTable.setPlaceholder(new Label("No Machine has been selected."));
		mooreTranslationTable.setPlaceholder(new Label("No Machine has been selected."));
	}

	public AutomataController(Main main) {
		super();
		this.main = main;
	}

	public AutomataController() {
		super();
	}

	@FXML
	TableView<core.Mealy.Machine> tableMealy;

	@FXML
	TableView<core.Moore.Machine> tableMoore;

	@FXML
	TableView<Character> symbolTable;

	@FXML
	TableView<core.Mealy.Translation> mealyTranslationTable;

	@FXML
	TableColumn<core.Mealy.Translation, String> mealyTrState;

	@FXML
	TableColumn<core.Mealy.Translation, String> mealyTrInput;

	@FXML
	TableColumn<core.Mealy.Translation, String> mealyTrOutput;

	@FXML
	TableColumn<core.Mealy.Translation, String> mealyTrTarget;

	@FXML
	TableView<core.Moore.Translation> mooreTranslationTable;

	@FXML
	TableColumn<core.Moore.Translation, String> mooreTrState;

	@FXML
	TableColumn<core.Moore.Translation, String> mooreTrTarget;

	@FXML
	TableColumn<core.Moore.Translation, String> mooreTrInput;

	@FXML
	TableColumn<core.Moore.Translation, String> mooreTrOutput;

	@FXML
	TableColumn<Character, String> tableInputSymbols;

	@FXML
	TableColumn<Character, String> tableOutputSymbols;

	@FXML
	TableColumn<core.Mealy.Machine, String> tableMealyID;

	@FXML
	TableColumn<core.Mealy.Machine, String> tableMealyStates;

	@FXML
	TableColumn<core.Moore.Machine, String> tableMooreID;

	@FXML
	TableColumn<core.Moore.Machine, String> tableMooreStates;

	@FXML
	Tab mealyTab;

	@FXML
	Tab mooreTab;

	@FXML
	Text textIAlphabet;

	@FXML
	Text textOAlphabet;

	@FXML
	Label labelID;

	@FXML
	Label labelNbrStates;

	@FXML
	TextArea textareaOA;

	@FXML
	TextArea textareaIA;

	@FXML
	private void initialize() {

		tableMealyID.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getID()));
		tableMealyStates.setCellValueFactory(
				celldata -> new SimpleStringProperty(Integer.toString(celldata.getValue().getStates().size())));
		tableMealy.getSelectionModel().selectedItemProperty()
				.addListener((o, oldValue, newValue) -> selectMealy(newValue));

		tableMooreID.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getID()));
		tableMooreStates.setCellValueFactory(
				celldata -> new SimpleStringProperty(Integer.toString(celldata.getValue().getStates().size())));
		tableMoore.getSelectionModel().selectedItemProperty()
				.addListener((o, oldValue, newValue) -> selectMoore(newValue));

		mealyTrState.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getParent().getID()));
		mealyTrInput
				.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getInput().toString()));
		mealyTrOutput
				.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getOutput().toString()));
		mealyTrTarget
				.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getTarget().getID()));

		mooreTrState.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getParent().getID()));
		mooreTrInput
				.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getInput().toString()));
		mooreTrOutput.setCellValueFactory(
				celldata -> new SimpleStringProperty(celldata.getValue().getTarget().getOutput().toString()));
		mooreTrTarget
				.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getTarget().getID()));
	}

	private void selectMealy(core.Mealy.Machine m) {
		main.setSelectedMealy(m);
		main.setSelectedMoore(null);
		if (main.getSelectedMealy() != null) {
			mealyTranslationTable
					.setItems(FXCollections.observableList(main.getSelectedMealy().getTranslationsAsList()));
			labelID.setText(main.getSelectedMealy().getID());
			labelNbrStates.setText(Integer.toString(main.getSelectedMealy().getStates().size()));
			textareaOA.setText(main.getSelectedMealy().getoAlphabet().toString());
			textareaIA.setText(main.getSelectedMealy().getiAlphabet().toString());
		} else {
			mealyTranslationTable.setItems(null);
			labelID.setText("");
			labelNbrStates.setText("");
			textareaOA.setText("");
			textareaIA.setText("");
		}
		mooreTranslationTable.setDisable(true);
		mooreTranslationTable.setVisible(false);
		mealyTranslationTable.setDisable(false);
		mealyTranslationTable.setVisible(true);
	}

	private void selectMoore(core.Moore.Machine m) {
		main.setSelectedMoore(m);
		main.setSelectedMealy(null);
		if (main.getSelectedMoore() != null) {
			mooreTranslationTable
					.setItems(FXCollections.observableList(main.getSelectedMoore().getTranslationsAsList()));
			labelID.setText(main.getSelectedMoore().getID());
			labelNbrStates.setText(Integer.toString(main.getSelectedMoore().getStates().size()));
			textareaOA.setText(main.getSelectedMoore().getoAlphabet().toString());
			textareaIA.setText(main.getSelectedMoore().getiAlphabet().toString());
		} else {
			mooreTranslationTable.setItems(null);
			labelID.setText("");
			labelNbrStates.setText("");
			textareaOA.setText("");
			textareaIA.setText("");
		}
		mealyTranslationTable.setDisable(true);
		mealyTranslationTable.setVisible(false);
		mooreTranslationTable.setDisable(false);
		mooreTranslationTable.setVisible(true);
	}

	@FXML
	private void handleExit(ActionEvent event) {
		main.getPrimaryStage().getOnCloseRequest()
				.handle(new WindowEvent(main.getPrimaryStage(), WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	@FXML
	private void handleButtonDelete(ActionEvent event) {
		if (main.getSelectedMealy() != null) {
			main.getMealyMachines().remove(main.getSelectedMealy());
		} else {
			main.getMooreMachines().remove(main.getSelectedMoore());
		}
	}

	@FXML
	private void handleButtonNew(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/fxml/NewMachine.fxml"));
			AnchorPane ap = (AnchorPane) loader.load();
			NewMachineController controller = loader.getController();
			controller.setMain(this.main);

			Stage stage = new Stage();
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setTitle("New Machine");

			stage.setScene(new Scene(ap));
			stage.show();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}

	@FXML
	private void handleButtonSave(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		if (mealyTab.isSelected())
			fileChooser.setInitialFileName("MyMealyMachines.xml");
		if (mooreTab.isSelected())
			fileChooser.setInitialFileName("MyMooreMachines.xml");

		fileChooser.setTitle("Save Automata");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("XML", "*.xml"),
				new ExtensionFilter("Other", "*.*"));
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		File file = fileChooser.showSaveDialog(main.getPrimaryStage());
		if (file != null) {
			if (mealyTab.isSelected()) {
				try {
					XMLHandler.exportMealy(file, main.getMealyMachines());
				} catch (Exception e) {
					e.printStackTrace();
				}
				return;
			}
			if (mooreTab.isSelected()) {
				try {
					XMLHandler.exportMoore(file, main.getMooreMachines());
				} catch (Exception e) {
					e.printStackTrace();
				}
				return;
			}
		}
	}

	@FXML
	private void handleButtonLoad(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		if (mealyTab.isSelected())
			fileChooser.setInitialFileName("MyMealyMachines.xml");
		if (mooreTab.isSelected())
			fileChooser.setInitialFileName("MyMooreMachines.xml");

		fileChooser.setTitle("Open Automata");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("XML", "*.xml"),
				new ExtensionFilter("Other", "*.*"));
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		File file = fileChooser.showOpenDialog(main.getPrimaryStage());
		if (file != null) {
			if (mealyTab.isSelected()) {
				try {
					main.getMealyMachines().addAll(XMLHandler.importMealy(file));
				} catch (Exception e) {
					// TODO
					System.out.println("new window says: XML cotains Moore Machines! Import it to the Moore Tab!");
					System.out.println(e.getMessage());
				}
				return;
			}
			if (mooreTab.isSelected()) {
				try {
					main.getMooreMachines().addAll(XMLHandler.importMoore(file));
					for (core.Moore.Machine m : main.getMooreMachines())
						System.out.println(m);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return;
			}
		}
	}

	@FXML
	private void handleButtonRemoveState(ActionEvent event) {
		if (main.getSelectedMealy() == null && main.getSelectedMoore() == null){
			main.showWarning("Select a machine first!");
			return;
		}
			
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/fxml/RemoveState.fxml"));
			AnchorPane ap = (AnchorPane) loader.load();
			RemoveStateController controller = loader.getController();
			controller.setMain(this.main);

			Stage stage = new Stage();
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setTitle("Remove State");

			stage.setScene(new Scene(ap));
			stage.show();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
