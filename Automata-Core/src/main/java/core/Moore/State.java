package core.Moore;

import java.util.ArrayList;
import java.util.List;

public class State {
	private String id;

	private List<Translation> translations;
	private Character output;

	public State() {
		this.translations = new ArrayList<Translation>();
		this.output = null;
		this.id = null;
	}
	
	public State(Character output) {
		this.id = null;
		this.output = output;
		this.translations = new ArrayList<Translation>();
	}

	public State(Character output, String id) {
		this.id = id;
		this.output = output;
		this.translations = new ArrayList<Translation>();
	}
	
	public String getID() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void addTranslation(Translation newTranslation) {
		this.translations.add(newTranslation);
	}

	public List<Translation> getTranslations() {
		return this.translations;
	}

	public Character getOutput() {
		return output;
	}

	public void setOutput(Character output) {
		this.output = output;
	}

}
