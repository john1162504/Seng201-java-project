package UI;

import java.util.Scanner;

public class CmdLineUi {
	
	Scanner scan = new Scanner(System.in); 
	
	public String getName() {
		boolean done = false;
		String name = null;
		while (!done) {
			System.out.println("Enter your name below");
			name = scan.nextLine();
			if (this.checkNameValidity(name)) {
				done = true;
			}
		}
		return name;
	}
	
	public boolean checkNameValidity(String string) {
		if (string.length() > 15 || string.length() < 3) {
			System.out.println("Length must be between 3 and 15!");
			return false;
		}
		else if (!string.matches(NAME_REGEX)) {
			System.out.println("Name must not contains special charater");
			return false;
		}
		else {
			return true;
		}
	}

}
