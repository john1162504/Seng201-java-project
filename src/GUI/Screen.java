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
    protected Screen(final String title, final GameEnvironment game) {
    	this.game = game;
    	
    	initialise(title);
    }
    
    private void initialise(final String title) {
        frame = new JFrame();
        frame.setTitle(title);

        // Prevent the frame from closing immediately when the user clicks the close button.
        // Instead we add a WindowListener so we can tell our rocket manager that the user
        // has requested to quit. This allows the rocket manager to perform actions that may
        // be required before quitting E.g. Confirming the user really wants to quit,
        // saving state etc.
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                game.onFinish();
            }
        });

        initialise(frame);

        // Size our frame
        frame.pack();

        // We set the location of our frame relative to null. This causes the frame to be placed
        // in the centre of the screen.
        frame.setLocationRelativeTo(null);
    }
    
    /**
     * Creates and adds the required graphical components to the given container.
     *
     * @param container The container to add content to
     */
    protected abstract void initialise(Container container);


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
