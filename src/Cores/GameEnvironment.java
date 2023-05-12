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
	
	private ArrayList<Athlete> allyTeam;
	
	private ArrayList<Athlete> reserveTeam;
	
	private ArrayList<Athlete> opponentTeam;
	
	private ArrayList<ArrayList<Athlete>> matches;
	
	private HashMap<String, Integer> inventory = new HashMap<>();
	
	private Market market;
	
	private Match match;
		
	private final List<Item> items;
	//Maximum length of the game
	private final int maxLength = 15;
	
	//Minimum length of the game 
	private final int minLength = 3;
	// length of the game
	private int gameLength;
	
	public static final int MAX_TEAM_SIZE = 4;
	
	// current game progress, measure in week
	private int currentWeek = 1;
	
	// score obtained by player 
	private int score = 0;
	
	// money obtained by player
	private int money = 0;
	
	private String name;
	
	private Difficulty difficulty;
	

	
	
	public GameEnvironment(GameEnvironmentUi ui, List<Item> items) {
		this.ui = ui;
		this.items = items;
		
		
	}
	

	public void onSetupFinished(String name, int gameLength, ArrayList<Athlete> startAthletes, Difficulty difficulty) {
		this.name = name;
		this.gameLength = gameLength;
		this.allyTeam = startAthletes;
		this.difficulty = difficulty;
		this.market = new Market();
		this.matches = this.refershMatches();
		ui.start();
		
	}
	
	public ArrayList<Athlete> generateAthletes(int num) {
		Random ran = new Random();
		int currentWeek = this.getCurrentWeek();
		ArrayList<Athlete> athletes = new ArrayList<Athlete>();
		for (int i = 0; i < num; i++) {
			int atk = (10 * currentWeek) + ran.nextInt(5 * currentWeek);
			int def = (5 * currentWeek) + ran.nextInt(3 * currentWeek);
			int sta = (15 * currentWeek) + ran.nextInt(10 * currentWeek);
			Athlete athlete = new Athlete(atk, def, sta);
			athletes.add(athlete);
		}
		return athletes;
		
	}
	
	public String getAthleteNames(ArrayList<Athlete> team) {
    	String returnString = "";
		for (Athlete athlete: team) {
			returnString += athlete.getName();
			returnString += ", ";
		}
		return returnString.substring(0, returnString.length() -2);
    }
	
	public void addNewAthlete(ArrayList<Athlete> team , Athlete newAthlete) {
		if (team.size() < 4) {
			team.add(newAthlete);
		} else {
			System.out.println("Team is full");
		}
	}
	
	public void removeAthlete(ArrayList<Athlete> team, Athlete target) {
		team.remove(target);
	}
	
	public int getCurrentWeek() {
		return this.currentWeek;
	}
	public int getMoney() {
		return this.money;
	}
	public int getRemainingWeeks() {
		return this.gameLength - this.currentWeek;
	}
	
	public String getProperties() {
		return "Money: " +this.money + "\nCurrent Week: "+this.currentWeek + "\nRemaining Weeks: "+ this.getRemainingWeeks();
	}
	
	public String viewTeam(ArrayList<Athlete> team) {
		String teamInfo = "";
		for(Athlete athlete: team) {
			teamInfo += athlete.toString()+"\n";
		}
		return teamInfo;
	}
	
	public ArrayList<Athlete> getTeam(){
		return this.allyTeam;
	}
	
	public String teamName() {
		return this.name;
	}
	
	public ArrayList<Athlete> getReserves(){
		return this.reserveTeam;
	}


	

	public String getMatchInfos() {
		String infos = "";
		for (int i = 0; i < matches.size(); i++) {
			infos += "Macth " + i + "\n" + viewTeam(matches.get(i));
		}
		return infos;
	}

	
	public ArrayList<ArrayList<Athlete>> getMatches() {
		return this.matches;
	}
	
	public ArrayList<ArrayList<Athlete>> refershMatches() {
		ArrayList<ArrayList<Athlete>> matches = new ArrayList<ArrayList<Athlete>>(3);
		for (int i = 0; i < 3; i++) {
			matches.add(this.generateAthletes(MAX_TEAM_SIZE));
		}
		return matches;
	}
	
	public String match(int index) {
		match = new Match(allyTeam,matches.get(index));
		String matchDetails = match.matchBegin();
		String matchResult = match.matchResult();
		matchReward(match);
		return matchDetails + '\n' + matchResult;
	}
	
	private void matchReward(Match match) {
		this.score += match.getScore();
		this.money += match.getMoney();
		
	}


	public int availableMatches() {
		return this.matches.size();
	}
	


}
