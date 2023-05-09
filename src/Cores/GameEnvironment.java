package Cores;
import java.util.*;

import UI.GameEnvironmentUi;

/**
 * 
 * Class that store game's data and manage the application  
 *
 */
public class GameEnvironment {
	
	private final GameEnvironmentUi ui;
	
	private Team allyTeam;
	
	private Team opponentTeam;
	
	private Club club;
	
	private Market market;
	
	private Match match;
	
	private final List<Athlete> Athletes;
	
	private final List<Item> items;
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
	
	private String name;
	
	
	public GameEnvironment(GameEnvironmentUi ui, List<Athlete> athletes, List<Item> items) {
		this.ui = ui;
		this.Athletes = athletes;
		this.items = items;
		
		
	}
	
	
	public void setup() {
		
	}

	
	public static void main(String[] args) {
		
	}

}
