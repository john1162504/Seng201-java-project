package Cores;
import java.util.*;

import Cores.Item.Type;
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
	
	private ArrayList<Item> items;
	
	private ArrayList<ArrayList<Athlete>> matches;
	
	private ArrayList<Purchasable> purchasable;
	
	private HashMap<Item, Integer> inventory = new HashMap<>();
	
	private Market market;
	
	private Match match;
		
	//Maximum length of the game
	private final int maxLength = 15;
	
	//Minimum length of the game 
	private final int minLength = 3;
	// length of the game
	private int gameLength;
	
	public static final int MAX_TEAM_SIZE = 4;
	
	public static final int MAX_RESERVE_TEAM_SIZE = 5;
	
	// current game progress, measure in week
	private int currentWeek = 1;
	
	// score obtained by player 
	private int score = 0;
	
	// money obtained by player
	private int money = 0;
	
	private String name;
	
	private Difficulty difficulty;
	

	
	
	public GameEnvironment(GameEnvironmentUi ui) {
		this.ui = ui;		
	}
	

	public void onSetupFinished(String name, int gameLength, ArrayList<Athlete> startAthletes, Difficulty difficulty) {
		this.name = name;
		this.gameLength = gameLength;
		this.allyTeam = startAthletes;
		this.reserveTeam = generateAthletes(3);
		this.difficulty = difficulty;
		this.market = new Market();
		this.matches = this.refershMatches();
		this.items = this.initiateItems();
		this.purchasable = this.refreshPurchasable();
		this.inventory = this.initiateInventory();
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
	
//	public String getAthleteNames(ArrayList<Athlete> team) {
//    	String returnString = "";
//		for (Athlete athlete: team) {
//			returnString += athlete.getName();
//			returnString += ", ";
//		}
//		return returnString.substring(0, returnString.length() -2);
//    }
	
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

	
	public String swapAthletes(Athlete active, Athlete reserve) {
		this.allyTeam.remove(active);
		this.reserveTeam.remove(reserve);
		this.allyTeam.add(reserve);
		this.reserveTeam.add(active);
		return String.format("Athlete %s has swapped with athlete %s!", active.getName(), reserve.getName());
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
	
	public int teamSize() {
		return this.allyTeam.size();
	}
	
	public String inventoryInfo() {
		String info = "";
		for (Map.Entry<Item, Integer> inven: inventory.entrySet()) {
			info += inven.getKey().getName() + ":" + inven.getValue() +'\n';
		}
		return info;
	}
	private HashMap<Item, Integer> initiateInventory() {
		HashMap<Item, Integer>  inventory = new HashMap<>();
		for (Item item: items) {
			inventory.put(item, 0);
		}
		return inventory;
	}
	
	private ArrayList<Item> initiateItems() {
		ArrayList<Item> items = new ArrayList<>();
		Item food = new Item(1, 1, "Food", 5, Type.FOOD);
		Item weight = new Item(1, 1, "Weight", 5, Type.WEIGHT);
		Item medicine = new Item(1, 1, "Medicine", 100, Type.MEDICINE);
		items.add(food);
		items.add(weight);
		items.add(medicine);
		return items;
	}
	
	public HashMap<Item, Integer> getInventory() {
		return this.inventory;
	}


	public String athleteinfo(ArrayList<Athlete> athletes) {
		String athletesDescription = "";
		int index = 0;
		for (Athlete athlete : athletes) {
			athletesDescription += "(" + index + ") " + athlete + '\n';
			index++;
		}
		return athletesDescription;
	}
	
	private ArrayList<Purchasable> refreshPurchasable() {
		ArrayList<Purchasable> market = new ArrayList<>();
		market.addAll(items);
		market.addAll(this.generateAthletes(3));
		return market;
	}


}
