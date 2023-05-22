package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Cores.Athlete;
import Cores.Athlete.Status;
import Cores.Item;
import Cores.Item.Type;

class ItemTest {

	private Athlete testAthlete;
	private Item med;
	private Item weight;
	private Item food;
	
	@BeforeEach
	public void init(){
		testAthlete = new Athlete(5, 10, 15);
		med = new Item(10, 5, "Injection", 5, Type.MEDICINE);
		weight = new Item(20, 15, "Dumbbell", 10, Type.WEIGHT);
		food = new Item(8, 6, "Pizza", 7, Type.FOOD);
	}
	@Test
	public void testUseItemOnAthlete() {
		assertEquals(5, testAthlete.getAttack());
		weight.useItem(testAthlete);
		assertEquals(15, testAthlete.getAttack());
		food.useItem(testAthlete);
		assertEquals(17, testAthlete.getDefence());
	}
	@Test
	public void testMedicineRecoveringStatus() {
		testAthlete.setStatus(Status.INJURED);
		assertEquals(Status.INJURED, testAthlete.getStatus());
		med.useItem(testAthlete);
		assertEquals(Status.ACTIVE, testAthlete.getStatus());
	}
	@Test
	public void testMedicineOnActiveAthlete() {
		String result = med.useItem(testAthlete);
		assertEquals(result, String.format("You used %s on %s, nothing happened...", med.getName(), testAthlete.getName()));
	}
	@Test
	public void testToStringMethod() {
		String result = med.toString();
		assertEquals(result, "Injection fully recovers athlete when he is injured");
	}

}
