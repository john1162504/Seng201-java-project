package UI;

import java.util.ArrayList;

import java.util.Scanner;

import Cores.Athlete;
import Cores.GameEnvironment;
import Cores.Item;


public class CmdLineUi implements GameEnvironmentUi{
	
	private boolean finish = false;

	
	private GameEnvironment game;
	
	private final Scanner scan; 
	
	private enum Option {
		PROPERTIES("Properties"), //print out games properties
		CLUB("Club"),
		STADIUM("Stadium"),
		MARKET("Market"),
		BYE("Take a bye"),
		QUIT("Quit game");
		
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
				if (name.matches(NAME_REGEX)) {
					return name;
				} 
				else {
					System.out.println(NAME_REQUIRMENTS);
				}
			}
			catch (Exception e) {
				scan.nextLine();
			}
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
				scan.nextLine();
			}
		}
	}


	private ArrayList<Athlete> getStartAthletes() {
		ArrayList<Athlete> availableAthletes = game.generateAthletes(6);
		ArrayList<Athlete> selectedAthletes = new ArrayList<Athlete>(GameEnvironment.MAX_ACTIVE_TEAM_SIZE);
		while (selectedAthletes.size() < GameEnvironment.MAX_ACTIVE_TEAM_SIZE) {
			Athlete athlete =  selectAthlete(availableAthletes, 
					"Select your athlete");
			availableAthletes.remove(athlete);
			selectedAthletes.add(athlete);
		}
		return selectedAthletes;
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
		scan.nextLine();
		while (true) {
			System.out.println("Are you sure you want to quit?\nY/N");
			try {
				String input = scan.next("[yYnN]");
				return input.matches("[Yy]");
			}
			catch (Exception e) {
				scan.nextLine();
			}
		}
	}	

	@Override
	public void quit() {
		finish = true;
	}

	@Override
	public void showError(String error) {
		System.out.println(error);
		
	}
	
	

    
    private void handleOption(Option option) {
        switch (option) {
	        case PROPERTIES:
	            displayProperties();
	            break;
	        case CLUB:
	            launchClub();
	            break;
	        case STADIUM:
	            launchStadium();
	            break;
	            
	        case MARKET:
	            launchMarket();
	            break;
	        case BYE:
	            takeABye();
	            break;
	        case QUIT:
	        	game.onFinish();
	        	break;
	        default:
	            throw new IllegalStateException("Unexpected value: " + option);
	    }
    }
    
    
   
  
    public void launchClub() {
    	boolean stay = true;
    	while (stay) {
    		System.out.println("What would you like to do next?\n"
    				+ "(0) View your active team\n"
    				+ "(1) View your reserve team\n"
    				+ "(2) Bench an athlete\n"
    				+ "(3) View your inventory\n"
    				+ "(4) Go back");
    		try {
    			int input = scan.nextInt();
    			switch (input) {
					case 0:
						goToTeam(game.getActiveTeam(), "Select an athlete to change their name or go back");
						break;
					case 1:
						goToTeam(game.getReservesTeam(), "Select an athlete to change their name or go back");
						break;
					case 2:
						swapAthletes();
						break;
					case 3:
						gotoInventory();
						break;
					case 4:
						stay = false;
						break;
				}
    		}
    		catch (Exception e) {
    			scan.nextLine();
    		}
    	}
    }



	private void goToTeam(ArrayList<Athlete> team, String description) {
		boolean viewing = true;
		int teamSize = team.size();
		while (viewing) {
			System.out.println(game.getTeamName()  + "'s team\n" +  description);
			displayTeam(team);	
			System.out.println("(" + teamSize + ") Go back");
			int index = scan.nextInt();
			try {
				switch ((index < teamSize) ? 0:
						(index == teamSize) ? 1: 2) {
					case 0:
						scan.nextLine();
						String newName = this.getName();
						Athlete athlete = team.get(index);
						String result = game.changeAtheleName(athlete, newName);
						System.out.println(result);
						break;
					case 1:
						viewing = false;
						break;
				}
			}
			catch (Exception e) {
				scan.nextLine();
			}
		}
	}

	

	private void gotoInventory() {
		boolean viewing = true;
		while (viewing) {
			System.out.println("Select an item to use or go back\n");
			displayInventory();
			System.out.println("(3) Go back");
			int index = scan.nextInt();
			try {
				switch (index) {
					case 0, 1, 2:
							Athlete target = selectAthlete(game.getAllAthlete(), "Select an Athlete to use " + 
												game.getItemInInventory(index).getName() + " on");
							String result = game.useItem(index, target);
							System.out.println(result);
						break;
					case 3:
						viewing = false;
						break;
				}	
			}
			catch (Exception e) {
				scan.hasNextLine();
			}
		}
		
	}


	
	 public void launchMarket() {
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
		    				goToBuy();
		    				break;
		    			case 1:
		    				goToSellAthlete();
		    				break;
		    			case 2:
		    				goToSellItem();
		    				
		    			case 3:
		    				stay = false;
		    				break;
	    			}
	    		}
	    		catch (Exception e) {
	    			scan.nextLine();
	    		}
	    	}
			
		}

	
	
    
    private void goToSellItem() {
    	boolean selling = true;
    	while (selling) {
    		displayMoney();
    		System.out.println("Select an item to sell");
    		displayItemSellInfos();
    		System.out.println("(3) Go back");
    		int index = scan.nextInt();
    		try {
    			switch (index) {
	    			case 0, 1, 2:
	    			String result = game.sellItem(index);
	    			System.out.println(result);
	    			break;
	    			case 3:
	    			selling = false;
	    			break;
    			}
    		}
    		catch (Exception e) {
    			scan.nextLine();
    		}
    	}
		
	}

	private void goToBuy() {
		boolean buying = true;
		while (buying) {
			int availablePurchase = game.getPurchasableSize();
			displayMoney();
			System.out.println("Select an object to purchase");
			displayPurchasableBuyInfo();
			System.out.println("(" + availablePurchase + ") Go back");
			int index = scan.nextInt();
			try {
				
				switch ((index < availablePurchase) ? 0:
						(index == availablePurchase) ? 1: 2) {
					case 0:
						String result = game.buyPurchasable(index);
						System.out.println(result);
						break;
					case 1:
						buying = false;
						break;
				}
			}
			catch (Exception e) {
				scan.nextLine();
			}
		}
		
	}

	private void goToSellAthlete() {
		boolean selling = true;
		int reserveSize = game.getReservesTeam().size();
		while (selling) {
			System.out.println("Select an athlete to sell (you can only sell reserve athletes)");
			displayAthleteSellInfos(game.getReservesTeam());
			System.out.println("(" + reserveSize + ") Goback");
			int index = scan.nextInt();
			try {
				switch ((index < reserveSize) ? 0:
						(index == reserveSize) ? 1: 2) {
					case 0:
						Athlete athlete = game.getReservesTeam().get(index);
						String result = game.sellAthlete(athlete);
						System.out.println(result);
						break;
					case 1:
						selling = false;
				}
			}
			catch (Exception e) {
				scan.nextLine();
			}
		}
		
	}
	
	
	public void launchStadium() {
		boolean matching = true;
		while (matching) {
			int availableMatches = game.getNumberOfAvailableMatches();
			System.out.println("Select your match or go back");
			displayMatches();
			System.out.println("(" + availableMatches + ") Go back");
			try {
				int index = scan.nextInt();
				switch ((index < availableMatches) ? 0:
						(index == availableMatches) ? 1: 2) {
					case 0:
						String result = game.matchStart(index);
						System.out.println(result);
						matching = false;
						break;
					case 1:
						matching = false;
						break;
				}

			}
			catch (Exception e) {
				scan.nextLine();
			}
		}
	}



    
	private Athlete selectAthlete(ArrayList<Athlete> availableAthletes, String prompt) {
		while (true) {
			System.out.println(prompt);
			displayTeam(availableAthletes);
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
	
	private void displayMatches() {
		System.out.println(game.getMatchInfos());
	}



    private void displayProperties() {
    	String properties = game.getProperties();
    	System.out.println(properties);
    }
    

    
	private void displayInventory() {
		System.out.println(game.getInventoryInfo());
		
	}



	private void displayMoney() {
		System.out.println("you have $" + game.getMoney());
		
	}
	
	private void displayTeam(ArrayList<Athlete> team) {
		String teamInfo = game.getAthletesinfo(team);
		System.out.println(teamInfo);
		
	}
	
	private void displayItemSellInfos() {
		System.out.println(game.getSellInfoItem());
	}
    
	private void displayAthleteSellInfos(ArrayList<Athlete> athletes) {
		System.out.println(game.getSellInfoAthlete(athletes));
	}
	
	private void displayPurchasableBuyInfo() {
		System.out.println(game.getBuyInfo());
	}


	private void takeABye() {
		String result = game.takeABye();
		if (game.getCurrentWeek() > game.getGameLength()) {
			gameFinish(result);
		}
		else {
			System.out.println(result);
		}
		
	}
	
	private void gameFinish(String result) {
		boolean selecting = true;
		while (selecting) {
			System.out.println(result);
			System.out.println("Would you like to restart?\nY/N");
			try {
				String input = scan.next("[yYnN]");
				if (input.matches("[yY]")) {
					this.setup(game);
					selecting = false;
				}
				else if (input.matches("[nN]")) {
					game.onFinish();
					selecting = false;
				}
				
			}
			catch (Exception e){
				scan.nextLine();
			}
		}
		
	}
	


	
	private void swapAthletes() {
		boolean swapping = true;
		String swapped = "";
		while(swapping) {
			try {
	    		Athlete athleteMain = selectAthlete(game.getActiveTeam(), 
	    				"Select active athlete to swap");
	    		Athlete athleteReserve = selectAthlete(game.getReservesTeam(), 
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
	
	
	/**
     * Outputs the set of options to the console.
     */
    private void printOptions() {

        for (Option option : Option.values()) {
            System.out.println("(" + option.ordinal() + ") " + option.name);
        }
    }
    
    public static void main(String[] args) {
    	CmdLineUi ui = new CmdLineUi();
    	GameEnvironment game = new GameEnvironment(ui); 
    	game.start();
    }
    
}
    
    
    


