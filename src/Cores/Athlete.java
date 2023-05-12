package Cores;

import java.util.Random;

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
	
	private enum Names {
	    ETHAN("Ethan"),
	    JACOB("Jacob"),
	    LUCAS("Lucas"),
	    WILLIAM("William"),
	    BENJAMIN("Benjamin"),
	    CHRISTOPHER("Christopher"),
	    DAVID("David"),
	    MICHAEL("Michael"),
	    ALEXANDER("Alexander"),
	    OLIVER("Oliver"),
	    MATTHEW("Matthew"),
	    NOAH("Noah"),
	    SAMUEL("Samuel"),
	    JOSEPH("Joseph"),
	    DANIEL("Daniel"),
	    JAMES("James"),
	    ANDREW("Andrew"),
	    NICHOLAS("Nicholas"),
	    GABRIEL("Gabriel"),
	    RYAN("Ryan");

	    public final String name;

	    Names(String name) {
	        this.name = name;
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
	public Athlete(int attack, int defence, int maxStamina) {
		this.attack = attack;
		this.defence = defence;
		this.maxStamina = maxStamina;
		this.stamina = this.maxStamina;
		this.worth = (this.attack + this.defence + this.stamina); 
		this.price = (this.worth) * 2;
		this.name = this.getRandomName();
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
		return (this.worth) * 2;
	}

	@Override
	public int getWorth() {
		return (this.attack + this.defence + this.stamina); 
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
	
	public void increaseStats(int amount) {
		this.attack += amount;
		this.defence += amount;
		this.maxStamina += amount;
		this.stamina = this.maxStamina;
	}
		
	public void heal() {
		this.status = Status.ACTIVE;
		this.stamina = this.maxStamina;
	}
	
    public String getRandomName() {
        Names[] names = Names.values();
        Random random = new Random();
        int index = random.nextInt(names.length);
        return names[index].name;
    }
    
    public void reduceStamina(int amount) {
    	this.stamina -= amount;
    }
    
    public String toString() {
    	return String.format("%s: Attack:%d Defence:%d Stamina:%d", 
    			this.getName(), this.getAttack(), this.getDefence(), this.getStamina());
    }

	

	
}
