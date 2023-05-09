package UI;

import java.util.Scanner;

import Cores.GameEnvironment;

public class CmdLineUi implements GameEnvironmentUi{
	
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

	@Override
	public void setup(GameEnvironment game) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean confirmQuit() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void quit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showError(String error) {
		// TODO Auto-generated method stub
		
	}

}
