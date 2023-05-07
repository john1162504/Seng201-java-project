package Cores;
/**
 *  A class describing the attributes of a given Athlete 
 */
public class Athlete implements Purchasable{
	
	//attack value of athlete
	private int attack;
	//defence value of athlete
	private int defence;
	//stamina value of athlete
	private int stamina;
	//name of athlete
	private String name;
	//how much given athlete contract costs in the market
	private int price;
	//how much given athlete contract sells for in the market
	private int worth;

	
	/**
	 * Constructor for athlete objects
	 * @param atkInput
	 * @param defInput
	 * @param staInput
	 */
	public Athlete(int atkInput, int defInput,int staInput) {
		this.attack = atkInput;
		this.defence = defInput;
		this.stamina = staInput;
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
	
	
	public void setAttack(int newAttack) {
		this.attack = newAttack;
	}
	
	public void setDefence(int newDefence) {
		this.defence = newDefence;
	}
	
	public void setStamina(int newStamina) {
		this.stamina = newStamina;
	}
	
	public void setRole(ROLE newRole) {
		this.role = newRole;
	}
	
	public void setName(String newName) {
		this.name = newName;
	}


	
}
