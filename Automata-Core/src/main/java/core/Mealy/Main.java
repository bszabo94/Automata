package core.Mealy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main {

	public static void main(String[] args) {
		Set<Character> in = new HashSet<Character>(Arrays.asList('1', '0'));
		Set<Character> out = new HashSet<Character>(Arrays.asList('1', '0'));
		
//		try {
			Machine m = new Machine("Mealy");
			m.setiAlphabet(in);
			m.setoAlphabet(out);
			m.addState();
			m.addState();
			m.addState();
			m.addState();
			
			m.getStates().get(0).addTranslation(new Translation('0', '0', m.getStates().get(3)));
			m.getStates().get(0).addTranslation(new Translation('1', '1', m.getStates().get(1)));
			
			m.getStates().get(1).addTranslation(new Translation('0', '1', m.getStates().get(0)));
			m.getStates().get(1).addTranslation(new Translation('1', '0', m.getStates().get(3)));
			
			m.getStates().get(2).addTranslation(new Translation('0', '1', m.getStates().get(2)));
			m.getStates().get(2).addTranslation(new Translation('1', '0', m.getStates().get(2)));
			
			m.getStates().get(3).addTranslation(new Translation('0', '0', m.getStates().get(1)));
			m.getStates().get(3).addTranslation(new Translation('1', '1', m.getStates().get(0)));
			
			System.out.println(m);
			core.Moore.Machine moo = m.toMoore();
			System.out.println(moo);
//		} catch (MachineExpection e) {
//			e.printStackTrace();
//		}
		
		

	}

}
