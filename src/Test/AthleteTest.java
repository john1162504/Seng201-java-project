package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Cores.Athlete;
import Cores.Athlete.Status;

class AthleteTest {
	
	private Athlete testAthlete;
	
	@BeforeEach
	public void init(){
		testAthlete = new Athlete(5, 10, 15);
	}

	@Test
	//Test status change of athlete
	public void statusChangeTest() {
		testAthlete.setStatus(Status.INJURED);
		testAthlete.heal();
		assertEquals(Status.ACTIVE, testAthlete.getStatus());
		assertEquals(15, testAthlete.getCurrentStamina());
	}
	@Test
	//Test worth of athlete updates accordingly with stat changes
	public void athleteWorthTestOnUpdatedStats() {
		testAthlete.increaseStats(3);
		assertEquals(36, testAthlete.getWorth());
	}
	@Test
	//Test price of athlete updates accordingly with stat changes
	public void athletePriceTestOnUpdatedStats() {
		testAthlete.increaseStats(7);
		assertEquals(88, testAthlete.getPrice());
	}
	@Test
	//Test max stamina changes accordingly when current stamina greater than max
	public void maxStamindOnCurrentStaminaChangeTest() {
		assertEquals(15, testAthlete.getCurrentStamina());
		testAthlete.setCurrentStamina(20);
		assertEquals(20, testAthlete.getMaxStamina());
	}
	@Test
	//getter methods are generic and differ only by variable name, so will only be testing one for functionality purposes
	public void genericGetterTest() {
		assertEquals(5, testAthlete.getAttack());
	}
	@Test
	//setter methods are also generic, so will test only one
	public void genericSetterTest() {
		testAthlete.setDefense(30);
		assertEquals(30, testAthlete.getDefence());
	}

}
