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
	

	public void onSetupFinished(String name2, int gameLength2, List<Athlete> startAthletes, Difficulty difficulty) {
		// TODO Auto-generated method stub
		
	}
	
	
	public static void main(String[] args) {
		
	}

}
