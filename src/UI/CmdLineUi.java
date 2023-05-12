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
		while (selectedAthletes.size() < GameEnvironment.MAX_TEAM_SIZE) {
			Athlete athlete =  selectAthlete(availableAthletes, 
					"Select your athlete");
			availableAthletes.remove(athlete);
			selectedAthletes.add(athlete);
		}
		return selectedAthletes;
	}



	private Athlete selectAthlete(ArrayList<Athlete> availableAthletes, String prompt) {
		while (true) {
			System.out.println(prompt);
			System.out.println(displayAthletes(availableAthletes));
			try {
				int index = scan.nextInt();
				Athlete athlete = availableAthletes.get(index);
				return athlete;
			}
			catch (Exception e) {
				System.out.println("Invalid input");
				scan.nextLine();
			}
		}
	}
	
	private String displayAthletes(ArrayList<Athlete> athletes) {
		String athletesDescription = "";
		int index = 0;
		for (Athlete athlete : athletes) {
			athletesDescription += "(" + index + ") " + athlete + '\n';
			index++;
		}
		return athletesDescription;
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
                displayProperties();
                break;
            case CLUB:
                goToClub();
                break;
            case STADIUM:
                goToStdium();
                break;
            case MARKET:
                goToMarket();
                break;
            case BYE:
                
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + option);
        }
    }
    
    
    private void goToMarket() {
    	boolean stay = true;
    	while (stay) {
    		displayMoney();
    		System.out.println("(0) Buy\n"
    						 + "(1) Sell an athlete\n"
    						 + "(2) Sell an item\n"
    						 + "(3) Go back");
    		try {
    			int input = scan.nextInt();
    			switch (input) {
    			case 0:
    				displayShop();
    				break;
    			case 1:
    				displayTeams();
    				break;
    			case 2:
    				displayInventory();
    			case 3:
    				stay = false;
    				break;
    			}
    		}
    		catch (Exception e) {
    			System.out.print(e.getMessage());
    			scan.nextLine();
    		}
    	}
		
	}






	private void displayInventory() {
		// TODO Auto-generated method stub
		
	}



	private void displayShop() {
		// TODO Auto-generated method stub
		
	}


	private void displayMoney() {
		System.out.println("you have $" + game.getMoney());
		
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



    private void displayProperties() {
    	String properties = game.getProperties();
    	System.out.println(properties);
    }
    
    private void goToClub() {
    	boolean stay = true;
    	while (stay) {
    		System.out.println("What would you like to do next?\n"
    				+ "(0) View Your team\n"
    				+ "(1) Bench an athlete\n"
    				+ "(2) View your inventory\n"
    				+ "(3) Go back");
    		try {
    			int input = scan.nextInt();
    			switch (input) {
    				case 0:
    					displayTeams();
    					break;
    				case 1:
    					swapAthletes();
    					break;
    				case 2:
    					displayItems();
    					break;
    				case 3:
    					stay = false;
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
    private void displayItems() {
    	System.out.println("Viewing inventory");
		
	}


    //print out all athletes
    //Call a method which retrieves a string, then print string
    //0 to 3 
    // enter int to select a athlete then action
	private void displayTeams() {
		displayActiveTeam();
		displayReserveTeam();
		
	}
	
	private void displayActiveTeam() {
		String teamInfo = displayAthletes(game.getTeam());
		System.out.println("Active team\n"+
							teamInfo);
	}
	
	private void displayReserveTeam() {
		String reserveTeamInfo = displayAthletes(game.getReserves()); 
		System.out.println("Reserve team\n"+
							reserveTeamInfo);	
	}
	
	private void swapAthletes() {
		boolean swapping = true;
		String swapped = "";
		while(swapping) {
			try {
	    		Athlete athleteMain = selectAthlete(game.getTeam(), 
	    				"Select active athlete to swap");
	    		Athlete athleteReserve = selectAthlete(game.getReserves(), 
	    				"Select reserve athlete to swap");
	    		swapped += game.swapAthletes(athleteMain, athleteReserve);
	    		swapping = false;
			}
			catch (Exception e) {
				scan.nextLine();
			}
    	}
		System.out.println(swapped);
	}
	

//	public String getClubInput(String action, String option1, String option2) {
//    	while(true) {
//    		System.out.println(action);
//    		System.out.println("(0) "+ option1);
//    		System.out.println("(1) "+ option2);
//    	
//    	try {
//    		String input = scan.next("[01]");
//    		
//    	}
//    	catch (Exception e) {
//    		//Remove bad input
//    		scan.nextLine();
//    	}
//    	}
//    }
    
}
    
    
    


