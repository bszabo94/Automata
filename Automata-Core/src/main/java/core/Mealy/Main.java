package core.Mealy;


import core.Moore.MachineException;

public class Main {

	public static void main(String[] args) throws MachineException {
		// Set<Character> in = new HashSet<Character>(Arrays.asList('a', 'b'));
		// Set<Character> out = new HashSet<Character>(Arrays.asList('0', '1',
		// '2'));
		//
		// Machine m = new Machine("mealy");
		// m.setiAlphabet(in);
		// m.setoAlphabet(out);
		//
		// m.addState();
		// m.addState();
		//
		//// m.getStates().get(0).addTranslation(new Translation('a', '0',
		// m.getStates().get(0)));
		//// m.getStates().get(0).addTranslation(new Translation('b', '1',
		// m.getStates().get(1)));
		////
		//// m.getStates().get(1).addTranslation(new Translation('a', '1',
		// m.getStates().get(0)));
		//// m.getStates().get(1).addTranslation(new Translation('c', '0',
		// m.getStates().get(0)));
		//
		// m.getStates().get(0).addTranslation(new Translation('a', '1',
		// m.getStates().get(0)));
		// m.getStates().get(0).addTranslation(new Translation('b', '0',
		// m.getStates().get(0)));
		//
		// m.getStates().get(1).addTranslation(new Translation('a', '2',
		// m.getStates().get(0)));
		// m.getStates().get(1).addTranslation(new Translation('b', '0',
		// m.getStates().get(0)));
		//
		// m.setCurrState(m.getStates().get(0));
		//
		// System.out.println(m.isValid());
		//
		// String teststring = "it's working! yaay";
		// Machine tester = new Machine("tester");
		//
		// Set<Character> testinput = tester.getSymbols(teststring);
		// Set<Character> testoutput = new HashSet<Character>(testinput);
		// testoutput.addAll(Arrays.asList('Ł', 'ß', '$', '<', '#', '>', '@'));
		// try {
		// tester.init(testinput, testoutput);
		// } catch (MachineExpection e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		//
		// System.out.println(tester);
		// System.out.println(tester.encode(teststring));
		// System.out.println(tester.decode(tester.encode(teststring)));
		//
		
		
		
		


//	}
//
//	Set<Character> in = new HashSet<Character>(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g'));
//	Set<Character> out = new HashSet<Character>(Arrays.asList('H', 'I', 'J', 'K', 'L', 'M', 'N'));
//	Machine mealy, mealy2;
//	core.Moore.Machine moore;
//
//	String input = new String();
//	List<Character> inputgenerator = new ArrayList<Character>(in);
//
//	for(
//	int i = 0;i<1000;i++)
//	{
//
//		for (int j = 0; j < 5; j++) {
//			Collections.shuffle(inputgenerator);
//			for (Character currChr : inputgenerator) {
//				input += currChr;
//			}
//		}
//		input = "";
//
//		try {
//			mealy = new Machine("test", in, out);
//			moore = mealy.toMoore();
//			mealy2 = moore.toMealy();
//			if (!mealy.encode(input).equals(moore.encode(input))) {
//				System.out.println("MEALY/MOORE FAILED AT: " + input);
//				return;
//			}
//			if (!mealy2.encode(input).equals(moore.encode(input))) {
//				System.out.println("MEALY2/MOORE FAILED AT: " + input);
//				return;
//			}
//			if (!mealy.encode(input).equals(mealy2.encode(input))) {
//				System.out.println("MEALY/MEALY2 FAILED AT: " + input);
//				return;
//			}
//
//		} catch (MachineExpection e) {
//			e.printStackTrace();
//		}
//
//		}
//		System.out.println("All test cases resulted correctly.");
	}

}
