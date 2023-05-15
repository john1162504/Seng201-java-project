package Cores;

import java.util.ArrayList;

import Cores.Athlete.Status;

public class Match {
	// 4 athletes from each  team who will compete
	private ArrayList<Athlete> ally;
	private ArrayList<Athlete> opponent;
	private int score;
	private int money;
	private int opponentScore;
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
		ally.setStamina(ally.getStamina() - 1); 
		int allyPoint = ally.getAttack() - opponent.getDefence();
		int opponentPoint = opponent.getAttack() - ally.getDefence();
		this.score += allyPoint;
		this.opponentScore += opponentPoint;
		if (allyPoint > opponentPoint) {
			this.money += allyPoint * 2;
			return String.format("Your %s get %d point from %s\n", ally.getName(), allyPoint, opponent.getName());
		} 
		else if (allyPoint < opponentPoint) {
			ally.setStamina(ally.getStamina() - 1); 
			return String.format("Opponent's %s get %d point from %s\n%s lost extra stamina!\n", opponent.getName(), allyPoint, ally.getName(), ally.getName());
		}
		else {
			this.money += allyPoint;
			return "Draw!\n";
		}
		
	}
	
	private String matchCleanUp() {
		String infos = "";
		for (int i = 0; i < GameEnvironment.MAX_TEAM_SIZE; i++) {
			if (ally.get(i).getStamina() <= 0) {
				ally.get(i).setStatus(Status.INJURED);
				infos += ally.get(i).getName() + " is injuried!\n";
			}
		}
		return infos;
	}
	
	
	public int getScore() {
		return this.score;
	}
	
	public int getMoney() {
		return this.money;
	}
	
	public String matchResult() {
		String result = "";
		if (this.score > this.opponentScore) {
			result = "You Won!\n";
		}
		else if (this.score < this.opponentScore) {
			result = "You Lost!\n";
		}
		else {
			result = "Draw!\n";
		}
		return result += String.format("%sYou gained %d money and %d score from this match!", matchCleanUp(), getMoney(), getScore());
		
	}
	
	
}
