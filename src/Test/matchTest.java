package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Cores.Athlete;
import Cores.Match;

class matchTest {

	private ArrayList<Athlete> allyTeam, oppTeam;
	private Match match;
	
	@BeforeEach
	public void init(){
		allyTeam =  new ArrayList<Athlete>(4);
		allyTeam.add(new Athlete(5, 10, 15));
		allyTeam.add(new Athlete(8,7,10));
		allyTeam.add(new Athlete(12,4,6));
		allyTeam.add(new Athlete(3,14,12));
		oppTeam = new ArrayList<Athlete>(4);
		oppTeam.add(new Athlete(7,10,10));
		oppTeam.add(new Athlete(12,7,9));
		oppTeam.add(new Athlete(4,6,3));
		oppTeam.add(new Athlete(10, 14, 12));
	}
	@Test
	public void testIndividualMatch() {
		match = new Match(allyTeam, oppTeam);
		Athlete ally = allyTeam.get(0);
		Athlete opp = oppTeam.get(0);
		String result = match.match(ally, opp);
		String expectedResult = String.format("Opponent's %s get %d point from %s\n%s lost extra stamina!\n", opp.getName(), (opp.getAttack()+opp.getDefence() - ally.getAttack()-ally.getDefence()), ally.getName(), ally.getName());
		assertEquals(expectedResult, result);
		assertEquals(-2, match.getScore());
	
	}
	@Test
	public void testAllMatches() {
		match = new Match(allyTeam, oppTeam);
		match.matchBegin();
		assertEquals(-7, match.getScore());
		assertEquals(12, match.getMoney());
	}
	@Test
	public void testStaminaDecrease() {
		match = new Match(allyTeam, oppTeam);
		Athlete ally = allyTeam.get(0);
		Athlete opp = oppTeam.get(0);
		String result = match.match(ally, opp);
		assertEquals(13, ally.getCurrentStamina());
	}


}
