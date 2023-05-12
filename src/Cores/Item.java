package Cores;

import Cores.Athlete.Status;

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
	
	
	public void useItem(Athlete target){
		switch (type) {
		
		case WEIGHT:
			int newAtk = target.getAttack() + this.value;
			target.setAttack(newAtk);
			break;
		
		case FOOD:
			int newDef = target.getDefence() + this.value;
			target.setAttack(newDef);
			break;
		case MEDICINE:
			if (target.getStatus().status == "Injured") {
				target.heal();
			} else {
				break;
			}
			break;
 		}
		
	}
	
	
	
	
	

}
