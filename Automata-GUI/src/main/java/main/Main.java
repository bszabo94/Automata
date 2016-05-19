package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import controllers.AutomataController;
import core.Mealy.State;
import io.XML.XMLHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

	private Stage primaryStage;
	private AnchorPane rootPane;

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	private ObservableList<core.Mealy.Machine> mealyMachines = FXCollections.observableArrayList();
	private ObservableList<core.Moore.Machine> mooreMachines = FXCollections.observableArrayList();
	private core.Mealy.Machine selectedMealy;
	private core.Moore.Machine selectedMoore;

	public ObservableList<core.Moore.Machine> getMooreMachines() {
		return mooreMachines;
	}

	public ObservableList<core.Mealy.Machine> getMealyMachines() {
		return mealyMachines;
	}

	public core.Mealy.Machine getSelectedMealy() {
		return selectedMealy;
	}

	public void setSelectedMealy(core.Mealy.Machine selectedMealy) {
		this.selectedMealy = selectedMealy;
	}

	public core.Moore.Machine getSelectedMoore() {
		return selectedMoore;
	}

	public void setSelectedMoore(core.Moore.Machine selectedMoore) {
		this.selectedMoore = selectedMoore;
	}

	public Main() {
		try {
			mealyMachines
					.addAll(XMLHandler.importMealy(new File(ClassLoader.getSystemResource("MealyTest.xml").toURI())));
			mooreMachines.add(mealyMachines.get(0).toMoore());
			mooreMachines.add(mealyMachines.get(1).toMoore());
			mooreMachines.add(mealyMachines.get(2).toMoore());
			mooreMachines.add(new core.Moore.Machine("Process"));
			mooreMachines.get(mooreMachines.size()-1).processData("abcdfegh1234567");
			mealyMachines.add(new core.Mealy.Machine("TESTER"));
			mealyMachines.get(mealyMachines.size()-1).processData("0123456789öüó"
					+ "qwertzuiopőúasdfghjkléáűíyxcvbnm,.-§'\"+!%/=()ÖÜÓQWERTZUIOPŐÚASDFGHJKLÉÁŰÍYXCVBNM?:_*>;<}{"
					+ "@&><äđĐ[]ħíłŁ$ß¤×÷”„Í€–ŧ¶e|\\");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage stage) {

		this.primaryStage = stage;
		stage.setTitle("Goat Simulator 2016");

		initGUI();

	}

	public static void main(String[] args) {
		launch(args);

		
		
	}

	public void initGUI() {
		try {
			FXMLLoader fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(Main.class.getResource("/fxml/Automata.fxml"));
			rootPane = (AnchorPane) fxmlloader.load();
			AutomataController controller = fxmlloader.getController();
			controller.setMain(this);

			Scene scene = new Scene(rootPane);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);

			primaryStage.setOnCloseRequest(event -> {
				Platform.exit();
				System.exit(0);
			});

			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showWarning(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setHeaderText(null);
		alert.setTitle("Warning!");
		alert.setContentText(message);

		alert.showAndWait();
	}

}
