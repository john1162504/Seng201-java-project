
/*
 * Purchasable interface 
 */
public interface purchasable {
	
	/**
	 * Get the price of the object
	 * 
	 * @return how much it needs to buy the object
	 */
	public int getPrice();
	
	/**
	 * Get the worth of the object
	 * 
	 * @return How much the object worth
	 */
	public int getWorth();
	
	/**
	 * Description of the object
	 * 
	 * @return A string description of the purchasable object  
	 */
	public String toString();

}
