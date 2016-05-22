package main;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import controllers.AutomataController;
import core.Mealy.MachineException;
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
	private AutomataController mainWindow;

	public AutomataController getMainWindow() {
		return mainWindow;
	}

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
	private core.Mealy.State selectedMealyState;
	private core.Moore.State selectedMooreState;
	private core.Mealy.Translation selectedMealyTranslation;
	private core.Moore.Translation selectedMooreTranslation;

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
			mooreMachines.get(mooreMachines.size() - 1).processData("abcdfegh1234567");
			mealyMachines.add(new core.Mealy.Machine("TESTER"));
			mealyMachines.get(mealyMachines.size() - 1)
					.processData("0123456789öüó"
							+ "qwertzuiopőúasdfghjkléáűíyxcvbnm,.-§'\"+!%/=()ÖÜÓQWERTZUIOPŐÚASDFGHJKLÉÁŰÍYXCVBNM?:_*>;<}{"
							+ "@&><äđĐ[]ħíłŁ$ß¤×÷”„Í€–ŧ¶e|\\");
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage stage) {

		this.primaryStage = stage;
		stage.setTitle("Automata Simulator");

		initGUI();

	}

	public static void main(String[] args) {
		// launch(args);
		try {
			List<core.Mealy.Machine> mealys = new ArrayList<core.Mealy.Machine>();
			List<core.Moore.Machine> moores = new ArrayList<core.Moore.Machine>();

			mealys.add(new core.Mealy.Machine("letters", new HashSet<Character>(Arrays.asList('a', 'b', 'c')),
					new HashSet<Character>(Arrays.asList('e', 'f', 'g'))));
			mealys.add(new core.Mealy.Machine("numbers", new HashSet<Character>(Arrays.asList('1', '2', '3')),
					new HashSet<Character>(Arrays.asList('4', '5', '6'))));
			mealys.add(new core.Mealy.Machine("symbols", new HashSet<Character>(Arrays.asList('ł', 'Ł', '$')),
					new HashSet<Character>(Arrays.asList('ß', '÷', '×'))));
			
			
//			for(int i=0; i<mealys.size(); i++){
//				System.out.println(mealys.get(i));
//				moores.add(mealys.get(i).toMoore());
//			}
				
			System.out.println("-------_");
			
			for(int i=0; i<mealys.size(); i++){
//				System.out.println(moores.get(i));
			}
			
//			XMLHandler.exportMealy(new File("XMLMEALY.xml"), mealys);
//			XMLHandler.exportMoore(new File("MOOREEXP.XML"), moores);
			
			moores.clear();
			moores = XMLHandler.importMoore(new File("MOOREEXP.XML"));
//			mealys = XMLHandler.importMealy(new File("XMLMEALY.xml"));
			for(int i=0; i<mealys.size(); i++){
				System.out.println(moores.get(i));
			}
		} catch (MachineException | ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

	}

	public void initGUI() {
		try {
			FXMLLoader fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(Main.class.getResource("/fxml/Automata.fxml"));
			rootPane = (AnchorPane) fxmlloader.load();
			AutomataController controller = fxmlloader.getController();
			controller.setMain(this);

			this.mainWindow = controller;

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

	public void showPopup(String message, AlertType alertType) {
		Alert alert = new Alert(alertType);
		alert.setHeaderText(null);
		alert.setTitle("Warning!");
		alert.setContentText(message);

		alert.showAndWait();
	}

	public core.Mealy.State getSelectedMealyState() {
		return selectedMealyState;
	}

	public void setSelectedMealyState(core.Mealy.State selectedMealyState) {
		this.selectedMealyState = selectedMealyState;
	}

	public core.Moore.State getSelectedMooreState() {
		return selectedMooreState;
	}

	public void setSelectedMooreState(core.Moore.State selectedMooreState) {
		this.selectedMooreState = selectedMooreState;
	}

	public core.Mealy.Translation getSelectedMealyTranslation() {
		return selectedMealyTranslation;
	}

	public void setSelectedMealyTranslation(core.Mealy.Translation selectedMealyTranslation) {
		this.selectedMealyTranslation = selectedMealyTranslation;
	}

	public core.Moore.Translation getSelectedMooreTranslation() {
		return selectedMooreTranslation;
	}

	public void setSelectedMooreTranslation(core.Moore.Translation selectedMooreTranslation) {
		this.selectedMooreTranslation = selectedMooreTranslation;
	}

}
