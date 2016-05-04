package core.Mealy;

import java.util.ArrayList;
import java.util.List;

public class State {
	private List<Translation> translations;

	public State() {
		this.translations = new ArrayList<Translation>();
	}

	public void addTranslation(Translation newTranslation) {
		this.translations.add(newTranslation);
	}

	public List<Translation> getTranslations() {
		return this.translations;
	}

}
