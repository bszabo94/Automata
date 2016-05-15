import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		
		
		
		ObservableList<core.Mealy.Machine> mealyMachines = FXCollections.observableArrayList();
		mealyMachines.add(new core.Mealy.Machine("abc"));
		mealyMachines.get(0).processData("abc");
		mealyMachines.add(new core.Mealy.Machine("123"));
		mealyMachines.get(0).processData("123");
		mealyMachines.add(new core.Mealy.Machine("ef3"));
		mealyMachines.get(0).processData("ef3");
		
		
		
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/Automata.fxml"));
		Scene scene = new Scene(root);		
		stage.setTitle("First Test");
		stage.setScene(scene);
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
