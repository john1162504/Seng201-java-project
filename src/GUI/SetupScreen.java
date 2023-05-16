package GUI;
import Cores.Athlete;
import Cores.GameEnvironment;
import UI.CmdLineUi.Difficulty;
//import seng201.rocketmanager.ui.gui.RocketTableModel;
import seng201.rocketmanager.ui.gui.GridBagConstraintsBuilder;

import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;

import java.util.ArrayList;

public class SetupScreen extends Screen {

	
	private JTextField fieldName;

	private JTable table;
		
	private JButton btnAccept;

	private JLabel lblError;

	/**
	 * Create the application.
	 */
	protected SetupScreen(GameEnvironment incomingManager) {
		super("GameEnvironment Setup", incomingManager);
	}
	
	/**
	 * Completes the setup of our {@link GameEnvironment}.
	 */
	private void setupComplete() {
		String name;
		int gameLength;
		ArrayList<Athlete> startAthletes;
		Difficulty difficulty;
		//getManager().onSetupFinished(name, gameLength, startAthletes, difficulty);
	}
	
	
	@Override
	protected void initialise(final Container container) {
		// Set the layout manager to a grid bag layout
		container.setLayout(new GridBagLayout());

		final GridBagConstraintsBuilder layoutBuilder = new GridBagConstraintsBuilder();
		
		frame = new JFrame();
		frame.setTitle("GameEnvironment Setup Screen");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblWelcome = new JLabel("Welcome to the Game!");
		lblWelcome.setBounds(10, 11, 118, 14);
		frame.getContentPane().add(lblWelcome);
		
		JLabel lblName = new JLabel("What is the name of your team?");
		lblName.setBounds(10, 33, 164, 14);
		frame.getContentPane().add(lblName);
		
		JLabel lblLength = new JLabel("Select your season length");
		lblLength.setBounds(10, 58, 164, 14);
		frame.getContentPane().add(lblLength);
		
		JLabel lblAthletes = new JLabel("Select your starting Athletes");
		lblAthletes.setBounds(10, 83, 164, 14);
		frame.getContentPane().add(lblAthletes);
		
		nameTextField = new JTextField();
		nameTextField.setBounds(255, 30, 129, 20);
		frame.getContentPane().add(nameTextField);
		nameTextField.setColumns(10);
		
		JSlider lengthSlider = new JSlider();
		lengthSlider.setMaximum(15);
		lengthSlider.setMinimum(5);
		lengthSlider.setBounds(184, 58, 200, 26);
		frame.getContentPane().add(lengthSlider);
		
		DefaultListModel<Athlete> athleteListModel = new DefaultListModel<Athlete>();
		startingAthletes = manager.generateAthletes(6);
		athleteListModel.addAll(startingAthletes);
		JList<Athlete> athleteList = new JList<Athlete>(athleteListModel);
		athleteList.setBounds(20, 171, 362, -62);
		frame.getContentPane().add(athleteList);
		
		athleteList.getSelectedValue();
	}

	
	
	
}
