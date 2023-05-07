package Cores;

import java.util.ArrayList;
/**
 * 
 * @author chunyuenchan
 * Take four athletes and store it in a array list
 * TBD
 */
public class Team {
	
	private ArrayList<Athlete> team = new ArrayList<Athlete>(4);
	
	private ArrayList<Athlete> reserveTeam = new ArrayList<Athlete>(5);
	
	private String teamName;
	
	public Team(Athlete athleteA, Athlete athleteB, Athlete athleteC, Athlete athleteD) {
		team.add(athleteA);
		team.add(athleteB);
		team.add(athleteC);
		team.add(athleteD);
	}
	
	public ArrayList<Athlete> getTeam() {
		return this.team;
	}
	
	public String getTeamName() {
		return this.teamName;
	}
	
	public void setTeamName(String newName) {
		this.teamName = newName;
	}
	
	public String getAthleteNames() {
    	String returnString = "";
		for (Athlete athlete: team) {
			returnString += athlete.getName();
			returnString += ", ";
		}
		return returnString;
    }
	public void addNewAthlete(Athlete newAthlete) {
		if (this.team.size() < 4) {
			this.team.add(newAthlete);
		} else {
			System.out.println("Team is full");
		}
	}
	
	public void removeAthlete(Athlete target) {
		this.team.remove(target);
	}
	
	
	
	
	
	
	
	
	
	

}
