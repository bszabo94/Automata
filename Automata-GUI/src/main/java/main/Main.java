package main;

import java.io.File;
import java.io.IOException;

import controllers.AutomataController;
import io.XML.XMLHandler;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
//		try {
//			List<core.Mealy.Machine> mlist = new ArrayList<core.Mealy.Machine>();
//			mlist.addAll(XMLHandler.importMealy(new File(ClassLoader.getSystemResource("MealyTest.xml").toURI())));
//			List<core.Moore.Machine> nlist = new ArrayList<core.Moore.Machine>();
//			for(core.Mealy.Machine m: mlist)
//				nlist.add(m.toMoore());
//			
//			System.out.println(nlist.get(0));
//			System.out.println(mlist.get(0));
//			
//		} catch (SAXException | IOException | ParserConfigurationException | URISyntaxException e) {
//			e.printStackTrace();
//		}
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

			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
