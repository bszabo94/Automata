package io.XML;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XMLHandler {

	public void doExport(String filename, List<core.Mealy.Machine> machines)
			throws ParserConfigurationException, TransformerException {
		
		core.Mealy.Machine ma = new core.Mealy.Machine("a");
		Class t = ma.getClass();
		System.out.println(t.getName());
		System.out.println(t.getPackage().getName());

		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

		Element mainRootElement = doc.createElement("Machines");
		doc.appendChild(mainRootElement);

		for (core.Mealy.Machine m : machines) {
			mainRootElement.appendChild(createNode(doc, m));
		}

		Transformer transf = TransformerFactory.newInstance().newTransformer();
		StreamResult sr = new StreamResult(new File(filename));

		DOMSource src = new DOMSource(doc);

		transf.setOutputProperty(OutputKeys.INDENT, "yes");
		transf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", Integer.toString(3));

		transf.transform(src, sr);

	}

	private Node createNode(Document doc, core.Mealy.Machine machine) {
		Element currMachine = doc.createElement("Machine");
		currMachine.setAttribute("name", machine.getName());
		currMachine.setAttribute("Initial_State",
				"q" + Integer.toString(machine.getStates().indexOf(machine.getCurrState())));

		Element iAlphabet = doc.createElement("InputAlphabet");
		currMachine.appendChild(iAlphabet);

		for (Character currChar : machine.getiAlphabet()) {
			Element currChr = doc.createElement("Symbol");
			currChr.appendChild(doc.createTextNode(Character.toString(currChar)));
			iAlphabet.appendChild(currChr);
		}

		Element oAlphabet = doc.createElement("OutputAlphabet");
		currMachine.appendChild(oAlphabet);

		for (Character currChar : machine.getoAlphabet()) {
			Element currChr = doc.createElement("Symbol");
			currChr.appendChild(doc.createTextNode(Character.toString(currChar)));
			oAlphabet.appendChild(currChr);
		}

		Element states = doc.createElement("States");
		currMachine.appendChild(states);

		for (core.Mealy.State currState : machine.getStates()) {
			Element state = doc.createElement("State");
			states.appendChild(state);
			state.setAttribute("id", "q" + machine.getStates().indexOf(currState));

			for (core.Mealy.Translation currTranslation : currState.getTranslations()) {
				Element translation = doc.createElement("Translation");
				translation.setAttribute("input", Character.toString(currTranslation.getInput()));
				translation.setAttribute("output", Character.toString(currTranslation.getOutput()));
				translation.setAttribute("target",
						Integer.toString(machine.getStates().indexOf(currTranslation.getTarget()) + 1));
				state.appendChild(translation);
			}
		}
		return currMachine;
	}

}
