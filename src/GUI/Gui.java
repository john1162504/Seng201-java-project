package GUI;

import Cores.GameEnvironment;
import UI.GameEnvironmentUi;

/**
 * A graphical user interface for a {@link RocketManager}.
 */
public class Gui implements GameEnvironmentUi {

    // The rocket manager this gui interacts with
    private GameEnvironment gameManager;

    // The currently active screen in this gui
    private Screen screen;

    @Override
    public void setup(GameEnvironment manager) {
        this.gameManager = manager;
        screen = new SetupScreen(gameManager);
        screen.show();
    }

    @Override
    public void showError(String error) {
        screen.showError(error);
    }

    @Override
    public void start() {
        screen.quit();
        //screen = new MainScreen(manager);
        screen.show();
    }

    @Override
    public boolean confirmQuit() {
        return screen.confirmQuit();
    }

    @Override
    public void quit() {
        screen.quit();
    }
}