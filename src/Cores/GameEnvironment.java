package Cores;
import java.util.*;

/**
 * 
 * Class that store game's data and manage the application  
 *
 */
public class GameEnvironment {
	
    String NAME_REGEX = "[a-zA-Z]{3,}";

	//Maximum length of the game
	private final int maxLength = 15;
	
	//Minimum length of the game 
	private final int minLength = 3;
	// length of the game
	private int gameLength;
	
	// current game progress, measure in week
	private int currenytWeek;
	
	// score obtained by player 
	private int score = 0;
	
	// money obtained by player
	private int money = 0;
	
	Scanner scan = new Scanner(System.in); 
	
	public void setup() {
		
	}
	
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
	
	public static void main(String[] args) {
		GameEnvironment game = new GameEnvironment();
		String name = game.getName();
		System.out.println(name);
		
		
	}

}
