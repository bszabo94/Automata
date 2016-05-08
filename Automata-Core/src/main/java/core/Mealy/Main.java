package core.Mealy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

	public static void main(String[] args) {
		Set<Character> in = new HashSet<Character>(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g'));
		Set<Character> out = new HashSet<Character>(Arrays.asList('H', 'I', 'J', 'K', 'L', 'M', 'N'));
		Machine mealy, mealy2;
		core.Moore.Machine moore;

		String input = new String();
		List<Character> inputgenerator = new ArrayList<Character>(in);

		for (int i = 0; i < 10000000; i++) {

			for (int j = 0; j < 5; j++) {
				Collections.shuffle(inputgenerator);
				for (Character currChr : inputgenerator) {
					input += currChr;
				}
			}
			input = "";

			try {
				mealy = new Machine("test", in, out);
				moore = mealy.toMoore();
				mealy2 = moore.toMealy();
				if (!mealy.encode(input).equals(moore.encode(input))) {
					System.out.println("MEALY/MOORE FAILED AT: " + input);
					return;
				}
				if (!mealy2.encode(input).equals(moore.encode(input))) {
					System.out.println("MEALY2/MOORE FAILED AT: " + input);
					return;
				}
				if (!mealy.encode(input).equals(mealy2.encode(input))) {
					System.out.println("MEALY/MEALY2 FAILED AT: " + input);
					return;
				}

			} catch (MachineExpection e) {
				e.printStackTrace();
			}

		}
		System.out.println("All test cases resulted correctly.");
	}

}
