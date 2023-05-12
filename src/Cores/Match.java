package Cores;

import java.util.ArrayList;

public class Match {
	// 4 athletes from each  team who will compete
	private ArrayList<Athlete> ally;
	private ArrayList<Athlete> opponent;
	private int score;
	private int money;
	/**
	 * Constructor to initialize  all  players of the match
	 * @param player object of type Team representing the players team
	 * @param opponent object of type Team representing the opposing team
	 */
	public Match(ArrayList<Athlete> allyTeam, ArrayList<Athlete> opponent) {
		this.ally = allyTeam;
		this.opponent = opponent;
		}
	
	
	
	public String matchBegin() {
		String matchDetails = "";
		for (int i = 0; i < GameEnvironment.MAX_TEAM_SIZE; i++) {
			matchDetails += match(ally.get(i), opponent.get(i));
		}
		return matchDetails;
				
	}
	//match played, athlete A and B on each team are defenders, C and  D are attackers, return winning team
	public String match(Athlete ally, Athlete opponent) {
		int allyPoint = ally.getAttack() - opponent.getDefence();
		int opponentPoint = opponent.getAttack() - ally.getDefence();
		this.score += allyPoint;
		if (allyPoint > opponentPoint) {
			this.money += allyPoint * 2;
			return String.format("Your %s get %d point from %s\n", ally.getName(), allyPoint, opponent.getName());
		} 
		else if (allyPoint < opponentPoint) {
			return String.format("Opponent's %s get %d point from %s\n", opponent.getName(), allyPoint, ally.getName());
		}
		else {
			this.money += allyPoint;
			return "Draw!\n";
		}
		
	}
	
	
	public int getScore() {
		return this.score;
	}
	
	public int getMoney() {
		return this.money;
	}
	
	public String matchResult() {
		return String.format("You gained %d money and %d score from this match!", getMoney(), getScore());
		
	}
	
	
}
