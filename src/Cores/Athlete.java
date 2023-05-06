package Cores;
/**
 *  A class describe behaviors of object athlete   
 */
public class Athlete implements Purchasable{
	
	public enum ROLE {
		ATTACKER,
		DFENDER,
	}
	public enum STATUS {
		INJURIED,
		ACTIVE,
		RESERVE
	}
	
	
	private int attack;
	private int defence;
	private int stamina;
	private final int energy = 5;
	private String name;
	private ROLE role;
	private int price;
	private int worth;

	
	
	public Athlete(int atkInput, int defInput,int staInput, ROLE roleInput) {
		this.attack = atkInput;
		this.defence = defInput;
		this.role = roleInput;
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
	public ROLE getRole() {
		return this.role;
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
