package core.Moore;

import java.util.ArrayList;
import java.util.List;

public class State {
	private List<Translation> translations;
	private Character output;

	public State() {
		this.translations = new ArrayList<Translation>();
		this.output = null;
	}

	public State(Character output) {
		this.output = output;
		this.translations = new ArrayList<Translation>();
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
