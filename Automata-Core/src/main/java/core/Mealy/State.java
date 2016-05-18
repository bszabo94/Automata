package core.Mealy;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;

public class State {
	private String id;
	private List<Translation> translations;

	public State() {
		this.translations = new ArrayList<Translation>();
		this.id = null;
	}
	
	public State(int n) {
		this.translations = new ArrayList<Translation>();
		this.id = "q" + Integer.toString(n);
	}

	public void addTranslation(Translation newTranslation) {
		this.translations.add(newTranslation);
	}
	
	public SimpleStringProperty getIDasProperty(){
		return new SimpleStringProperty(this.id.toString());
	}

	public List<Translation> getTranslations() {
		return this.translations;
	}
	
	public String getID(){
		return this.id;
	}

}
