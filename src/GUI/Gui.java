package GUI;

import Cores.GameEnvironment;
import UI.GameEnvironmentUi;

/**
 * A graphical user interface for a {@link RocketManager}.
 */
public class Gui implements GameEnvironmentUi {

    // The rocket manager this gui interacts with
    private GameEnvironment game;

    // The currently active screen in this gui
    private Screen screen;

    @Override
    public void setup(GameEnvironment game) {
        this.game = game;
        screen = new SetupScreen(game);
        screen.show();
    }

    @Override
    public void showError(String error) {
        screen.showError(error);
    }

    @Override
    public void start() {
        screen.quit();
        screen = new MainScreen(game, this);
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
    
	public void launchStadium() {
		screen.quit();
		screen = new StadiumScreen(game);
		screen.show();
	}

	public void launchMarket() {
		screen.quit();
		screen = new MarketScreen(game);
		screen.show();
	}

	public void launchClub() {
		screen.quit();
		screen = new ClubScreen(game);
		screen.show();
	}
    
}
