package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

import Cores.Athlete;
import Cores.GameEnvironment;

import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class StadiumScreen extends Screen{
//JFrame frame;
	
	private ArrayList<ArrayList<Athlete>> matches;
	
	private int selectedMatchIndex;



	private ArrayList<JButton> buttons;

	private JTextArea opponentTeamInfoArea;

	private JTextArea teamInfoArea;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameEnvironment game = new GameEnvironment();
					StadiumScreen window = new StadiumScreen(game);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public StadiumScreen(GameEnvironment game) {
		super(game);
		this.matches = game.getMatches();
		this.buttons = new ArrayList<>();
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 750, 500);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		addMatchButtons();
		updateButtons();

		
		opponentTeamInfoArea = new JTextArea();
		opponentTeamInfoArea.setBounds(27, 171, 326, 116);
		frame.getContentPane().add(opponentTeamInfoArea);
		
		JButton matchbutton = new JButton("Match!");
		matchbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String feedback = game.matchStart(selectedMatchIndex);
				JOptionPane.showMessageDialog(frame, feedback);
				updateButtons();
				teamInfoArea.setText(game.getAthletesinfo(game.getActiveTeam()));
			}
		});
		matchbutton.setBounds(474, 429, 117, 29);
		frame.getContentPane().add(matchbutton);
		
		JButton backButton = new JButton("Go Back");
		backButton.addActionListener(e -> game.launchMain());
		backButton.setBounds(611, 429, 117, 29);
		frame.getContentPane().add(backButton);
		

		teamInfoArea = new JTextArea();
		teamInfoArea.setBounds(27, 327, 326, 131);
		frame.getContentPane().add(teamInfoArea);
		teamInfoArea.setText(game.getAthletesinfo(game.getActiveTeam()));
		addLabels();
		
	}

	private void addLabels() {
		JLabel opponentLabel = new JLabel("Opponent's Team");
		opponentLabel.setBounds(27, 143, 161, 16);
		frame.getContentPane().add(opponentLabel);
		
		JLabel teamLabel = new JLabel("Your Team");
		teamLabel.setBounds(27, 299, 139, 16);
		frame.getContentPane().add(teamLabel);
	}

	private void addMatchButtons() {
		for (int i = 0; i < matches.size(); i++) {
			JButton matchButton = new JButton("Match " + (i + 1));
			matchButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					selectedMatchIndex = buttons.indexOf(matchButton);
					opponentTeamInfoArea.setText(game.getAthletesinfo(matches.get(selectedMatchIndex)));
					
				}
			});
			matchButton.setBounds((16 + (i * 125)), 18, 108, 100);
			frame.getContentPane().add(matchButton);
			buttons.add(matchButton);
		}
	}

	private void updateButtons() {
		for (JButton button : buttons) {
			button.setVisible(false);
		}
		for (int i = 0; i < matches.size(); i++) {
			buttons.get(i).setVisible(true);
		}
	}
}
