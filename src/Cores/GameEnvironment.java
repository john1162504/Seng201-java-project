package Cores;
import java.util.*;

import UI.CmdLineUi.Difficulty;
import UI.GameEnvironmentUi;

/**
 * 
 * Class that store game's data and manage the application  
 * @param <Difficulty>
 *
 */
public class GameEnvironment {
	
	private final GameEnvironmentUi ui;
	
	private Team allyTeam;
	
	private Team opponentTeam;
	
	private Market market;
	
	private Match match;
	
	private Club club;
	
	private final List<Athlete> Athletes;
	
	private final List<Item> items;
	//Maximum length of the game
	private final int maxLength = 15;
	
	//Minimum length of the game 
	private final int minLength = 3;
	// length of the game
	private int gameLength;
	
	// current game progress, measure in week
	private int currenytWeek = 1;
	
	// score obtained by player 
	private int score = 0;
	
	// money obtained by player
	private int money = 0;
	
	private String name;
	
	private Difficulty difficulty;
	
	
	public GameEnvironment(GameEnvironmentUi ui, List<Athlete> athletes, List<Item> items) {
		this.ui = ui;
		this.Athletes = athletes;
		this.items = items;
		
		
	}
	

	public void onSetupFinished(String name, int gameLength, ArrayList<Athlete> startAthletes, Difficulty difficulty) {
		this.name = name;
		this.gameLength = gameLength;
		this.allyTeam = new Team(startAthletes);
		this.difficulty = difficulty;
		this.market = new Market();
		this.match = new Match();
		this.club = new Club();
		ui.start();
		
		
	}
	
	
	public static void main(String[] args) {
		
	}

}
