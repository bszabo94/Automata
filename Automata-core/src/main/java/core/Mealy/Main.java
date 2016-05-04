package core.Mealy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main {

	public static void main(String[] args) {
		Set<Character> in = new HashSet<Character>(Arrays.asList('a', 'b', 'c'));
		Set<Character> out = new HashSet<Character>(Arrays.asList('e', 'f', 'g'));
		
		try {
			String a = new String("This is a string! hooray!Ł$ß¤");
			Machine m = new Machine("Mealy");
			m.processData(a);
			System.out.println(m);
			System.out.println(m.encode(a));
			System.out.println(m.decode(m.encode(a)));
		} catch (MachineExpection e) {
			e.printStackTrace();
		}
		
		

	}

}
