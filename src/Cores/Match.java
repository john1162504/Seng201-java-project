package Cores;

import java.util.ArrayList;

import Cores.Athlete.Status;

/**
 * 
 * Class that models a Match
 */
public class Match {
	//player's team
	private ArrayList<Athlete> ally;
	
	//team that player play against
	private ArrayList<Athlete> opponent;
	
	//score gained by the player from this match
	private int score;
	
	//money gained by the player from this match
	private int money;
	
	//opponent's score
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
	
	
	/**
	 * 
	 * Feed all athletes in ally team and opponent team to {@link #match(Athlete, Athlete)} 
	 * 
	 * @return match details from {@Link Match#match(Athlete, Athlete)}
	 */
	public String matchBegin() {
		String matchDetails = "";
		for (int i = 0; i < GameEnvironment.MAX_ACTIVE_TEAM_SIZE; i++) {
			matchDetails += match(ally.get(i), opponent.get(i));
			
		}
		return matchDetails;
				
	}
	
	/**
	 *  Construct a description depends on the match result, match result determines by athletes's stats
	 * 
	 * @param ally Athlete in player's team
	 * @param opponent Athlete in opponent's team
	 * @return match details from two input athletes 
	 */
	public String match(Athlete ally, Athlete opponent) {
		ally.setCurrentStamina(ally.getCurrentStamina() - 1); 
		int allyPoint = ally.getAttack() - opponent.getDefence();
		int opponentPoint = opponent.getAttack() - ally.getDefence();
		this.score += allyPoint;
		this.opponentScore += opponentPoint;
		if (allyPoint > opponentPoint) {
			this.money += allyPoint * 2;
			return String.format("Your %s get %d point from %s\n", ally.getName(), allyPoint, opponent.getName());
		} 
		else if (allyPoint < opponentPoint) {
			ally.setCurrentStamina(ally.getCurrentStamina() - 1); 
			return String.format("Opponent's %s get %d point from %s\n%s lost extra stamina!\n", opponent.getName(), allyPoint, ally.getName(), ally.getName());
		}
		else {
			this.money += allyPoint;
			return "Draw!\n";
		}
		
	}
	
	/**
	 *  For athlete in ally check if stamina below or equal to 0, if yes set {@Link Athlete#Status} to be {@Link Status#INJURIED}
	 * 
	 * @return Return a message if an athlete is injured 
	 */
	private String matchCleanUp() {
		String infos = "";
		for (int i = 0; i < GameEnvironment.MAX_ACTIVE_TEAM_SIZE; i++) {
			if (ally.get(i).getCurrentStamina() <= 0) {
				ally.get(i).setStatus(Status.INJURED);
				infos += ally.get(i).getName() + " is injuried!\n";
			}
		}
		return infos;
	}
	
	/**
	 * get score of this match
	 * 
	 * @return score of this match
	 */
	public int getScore() {
		return this.score;
	}
	
	/**
	 * get money of this match
	 * 
	 * @return money of this match
	 */
	public int getMoney() {
		return this.money;
	}
	
	/**
	 * provides a string represent result of this match
	 * 
	 * @return a string represent result of this match, string vary depends on match result, 
	 * string contains score and money obtained from this match
	 */
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
