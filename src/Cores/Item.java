package Cores;

/**
 * 
 * Class that describe behaviors object Item 
 *
 */
public class Item implements Purchasable {
	
	/**
	 * Indicates the type of item
	 *
	 */
	private enum TYPE {
		WEIGHT,
		FOOD,
		MEDICINE
	}
	
	// price of the item
	private int price;
	
	// item's worth 
	private int worth;
	

	/**
	 * get the price of item
	 * 
	 * @return price of the item 
	 */
	public int getPrice() {
		return this.price;
	}

	@Override
	public int getWorth() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

}
