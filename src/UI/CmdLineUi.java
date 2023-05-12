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
                viewProperties();
                break;
            case CLUB:
                goToClub();
                break;
            case STADIUM:
                goToStdium();
                break;
            case MARKET:
                
                break;
            case BYE:
                
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + option);
        }
    }
    
    
    private void goToStdium() {
    	 int index = chooseMatch();
    	 String result = game.match(index);
    	 System.out.println(result);
    	
	}
    
    private int chooseMatch() {
    	while(true) {
    		System.out.println("Select you match\n" + (this.game.getMatchInfos()));
    		try {
    			int index = scan.nextInt();
    			if (index < game.availableMatches())
    				return index;
    		}
    		catch (Exception e) {
    			System.out.println(e.getMessage());
    		}
    	}
    

    }



    private void viewProperties() {
    	String properties = game.getProperties();
    	System.out.println(properties);
    }
    
    private void goToClub() {
    	boolean con = true;
    	while (con) {
    		System.out.println("What would you like to do next?\n"
    				+ "(0) View Your team\n"
    				+ "(1) View your inventory\n"
    				+ "(2) Go back");
    		try {
    			int input = scan.nextInt();
    			switch (input) {
    				case 0:
    					viewTeam();
    					
    					break;
    				case 1:
    					viewInventory();
    					break;
    				case 2:
    					con = false;
    					break;
    			}
    		}
    		catch (Exception e) {
    			scan.nextLine();
    			System.out.println(e.getMessage());
    		}
    	}
//    	int input = Integer.parseInt(getClubInput("What would you like to do next?", "View your team", "View your inventory"));
//    	if(input==0) {
//    		System.out.println(game.teamName());
//    		String teamInfo = game.viewTeam(game.getTeam());
//    		System.out.println(teamInfo);
//    		System.out.println("Reserves");
//    		String reserveInfo = game.viewTeam(game.getReserves());
//    		System.out.println(reserveInfo);
//    		//Can't do 2 scanners ?
//    		int swapMembers = Integer.parseInt(getClubInput("What would you like to do next?", "View your team", "View your inventory"));
//    		
//    		if(swapMembers==0) {
//    			System.out.println("Which athletes would you like to swap?");    		}
//    	}
    	
    	
    }
    
    //print out all item
    //0 to use item 1 go back
    private void viewInventory() {
    	System.out.println("Viewing in team");
		
	}


    //print out all athletes 
    //0 to 3 
    // enter int to select a athlete then action
	private void viewTeam() {
		System.out.println("Viewing in ventory");
		
	}



	public String getClubInput(String action, String option1, String option2) {
    	while(true) {
    		System.out.println(action);
    		System.out.println("(0) "+ option1);
    		System.out.println("(1) "+ option2);
    	
    	try {
    		String input = scan.next("[01]");
    		
    	}
    	catch (Exception e) {
    		//Remove bad input
    		scan.nextLine();
    	}
    	}
    }
    
}
    
    
    


