package UI;

import java.util.ArrayList;

import java.util.Scanner;

import Cores.Athlete;
import Cores.GameEnvironment;
import Cores.Market;
import Cores.Match;


public class CmdLineUi implements GameEnvironmentUi{
	
	private boolean finish = false;
	
	private Market market;
	
	private Match match;
	
	private GameEnvironment game;
	
	private final Scanner scan; 
	
	private enum Option {
		PROPERTIES("Properties"), //print out games properties
		CLUB("Club"),
		STADIUM("Stadium"),
		MARKET("Market"),
		BYE("Take a bye");
		
		public final String name;
		
		Option(String name) {
			this.name = name;
		}
	}
	
	public enum Difficulty {
		NORMAL("Normal"),
		HARD("Hard");
		
		public final String name;
		
		Difficulty(String name) {
			this.name = name;
		}
		
	}
	
	public CmdLineUi() {
		 this.scan = new Scanner(System.in);
	}
	
	

	@Override
	public void setup(GameEnvironment game) {
		this.game = game;
		final String name = getName();
		final int gameLength = getGameLength();
		final ArrayList<Athlete> startAthletes = getStartAthletes();
		final Difficulty difficulty = getDifficulty();
		game.onSetupFinished(name, gameLength, startAthletes, difficulty);
		
		
	}
	
	public String getName() {
		while (true) {
			System.out.println("Enter your name below");
			try {
				String name = scan.nextLine();
				if (this.checkNameValidity(name)) {
					return name;
				}
			}
			catch (Exception e) {
				scan.nextLine();
			}
		}
	}
	
	public boolean checkNameValidity(String string) {
		if (string.length() > 15 || string.length() < 3) {
			System.out.println("Length must be between 3 and 15!");
			return false;
		}
		else if (!string.matches(NAME_REGEX)) {
			System.out.println("Name must not contain numbers or special characters");
			return false;
		}
		else {
			return true;
		}
	}

	private Difficulty getDifficulty() {
		while (true) {
			System.out.println("Select difficulty.");
			for (Difficulty d: Difficulty.values()) {
				System.out.println(String.format("(%d) %s", d.ordinal(),d.name));
			}
			try {
				return Difficulty.values()[scan.nextInt()];
				
			}
			catch (Exception e) {
				System.out.println("Invalid input");
				scan.nextLine();
			}
		}
			
	}


	private int getGameLength() {
		while (true) {
			System.out.println("Enter season length, from 5-15 weeks");
			try {
				int length = scan.nextInt();
				if (5 <= length && length <=15) {
					return length;
				} 
			}
			catch (Exception e) {
				System.out.println("Invalid input");
				scan.nextLine();
			}
		}
	}


	private ArrayList<Athlete> getStartAthletes() {
		ArrayList<Athlete> availableAthletes = game.generateAthletes(6);
		ArrayList<Athlete> selectedAthletes = new ArrayList<Athlete>(GameEnvironment.MAX_TEAM_SIZE);
		while(selectedAthletes.size() < GameEnvironment.MAX_TEAM_SIZE) {
			System.out.println("Select your athletes");
			this.printTeam(availableAthletes);
			try {
				int index = scan.nextInt();
				Athlete athlete = availableAthletes.get(index);
				availableAthletes.remove(index);
				selectedAthletes.add(athlete);
			}
			catch (Exception e) {
				System.out.println("Invalid input");
				scan.nextLine();
			}
		}
		return selectedAthletes;
	}
	
	private void printTeam(ArrayList<Athlete> team) {
		int index = 0;
		for (Athlete athlete : team) {
			System.out.println("(" + index + ") " + athlete);
			index++;
		}
	}


	@Override
	public void start() {
		final Option[] options = Option.values();

        while (!finish) {
            System.out.println("\nSelect an option:");

            printOptions();

            try {
                Option option = options[scan.nextInt()];
                handleOption(option);
            }
            catch (ArrayIndexOutOfBoundsException e) {
                // Ignore the bad input and continue
            }
            catch (Exception e) {
                // Discard the unacceptable input
                scan.nextLine();
            }
        }
		
	}

	@Override
	public boolean confirmQuit() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void quit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showError(String error) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		CmdLineUi ui = new CmdLineUi();
		GameEnvironment game = new GameEnvironment(ui, null);
		ui.setup(game);
//		ArrayList<Athlete> a = ui.getStartAthletes();
//		ui.printTeam(a);
		
	
	}
	
	/**
     * Outputs the set of options to the console.
     */
    private void printOptions() {

        for (Option option : Option.values()) {
            System.out.println("(" + option.ordinal() + ") " + option.name);
        }
    }
    
    private void handleOption(Option option) {
        switch (option) {
            case PROPERTIES:
                viewProperties(game);
                break;
            case CLUB:
                goToClub(game);
                break;
            case STADIUM:
                
                break;
            case MARKET:
                
                break;
            case BYE:
                
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + option);
        }
    }
    
    private void viewProperties(GameEnvironment game) {
    	String properties = game.getProperties();
    	System.out.println(properties);
    }
    
    private void goToClub(GameEnvironment game) {
    	String input = getClubInput();
    	System.out.println(input.getClass());
    	String info = game.viewTeam(game.getTeam());
		System.out.println(info);
    	
    }
    
    public String getClubInput() {
    	while(true) {
    		System.out.println("What would you like to do next?");
    		System.out.println("(0) View your team");
    		System.out.println("(1) View your inventory");
    	
    	try {
    		String input = scan.next("[01]");
    		return input;
    	}
    	catch (Exception e) {
    		//Remove bad input
    		scan.nextLine();
    	}
    	}
    }
    
    
}

