package Cores;


/**
 * 
 * Class that models a item.
 *
 */
public class Item implements Purchasable {
	
	/**
	 * Indicates the type of {@link Item}
	 *
	 */
	public enum Type {
		WEIGHT("Weight"),
		FOOD("Food"),
		MEDICINE("Medicine");
		
		/**
		 * A user friendly description of the value of this enum.
		 */
		public final String type;
		
		Type(String type) {
			this.type = type;
		}
	}
	
	// Item's price
	private int price;
	
	// Item's worth 
	private int worth;
	
	// Item's name
	private String name;
	
	private Type type;
	
	// Represent how much it changes the athlete's stat
	private int value;
	
	
	/**
	 * Create an item with the given parameters 
	 * 
	 * @param price The buying price of this item
	 * @param worth The selling worth of this item
	 * @param name The name of this item 
	 * @param value The value of this item
	 * @param type The type of this item
	 */
	public Item(int price, int worth, String name, int value, Type type) {
		this.price = price;
		this.worth = worth;
		this.name = name;
		this.value = value;
		this.type = type;
	}
	
	/**
	 * Gets the price of {@link Item}
	 * 
	 * @return The price of this item 
	 */
	public int getPrice() {
		return this.price;
	}

	/**
	 * Gets the worth of {@link Item}
	 * 
	 * @return The worth of this item
	 */
	public int getWorth() {
		return this.worth;	
	}
	
	/**
	 * Gets the value of {@link Item} changes to {@link Athlete}
	 * 
	 * @return The value of this item
	 */
	public int getValue() {
		return this.value;
	}
	
	/**
	 * Gets the name of {@link Item}
	 * 
	 * @return The name of this item
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * 
	 * Gets the type of {@link Item}
	 * 
	 * @return THe type of this item
	 */
	public Type getType() {
		return this.type;
	}
	
	
	/**
	 * use this item on @{Link Athlete} and return a String indicate the effect
	 * 
	 * @param target the target item effect on 
	 * @return A description of this item effect
	 */
	public String useItem(Athlete target){
		String effect = "";
		switch (type) {
		case WEIGHT:
			int newAtk = target.getAttack() + this.value;
			target.setAttack(newAtk);
			effect = String.format("%s increased %s's attack by %d", name, target.getName(), value);
			break;
		case FOOD:
			int newDef = target.getDefence() + this.value;
			target.setDefense(newDef);
			effect = String.format("%s increased %s's defence by %d", name, target.getName(), value);
			break;
		case MEDICINE:
			if (target.getStatus().status == "Injured") {
				target.heal();
				effect = String.format("%s is fully recovered!", target.getName());
			} else {
				effect = String.format("You used %s on %s, nothing happened...", name, target.getName());
				break;
			}
 		}
		return effect;
		
	}
	@Override
	public String getSellMessage() {
		return String.format("Sold one %s receive %d$\n", getName(), getWorth());
	}
	
	@Override
	public String getBuyMessage() {
		return String.format("A %s is added to your inventory.", getName());
	}
	
	@Override
	public String getSellInfo() {
		return String.format("%s is worth %d$", this.toString(), getWorth());
	}
	
	@Override
	public String getBuyInfo() {
		return String.format("%s costs %d$", this.toString(), getPrice());
	}
		
	
	/**
	 * A description of this item
	 * 
	 * @return A description of this item, vary depends on {@Item #type}
	 */
	public String toString() {
		String toString = "";
		switch (type) {
		case WEIGHT:
			toString += String.format("%s increases athlete's attack by %d", this.getName(), this.getValue());
			break;
		case FOOD:
			toString += String.format("%s increases athlete's defence by %d", this.getName(), this.getValue());
			break;
		case MEDICINE:
			toString += String.format("%s fully recovers athlete when he is injured", this.getName());
			break;
 		}
		return toString;
	}
	
	
	
	
	

}
