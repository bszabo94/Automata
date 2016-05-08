package io.XML;



public class Main {

	public static void main(String[] args) {

		try {
//			List<core.Mealy.Machine> mlist = new ArrayList<core.Mealy.Machine>();
//			mlist.add(new core.Mealy.Machine("letters", new HashSet<Character>(Arrays.asList('a', 'b', 'c')),
//					new HashSet<Character>(Arrays.asList('d', 'e', 'f'))));
//			mlist.add(new core.Mealy.Machine("numbers", new HashSet<Character>(Arrays.asList('1', '2', '3')),
//					new HashSet<Character>(Arrays.asList('3', '4', '5'))));
//			mlist.add(new core.Mealy.Machine("spec", new HashSet<Character>(Arrays.asList('$', 'Ł', 'ß')),
//					new HashSet<Character>(Arrays.asList('ß', ' ', 'ł'))));
//
//			XMLHandler a = new XMLHandler();

//			a.doExport("asd", mlist);
			XMLHandler a = new XMLHandler();
			
			a.doImport("asd");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
