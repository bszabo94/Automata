package io.XML;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		try {
			List<core.Mealy.Machine> mlist = new ArrayList<core.Mealy.Machine>();
			mlist.add(new core.Mealy.Machine("letters", new HashSet<Character>(Arrays.asList('a', 'b')),
					new HashSet<Character>(Arrays.asList('d', 'e'))));
			mlist.add(new core.Mealy.Machine("numbers", new HashSet<Character>(Arrays.asList('1', '2')),
					new HashSet<Character>(Arrays.asList('3', '4'))));
			mlist.add(new core.Mealy.Machine("spec", new HashSet<Character>(Arrays.asList('$', 'Ł')),
					new HashSet<Character>(Arrays.asList('ß', ' '))));

			XMLHandler a = new XMLHandler();

			a.doExport("asd", mlist);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
