package core.Tests;

import core.Mealy.*;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class MealyTests {

	@Test
	public void testGetType() throws MachineException {
		assertEquals("Mealy", new Machine("Tester").getType());
	}

	@Test
	public void testAddGetStates() throws MachineException {
		Machine m = new Machine("Tester");
		List<State> compareStates = new ArrayList<State>();
		for (int i = 0; i < 100; i++) {
			m.addState();
			compareStates.add(m.getStates().get(m.getStates().size() - 1));
		}
		int i = 0;
		for (State currState : m.getStates()) {
			assertSame(currState, compareStates.get(i));
			i++;
		}

	}

	@Test
	public void testGetCurrState() throws MachineException {
		Machine m = new Machine("Tester");
		try {
			m.processData("Data for processing.");
		} catch (MachineException e) {
			e.printStackTrace();
		}
		assertSame(m.getStates().get(0), m.getCurrState());
		State testState = new State();
		m.setCurrState(testState);
		assertSame(testState, m.getCurrState());
	}

	@Test
	public void testGetID() throws MachineException {
		String id = "Tester Machine";
		assertEquals(id, new Machine(id).getID());
	}

	@Test
	public void testSetID() throws MachineException {
		String id = "Tester Machine";
		Machine m = new Machine("Old ID");
		m.setID("Tester Machine");
		assertEquals(id, m.getID());
	}

	@Test
	public void testiAlphabet() {
		Set<Character> testAlphabet = new HashSet<Character>(Arrays.asList('a', 'b', 'c', '1', '2', '3'));
		try {
			Machine m = new Machine("Initialized Tester",
					new HashSet<Character>(Arrays.asList('a', 'b', 'c', '1', '2', '3')),
					new HashSet<Character>(Arrays.asList('a', 'b', 'c', '1', '2', '3')));
			m.setiAlphabet(testAlphabet);
			assertSame(testAlphabet, m.getiAlphabet());
		} catch (MachineException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testoAlphabet() {
		Set<Character> testAlphabet = new HashSet<Character>(Arrays.asList('a', 'b', 'c', '1', '2', '3'));
		try {
			Machine m = new Machine("Initialized Tester",
					new HashSet<Character>(Arrays.asList('a', 'b', 'c', '1', '2', '3')),
					new HashSet<Character>(Arrays.asList('a', 'b', 'c', '1', '2', '3')));
			m.setoAlphabet(testAlphabet);
			assertSame(testAlphabet, m.getoAlphabet());
		} catch (MachineException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testSetCurrState() throws MachineException {
		State testState = new State();
		Machine m = new Machine("Tester");
		m.setCurrState(testState);
		assertSame(testState, m.getCurrState());
	}

	@Test
	public void testIsValid() throws MachineException {
		Machine m = new Machine("Tester");

		assertFalse(m.isValid());

		m.setiAlphabet(new HashSet<Character>(Arrays.asList('a', 'b')));
		m.setoAlphabet(new HashSet<Character>(Arrays.asList('a', 'b', 'd', 'e', 'f')));

		assertFalse(m.isValid());

		m.addState();

		assertFalse(m.isValid());

		m.setCurrState(m.getStates().get(0));

		assertFalse(m.isValid());

		m.getStates().get(0).addTranslation(new Translation('a', 'a',m.getStates().get(0), m.getStates().get(0)));
		m.getStates().get(0).addTranslation(new Translation('b', 'b',m.getStates().get(0), m.getStates().get(0)));

		assertTrue(m.isValid());

		m.addState();
		m.getStates().get(1).addTranslation(new Translation('a', 'e',m.getStates().get(1), m.getStates().get(0)));
		m.getStates().get(1).addTranslation(new Translation('b', 'f',m.getStates().get(1), m.getStates().get(1)));

		assertTrue(m.isValid());

		m.addState();
		m.getStates().get(2).addTranslation(new Translation('a', 'f',m.getStates().get(2), m.getStates().get(1)));
		m.getStates().get(2).addTranslation(new Translation('b', 'f',m.getStates().get(2), m.getStates().get(1)));

		assertFalse(m.isValid());
	}

	@Test
	public void testInit() throws MachineException {
		Machine m = new Machine("Tester");
		if (m.getCurrState() != null)
			fail("Current State must be null before initialization.");
		try {
			m.init(new HashSet<Character>(Arrays.asList('a', 'b')), new HashSet<Character>(Arrays.asList('1', '2')));
			assertEquals(m.getiAlphabet(), new HashSet<Character>(Arrays.asList('a', 'b')));
			assertEquals(m.getoAlphabet(), new HashSet<Character>(Arrays.asList('1', '2')));
			if (m.getCurrState() == null)
				fail("Current State must not be null after initialization.");
			if (!m.isValid())
				fail("Init must produce a valid Machine.");
		} catch (MachineException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testStep() throws MachineException {

		// q0:
		// a --> 0 --> q0
		// b --> 1 --> q1
		// q1:
		// a --> 1 --> q1
		// b --> 0 --> q0

		Machine m = new Machine("Tester");
		m.setiAlphabet(new HashSet<Character>(Arrays.asList('a', 'b')));
		m.setiAlphabet(new HashSet<Character>(Arrays.asList('1', '0')));
		m.addState();
		m.addState();
		m.setCurrState(m.getStates().get(0));

		m.getStates().get(0).addTranslation(new Translation('a', '0', m.getStates().get(0), m.getStates().get(0)));
		m.getStates().get(0).addTranslation(new Translation('b', '1', m.getStates().get(0), m.getStates().get(1)));
		m.getStates().get(1).addTranslation(new Translation('a', '1', m.getStates().get(1), m.getStates().get(1)));
		m.getStates().get(1).addTranslation(new Translation('b', '0', m.getStates().get(1), m.getStates().get(0)));

		if (!m.step('a', true).equals('0'))
			fail("step method not working correctly.");
		if (!m.step('b', true).equals('1'))
			fail("step method not working correctly.");
		assertSame(m.getCurrState(), m.getStates().get(1));
		if (!m.step('1', false).equals('a'))
			fail("step method not working correctly.");
		assertEquals(null, m.step('u', true));

	}

	@Test
	public void testEncode() throws MachineException {
		// q0:
		// a --> 0 --> q0
		// b --> 1 --> q1
		// q1:
		// a --> 1 --> q1
		// b --> 0 --> q0

		Machine m = new Machine("Tester");
		m.setiAlphabet(new HashSet<Character>(Arrays.asList('a', 'b')));
		m.setiAlphabet(new HashSet<Character>(Arrays.asList('1', '0')));
		m.addState();
		m.addState();
		m.setCurrState(m.getStates().get(0));

		m.getStates().get(0).addTranslation(new Translation('a', '0', m.getStates().get(0), m.getStates().get(0)));
		m.getStates().get(0).addTranslation(new Translation('b', '1', m.getStates().get(0), m.getStates().get(1)));
		m.getStates().get(1).addTranslation(new Translation('a', '1', m.getStates().get(1), m.getStates().get(1)));
		m.getStates().get(1).addTranslation(new Translation('b', '0', m.getStates().get(1), m.getStates().get(0)));

		assertEquals("0100011000", m.encode("abbaababaa"));
	}

	@Test
	public void testDecode() {
		try {

			Set<Character> in = new HashSet<Character>(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g'));
			Set<Character> out = new HashSet<Character>(Arrays.asList('H', 'I', 'J', 'K', 'L', 'M', 'N'));
			Machine m = new Machine("Tester", in, out);

			String input = new String();
			List<Character> inputgenerator = new ArrayList<Character>(in);

			for (int i = 0; i < 1000; i++) {
				for (int j = 0; j < 5; j++) {
					Collections.shuffle(inputgenerator);
					for (Character currChr : inputgenerator) {
						input += currChr;
					}
				}
				if (!input.equals(m.decode(m.encode(input))))
					fail("decode method not working correctly.");
				input = "";
			}
		} catch (MachineException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetSymbols() throws MachineException {
		Machine m = new Machine("Tester");
		String sentence = "This is a sentence to test getSymbol method.";
		assertEquals(m.getSymbols(sentence),
				new HashSet<Character>(Arrays.asList('T', 'h', 'i', 's', ' ', 'i', 's', ' ', 'a', ' ', 's', 'e', 'n',
						't', 'e', 'n', 'c', 'e', ' ', 't', 'o', ' ', 't', 'e', 's', 't', ' ', 'g', 'e', 't', 'S', 'y',
						'm', 'b', 'o', 'l', ' ', 'm', 'e', 't', 'h', 'o', 'd', '.')));
	}

	@Test
	public void testProcessData() {
		try {
			Machine m = new Machine("Tester");
			m.processData("This is a sentence to test processData method.");
			Set<Character> compareSet = new HashSet<Character>(Arrays.asList('T', 'h', 'i', 's', ' ', 'i', 's', ' ',
					'a', ' ', 's', 'e', 'n', 't', 'e', 'n', 'c', 'e', ' ', 't', 'o', ' ', 't', 'e', 's', 't', ' ', 'p',
					'r', 'o', 'c', 'e', 's', 's', 'D', 'a', 't', 'a', ' ', 'm', 'e', 't', 'h', 'o', 'd', '.'));

			assertEquals(m.getiAlphabet(), compareSet);
			assertEquals(m.getoAlphabet(), compareSet);
			assertTrue(m.isValid());

		} catch (MachineException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetTranslations() {
		try {
			Machine m = new Machine("Tester");
			m.processData("This is a test for getTranslations method.");
			Map<State, List<Translation>> compareTranslations = new HashMap<State, List<Translation>>();

			for (State currState : m.getStates())
				compareTranslations.put(currState, new ArrayList<Translation>());

			for (State currState : m.getStates())
				for (Translation currTranslation : currState.getTranslations())
					compareTranslations.get(currState).add(currTranslation);

			assertEquals(compareTranslations, m.getTranslations());

		} catch (MachineException e) {
			fail(e.getMessage());
		}
	}

}
