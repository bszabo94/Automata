package io.XML;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLHandler {

	public List<core.Mealy.Machine> importMealy(String filename)
			throws SAXException, IOException, ParserConfigurationException {
		List<core.Mealy.Machine> machineList = new ArrayList<core.Mealy.Machine>();

		// Document doc =
		// DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(this.getClass().getResourceAsStream(filename));
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(ClassLoader.getSystemResourceAsStream(filename));

		// Document doc =
		// DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(this.getClass().getResourceAsStream(filename));
		Set<Character> alphabet = new HashSet<Character>();

		NodeList machines = doc.getDocumentElement().getElementsByTagName("Machine");
		for (int i = 0; i < machines.getLength(); i++) {
			machineList.add(new core.Mealy.Machine(machines.item(i).getAttributes().getNamedItem("id").getNodeValue()));
			NodeList properties = machines.item(i).getChildNodes();
			for (int j = 0; j < properties.getLength(); j++) {
				if (properties.item(j).getNodeType() == Node.ELEMENT_NODE) {
					if (properties.item(j).getNodeName() == "InputAlphabet") {
						NodeList symbols = properties.item(j).getChildNodes();
						for (int k = 0; k < symbols.getLength(); k++) {
							if (symbols.item(k).getNodeType() == Node.ELEMENT_NODE) {
								alphabet.add(symbols.item(k).getTextContent().charAt(0));
							}

						}
						machineList.get(machineList.size() - 1).setiAlphabet(new HashSet<Character>(alphabet));
						alphabet.clear();
					}
					if (properties.item(j).getNodeName() == "OutputAlphabet") {
						NodeList symbols = properties.item(j).getChildNodes();
						for (int k = 0; k < symbols.getLength(); k++) {
							if (symbols.item(k).getNodeType() == Node.ELEMENT_NODE) {
								alphabet.add(symbols.item(k).getTextContent().charAt(0));
							}

						}
						machineList.get(machineList.size() - 1).setoAlphabet(new HashSet<Character>(alphabet));
						alphabet.clear();
					}
					if (properties.item(j).getNodeName() == "States") {

						NodeList states = properties.item(j).getChildNodes();
						for (int l = 0; l < states.getLength(); l++)
							if (states.item(l).getNodeType() == Node.ELEMENT_NODE)
								machineList.get(machineList.size() - 1).addState();

						machineList.get(machineList.size() - 1)
								.setCurrState(machineList.get(machineList.size() - 1).getStates()
										.get(Integer.parseInt(machines.item(i).getAttributes()
												.getNamedItem("Initial_State").getNodeValue().substring(1))));

						for (int l = 0; l < states.getLength(); l++) {
							if (states.item(l).getNodeType() == Node.ELEMENT_NODE) {
								NodeList translations = states.item(l).getChildNodes();
								for (int m = 0; m < translations.getLength(); m++) {
									if (translations.item(m).getNodeType() == Node.ELEMENT_NODE) {
										machineList.get(machineList.size() - 1).getStates()
												.get(Integer.parseInt(states.item(l).getAttributes().getNamedItem("id")
														.getNodeValue().substring(1)))
												.addTranslation(new core.Mealy.Translation(
														translations.item(m).getAttributes().getNamedItem("input")
																.getNodeValue().charAt(0),
														translations.item(m).getAttributes().getNamedItem("output")
																.getNodeValue().charAt(0),
														machineList.get(machineList.size() - 1).getStates()
																.get(Integer.parseInt(translations.item(m)
																		.getAttributes().getNamedItem("target")
																		.getNodeValue().substring(1)))));
									}
								}
							}
						}

					}
				}

			}
		}

		return machineList;
	}

	public List<core.Moore.Machine> importMoore(String filename)
			throws SAXException, IOException, ParserConfigurationException {
		List<core.Moore.Machine> machineList = new ArrayList<core.Moore.Machine>();

		// Document doc =
		// DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(this.getClass().getResourceAsStream(filename));
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(ClassLoader.getSystemResourceAsStream(filename));

		// Document doc =
		// DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(this.getClass().getResourceAsStream(filename));
		Set<Character> alphabet = new HashSet<Character>();

		NodeList machines = doc.getDocumentElement().getChildNodes();
		for (int i = 0; i < machines.getLength(); i++) {
			if (machines.item(i).getNodeType() == Node.ELEMENT_NODE) {
				machineList.add(
						new core.Moore.Machine(machines.item(i).getAttributes().getNamedItem("id").getNodeValue()));

				NodeList properties = machines.item(i).getChildNodes();
				for (int j = 0; j < properties.getLength(); j++) {
					if (properties.item(j).getNodeType() == Node.ELEMENT_NODE) {
						if (properties.item(j).getNodeName() == "InputAlphabet") {
							NodeList symbols = properties.item(j).getChildNodes();
							for (int k = 0; k < symbols.getLength(); k++) {
								if (symbols.item(k).getNodeType() == Node.ELEMENT_NODE) {
									alphabet.add(symbols.item(k).getTextContent().charAt(0));
								}

							}
							machineList.get(machineList.size() - 1).setiAlphabet(new HashSet<Character>(alphabet));
							alphabet.clear();
						}
						if (properties.item(j).getNodeName() == "OutputAlphabet") {
							NodeList symbols = properties.item(j).getChildNodes();
							for (int k = 0; k < symbols.getLength(); k++) {
								if (symbols.item(k).getNodeType() == Node.ELEMENT_NODE) {
									alphabet.add(symbols.item(k).getTextContent().charAt(0));
								}

							}
							machineList.get(machineList.size() - 1).setoAlphabet(new HashSet<Character>(alphabet));
							alphabet.clear();
						}
						if (properties.item(j).getNodeName() == "States") {

							NodeList states = properties.item(j).getChildNodes();
							for (int l = 0; l < states.getLength(); l++)
								if (states.item(l).getNodeType() == Node.ELEMENT_NODE)
									machineList.get(machineList.size() - 1).addState(states.item(l).getAttributes()
											.getNamedItem("output").getNodeValue().charAt(0));

							machineList.get(machineList.size() - 1)
									.setCurrState(
											machineList.get(machineList.size() - 1).getStates()
													.get(Integer.parseInt(machines.item(i).getAttributes()
															.getNamedItem("Initial_State").getNodeValue()
															.substring(1))));

							for (int l = 0; l < states.getLength(); l++) {
								if (states.item(l).getNodeType() == Node.ELEMENT_NODE) {
									NodeList translations = states.item(l).getChildNodes();
									for (int m = 0; m < translations.getLength(); m++) {
										if (translations.item(m).getNodeType() == Node.ELEMENT_NODE) {
											machineList.get(machineList.size() - 1).getStates()
													.get(Integer.parseInt(states.item(l).getAttributes()
															.getNamedItem("id").getNodeValue().substring(1)))
													.addTranslation(new core.Moore.Translation(
															translations.item(m).getAttributes().getNamedItem("input")
																	.getNodeValue().charAt(0),
															machineList.get(machineList.size() - 1).getStates()
																	.get(Integer.parseInt(translations.item(m)
																			.getAttributes().getNamedItem("target")
																			.getNodeValue().substring(1)))));
										}
									}
								}
							}

						}
					}

				}
			}
		}

		return machineList;
	}

	public void exportMealy(String filename, List<core.Mealy.Machine> machines)
			throws ParserConfigurationException, TransformerException {

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

	public void exportMoore(String filename, List<core.Moore.Machine> machines)
			throws ParserConfigurationException, TransformerException {

		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

		Element mainRootElement = doc.createElement("Machines");
		doc.appendChild(mainRootElement);

		for (core.Moore.Machine m : machines) {
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
		currMachine.setAttribute("id", machine.getID());
		currMachine.setAttribute("Initial_State",
				"q" + Integer.toString(machine.getStates().indexOf(machine.getCurrState())));

		Element iAlphabet = doc.createElement("InputAlphabet");

		for (Character currChar : machine.getiAlphabet()) {
			Element currChr = doc.createElement("Symbol");
			currChr.appendChild(doc.createTextNode(Character.toString(currChar)));
			iAlphabet.appendChild(currChr);
		}

		currMachine.appendChild(iAlphabet);

		Element oAlphabet = doc.createElement("OutputAlphabet");

		for (Character currChar : machine.getoAlphabet()) {
			Element currChr = doc.createElement("Symbol");
			currChr.appendChild(doc.createTextNode(Character.toString(currChar)));
			oAlphabet.appendChild(currChr);
		}
		currMachine.appendChild(oAlphabet);

		Element states = doc.createElement("States");

		for (core.Mealy.State currState : machine.getStates()) {
			Element state = doc.createElement("State");

			state.setAttribute("id", "q" + machine.getStates().indexOf(currState));

			for (core.Mealy.Translation currTranslation : currState.getTranslations()) {
				Element translation = doc.createElement("Translation");
				translation.setAttribute("input", Character.toString(currTranslation.getInput()));
				translation.setAttribute("output", Character.toString(currTranslation.getOutput()));
				translation.setAttribute("target",
						"q" + Integer.toString(machine.getStates().indexOf(currTranslation.getTarget())));
				state.appendChild(translation);
			}
			states.appendChild(state);
		}
		currMachine.appendChild(states);
		return currMachine;
	}

	private Node createNode(Document doc, core.Moore.Machine machine) {
		Element currMachine = doc.createElement("Machine");
		currMachine.setAttribute("id", machine.getID());
		currMachine.setAttribute("Initial_State",
				"q" + Integer.toString(machine.getStates().indexOf(machine.getCurrState())));

		Element iAlphabet = doc.createElement("InputAlphabet");

		for (Character currChar : machine.getiAlphabet()) {
			Element currChr = doc.createElement("Symbol");
			currChr.appendChild(doc.createTextNode(Character.toString(currChar)));
			iAlphabet.appendChild(currChr);
		}

		currMachine.appendChild(iAlphabet);

		Element oAlphabet = doc.createElement("OutputAlphabet");

		for (Character currChar : machine.getoAlphabet()) {
			Element currChr = doc.createElement("Symbol");
			currChr.appendChild(doc.createTextNode(Character.toString(currChar)));
			oAlphabet.appendChild(currChr);
		}
		currMachine.appendChild(oAlphabet);

		Element states = doc.createElement("States");

		for (core.Moore.State currState : machine.getStates()) {
			Element state = doc.createElement("State");

			state.setAttribute("id", "q" + machine.getStates().indexOf(currState));
			state.setAttribute("output", currState.getOutput().toString());

			for (core.Moore.Translation currTranslation : currState.getTranslations()) {
				Element translation = doc.createElement("Translation");
				translation.setAttribute("input", Character.toString(currTranslation.getInput()));
				translation.setAttribute("target",
						"q" + Integer.toString(machine.getStates().indexOf(currTranslation.getTarget())));
				state.appendChild(translation);
			}
			states.appendChild(state);
		}
		currMachine.appendChild(states);
		return currMachine;
	}

}
