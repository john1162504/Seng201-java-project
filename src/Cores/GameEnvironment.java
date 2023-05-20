package Cores;
import java.util.*;

import javax.swing.SwingUtilities;

import Cores.Athlete.Status;
import Cores.Item.Type;
import GUI.Gui;
import UI.CmdLineUi.Difficulty;
import UI.CmdLineUi;
import UI.GameEnvironmentUi;

/**
 * 
 * Class that store game's data and manage the application  
 *
 
 */
public class GameEnvironment {
	
	//user interface associate with this game
	private final GameEnvironmentUi ui;
	
	//Instance of a RandomeEvent class 
	private RandomEvent randomEvent;
	
	//Instance of a Market class
	private Market market;
	
	//Active athletes selected by players
	private ArrayList<Athlete> activeTeam;
	
	//Reserve athletes selected by players
	private ArrayList<Athlete> reserveTeam;
	
	//All items available in this game
	private ArrayList<Item> allItems;
	
	//Available matches for player to compete  
	private ArrayList<ArrayList<Athlete>> matches;

	//Items own by player and the amount
	private HashMap<Item, Integer> inventory = new HashMap<>();
	
	//A instance of class Match
	private Match match;
	
	//Random number generator
	private Random rng = new Random();

	// length of the game
	private int gameLength;
	
	/**
	 * Maximum size for active team
	 */
	public static final int MAX_ACTIVE_TEAM_SIZE = 4;
	
	/**
	 * Maximum size for reserve team
	 */
	public static final int MAX_RESERVE_TEAM_SIZE = 5;
	
	/**
	 * Maximum matches available per week
	 */
	public static final int MAX_MATCHES_AVAILABLE = 5;
	
	
	// current game progress, measure in week
	private int currentWeek = 1;
	
	// score obtained by player 
	private int score = 0;
	
	// money obtained by player
	private int money = 0;
	
	//The name of the team/player
	private String teamName;
	
	//The difficulty selected by player
	private Difficulty difficulty;
	

	/**
	 * Constructor of this class
	 * 
	 * @param ui the user interface use by this game
	 */
	public GameEnvironment(GameEnvironmentUi ui) {
		this.ui = ui;		
	}
	
	/**
	 * Start this game, call {@link GameEnvironmentUi#setup(GameEnvironment)} to initiate the set up process
	 */
	public void start() {
		ui.setup(this);
	}
	
	/**
	 * 
	 * This method should be called by user interface when {@link GameEnvironmentUi#setup(GameEnvironment)}
	 * is finished. This method call {@Link GameEnvironment#start()} to signal user interface to start.  
	 * 
	 * @param name The name of the player
	 * @param gameLength The length of this game
	 * @param startAthletes The list of athletes that the player choose to start with
	 * @param difficulty The difficulty of this game selected by player
	 */
	public void onSetupFinished(String name, int gameLength, ArrayList<Athlete> startAthletes, Difficulty difficulty) {
		this.teamName = name;
		this.gameLength = gameLength;
		this.activeTeam = startAthletes;
		this.reserveTeam = generateAthletes(3);
		this.difficulty = difficulty;
		this.matches = this.refershMatches();
		this.allItems = this.initiateItems();
		this.market = new Market(this);
		this.inventory = this.initiateInventory();
		this.randomEvent = new RandomEvent(this);
		this.currentWeek = 1;
		this.money = 0;
		this.score = 0;
		if (this.difficulty == Difficulty.NORMAL) {
			this.money = 100;
		}
		else {
			this.money = 0;
		}
		ui.start();
		
	}
	
	/**
	 * This method should be called by the user interface when {@link GameEnvironmentUi}
	 * when the player has requested to quit. This method calls {@link GameEnvironmentUi#quit()} after confirming to quit
	 * 
	 */
	public void onFinish() {
		if (ui.confirmQuit()) {
			ui.quit();
		}
	}
	
	/**
	 * This method generate list of random athletes when call
	 * athletes's stat depends on a random factor and {@link GameEnvironment#currentWeek}
	 * 
	 * @param num The number of athletes need to be generated
	 * @return A list of random athletes
	 */
	public ArrayList<Athlete> generateAthletes(int num) {
		
		int currentWeek = this.getCurrentWeek();
		ArrayList<Athlete> athletes = new ArrayList<Athlete>();
		for (int i = 0; i < num; i++) {
			int atk = (10 * currentWeek) + rng.nextInt(5 * currentWeek);
			int def = (5 * currentWeek) + rng.nextInt(3 * currentWeek);
			int sta = 5;
			Athlete athlete = new Athlete(atk, def, sta);
			athletes.add(athlete);
		}
		return athletes;
		
	}
	
	/**
	 * Remove the targeted athlete in team 
	 * 
	 * @param team The team contains the targeted athlete
	 * @param target The athlete to be removed
	 */
	public void removeAthlete(ArrayList<Athlete> team, Athlete target) {
		team.remove(target);
	}
	
	/**
	 * Get the current week of this game
	 * 
	 * @return The current week of this game
	 */
	public int getCurrentWeek() {
		return this.currentWeek;
	}
	
	/**
	 * Get the amount of money gained by player in this game
	 * 
	 * @return The amount of money gained by player in this game
	 */
	public int getMoney() {
		return this.money;
	}
	
	/**
	 * Get remaining time of this game
	 * 
	 * @return The remaining time of this game
	 */
	public int getRemainingWeeks() {
		return this.gameLength - this.currentWeek;
	}
	
	/**
	 * Get the properties of this game, include money, score, current week and remaining week
	 * 
	 * @return A String description of the properties of this game, include money, score, current week and remaining week
	 */
	public String getProperties() {
		return "Money: " + this.money + "\nScore: " + this.score + "\nCurrent Week: "+ this.currentWeek + "\nRemaining Weeks: "+ this.getRemainingWeeks();
	}
	
	
	/**
	 * Get the description of the incoming team
	 * 
	 * @param team The targeted team 
	 * @return The description of the incoming team
	 */
	public String getTeamInfo(ArrayList<Athlete> team) {
		String teamInfo = "";
		for(Athlete athlete: team) {
			teamInfo += athlete.toString()+"\n";
		}
		return teamInfo;
	}
	
	/**
	 * Get the name entered by the player when configuring this game
	 * 
	 * @return The name entered by the player when configuring this game
	 */
	public String getTeamName() {
		return this.teamName;
	}
	
	/**
	 * Get the list of reserved athlete
	 * 
	 * @return The list contains reserved athlete
	 */
	public ArrayList<Athlete> getReserves(){
		return this.reserveTeam;
	}

	/**
	 * Swap an active athlete with a reserve athlete
	 * 
	 * @param active The active athlete to swap down 
	 * @param reserve The reserve athlete to swap up
	 * @return A String description as a feedback to signal player
	 */
	public String swapAthletes(Athlete active, Athlete reserve) {
		this.activeTeam.remove(active);
		this.reserveTeam.remove(reserve);
		this.activeTeam.add(reserve);
		this.reserveTeam.add(active);
		return String.format("Athlete %s has swapped with athlete %s!", active.getName(), reserve.getName());
	}
	
	/**
	 * Get the information of all available matches
	 * 
	 * @return A message contain the information of all available matches
	 */
	public String getMatchInfos() {
		String infos = "";
		for (int i = 0; i < matches.size(); i++) {
			infos += "("+ i + ")\nTeam " + i + "\n" + getTeamInfo(matches.get(i));
		}
		return infos;
	}


	/**
	 * Get all available matches 
	 * 
	 * @return List contain Lists of athlete, each list of athletes is a team for player to compete
	 */
	public ArrayList<ArrayList<Athlete>> getMatches() {
		return this.matches;
	}
	
	/**
	 * Generate a new list of matches 
	 * 
	 * @return A list contain list of new matches  
	 */
	public ArrayList<ArrayList<Athlete>> refershMatches() {
		ArrayList<ArrayList<Athlete>> matches = new ArrayList<ArrayList<Athlete>>(MAX_MATCHES_AVAILABLE);
		for (int i = 0; i < MAX_MATCHES_AVAILABLE; i++) {
			matches.add(this.generateAthletes(MAX_ACTIVE_TEAM_SIZE));
		}
		return matches;
	}
	
	/**
	 * This method is called when player select a match to compete, then create a new instance of 
	 * {@link Match} with the two teams feed in the constructor, after that the selected match is removed and furthermore 
	 * {@link Match#matchBegin()} and {@link Match#matchResult()} is called to construct a detailed message of the match.
	 * Lastly this method call {@link this#matchReward(Match)} to increase player's properties
	 * 
	 * @param index This input indicate which match is selected by the player
	 * @return A detailed message of what happened in the match.
	 */
	public String matchStart(int index) {
		String result = "Your team is not ready to match!";
		if (readyToMatch()) {
			match = new Match(activeTeam,matches.get(index));
			this.matches.remove(index);
			result = match.matchBegin();
			result += match.matchResult();
			matchReward(match);
		}
		return result;
	}
	
	/**
	 * Gather what player earned in the match and add them to this game properties
	 * 
	 * @param match the match
	 */
	private void matchReward(Match match) {
		this.score += match.getScore();
		this.money += match.getMoney();
		
	}
	
	private boolean readyToMatch() {
		boolean ready = true;
		for (Athlete athlete: activeTeam) {
			if (athlete.getStatus() == Status.INJURED) {
				ready = false;
				return ready;
			}
		}
		return ready;
	}

	/**
	 * Get the number of available matches
	 * 
	 * @return The number of available matches
	 */
	public int getNumberOfAvailableMatches() {
		return this.matches.size();
	}
	
	/**
	 * Get the items names and amount own by player
	 * 
	 * @return A description describe items names and amount own by player 
	 */
	public String getInventoryInfo() {
		String info = "";
		int i = 0;
		for (Map.Entry<Item, Integer> item: inventory.entrySet()) {
			info += String.format("(%d) %s you have (%d)\n", i, item.getKey().toString(), item.getValue());
			i++;
		}
		return info;
	}
	
	/**
	 * Initiate an empty inventory for player 
	 * 
	 * @return A hashmap contains all the items as keys as amount as value
	 */
	private HashMap<Item, Integer> initiateInventory() {
		HashMap<Item, Integer>  inventory = new HashMap<>();
		for (Item item: allItems) {
			inventory.put(item, 0);
		}
		return inventory;
	}
	
	/**
	 * Initiate all items and store them in an ArrayList
	 * 
	 * @return The ArrayList that stores all items
	 */
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
	
	/**
	 * Get the inventory
	 * 
	 * @return A HashMap function as a inventory of player
	 */
	public HashMap<Item, Integer> getInventory() {
		return this.inventory;
	}

	
	/**
	 * Get descriptions for all input athletes 
	 * 
	 * @param athletes Athletes that info requested by the player
	 * @return Descriptions for all input athletes 
	 */
	public String getAthletesinfo(ArrayList<Athlete> athletes) {
		String athletesDescription = "";
		int index = 0;
		for (Athlete athlete : athletes) {
			athletesDescription += "(" + index + ") " + athlete + '\n';
			index++;
		}
		return athletesDescription;
	}

	
	/**
	 *  This method is called by player when player requested to end this week,
	 *  heal all athletes by calling method {@link #healAthletes()},
	 *  and refresh matches available and purchasable objects in market by calling {@link refreshPurchasable()} and {@link refershMatches()}.
	 *  If current week is greater than game's length entered by player when this game was configuring,
	 *  call {@link #gameFinished()}
	 * 
	 * @return 
	 */
	public String takeABye() {
		String result = "";
		this.currentWeek +=1;
		if (currentWeek > gameLength) {
			return gameFinished();
		}
		else {
			result += randomEvent.athleteStatIncreaseEvent();
			result += randomEvent.athleteQuitEvent();
			market.refreshPurchasable();
			this.matches = refershMatches();
			result += healAthletes();
			return result;
		}
	}
	
	/**
	 * This method is called when game is finished and return player's status 
	 * 
	 * @return A description of player's status
	 */
	private String gameFinished() {
		return String.format("Game Over!\nYour score is %d", this.score);
	}
	
	/**
	 * This method is called by {@link takeAbye()} when game move on to next week,
	 * and heal all athletes own by player
	 */
	public String healAthletes() {
		for(Athlete athlete: activeTeam) {
			athlete.heal();
		}
		for(Athlete athlete: reserveTeam) {
			athlete.heal();
		}
		return "All athletes have been healed!\n";
	}
	
	
	/**
	 * Get the item in inventory by the input index
	 *  
	 * @param index The item's index entered by player
	 * @return A instance of that item
	 */
	public Item getItemInInventory(int index) {
		return (Item) inventory.keySet().toArray()[index];
	}
	
	/**
	 * update inventory with a new amount of items 
	 * 
	 * @param item The item that amount changes
	 * @param newAmount The new amount
	 */
	public void updateInventory(Item item, int newAmount) {
		this.inventory.put(item, newAmount);
	}


	/**
	 * Get all athletes own by player
	 * 
	 * @return All athletes own by player
	 */
	public ArrayList<Athlete> getAllAthlete() {
		ArrayList<Athlete> all = new ArrayList<>();
		all.addAll(activeTeam);
		all.addAll(reserveTeam);
		return all;
	}

	/**
	 * Use the selected item to the selected athlete by calling {@link Item#useItem(Athlete)}
	 * and return the item effect message as a feedback 
	 * 
	 * @param item The selected item
	 * @param target The selected athlete
	 * @return The item effect message as a feedback 
	 */
	public String useItem(Item item, Athlete target) {
		String itemEffect = item.useItem(target);
		this.updateInventory(item, inventory.get(item) - 1);
		return itemEffect;
	}
	
	/**
	 * Get the active team of this game
	 * 
	 * @return The active team of this game
	 */
	public ArrayList<Athlete> getActiveTeam() {
		return this.activeTeam;
	}
	
	/**
	 * Change the selected athlete's name to a name entered by player,
	 * this method call {@link Athlete#setName(String)} to change name
	 * 
	 * @param athlete The selected athlete
	 * @param newName The new name entered by player
	 * @return A message describes the effect to the selected athlete
	 */
	public String cahngeAtheleName(Athlete athlete, String newName) {
		String oldName = athlete.getName();
		athlete.setName(newName);
		return String.format("%s has changed his name to %s", oldName, newName);
	}


	
	/**
	 * Get information for selling items,
	 * this include item's name, effect, worth and amount own by player
	 * 
	 * @return Information for selling all items,
	 */
	public String getSellInfoItem() {
		int i = 0;
		String infos = "";
		for (Item item: (inventory.keySet())) {
			infos += "(" + i + ")" + item.getSellInfo() + " you have " + inventory.get(item) + '\n';
			i++;
		}
		return infos;
	}
	
	/**
	 * Get information for selling athletes,
	 * this include athlete's name, stats and worth
	 * 
	 * @return Information for selling  athletes,
	 */
	public String getSellInfoAthlete(ArrayList<Athlete> athletes) {
		int i = 0;
		String infos = "";
		for (Athlete athlete: athletes) {
			infos += "(" + i + ")" + athlete.getSellInfo() +'\n';
			i++;
		}
		return infos;
	}
	
	/**
	 * Get the length of this game entered by player when configuring this game 
	 * 
	 * @return The length of this game
	 */
	public int getGameLength() {
		return this.gameLength;
	}

	
    public static void main(String[] args) {
    	
        GameEnvironmentUi ui;

//        if (args.length > 0 && (args[0].equals("cmd"))) {
            ui = new CmdLineUi();
            GameEnvironment manager = new GameEnvironment(ui);
            manager.start();
//        } else {
//            ui = new Gui();
//            GameEnvironment manager = new GameEnvironment(ui);
//
//            // Ensure the RocketManager is started on the Swing event dispatch thread (EDT). To be thread safe,
//            // all swing code should run on this thread unless explicitly stated as being thread safe.
//            SwingUtilities.invokeLater(() -> manager.start());
//        }
    }
    
    /**
     * Get the ArrayList contains all items
     * 
     * @return The ArrayList contains all items
     */
	public ArrayList<Item> getAllItems() {
		return this.allItems;
	}
	
	/**
	 * Set the money of this game to a new value
	 * 
	 * @param newMoney The new value use to replace old value
	 */
	public void setMoney(int newMoney) {
		this.money = newMoney;
	}

	/**
	 * This method call {@link Market#sellItem(int)} to sell a item
	 * more info refer to {@link Market#sellItem(int)}
	 * 
	 * @param index The index of the selected item
	 * @return A description of the process
	 */
	public String sellItem(int index) {
		return market.sellItem(index);
	}
	
	/**
	 * This method call {@link Market#getPurchasableSize()}
	 * @return Size of purchasables
	 */
	public int getPurchasableSize() {
		return market.getPurchasableSize();
	}
	
	/**
	 * This method call {@link Market#buyPurchasable(int)}
	 * refer to {@link Market#buyPurchasable(int)} for further details
	 * 
	 * @param index The index of the selected purchasable object
	 * @return A message as a feedback after this purchase also an indication rather this purchase is success  
	 */
	public String buyPurchasable(int index) {
		return market.buyPurchasable(index);
	}

	/**
	 * This method call {@link Market#sellAthlete(Athlete)}
	 * refer to {@link Market#sellAthlete(Athlete)} for further details
	 * 
	 * @param athlete The selected athlete 
	 * @return A description describe how much player earned as a feedback
	 */
	public String sellAthlete(Athlete athlete) {
		return market.sellAthlete(athlete);
	}
	
	/**
	 * This method call {@link Market#getBuyInfo()}
	 * refer to {@link Market#getBuyInfo()}
	 * 
	 * @return Informations for all purchasable objects on market
	 */
	public String getBuyInfo() {
		return market.getBuyInfo();
	}
}



