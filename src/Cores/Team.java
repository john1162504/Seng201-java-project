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
	//name of team set by player
	private String teamName;
	//season length measure in week
	
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
	
	public void addNewAthlete(Athlete newAthlate) {
		if (this.team.size() < 4) {
			this.team.add(newAthlate);
		} else {
			System.out.println("Team is full");
		}
	}
	
	public void removeAthlete(Athlete target) {
		this.team.remove(target);
	}
	
	
	
	
	
	
	
	
	
	

}
