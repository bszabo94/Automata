package core.Moore;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main {

	public static void main(String[] args) {
		Set<Character> in = new HashSet<Character>(Arrays.asList('a', 'b', 'c'));
		Set<Character> out = new HashSet<Character>(Arrays.asList('e', 'f', 'g'));
		
		try {
			String a = new String("aabcc");
			Machine m = new Machine("Moore");
			m.init(in, out);
			System.out.println(m);
			System.out.println(a);
			System.out.println(m.encode(a));
			System.out.println(m.decode(m.encode(a)));
			System.out.println(m.isValid());
			
			m.getiAlphabet().add('d');
			m.getoAlphabet().add('h');
			m.addState('h');
			m.getState(3).addTranslation(new Translation('a', m.getState(0)));
			m.getState(3).addTranslation(new Translation('b', m.getState(1)));
			m.getState(3).addTranslation(new Translation('c', m.getState(2)));
			m.getState(3).addTranslation(new Translation('d', m.getState(3)));
			
			m.getState(0).addTranslation(new Translation('d', m.getState(3)));
			m.getState(1).addTranslation(new Translation('d', m.getState(3)));
			m.getState(2).addTranslation(new Translation('d', m.getState(3)));
			System.out.println(m.isValid());
			System.out.println(m);
			
		} catch (MachineExpection e) {
			e.printStackTrace();
		}
		
		

	}

}
