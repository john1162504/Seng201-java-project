package Cores;

import java.util.ArrayList;
/**
 * 
 * Take four athletes and store it in a array list
 * TBD
 */
public class Team {
	
	private ArrayList<Athlete> team;
	
	private String teamName;
	
	public Team(int size) {
		this.team = new ArrayList<Athlete>(size); // 4 for active team, 5 for reserve 
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
