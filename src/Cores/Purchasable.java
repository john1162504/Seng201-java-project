package Cores;

/*
 * Purchasable interface 
 */
public interface Purchasable {
	
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
	 * get the information of purchasable object
	 * 
	 * @return A information for {@Link Purchasable} when it appears in selling screen
	 */
	public String getSellInfo();
	
	/**
	 * get the information of purchasable object
	 * 
	 * @return A information for {@Link Purchasable} when it appears in buying screen
	 */
	public String getBuyInfo();
	
	/**
	 * get the message use after purchased this object
	 * 
	 * @return A message use as a feedback after a purchase 
	 */
	public String getBuyMessage();
	
	/**
	 * get the message use after sold this object
	 * 
	 * @return A message use as a feedback after a sell
	 */
	public String getSellMessage();
	
	/**
	 * Description of the object
	 * 
	 * @return A string description of the purchasable object  
	 */
	public String toString();

}
