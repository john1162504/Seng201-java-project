package Cores;
/**
 *  A class describe behaviors of object athlete   
 */
public class Athlete implements Purchasable{
	
	public enum ROLE {
		RACER,
		LIFTER,
		RUNNER,
	}
	public enum STATUS {
		INJURIED,
		ACTIVE,
		
	}
	
	
	private int strength;
	private int pace;
	private int stamina;
	private final int energy = 5;
	private String name;
	private ROLE role;
	private int price;
	private int worth;

	
	
	public Athlete(int strInput, int paceInput, int staInput, ROLE roleInput) {
		this.strength = strInput;
		this.pace = paceInput;
		this.stamina =staInput;
		this.role = roleInput;
	}
	
	public int getStrength() {
		return this.strength;
	}
	
	public int getPace() {
		return this.pace;
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
	
	
	public void setStrength(int newStrength) {
		this.strength = newStrength;
	}
	
	public void setPace(int newPace) {
		this.pace = newPace;
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
