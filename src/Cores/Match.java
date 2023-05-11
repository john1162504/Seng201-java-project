package Cores;

import java.util.ArrayList;

public class Match {
	// 4 athletes from each  team who will compete
	private Athlete ally;
	private Athlete opponent;
	private int score;
	private int money;
	/**
	 * Constructor to initialize  all  players of the match
	 * @param player object of type Team representing the players team
	 * @param opponent object of type Team representing the opposing team
	 */
	public Match() {
		}
	
	
	//match played, athlete A and B on each team are defenders, C and  D are attackers, return winning team
	public String match(Athlete ally, Athlete opponent) {
		int allyPoint = ally.getAttack() - opponent.getDefence();
		int opponentPoint = opponent.getAttack() - ally.getDefence();
		if (allyPoint > opponentPoint) {
			return String.format("%s get %d point from %s", ally.getName(), allyPoint, opponent.getName());
		} 
		else if (allyPoint < opponentPoint) {
			return String.format("%s get %d point from %s", opponent.getName(), allyPoint, ally.getName());
		}
		else {
			return "Draw!";
		}
		
	}
	
	
	//reward or penalty after match
	public void matchResult() {
		
		
	}
	
	
	
	public static void main(String[] args) {
		
		
		
		
	}
}
