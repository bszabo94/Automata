package core.Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class ConversionTests {

	@Test
	public void testToMealy() {
		try {
			String data = "This string serves as data for the text Machine. 0123456789łŁ$ß¤|e<>#";
			core.Moore.Machine moore = new core.Moore.Machine("Test Moore");
			moore.processData(data);
			core.Mealy.Machine mealy = moore.toMealy();

			assertEquals(moore.getID() + " --> Mealy", mealy.getID());
			assertNotSame(moore.getID() + " --> Mealy", mealy.getID());

			assertEquals(moore.getiAlphabet(), mealy.getiAlphabet());
			assertEquals(moore.getoAlphabet(), mealy.getoAlphabet());

			assertNotSame(moore.getiAlphabet(), mealy.getiAlphabet());
			assertNotSame(moore.getoAlphabet(), mealy.getoAlphabet());

			String testinput = new String();
			List<Character> testAlphabet = new ArrayList<Character>(moore.getiAlphabet());

			for (int i = 0; i < 100; i++) {
				for (int j = 0; j < 5; j++) {
					Collections.shuffle(testAlphabet);
					for (Character currChar : testAlphabet) {
						testinput += currChar;
					}
				}
				assertEquals(moore.encode(testinput), mealy.encode(testinput));
				assertNotSame(moore.encode(testinput), mealy.encode(testinput));

				assertEquals(moore.decode(testinput), mealy.decode(testinput));
				assertNotSame(moore.decode(testinput), mealy.decode(testinput));
			}

		} catch (core.Moore.MachineException | core.Mealy.MachineException e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testToMoore() {
		try {
			String data = "This string serves as data for the text Machine. 0123456789łŁ$ß¤|e<>#";
			core.Mealy.Machine mealy = new core.Mealy.Machine("Test Mealy");
			mealy.processData(data);
			core.Moore.Machine moore = mealy.toMoore();

			assertEquals(mealy.getID() + " --> Moore", moore.getID());
			assertNotSame(mealy.getID() + " --> Moore", moore.getID());

			assertEquals(moore.getiAlphabet(), mealy.getiAlphabet());
			assertEquals(moore.getoAlphabet(), mealy.getoAlphabet());

			assertNotSame(moore.getiAlphabet(), mealy.getiAlphabet());
			assertNotSame(moore.getoAlphabet(), mealy.getoAlphabet());

			String testinput = new String();
			List<Character> testAlphabet = new ArrayList<Character>(moore.getiAlphabet());

			for (int i = 0; i < 100; i++) {
				for (int j = 0; j < 5; j++) {
					Collections.shuffle(testAlphabet);
					for (Character currChar : testAlphabet) {
						testinput += currChar;
					}
				}
				assertEquals(moore.encode(testinput), mealy.encode(testinput));
				assertNotSame(moore.encode(testinput), mealy.encode(testinput));

				assertEquals(moore.decode(testinput), mealy.decode(testinput));
				assertNotSame(moore.decode(testinput), mealy.decode(testinput));
			}

		} catch (core.Moore.MachineException | core.Mealy.MachineException e) {
			fail(e.getMessage());
		}

	}

}
