package UI;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Cores.Athlete;
import Cores.Club;
import Cores.GameEnvironment;
import Cores.Market;
import Cores.Match;
//

public class CmdLineUi implements GameEnvironmentUi{
	
	private Club club;
	
	private Market market;
	
	private Match match;
	
	private GameEnvironment game;
	
	private final Scanner scan; 
	
	private enum Option {
		PROPERTIES("Properties"), //print out games properties
		CLUB("Club"),
		STADIUM("Studium"),
		MARKET("Market"),
		BYE("Take a bye");
		
		public final String name;
		
		Option(String name) {
			this.name = name;
		}
	}
	
	public enum Difficulty {
		NORMAL("Normal"),
		HARD("Hard");
		
		public final String name;
		
		Difficulty(String name) {
			this.name = name;
		}
		
	}
	
	public  CmdLineUi() {
		 this.scan = new Scanner(System.in);
	}
	
	

	@Override
	public void setup(GameEnvironment game) {
		this.game = game;
		final String name = getName();
		final int gameLength = getGameLength();
		final ArrayList<Athlete> startAthletes = getStartAthlets();
		final Difficulty difficulty = getDifficulty();
		game.onSetupFinished(name, gameLength, startAthletes, difficulty);
		
		
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

	private Difficulty getDifficulty() {
		// TODO Auto-generated method stub
		return null;
	}


	private int getGameLength() {
		// TODO Auto-generated method stub
		return 0;
	}


	private ArrayList<Athlete> getStartAthlets() {
		// TODO Auto-generated method stub
		return null;
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
