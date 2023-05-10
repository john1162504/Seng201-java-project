package Cores;

import java.util.ArrayList;

public class Match {
	// 4 athletes from each  team who will compete
	private Athlete playerA, playerB, playerC, playerD, oppA, oppB, oppC, oppD;
	// Stat's for player athletes
	private int playerADef,  playerAAtt, playerBDef, playerBAtt,  playerCDef, playerCAtt, playerDDef, playerDAtt;
	// Stat's for opposing athletes
	private int  oppADef, oppAAtt, oppBDef, oppBAtt, oppCDef, oppCAtt,  oppDDef, oppDAtt;
	// Name of teams
	private String teamName, oppTeamName;
	//teams
	private ArrayList<Athlete> team, oppTeam;
	/**
	 * Constructor to initialize  all  players of the match
	 * @param player object of type Team representing the players team
	 * @param opponent object of type Team representing the opposing team
	 */
	public Match(Team player,  Team opponent) {
		team = player.getTeam();
		teamName = player.getTeamName();
		playerA = team.get(0);
		playerADef  = playerA.getDefence();
		playerAAtt = playerA.getAttack();
		playerB = team.get(1);
		playerBDef =  playerB.getDefence();
		playerBAtt = playerB.getAttack();
		playerC = team.get(2);
		playerCDef = playerC.getDefence();
		playerCAtt = playerC.getAttack();
		playerD = team.get(3);
		playerDDef = playerD.getDefence();
		playerDAtt = playerD.getAttack();
		
		oppTeam = opponent.getTeam();
		oppTeamName = opponent.getTeamName();
		oppA = oppTeam.get(0);
		oppADef = oppA.getDefence();
		oppAAtt = oppA.getAttack();
		oppB = oppTeam.get(1);
		oppBDef = oppB.getDefence();
		oppBAtt = oppB.getAttack();
		oppC = oppTeam.get(2);
		oppCDef = oppC.getDefence();
		oppCAtt = oppC.getAttack();
		oppD = oppTeam.get(3);
		oppDDef = oppD.getDefence();
		oppDAtt = oppD.getAttack();
		}
	
	// match setup slot select etc
	public void matchBegin() {
		//not sure  if  choose slots  before or after choosing opponent, come back to?
	}
	
	//match played, athlete A and B on each team are defenders, C and  D are attackers, return winning team
	public void match() {
		int playerScore = 0;
		int oppScore = 0;
		//playerA vs oppA, if player loses, stamina further reduced, if equal stat's, nothing happens
		if(playerADef > oppADef) {
			playerScore += playerADef;
			playerD.reduceStamina(5);
		}
		if(playerADef < oppADef){
			oppScore += oppADef;
			playerA.reduceStamina(10);
		}
		//playerB vs oppB
		if(playerBDef > oppBDef) {
			playerScore += playerBDef;
			playerD.reduceStamina(5);
		}
		if(playerBDef < oppBDef){
			oppScore += oppBDef;
			playerB.reduceStamina(10);
		}
		//playerC vs oppC
		if(playerCAtt > oppCAtt) {
			playerScore += playerCAtt;
			playerD.reduceStamina(5);
		}
		if(playerCAtt < oppCAtt){
			oppScore += oppCAtt;
			playerC.reduceStamina(10);
		}
		//playerD vs oppD
		if(playerDAtt > oppDAtt) {
			playerScore += playerDAtt;
			playerD.reduceStamina(5);
		}
		if(playerDAtt < oppDAtt){
			oppScore += oppDAtt;
			playerD.reduceStamina(10);
		}
		//print winning team
		if(playerScore > oppScore) {
			System.out.println("The winning team is "+teamName+" with a score of "+playerScore);
		}
		if(playerScore < oppScore) {
			System.out.println("The winning team is "+oppTeamName+" with a score of "+playerScore);
		}
		if(playerScore == oppScore) {
			System.out.println("Both teams draw with a score of "+playerScore);
		}
		
		
	}
	
	
	//reward or penalty after match
	public void matchFinished() {
		
	}
	
	
	
	public static void main(String[] args) {
		
		
		
		
	}
}
