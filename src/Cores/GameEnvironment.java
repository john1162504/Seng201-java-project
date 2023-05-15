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
	
	private ArrayList<Athlete> activeTeam;
	
	private ArrayList<Athlete> reserveTeam;
	
	private ArrayList<Item> items;
	
	private ArrayList<ArrayList<Athlete>> matches;
	
	private ArrayList<Purchasable> purchasables;
	
	private HashMap<Item, Integer> inventory = new HashMap<>();
		
	private Match match;
	
	private Random rng = new Random();

	// length of the game
	private int gameLength;
	
	public static final int MAX_TEAM_SIZE = 4;
	
	public static final int MAX_RESERVE_TEAM_SIZE = 5;
	
	public static final int MAX_MATCHES_AVAILABLE = 5;
	
	
	// current game progress, measure in week
	private int currentWeek = 1;
	
	// score obtained by player 
	private int score = 0;
	
	// money obtained by player
	private int money = 0;
	
	private String teamName;
	
	private Difficulty difficulty;
	

	
	
	public GameEnvironment(GameEnvironmentUi ui) {
		this.ui = ui;		
	}
	

	public void onSetupFinished(String name, int gameLength, ArrayList<Athlete> startAthletes, Difficulty difficulty) {
		this.teamName = name;
		this.gameLength = gameLength;
		this.activeTeam = startAthletes;
		this.reserveTeam = generateAthletes(3);
		this.difficulty = difficulty;
		this.matches = this.refershMatches();
		this.items = this.initiateItems();
		this.purchasables = this.refreshPurchasable();
		this.inventory = this.initiateInventory();
		this.money += 1000; // for testing delete later
//		if (this.difficulty == Difficulty.NORMAL) {
//			this.money = 100;
//		}
//		else {
//			this.money = 0;
//		}
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
		return "Money: " + this.money + "\nScore: " + this.score + "\nCurrent Week: "+ this.currentWeek + "\nRemaining Weeks: "+ this.getRemainingWeeks();
	}
	
	public String viewTeam(ArrayList<Athlete> team) {
		String teamInfo = "";
		for(Athlete athlete: team) {
			teamInfo += athlete.toString()+"\n";
		}
		return teamInfo;
	}
	
	
	public ArrayList<Athlete> getTeam(){
		return this.activeTeam;
	}
	
	public String getTeamName() {
		return this.teamName;
	}
	
	public ArrayList<Athlete> getReserves(){
		return this.reserveTeam;
	}

	
	public String swapAthletes(Athlete active, Athlete reserve) {
		this.activeTeam.remove(active);
		this.reserveTeam.remove(reserve);
		this.activeTeam.add(reserve);
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
	
	public String getPurchasableInfos() {
		String infos = "";
		int index = 0;
		for (Purchasable purchasable: purchasables) {
			infos += String.format("(%d) %s\n", index, purchasable);
			index++;
		}
		return infos;
	}

	
	public ArrayList<ArrayList<Athlete>> getMatches() {
		return this.matches;
	}
	
	public ArrayList<ArrayList<Athlete>> refershMatches() {
		ArrayList<ArrayList<Athlete>> matches = new ArrayList<ArrayList<Athlete>>(MAX_MATCHES_AVAILABLE);
		for (int i = 0; i < MAX_MATCHES_AVAILABLE; i++) {
			matches.add(this.generateAthletes(MAX_TEAM_SIZE));
		}
		return matches;
	}
	
	public String match(int index) {
		match = new Match(activeTeam,matches.get(index));
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
		return this.activeTeam.size();
	}
	
	public String inventoryInfo() {
		String info = "";
		int i = 0;
		for (Map.Entry<Item, Integer> item: inventory.entrySet()) {
			info += String.format("(%d) %s you have (%d)\n", i, item.getKey().toString(), item.getValue());
			i++;
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
	
	public void takeABye() {
		this.currentWeek +=1;
		this.purchasables = refreshPurchasable();
		this.matches = refershMatches();
		this.healAthletes();
		
		
	}
	
	public void healAthletes() {
		for(Athlete athlete: activeTeam) {
			athlete.heal();
		}
		for(Athlete athlete: reserveTeam) {
			athlete.heal();
		}
	}
	
	public String athleteStatIncreaseEvent() {
		int increaseChance = rng.nextInt(100);
		String result = "No other athlete's stat's have increased";
		if(increaseChance < 20) {
			int chosenAthleteIndex = rng.nextInt(4);
			Athlete chosenAthlete = activeTeam.get(chosenAthleteIndex);
			chosenAthlete.increaseStats(5);
			result = "Athlete "+chosenAthlete.getName()+"'s stats have increased by 5!";
		}
		return result;
	}
	
	public String athleteQuitEvent() {
		String result = "All athletes are high in morale!";
		for(Athlete athlete: activeTeam) {
			int quitChance = rng.nextInt(100);
			if(quitChance < 5 && (athlete.getStamina() > 0)) {
				removeAthlete(activeTeam, athlete);
				return "Athlete "+athlete.getName() +" has decided to leave the team...";
			}
			else if(quitChance < 30 && (athlete.getStamina() <= 0)){
				removeAthlete(activeTeam, athlete);
				return "Athlete "+athlete.getName() +" is injured and has decided to leave the team...";
			}
		}
		return result;
	}

	public Item getItemInInventory(int index) {
		return (Item) inventory.keySet().toArray()[index];
	}
	
	private void updateInventory(Item item, int newAmount) {
		this.inventory.put(item, newAmount);
	}

	public String sellItem(int index) {
		Item selected = this.getItemInInventory(index);
		String sellMessage = "You do not own any of this item.";
		if (this.inventory.get(selected) > 0) {
			int newAmount = this.inventory.get(selected) - 1;
			this.updateInventory(selected, newAmount);
			this.money += selected.getWorth();
			sellMessage = selected.sellMessage();
		}
		return sellMessage;
	}


	public String buyPurchasable(int index) {
		Purchasable object = this.purchasables.get(index);
		String buyMessage = "You do not have sufficient funds.";
		if (checkSufficientMoney(object.getPrice())) {
			if (object instanceof Item) {
				int newAmount = this.inventory.get(object) + 1;
				this.updateInventory((Item) object, newAmount);
				this.money -= object.getPrice();
				buyMessage = object.buyMessage();
			} 
			else {
				if (reserveTeam.size() < MAX_RESERVE_TEAM_SIZE) {
					this.reserveTeam.add((Athlete) object);
					this.purchasables.remove(object);
					buyMessage = object.buyMessage();
					this.money -= object.getPrice();
				} 
				else {
					buyMessage = "Reserve team is full!";
				}
			}
		}
		return buyMessage;
		
	}
	
	private boolean checkSufficientMoney(int cost) {
		return this.money >= cost;
	}


	public int getPurchasableSize() {
		return this.purchasables.size();
	}
	
	public ArrayList<Athlete> getAllAthlete() {
		ArrayList<Athlete> all = new ArrayList<>();
		all.addAll(activeTeam);
		all.addAll(reserveTeam);
		return all;
	}


	public String useItem(Item item, Athlete target) {
		String itemEffect = item.useItem(target);
		this.updateInventory(item, inventory.get(item) - 1);
		return itemEffect;
	}
	
	public ArrayList<Athlete> getActiveTeam() {
		return this.activeTeam;
	}
	



	public String cahngeAtheleName(Athlete athlete, String newName) {
		String oldName = athlete.getName();
		athlete.setName(newName);
		return String.format("%s has changed his name to %s", oldName, newName);
	}


	public String sellAthlete(Athlete athlete) {
		int earn = athlete.getWorth();
		this.money += earn;
		this.removeAthlete(reserveTeam, athlete);
		return athlete.sellMessage();
	}
	
	public String getSellInfoItem() {
		int i = 0;
		String infos = "";
		for (Item item: (inventory.keySet())) {
			infos += "(" + i + ")" + item.sellInfo() + " you have " + inventory.get(item) + '\n';
			i++;
		}
		return infos;
	}
	
	public String getSellInfoAthlete(ArrayList<Athlete> athletes) {
		int i = 0;
		String infos = "";
		for (Athlete athlete: athletes) {
			infos += "(" + i + ")" + athlete.sellInfo() +'\n';
			i++;
		}
		return infos;
	}
	
	public String getBuyInfo() {
		int i =0;
		String infos = "";
		for (Purchasable purchasable: purchasables) {
			infos += "(" + i + ")" + purchasable.buyInfo() +'\n';
			i++;
		}
		return infos;
	}


}
