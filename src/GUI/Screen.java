package GUI;

import Cores.GameEnvironment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Defines common behaviour supported by a gui screen.
 */
public abstract class Screen {

    // The frame for this screen
    private JFrame frame;

    // The rocket manager that this screen interacts with
    private final GameEnvironment game;

    /**
     * Creates this screen.
     *
     * @param title The title for the screen
     * @param manager The {@link RocketManager} that this screen interacts with
     */
    protected Screen(GameEnvironment game) {
    	this.game = game;
    }


    /**
     * Gets the {@link RocketManager} that this screen supports.
     *
     * @return The rocket manager for this screen
     */
    protected GameEnvironment getManager() {
        return game;
    }

    /**
     * Shows this screen by making it visible.
     */
    protected void show() {
        frame.setVisible(true);
    }

    /**
     * Confirms if the user wants to quit this screen.
     *
     * @return true to quit, false otherwise
     */
    protected boolean confirmQuit() {
        int selection = JOptionPane.showConfirmDialog(frame, "Are you sure you want to quit?",
                "Quit?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        return selection == JOptionPane.YES_OPTION;
    }

    /**
     * Quits this screen. This should dispose of the screen as necessary.
     */
    void quit() {
        frame.dispose();
    }

    /**
     * Reports the given error to the user.
     *
     * @param error The error to report
     */
    void showError(String error) {
        JOptionPane.showMessageDialog(frame, error, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
