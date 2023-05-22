package Cores;

import java.util.Random;

/*
 * Class that models random event of this game
 */
public class RandomEvent {

	// Instance of this game
	private GameEnvironment game;
	// Random number generator
	private Random rng;

	/**
	 * Constructor of this class
	 *
	 * @param game The instance of a GameEnvironment
	 */
	public RandomEvent(GameEnvironment game) {
		this.game = game;
		this.rng = new Random();
	}

	/**
	 * A method models a random event, this event has 5% chance an athlete leaves
	 * his team if he is not injured, if he is injured this event has 30% chance
	 * occurs
	 *
	 * @return A description indicates if this event occurs
	 */
	protected String athleteQuitEvent() {
		String result = "All athletes are high in morale!\n";
		for (Athlete athlete : game.getActiveTeam()) {
			int quitChance = rng.nextInt(100);
			if (quitChance < 5 && (athlete.getCurrentStamina() > 0)) {
				game.removeAthlete(game.getActiveTeam(), athlete);
				return "Athlete " + athlete.getName() + " has decided to leave the team...\n";
			} else if (quitChance < 30 && (athlete.getCurrentStamina() <= 0)) {
				game.removeAthlete(game.getActiveTeam(), athlete);
				return "Athlete " + athlete.getName() + " is injured and has decided to leave the team...\n";
			}
		}
		return result;
	}

	/**
	 * A method models a random event, this event has 20% chance increases a random
	 * athlete's stats by 5
	 *
	 * @return A description indicates if this event occurs
	 */
	protected String athleteStatIncreaseEvent() {
		int increaseChance = rng.nextInt(100);
		String result = "No athlete's stat's have increased\n";
		if (increaseChance < 20) {
			int chosenAthleteIndex = rng.nextInt(game.getActiveTeam().size());
			Athlete chosenAthlete = game.getActiveTeam().get(chosenAthleteIndex);
			chosenAthlete.increaseStats(5);
			result = "Athlete " + chosenAthlete.getName() + "'s stats have increased by 5!\n";
		}
		return result;
	}
}
