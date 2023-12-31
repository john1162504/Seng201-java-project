package sumoagent;

import Cores.GameEnvironment;
import UI.CmdLineUi;
import UI.GameEnvironmentUi;
import GUI.Gui;

import javax.swing.*;

/**
 * Class where application execution begins. If {@code cmd} is passed as a
 * program argument the application will run as a command line application,
 * otherwise a GUI will be used.
 * This is adapt from Rocket Manager example
 */
public class Main {

	/**
	 * Application entry point for the sumo agent application.
	 *
	 * @param args The command line arguments. This application supports a single
	 *             argument: {@code cmd}. If {@code cmd} is present the application
	 *             will use a command line interface. When no argument is specified
	 *             the application will use a graphical interface.
	 */
	public static void main(String[] args) {

		GameEnvironmentUi ui;

		if (args.length > 0 && (args[0].equals("cmd"))) {
			ui = new CmdLineUi();
			GameEnvironment manager = new GameEnvironment(ui);
			manager.start();
		} else {
			ui = new Gui();
			GameEnvironment manager = new GameEnvironment(ui);

			// Ensure the GameEnvironment is started on the Swing event dispatch thread
			// (EDT). To be thread safe,
			// all swing code should run on this thread unless explicitly stated as being
			// thread safe.
			SwingUtilities.invokeLater(() -> manager.start());
		}
	}
}
