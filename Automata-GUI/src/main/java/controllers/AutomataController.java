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

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import io.XML.XMLHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import main.Main;

public class AutomataController {

	private final static Logger logger = LoggerFactory.getLogger(Main.class);

	private Main main;

	public void setMain(Main main) {
		this.main = main;
		tableMealy.setItems(main.getMealyMachines());
		tableMealy.setPlaceholder(new Label("There are no Mealy Machines."));
		tableMoore.setItems(main.getMooreMachines());
		tableMoore.setPlaceholder(new Label("There are no Moore Machines."));
		mealyTranslationTable.setPlaceholder(new Label("No Machine has been selected."));
		mooreTranslationTable.setPlaceholder(new Label("No Machine has been selected."));
		mealyStatesTable.setPlaceholder(new Label(""));
		mooreStatesTable.setPlaceholder(new Label(""));
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
	TableView<core.Mealy.State> mealyStatesTable;

	@FXML
	TableView<core.Moore.State> mooreStatesTable;

	@FXML
	TableColumn<core.Mealy.State, String> mealyStatesState;

	@FXML
	TableColumn<core.Moore.State, String> mooreStatesState;

	@FXML
	TableColumn<core.Moore.State, String> mooreStatesOutput;

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
	Label labelCurrState;

	@FXML
	TextArea textareaOA;

	@FXML
	TextArea textareaIA;

	@FXML
	TextArea textAreaInput;

	@FXML
	TextArea textAreaOutput;

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

		// Translations table
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

		mealyTranslationTable.getSelectionModel().selectedItemProperty()
				.addListener((o, oldValue, newValue) -> selectMealyTranslation(newValue));

		mooreTranslationTable.getSelectionModel().selectedItemProperty()
				.addListener((o, oldValue, newValue) -> selectMooreTranslation(newValue));

		// States table
		mealyStatesTable.getSelectionModel().selectedItemProperty()
				.addListener((o, oldValue, newValue) -> selectMealyState(newValue));

		mooreStatesTable.getSelectionModel().selectedItemProperty()
				.addListener((o, oldValue, newValue) -> selectMooreState(newValue));

		mealyStatesState.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getID()));

		mooreStatesState.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getID()));
		mooreStatesOutput
				.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getOutput().toString()));

	}

	private void selectMealyTranslation(core.Mealy.Translation t) {
		main.setSelectedMealyTranslation(t);
		main.setSelectedMooreTranslation(null);
	}

	private void selectMooreTranslation(core.Moore.Translation t) {
		main.setSelectedMealyTranslation(null);
		main.setSelectedMooreTranslation(t);
	}

	private void selectMealyState(core.Mealy.State s) {
		main.setSelectedMealyState(s);
		main.setSelectedMooreState(null);

	}

	private void selectMooreState(core.Moore.State s) {
		main.setSelectedMooreState(s);
		main.setSelectedMealyState(null);

	}

	private void selectMealy(core.Mealy.Machine m) {
		main.setSelectedMealy(m);
		main.setSelectedMoore(null);
		if (main.getSelectedMealy() != null) {
			mealyTranslationTable
					.setItems(FXCollections.observableList(main.getSelectedMealy().getTranslationsAsList()));
			mealyStatesTable.setItems(FXCollections.observableList(main.getSelectedMealy().getStates()));

			labelID.setText(main.getSelectedMealy().getID());
			labelNbrStates.setText(Integer.toString(main.getSelectedMealy().getStates().size()));
			if (main.getSelectedMealy().getCurrState() != null) {
				labelCurrState.setText(main.getSelectedMealy().getCurrState().getID());
			} else {
				labelCurrState.setText("None!");
			}
			textareaOA.setText(main.getSelectedMealy().getoAlphabet().toString());
			textareaIA.setText(main.getSelectedMealy().getiAlphabet().toString());
		} else {
			mealyTranslationTable.setItems(null);
			mealyStatesTable.setItems(null);
			labelID.setText("");
			labelNbrStates.setText("");
			labelCurrState.setText("");
			textareaOA.setText("");
			textareaIA.setText("");
		}
		// mooreTranslationTable.setDisable(true);
		mooreTranslationTable.setVisible(false);
		// mealyTranslationTable.setDisable(false);
		mealyTranslationTable.setVisible(true);

		mooreStatesTable.setVisible(false);
		// mooreStatesTable.setDisable(true);

		mealyStatesTable.setVisible(true);
		// mealyStatesTable.setDisable(false);
	}

	private void selectMoore(core.Moore.Machine m) {
		main.setSelectedMoore(m);
		main.setSelectedMealy(null);
		if (main.getSelectedMoore() != null) {
			mooreTranslationTable
					.setItems(FXCollections.observableList(main.getSelectedMoore().getTranslationsAsList()));
			mooreStatesTable.setItems(FXCollections.observableList(main.getSelectedMoore().getStates()));
			labelID.setText(main.getSelectedMoore().getID());
			labelNbrStates.setText(Integer.toString(main.getSelectedMoore().getStates().size()));
			if (main.getSelectedMoore().getCurrState() != null) {
				labelCurrState.setText(main.getSelectedMoore().getCurrState().getID());
			} else {
				labelCurrState.setText("None!");
			}
			textareaOA.setText(main.getSelectedMoore().getoAlphabet().toString());
			textareaIA.setText(main.getSelectedMoore().getiAlphabet().toString());
		} else {
			mooreTranslationTable.setItems(null);
			mooreStatesTable.setItems(null);
			labelID.setText("");
			labelNbrStates.setText("");
			labelCurrState.setText("");
			textareaOA.setText("");
			textareaIA.setText("");
		}
		// mealyTranslationTable.setDisable(true);
		mealyTranslationTable.setVisible(false);
		// mooreTranslationTable.setDisable(false);
		mooreTranslationTable.setVisible(true);

		mooreStatesTable.setVisible(true);
		// mooreStatesTable.setDisable(false);

		mealyStatesTable.setVisible(false);
		// mealyStatesTable.setDisable(true);
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
			main.showPopup(e.getMessage(), AlertType.ERROR);
			logger.warn(e.getMessage());
			logger.error(e.getCause().toString());
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
					main.showPopup(e.getMessage(), AlertType.ERROR);
					logger.warn(e.getMessage());
					logger.error(e.getCause().toString());
				}
				return;
			}
			if (mooreTab.isSelected()) {
				try {
					XMLHandler.exportMoore(file, main.getMooreMachines());
				} catch (Exception e) {
					main.showPopup(e.getMessage(), AlertType.ERROR);
					logger.warn(e.getMessage());
					logger.error(e.getCause().toString());
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
					main.getMealyMachines().addAll(XMLHandler.importMealyFromFile(file));
				} catch (Exception e) {
					main.showPopup(e.getMessage(), AlertType.ERROR);
				}
				return;
			}
			if (mooreTab.isSelected()) {
				try {
					main.getMooreMachines().addAll(XMLHandler.importMooreFromFile(file));
					for (core.Moore.Machine m : main.getMooreMachines())
						System.out.println(m);
				} catch (SAXException | ParserConfigurationException | IOException | DOMException
						| core.Moore.MachineException e) {
					main.showPopup(e.getMessage(), AlertType.ERROR);
					logger.warn(e.getMessage());
					logger.error(e.getCause().toString());
				}
				return;
			}
		}
	}

	@FXML
	private void handleButtonRemoveState(ActionEvent event) {
		if (main.getSelectedMealyState() == null && main.getSelectedMooreState() == null) {
			main.showPopup("Select a state first!", AlertType.WARNING);
			return;
		}
		if (main.getSelectedMealy() != null && main.getSelectedMealyState() != null) {
			core.Mealy.State s = main.getSelectedMealyState();
			try {
				main.setSelectedMealyState(null);
				main.getSelectedMealy().removeState(s.getID());

				mealyTranslationTable
						.setItems(FXCollections.observableList(main.getSelectedMealy().getTranslationsAsList()));
				mealyStatesTable.setItems(FXCollections.observableList(main.getSelectedMealy().getStates()));
				labelNbrStates.setText(Integer.toString(main.getSelectedMealy().getStates().size()));
			} catch (core.Mealy.MachineException e) {
				main.showPopup(e.getMessage(), AlertType.ERROR);
			}
		} else if (main.getSelectedMoore() != null && main.getSelectedMooreState() != null) {
			core.Moore.State s = main.getSelectedMooreState();
			try {
				main.setSelectedMooreState(null);
				main.getSelectedMoore().removeState(s.getID());

				mooreTranslationTable
						.setItems(FXCollections.observableList(main.getSelectedMoore().getTranslationsAsList()));
				mooreStatesTable.setItems(FXCollections.observableList(main.getSelectedMoore().getStates()));
				labelNbrStates.setText(Integer.toString(main.getSelectedMoore().getStates().size()));
			} catch (core.Moore.MachineException e) {
				main.showPopup(e.getMessage(), AlertType.ERROR);
				logger.warn(e.getMessage());
				logger.error(e.getCause().toString());
			}
		} else {
			main.showPopup("Something went wrong. Try again!", AlertType.ERROR);
		}

	}

	@FXML
	private void handleMenuButtonRemoveState(ActionEvent event) {
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

		} catch (IOException e) {
			main.showPopup(e.getMessage(), AlertType.ERROR);
			logger.warn(e.getMessage());
			logger.error(e.getCause().toString());
		}
	}

	@FXML
	private void handleButtonAddState(ActionEvent event) {
		if (main.getSelectedMealy() == null && main.getSelectedMoore() == null) {
			main.showPopup("Select a machine first!", AlertType.WARNING);
			return;
		}

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/fxml/AddState.fxml"));
			AnchorPane ap = (AnchorPane) loader.load();
			AddStateController controller = loader.getController();
			controller.setMain(this.main);

			Stage stage = new Stage();
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setTitle("Add New State");

			stage.setScene(new Scene(ap));
			stage.show();

		} catch (IOException e) {
			main.showPopup(e.getMessage(), AlertType.ERROR);
			logger.warn(e.getMessage());
			logger.error(e.getCause().toString());
		}
	}

	@FXML
	private void handleButtonValidate(ActionEvent event) {
		if (main.getSelectedMealy() != null) {
			String isValid = main.getSelectedMealy().isValid() ? "valid" : "invalid";
			main.showPopup(main.getSelectedMealy().getID() + " is " + isValid + ".", AlertType.INFORMATION);
			return;
		} else if (main.getSelectedMoore() != null) {
			String isValid = main.getSelectedMoore().isValid() ? "valid" : "invalid";
			main.showPopup(main.getSelectedMoore().getID() + " is " + isValid + ".", AlertType.INFORMATION);
			return;
		} else {
			main.showPopup("Select a Machine first!", AlertType.WARNING);
		}
	}

	@FXML
	private void handleButtonEncode(ActionEvent event) {
		try {
			if (main.getSelectedMealy() != null) {
				textAreaOutput.setText(main.getSelectedMealy().encode(textAreaInput.getText()));
			} else if (main.getSelectedMoore() != null) {
				textAreaOutput.setText(main.getSelectedMoore().encode(textAreaInput.getText()));
			} else {
				main.showPopup("Select a Machine first!", AlertType.WARNING);
			}
		} catch (core.Mealy.MachineException | core.Moore.MachineException e) {
			main.showPopup(e.getMessage(), AlertType.ERROR);
		}
	}

	@FXML
	private void handleButtonDecode(ActionEvent event) {
		try {
			if (main.getSelectedMealy() != null) {
				textAreaOutput.setText(main.getSelectedMealy().decode(textAreaInput.getText()));
			}
			if (main.getSelectedMoore() != null) {
				textAreaOutput.setText(main.getSelectedMoore().decode(textAreaInput.getText()));
			}
		} catch (core.Mealy.MachineException | core.Moore.MachineException e) {
			main.showPopup(e.getMessage(), AlertType.ERROR);
		}

	}

	@FXML
	private void handleButtonAddTranslation(ActionEvent event) {
		if (main.getSelectedMealy() == null && main.getSelectedMoore() == null) {
			main.showPopup("Select a machine first!", AlertType.WARNING);
			return;
		}

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/fxml/NewTranslation.fxml"));
			AnchorPane ap = (AnchorPane) loader.load();
			NewTranslationController controller = loader.getController();
			controller.setMain(this.main);

			Stage stage = new Stage();
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setTitle("New Translation");

			stage.setScene(new Scene(ap));
			stage.show();

		} catch (IOException e) {
			main.showPopup(e.getMessage(), AlertType.ERROR);
			logger.warn(e.getMessage());
			logger.error(e.getCause().toString());
		}
	}

	@FXML
	private void handleButtonRemoveTranslation(ActionEvent event) {
		if (main.getSelectedMealyTranslation() != null) {
			main.getSelectedMealy().removeTranslation(main.getSelectedMealyTranslation());
			mealyTranslationTable
					.setItems(FXCollections.observableList(main.getSelectedMealy().getTranslationsAsList()));
		} else if (main.getSelectedMooreTranslation() != null) {
			main.getSelectedMoore().removeTranslation(main.getSelectedMooreTranslation());
			mooreTranslationTable
					.setItems(FXCollections.observableList(main.getSelectedMoore().getTranslationsAsList()));
		} else {
			main.showPopup("Select a translation first!", AlertType.ERROR);
		}
	}

	@FXML
	private void handleButtonRead(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/fxml/ReadWindow.fxml"));
			AnchorPane ap = (AnchorPane) loader.load();
			ReadController controller = loader.getController();
			controller.setMain(this.main, textAreaInput.getText());

			Stage stage = new Stage();
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setTitle("New Machine");

			stage.setScene(new Scene(ap));
			stage.show();

		} catch (Exception e) {
			main.showPopup(e.getMessage(), AlertType.ERROR);
			logger.warn(e.getMessage());
			logger.error(e.getCause().toString());
		}
	}

	@FXML
	private void handleButtonInit(ActionEvent event) {
		try {
			if (main.getSelectedMealy() != null) {
				main.getSelectedMealy().init(main.getSelectedMealy().getiAlphabet(),
						main.getSelectedMealy().getoAlphabet());
			} else if (main.getSelectedMoore() != null) {
				main.getSelectedMoore().init(main.getSelectedMoore().getiAlphabet(),
						main.getSelectedMoore().getoAlphabet());
			} else {
				main.showPopup("Select a Machine first!", AlertType.WARNING);
			}
		} catch (core.Mealy.MachineException | core.Moore.MachineException e) {
			main.showPopup(e.getMessage(), AlertType.ERROR);
			logger.warn(e.getMessage());
			logger.error(e.getCause().toString());
		}
	}

	@FXML
	private void handleButtonEdit(ActionEvent event) {
		if (main.getSelectedMealy() == null && main.getSelectedMoore() == null) {
			main.showPopup("Select a machine first!", AlertType.ERROR);
			return;
		}

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/fxml/EditMachine.fxml"));
			AnchorPane ap = (AnchorPane) loader.load();
			EditMachineController controller = loader.getController();
			controller.setMain(this.main);

			Stage stage = new Stage();
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setTitle("Edit Machine");

			stage.setScene(new Scene(ap));
			stage.show();

		} catch (IOException e) {
			main.showPopup(e.getMessage(), AlertType.ERROR);
			logger.warn(e.getMessage());
			logger.error(e.getCause().toString());
		}
	}

	@FXML
	private void handleConversion() {
		try {
			if (main.getSelectedMealy() != null) {
				main.getMooreMachines().add(main.getSelectedMealy().toMoore());
			} else if (main.getSelectedMoore() != null) {
				main.getMealyMachines().add(main.getSelectedMoore().toMealy());
			} else {
				main.showPopup("Select a machine first!", AlertType.WARNING);
			}
		} catch (core.Moore.MachineException | core.Mealy.MachineException e) {
			main.showPopup(e.getMessage(), AlertType.ERROR);
			logger.warn(e.getMessage());
			logger.error(e.getCause().toString());
		}
	}

	@FXML
	private void handleButtonExit(ActionEvent event) {
		main.getPrimaryStage().getOnCloseRequest()
				.handle(new WindowEvent(main.getPrimaryStage(), WindowEvent.WINDOW_CLOSE_REQUEST));
	}

}
