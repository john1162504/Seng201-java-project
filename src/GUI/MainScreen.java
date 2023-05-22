package GUI;

import Cores.GameEnvironment;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * 
 *Class that models a main screen graphical user interface
 * 
 */
public class MainScreen extends Screen{
	
	
	
	JLabel propertiesLabel;

	/**
	 * Create the application.
	 */
	protected MainScreen(GameEnvironment game) {
		super(game);
		//this.game = game;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setupFrame();
		addLebals();
		addButtons();
	}

	private void setupFrame() {
		frame = new JFrame();
		frame.setTitle("Main Screen");
		frame.setResizable(false);
		frame.setBounds(100, 100, 750, 500);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}
	

	private void addLebals() {
		JLabel greetingLebal = new JLabel("Hello " + game.getTeamName());
		greetingLebal.setFont(new Font("Lucida Grande", Font.ITALIC, 20));
		greetingLebal.setBounds(6, 6, 308, 48);
		frame.getContentPane().add(greetingLebal);
		
		propertiesLabel = new JLabel("<html>" + "Your properties<br/>" +
											game.getProperties().replaceAll("\n", "<br/>") + "<html>");
		propertiesLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		propertiesLabel.setBounds(523, 6, 204, 87);
		frame.getContentPane().add(propertiesLabel);
	}

	private void addButtons() {
		JButton clubButton = new JButton("Club");
		clubButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.launchClub();
				updateProperties();
			}
		});
		clubButton.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		clubButton.setBounds(22, 113, 340, 164);
		frame.getContentPane().add(clubButton);
		
		JButton marketButton = new JButton("Market");
		marketButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.launchMarket();
				updateProperties();
			}
		});
		marketButton.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		marketButton.setBounds(387, 113, 340, 164);
		frame.getContentPane().add(marketButton);
		
		JButton stadiumButton = new JButton("Stadium");
		stadiumButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.launchStadium();
				updateProperties();
			}
		});
		stadiumButton.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		stadiumButton.setBounds(22, 296, 340, 164);
		frame.getContentPane().add(stadiumButton);
		
		JButton takeAByeButton = new JButton("Take A Bye");
		takeAByeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String report = game.takeABye();
				if (game.getGameover() == true) {
					JOptionPane.showMessageDialog(frame, report);
					 gameFinished();
				}
				else {
					updateProperties();
					JOptionPane.showMessageDialog(frame, report);
				}
			}

		});
		takeAByeButton.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		takeAByeButton.setBounds(387, 296, 340, 164);
		frame.getContentPane().add(takeAByeButton);
	}
	
	private void updateProperties() {
		propertiesLabel.setText("<html>" + "Your properties<br/>" +
				game.getProperties().replaceAll("\n", "<br/>") + "<html>");
	}
	
	private void gameFinished() {
		int selection = JOptionPane.showConfirmDialog(frame, "Would you like to restart?",
		            "Restart?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (selection == JOptionPane.YES_OPTION) {
				this.quit();
				 game.start();
			}
			else {
				game.finish();
		}
		
	}

}
