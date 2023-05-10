package UI;

import Cores.GameEnvironment;

public interface GameEnvironmentUi {
	
    String NAME_REGEX = "[a-zA-Z]{3,}";
    
    void setup(GameEnvironment game);
    
    void start();
    
    boolean confirmQuit();
    
    void quit();
    
    
    void showError(String error);
}
