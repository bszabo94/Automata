package io.XML;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;


public class Main {

	public static void main(String[] args) {

		try {
			List<core.Mealy.Machine> mlist = new ArrayList<core.Mealy.Machine>();
			mlist.add(new core.Mealy.Machine("letters", new HashSet<Character>(Arrays.asList('a', 'b', 'c')),
					new HashSet<Character>(Arrays.asList('d', 'e', 'f'))));
			mlist.add(new core.Mealy.Machine("numbers", new HashSet<Character>(Arrays.asList('1', '2', '3')),
					new HashSet<Character>(Arrays.asList('3', '4', '5'))));
			mlist.add(new core.Mealy.Machine("spec", new HashSet<Character>(Arrays.asList('$', 'Ł', 'ß')),
					new HashSet<Character>(Arrays.asList('ß', ' ', 'ł'))));
			mlist.add(new core.Mealy.Machine("big",
					new HashSet<Character>(
							Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c')),
					new HashSet<Character>(
							Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c'))));
			
			List<core.Moore.Machine> moorelist = new ArrayList<core.Moore.Machine>();
			moorelist.add(new core.Moore.Machine("letters", new HashSet<Character>(Arrays.asList('a', 'b', 'c')),
					new HashSet<Character>(Arrays.asList('d', 'e', 'f'))));
			moorelist.add(new core.Moore.Machine("numbers", new HashSet<Character>(Arrays.asList('1', '2', '3')),
					new HashSet<Character>(Arrays.asList('3', '4', '5'))));
			moorelist.add(new core.Moore.Machine("spec", new HashSet<Character>(Arrays.asList('$', 'Ł', 'ß')),
					new HashSet<Character>(Arrays.asList('ß', ' ', 'ł'))));
			moorelist.add(new core.Moore.Machine("big",
					new HashSet<Character>(
							Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c')),
					new HashSet<Character>(
							Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c'))));

			XMLHandler a = new XMLHandler();

			String filename = "testIO.xml";
			String filename2 = "testMooreIO.xml";
			List<core.Moore.Machine> importedmoore = new ArrayList<core.Moore.Machine>();
			

			a.exportMealy(filename, mlist);
			a.exportMoore(filename2, moorelist);
			importedmoore = a.importMoore(filename2);
			System.out.println(importedmoore);
			XMLHandler b = new XMLHandler();

			List<core.Mealy.Machine> myList = b.importMealy(filename);
			core.Mealy.Machine myMealy = myList.get(myList.size() - 1);
			core.Moore.Machine myMoore = myMealy.toMoore();
			core.Mealy.Machine mySecond = myMoore.toMealy();
			core.Moore.Machine myMooreSecond = mySecond.toMoore();
			core.Mealy.Machine asd = myMooreSecond.toMealy();
			// System.out.println(myMoore);

			List<Character> var = new ArrayList<Character>(myMoore.getiAlphabet());
			String testinput = new String();
			int cntr = 0;
			for (int i = 0; i < 10000; i++) {
				for (int j = 0; j < 5; j++) {
					Collections.shuffle(var);
					for (int k = 0; k < 6; k++) {
						testinput += var.get(k);
					}
				}
				if (myMealy.encode(testinput).equals(myMoore.encode(testinput)))
					cntr++;
				// System.out.println(myMealy.encode(testinput) + " --- " +
				// myMoore.encode(testinput) + " --- "
				// + mySecond.encode(testinput) + " --- " +
				// myMooreSecond.encode(testinput) + " --- " +
				// asd.encode(testinput));
				testinput = "";
			}
			System.out.println(cntr);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
