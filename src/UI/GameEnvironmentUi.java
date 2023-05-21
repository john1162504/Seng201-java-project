package UI;

import Cores.GameEnvironment;

/**
 *User interface's interface  
 *
 */
public interface GameEnvironmentUi {
	//Name regex use to test name validity
   public final static String NAME_REGEX = "[a-zA-Z]{3,15}";
    
    //Name requirements 
    final static String NAME_REQUIRMENTS = "Your name must only contain letters and have 3 to 15 charaters";
    
    /**
     * Initialises this UI and setup the given game 
     * @param game The game this interface interact with
     */
    void setup(GameEnvironment game);
    
    /**
     * This method is called once setup is finished and allowing player move on to the main game
     */
    void start();
    
    /**
     * Confirms if player really wants to quit
     * 
     * @return true if player confirm to quit, false otherwise
     */
    boolean confirmQuit();
    
    /**
     * quit this application
     */
    void quit();
    
    /**
     * Report errors to the player
     * @param error The error
     */
    void showError(String error);

	void launchStadium();

	void launchMarket();

	void launchClub();
}
