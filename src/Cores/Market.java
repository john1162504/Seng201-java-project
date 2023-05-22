package Cores;

import java.util.ArrayList;
import java.util.HashMap;

public class Market {

	// Available items and athletes for player to purchase
	private ArrayList<Purchasable> purchasables;

	// Instance of a GameEnvironment
	private GameEnvironment game;

	/**
	 * Constructor of this class
	 *
	 * @param game Instance of a GameEnvironment
	 */
	public Market(GameEnvironment game) {
		this.game = game;
		refreshPurchasable();
	}

	/**
	 * Buy the selected purchasable object if player has sufficient funds this is
	 * done by call {@link checkSufficientMoney()}. If the object is an item, the
	 * item is add to player's inventory by calling {@link #updateInventory}, the
	 * item will not be remove in the market, as there is no purchase limit for
	 * items else if object is an athlete, the athlete will be added to player's
	 * reserve team if there is a slot available, then the athlete is removed from
	 * the market
	 *
	 * @param index The index of the selected purchasable object
	 * @return A message as a feedback after this purchase also an indication rather
	 *         this purchase is success
	 */
	protected String buyPurchasable(int index) {
		Purchasable object = this.purchasables.get(index);
		String buyMessage = "You do not have sufficient funds.";
		HashMap<Item, Integer> inventory = game.getInventory();
		if (checkSufficientMoney(object.getPrice())) {
			if (object instanceof Item) {
				int newAmount = inventory.get(object) + 1;
				game.updateInventory((Item) object, newAmount);
				game.setMoney(game.getMoney() - object.getPrice());
				buyMessage = object.getBuyMessage();
			} else {
				ArrayList<Athlete> reserveTeam = game.getReservesTeam();
				if (reserveTeam.size() < GameEnvironment.MAX_RESERVE_TEAM_SIZE) {
					reserveTeam.add((Athlete) object);
					this.purchasables.remove(object);
					buyMessage = object.getBuyMessage();
					game.setMoney(game.getMoney() - object.getPrice());
				} else {
					buyMessage = "Reserve team is full!";
				}
			}
		}
		return buyMessage;
	}

	/**
	 * This method is called by {@link #buyPurchasable(int)} when player requested
	 * to purchase a object
	 *
	 * @param cost The cost of the object
	 * @return True if {@link #money} is greater or equal to the cost, false
	 *         otherwise
	 */
	private boolean checkSufficientMoney(int cost) {
		return game.getMoney() >= cost;
	}

	/**
	 * Get all object own by players that can be sell
	 *
	 * @return An ArrayList contains all object own by players that can be sell
	 */
	protected ArrayList<Purchasable> getAllSellable() {
		ArrayList<Purchasable> sellables = new ArrayList<>();
		for (Item item : game.getInventory().keySet()) {
			sellables.add(item);
		}
		for (Athlete athlete : game.getReservesTeam()) {
			sellables.add(athlete);
		}
		return sellables;
	}

	/**
	 * Get informations for all purchasable objects on market
	 *
	 * @return Informations for all purchasable objects on market
	 */
	protected String getBuyInfo() {
		int i = 0;
		String infos = "";
		for (Purchasable purchasable : purchasables) {
			infos += "(" + i + ")" + purchasable.getBuyInfo() + '\n';
			i++;
		}
		return infos;
	}

	/**
	 * Find the price of the cheapest athlete in {@link purchasables}
	 *
	 * @return The price of the cheapest athlete in {@link purchasables}
	 */
	public int getCheapestAthletePrice() {
		int cheapest = 1000;
		for (Purchasable object : purchasables) {
			if (object instanceof Athlete && object.getPrice() < cheapest) {
				cheapest = object.getPrice();
			}
		}
		return cheapest;
	}

	/**
	 * Get the information of all available purchasable item on market
	 *
	 * @return A message contains the information of all available purchasable item
	 *         on market
	 */
	protected String getPurchasableInfos() {
		String infos = "";
		int index = 0;
		for (Purchasable purchasable : purchasables) {
			infos += String.format("(%d) %s\n", index, purchasable);
			index++;
		}
		return infos;
	}

	/**
	 * Get the ArrayList contains all purchasable objects
	 *
	 * @return The ArrayList contains all purchasable objects
	 */
	protected ArrayList<Purchasable> getPurchasables() {
		return this.purchasables;
	}

	/**
	 * Get the size of the {@link #purchasables}
	 *
	 * @return The size of the {@link #purchasables}
	 */
	protected int getPurchasableSize() {
		return this.purchasables.size();
	}

	/**
	 * Create a new ArrayList that contains list of purchasable objects, use this
	 * new list to replace the old one when called by {@link this#takeABye()}
	 *
	 * @return A new ArrayList that contains list of purchasable objects
	 */
	protected void refreshPurchasable() {
		ArrayList<Purchasable> newPurchasable = new ArrayList<>();
		newPurchasable.addAll(game.getAllItems());
		newPurchasable.addAll(game.generateAthletes(3));
		this.purchasables = newPurchasable;
	}

	/**
	 * Sell a selected athlete
	 *
	 * @param athlete The selected athlete
	 * @return A description describe how much player earned as a feedback
	 */
	protected String sellAthlete(Athlete athlete) {
		int earn = athlete.getWorth();
		game.setMoney(game.getMoney() + earn);
		game.removeAthlete(game.getReservesTeam(), athlete);
		return athlete.getSellMessage();
	}

	/**
	 * Sell a selected item by player and return a description of the process
	 *
	 * @param index The index of the selected item
	 * @return A description of the process
	 */
	protected String sellItem(int index) {
		Item selected = game.getItemInInventory(index);
		String sellMessage = "You do not own any of this item.";
		HashMap<Item, Integer> inventory = game.getInventory();
		if (inventory.get(selected) > 0) {
			int newAmount = inventory.get(selected) - 1;
			game.updateInventory(selected, newAmount);
			game.setMoney(game.getMoney() + selected.getWorth());
			sellMessage = selected.getSellMessage();
		}
		return sellMessage;
	}
}
