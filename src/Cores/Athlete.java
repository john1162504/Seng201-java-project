package Cores;
/**
 *  A class describing the attributes of a given Athlete 
 */
public class Athlete implements Purchasable{
	
	/**
	 * Represent the status of a {@link Athlete}
	 */
	public enum Status {
		INJURED("Injured"),
		ACTIVE("Active"),
		RESERVE("Reserve");
		
		/**
		 * A User friendly description of the value of this enum
		 */
		public final String status;
		Status(String status) {
			this.status = status;
		}
	}
	
	//attack value of athlete
	private int attack;
	//defence value of athlete
	private int defence;
	//Max stamina value of athlete
	private int maxStamina;
	//Current stamina of this athlete
	private int stamina;
	//name of athlete
	private String name;
	//how much given athlete contract costs in the market
	private int price;
	//how much given athlete contract sells for in the market
	private int worth;
	//The status of this athlete
	private Status status;

	
	/**
	 * Constructor for athlete objects
	 * @param atkInput
	 * @param defInput
	 * @param staInput
	 */
	public Athlete(int attack, int defence, int maxStamina, int price, int worth, String name) {
		this.attack = attack;
		this.defence = defence;
		this.maxStamina = maxStamina;
		this.stamina = this.maxStamina;
		this.price = price;
		this.worth = worth;
		this.name = name;
		this.status = Status.ACTIVE;
	}
	
	public int getAttack() {
		return this.attack;
	}
	
	public int getDefence() {
		return this.defence;
	}
	
	public int getStamina() {
		return this.stamina;
	}
	
	public int getMaxStamina() {
		return this.maxStamina;
	}

	public String getName() {
		return this.name;
	}
	@Override
	public int getPrice() {
		return this.price;
	}

	@Override
	public int getWorth() {
		return this.worth;
	}
	
	public Status getStatus() {
		return this.status;
	}
	
	public void setAttack(int newAttack) {
		this.attack = newAttack;
	}
	
	public void setDefence(int newDefence) {
		this.defence = newDefence;
	}
	
	public void setStamina(int newStamina) {
		this.stamina = newStamina;
	}
	
	
	public void setName(String newName) {
		this.name = newName;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
		
	public void heal() {
		this.status = Status.ACTIVE;
		this.stamina = this.maxStamina;
	}
	

	
}
