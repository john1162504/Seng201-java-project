package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import Cores.Athlete;
import Cores.GameEnvironment;

/**
 *
 * Class that models a graphical interface for stadium
 */
public class StadiumScreen extends Screen {

	// The array list contains list of matches
	private ArrayList<ArrayList<Athlete>> matches;
	// The index of the selected macth
	private int selectedMatchIndex;

	// The array list contain match buttons
	private ArrayList<JButton> matchButtons;
	// The text area that display opponent's team information
	private JTextArea opponentTeamInfoArea;
	// The text area that display user's team information
	private JTextArea teamInfoArea;

	/**
	 * Create the application.
	 */
	public StadiumScreen(GameEnvironment game) {
		super(game);
		this.matches = game.getMatches();
		this.matchButtons = new ArrayList<>();
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setupFrame();
		addMatchButtons();
		updateButtons();
		addInfoArea();
		addStartMatchButton();
		addBackButton();
		addLabels();

	}

	/**
	 * Add two text area to display team's information and match information
	 */
	private void addInfoArea() {
		opponentTeamInfoArea = new JTextArea();
		opponentTeamInfoArea.setBounds(27, 171, 326, 116);
		frame.getContentPane().add(opponentTeamInfoArea);
		teamInfoArea = new JTextArea();
		teamInfoArea.setBounds(27, 327, 326, 131);
		frame.getContentPane().add(teamInfoArea);
		teamInfoArea.setText(game.getAthletesinfo(game.getActiveTeam()));
	}

	/**
	 * Add the back button to the frame back button can take user back to main
	 * screen
	 */
	private void addBackButton() {
		JButton backButton = new JButton("Go Back");
		backButton.addActionListener(e -> game.launchMain());
		backButton.setBounds(611, 429, 117, 29);
		frame.getContentPane().add(backButton);
	}

	/**
	 * Add the start match button to the frame this button let user start matching
	 * with the selected match
	 */
	private void addStartMatchButton() {
		JButton startMatchbutton = new JButton("Match!");
		startMatchbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String feedback = game.matchStart(selectedMatchIndex);
					JOptionPane.showMessageDialog(frame, feedback);
					updateButtons();
					teamInfoArea.setText(game.getAthletesinfo(game.getActiveTeam()));
				} catch (Exception error) {
					showError(error.getMessage());
				}
			}
		});
		startMatchbutton.setBounds(474, 429, 117, 29);
		frame.getContentPane().add(startMatchbutton);
	}

	/**
	 * Setup this frame
	 */
	private void setupFrame() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 750, 500);
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}

	/**
	 * Add the static labels
	 */
	private void addLabels() {
		JLabel opponentLabel = new JLabel("Opponent's Team");
		opponentLabel.setBounds(27, 143, 161, 16);
		frame.getContentPane().add(opponentLabel);

		JLabel teamLabel = new JLabel("Your Team");
		teamLabel.setBounds(27, 299, 139, 16);
		frame.getContentPane().add(teamLabel);
	}

	/**
	 * Initialize buttons for matches number of buttons are equal to number of
	 * matches each button is corespond to a match
	 */
	private void addMatchButtons() {
		for (int i = 0; i < matches.size(); i++) {
			JButton matchButton = new JButton("Match " + (i + 1));
			matchButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					selectedMatchIndex = matchButtons.indexOf(matchButton);
					opponentTeamInfoArea.setText(game.getAthletesinfo(matches.get(selectedMatchIndex)));
				}
			});
			matchButton.setBounds((16 + (i * 125)), 18, 108, 100);
			frame.getContentPane().add(matchButton);
			matchButtons.add(matchButton);
		}
	}

	/**
	 * Update visibility of buttons to ensure every button has it own correspond
	 * match
	 *
	 */
	private void updateButtons() {
		for (JButton button : matchButtons) {
			button.setVisible(false);
		}
		for (int i = 0; i < matches.size(); i++) {
			matchButtons.get(i).setVisible(true);
		}
	}
}
