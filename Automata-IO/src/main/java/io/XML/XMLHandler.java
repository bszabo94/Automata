package io.XML;

/*
 * #%L
 * Automata-IO
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

/**
 * <h1>Handler for XML input and output operations.</h1>
 * 
 * <P>
 * Manages input and output operations into and from XML files. Allows to export
 * machine objects with all their states and translations, and other information
 * to XML files, as well as import data from valid XML files, and create machine
 * objects defined in the files.
 * 
 * @author bszabo
 * @version 1.0
 */
public class XMLHandler {

	/**
	 * Imports Mealy machines from a valid XML file.
	 * 
	 * <P>
	 * Opens and reads an XML file and creates a Machine described in the opened
	 * file.
	 * 
	 * @param inFile
	 *            The file used as input.
	 * @return A list containing all the machines exported from the file.
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static List<core.Mealy.Machine> importMealy(File inFile)
			throws SAXException, IOException, ParserConfigurationException {
		List<core.Mealy.Machine> machineList = new ArrayList<core.Mealy.Machine>();

		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inFile);

		Set<Character> alphabet = new HashSet<Character>();

		NodeList machines = doc.getDocumentElement().getElementsByTagName("Machine");
		for (int i = 0; i < machines.getLength(); i++) {
			try {
				machineList.add(
						new core.Mealy.Machine(machines.item(i).getAttributes().getNamedItem("id").getNodeValue()));
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
									machineList.get(machineList.size() - 1)
											.addState(states.item(l).getAttributes().getNamedItem("id").getNodeValue());

							machineList.get(machineList.size() - 1)
									.setCurrState(machineList.get(machineList.size() - 1).findState(machines.item(i)
											.getAttributes().getNamedItem("Initial_State").getNodeValue()));

							for (int l = 0; l < states.getLength(); l++) {
								if (states.item(l).getNodeType() == Node.ELEMENT_NODE) {
									NodeList translations = states.item(l).getChildNodes();
									for (int m = 0; m < translations.getLength(); m++) {
										if (translations.item(m).getNodeType() == Node.ELEMENT_NODE) {
											machineList.get(machineList.size() - 1).getStates()
													.get(Integer.parseInt(states.item(l).getAttributes()
															.getNamedItem("id").getNodeValue().substring(1)))
													.addTranslation(new core.Mealy.Translation(
															translations.item(m).getAttributes().getNamedItem("input")
																	.getNodeValue().charAt(0),
															translations.item(m).getAttributes().getNamedItem("output")
																	.getNodeValue().charAt(0),
															machineList.get(machineList.size() - 1)
																	.findState(states.item(l).getAttributes()
																			.getNamedItem("id").getNodeValue()),
															machineList.get(machineList.size() - 1)
																	.findState(translations.item(m).getAttributes()
																			.getNamedItem("target").getNodeValue())));

											// machineList.get(machineList.size()
											// - 1).getStates()
											// .get(Integer.parseInt(states.item(l).getAttributes()
											// .getNamedItem("id").getNodeValue().substring(1)))
											// .addTranslation(new
											// core.Mealy.Translation(
											// translations.item(m).getAttributes().getNamedItem("input")
											// .getNodeValue().charAt(0),
											// translations.item(m).getAttributes().getNamedItem("output")
											// .getNodeValue().charAt(0),
											// machineList.get(machineList.size()
											// - 1).getStates()
											// .get(Integer.parseInt(states.item(l).getAttributes()
											// .getNamedItem("id").getNodeValue()
											// .substring(1))),
											// machineList.get(machineList.size()
											// - 1).getStates()
											// .get(Integer.parseInt(translations.item(m)
											// .getAttributes().getNamedItem("target")
											// .getNodeValue().substring(1)))));
										}
									}
								}
							}

						}
					}

				}
			} catch (core.Mealy.MachineException e) {
				e.printStackTrace();
			}
		}

		return machineList;
	}

	/**
	 * Imports Moore machines from a valid XML file.
	 * 
	 * <P>
	 * Opens and reads an XML file and creates a Machine described in the opened
	 * file.
	 * 
	 * @param inFile
	 *            The file used as input.
	 * @return A list containing all the machines exported from the file.
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static List<core.Moore.Machine> importMoore(File inFile)
			throws SAXException, IOException, ParserConfigurationException {
		List<core.Moore.Machine> machineList = new ArrayList<core.Moore.Machine>();

		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inFile);

		Set<Character> alphabet = new HashSet<Character>();

		NodeList machines = doc.getDocumentElement().getChildNodes();
		for (int i = 0; i < machines.getLength(); i++) {
			try {
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
										machineList.get(machineList.size() - 1).addState(
												states.item(l).getAttributes().getNamedItem("output").getNodeValue()
														.charAt(0),
												states.item(l).getAttributes().getNamedItem("id").getNodeValue());

								machineList.get(machineList.size() - 1)
										.setCurrState(machineList.get(machineList.size() - 1).findState(machines.item(i)
												.getAttributes().getNamedItem("Initial_State").getNodeValue()));

								for (int l = 0; l < states.getLength(); l++) {
									if (states.item(l).getNodeType() == Node.ELEMENT_NODE) {
										NodeList translations = states.item(l).getChildNodes();
										for (int m = 0; m < translations.getLength(); m++) {
											if (translations.item(m).getNodeType() == Node.ELEMENT_NODE) {
												machineList.get(machineList.size() - 1)
														.findState(states.item(l).getAttributes().getNamedItem("id")
																.getNodeValue())
														.addTranslation(new core.Moore.Translation(
																machineList.get(machineList.size() - 1)
																		.findState(states.item(l).getAttributes()
																				.getNamedItem("id").getNodeValue()),
																translations.item(m).getAttributes()
																		.getNamedItem("input").getNodeValue().charAt(0),
																machineList.get(machineList.size() - 1)
																		.findState(translations.item(m).getAttributes()
																				.getNamedItem("target")
																				.getNodeValue())));
											}
										}
									}
								}

							}
						}

					}
				}
			} catch (core.Moore.MachineException e) {

			}
		}

		return machineList;
	}

	/**
	 * Exports Machine objects to an XML file.
	 * 
	 * @param outFile
	 *            The file used as output.
	 * @param machines
	 *            The list of Mealy Machines to be exported.
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 */
	public static void exportMealy(File outFile, List<core.Mealy.Machine> machines)
			throws ParserConfigurationException, TransformerException {

		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

		Element mainRootElement = doc.createElement("Machines");
		doc.appendChild(mainRootElement);

		for (core.Mealy.Machine m : machines) {
			mainRootElement.appendChild(createNode(doc, m));
		}

		Transformer transf = TransformerFactory.newInstance().newTransformer();
		// StreamResult sr = new StreamResult(new File(filename));
		StreamResult sr = new StreamResult(outFile);

		DOMSource src = new DOMSource(doc);

		transf.setOutputProperty(OutputKeys.INDENT, "yes");
		transf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", Integer.toString(3));

		transf.transform(src, sr);

	}

	/**
	 * Exports Machine objects to an XML file.
	 * 
	 * @param outFile
	 *            The file used as output.
	 * @param machines
	 *            The list of Moore Machines to be exported.
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 */
	public static void exportMoore(File outFile, List<core.Moore.Machine> machines)
			throws ParserConfigurationException, TransformerException {

		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

		Element mainRootElement = doc.createElement("Machines");
		doc.appendChild(mainRootElement);

		for (core.Moore.Machine m : machines) {
			mainRootElement.appendChild(createNode(doc, m));
		}

		Transformer transf = TransformerFactory.newInstance().newTransformer();
		StreamResult sr = new StreamResult(outFile);

		DOMSource src = new DOMSource(doc);

		transf.setOutputProperty(OutputKeys.INDENT, "yes");
		transf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", Integer.toString(3));

		transf.transform(src, sr);

	}

	private static Node createNode(Document doc, core.Mealy.Machine machine) {
		Element currMachine = doc.createElement("Machine");
		currMachine.setAttribute("id", machine.getID());
		currMachine.setAttribute("Initial_State", machine.getCurrState().getID());

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

			state.setAttribute("id", currState.getID());

			for (core.Mealy.Translation currTranslation : currState.getTranslations()) {
				Element translation = doc.createElement("Translation");
				translation.setAttribute("input", Character.toString(currTranslation.getInput()));
				translation.setAttribute("output", Character.toString(currTranslation.getOutput()));
				translation.setAttribute("target", currTranslation.getTarget().getID());
				state.appendChild(translation);
			}
			states.appendChild(state);
		}
		currMachine.appendChild(states);
		return currMachine;
	}

	private static Node createNode(Document doc, core.Moore.Machine machine) {
		Element currMachine = doc.createElement("Machine");
		currMachine.setAttribute("id", machine.getID());
		currMachine.setAttribute("Initial_State", machine.getCurrState().getID());

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

			state.setAttribute("id", currState.getID());
			state.setAttribute("output", currState.getOutput().toString());

			for (core.Moore.Translation currTranslation : currState.getTranslations()) {
				Element translation = doc.createElement("Translation");
				translation.setAttribute("input", Character.toString(currTranslation.getInput()));
				translation.setAttribute("target", currTranslation.getTarget().getID());
				state.appendChild(translation);
			}
			states.appendChild(state);
		}
		currMachine.appendChild(states);
		return currMachine;
	}

}
