package Cores;

import java.util.Random;

/**
 * A class describing the attributes of a given Athlete
 */
public class Athlete implements Purchasable {

	/**
	 *
	 * contain available names for {@link Athlete}
	 *
	 */
	private enum Names {
		ETHAN("Ethan"), JACOB("Jacob"), LUCAS("Lucas"), WILLIAM("William"), BENJAMIN("Benjamin"),
		CHRISTOPHER("Christopher"), DAVID("David"), MICHAEL("Michael"), ALEXANDER("Alexander"), OLIVER("Oliver"),
		MATTHEW("Matthew"), NOAH("Noah"), SAMUEL("Samuel"), JOSEPH("Joseph"), DANIEL("Daniel"), JAMES("James"),
		ANDREW("Andrew"), NICHOLAS("Nicholas"), GABRIEL("Gabriel"), RYAN("Ryan");

		public final String name;

		Names(String name) {
			this.name = name;
		}
	}

	/**
	 * Represent the status of a {@link Athlete}
	 */
	public enum Status {
		INJURED("Injured"), ACTIVE("Active");

		/**
		 * A User friendly description of the value of this enum
		 */
		public final String status;

		Status(String status) {
			this.status = status;
		}
	}

	// attack value of athlete
	private int attack;
	// defence value of athlete
	private int defense;
	// Max stamina value of athlete
	private int maxStamina;
	// Current stamina of this athlete
	private int currentStamina;
	// name of athlete
	private String name;
	// how much given athlete contract costs in the market
	private int price;
	// how much given athlete contract sells for in the market
	private int worth;
	// The status of this athlete
	private Status status;

	/**
	 * Constructor for athlete objects
	 * 
	 * @param attack
	 * @param defence
	 * @param maxStamina
	 */
	public Athlete(int attack, int defence, int maxStamina) {
		this.attack = attack;
		this.defense = defence;
		this.maxStamina = maxStamina;
		this.currentStamina = this.maxStamina;
		this.worth = (this.attack + this.defense + this.currentStamina);
		this.price = (this.worth) * 2;
		this.name = this.getRandomName();
		this.status = Status.ACTIVE;
	}

	/**
	 * getter for {@link Athlete} attack value as int
	 *
	 * @return attack of {@link Athlete}
	 */
	public int getAttack() {
		return this.attack;
	}

	@Override
	public String getBuyInfo() {
		return String.format("%s need %d$ to purchasae", this.toString(), this.price);
	}

	@Override
	public String getBuyMessage() {
		return String.format("%s has joined your team!", this.name);
	}

	/**
	 * getter for {@link Athlete} current stamina value as int
	 *
	 * @return current stamina of {@link Athlete}
	 */
	public int getCurrentStamina() {
		return this.currentStamina;
	}

	/**
	 * getter for {@link Athlete} defense value as int
	 *
	 * @return defense of {@link Athlete}
	 */
	public int getDefence() {
		return this.defense;
	}

	/**
	 * getter for {@link Athlete} max stamina value as int
	 *
	 * @return max stamina of {@link Athlete}
	 */
	public int getMaxStamina() {
		return this.maxStamina;
	}

	/**
	 * getter for {@link Athlete} name as String
	 *
	 * @return name of {@link Athlete}
	 */
	public String getName() {
		return this.name;
	}

	@Override
	public int getPrice() {
		return getWorth() * 2;
	}

	/**
	 *
	 * @return a random name from enum Names
	 */
	private String getRandomName() {
		Names[] names = Names.values();
		Random random = new Random();
		int index = random.nextInt(names.length);
		return names[index].name;
	}

	@Override
	public String getSellInfo() {
		return String.format("%s is worth %d$", this.toString(), this.worth);
	}

	@Override
	public String getSellMessage() {
		return String.format("Sold %s you received %d$", this.name, this.worth);
	}

	/**
	 * getter for {@link Athlete} status as Status
	 *
	 * @return status of {@link Athlete}
	 */
	public Status getStatus() {
		return this.status;
	}

	@Override
	public int getWorth() {
		return (this.attack + this.defense + this.currentStamina);
	}

	/**
	 * set {@Link Athlete} status to be Status.ACTIVE and restore current stamina to
	 * max
	 *
	 */
	public void heal() {
		this.status = Status.ACTIVE;
		this.currentStamina = this.maxStamina;
	}

	/**
	 * method that increase {@Link Athlete} attack and defense
	 *
	 * @param amount increase {@Link Athlete} stats by amount
	 */
	public void increaseStats(int amount) {
		this.attack += amount;
		this.defense += amount;
	}

	/**
	 * setter for {@Link Athlete} attack
	 *
	 * @param newAttack to replace {@Link Athlete} attack
	 */
	public void setAttack(int newAttack) {
		this.attack = newAttack;
	}

	/**
	 * setter for {@Link Athlete} current stamina
	 *
	 * @param newStamina to replace {@Link Athlete} current stamina
	 */
	public void setCurrentStamina(int newStamina) {
		this.currentStamina = newStamina;
		if (currentStamina < 0) {
			currentStamina = 0;
		}
	}

	/**
	 * setter for {@Link Athlete} defense
	 *
	 * @param newdefense to replace {@Link Athlete} defense
	 */
	public void setDefense(int newDefense) {
		this.defense = newDefense;
	}

	/**
	 * setter for {@Link Athlete} name
	 *
	 * @param newName to replace {@Link Athlete} name
	 */
	public void setName(String newName) {
		this.name = newName;
	}

	/**
	 * setter for {@Link Athlete} status
	 *
	 * @param status to replace {@Link Athlete} status
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 *
	 * return information of {@Link Athlete}
	 */
	@Override
	public String toString() {
		return String.format("%s: Attack:%d Defence:%d Stamina:%d", this.getName(), this.getAttack(), this.getDefence(),
				this.getCurrentStamina());
	}

}
