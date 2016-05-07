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
			Machine m = new core.Moore.Machine("Moore");
			m.init(in, out);
			System.out.println(m);
			System.out.println("------");
			core.Mealy.Machine mealy = m.toMealy();
			System.out.println(mealy);
			
			
		} catch (MachineExpection e) {
			e.printStackTrace();
		}
		
		

	}

}
