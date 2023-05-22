package GUI;


import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Cores.Athlete;
import Cores.GameEnvironment;
import UI.CmdLineUi.Difficulty;
import UI.GameEnvironmentUi;

import javax.swing.JLabel;
import java.awt.Font;
import java.util.*;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JSlider;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JList;
import java.awt.event.ActionListener;

/**
 *  Class that models graphical user interface for setup screen
 * @author cch235
 *
 */
public class SetupScreen extends Screen{
	
	ArrayList<Athlete> athletes;

	private JTextField nameField;
	private JSlider lengthSlider;
	private JList<Athlete> athletesList;
	
	JLabel nameRequirementLabel;
	JLabel selectAthleteLabel;
	JButton continueButton;
	JCheckBox normalCheckBox;
	JCheckBox hardCheckBox;


	/**
	 * Create the application.
	 */
	public SetupScreen(GameEnvironment game) {
		super(game);
		this.athletes = game.generateAthletes(6);
		initialize();
	}
	
	public void onSetupFinish() {
		ArrayList<Athlete> selected = new ArrayList<>();
		for (int i: athletesList.getSelectedIndices()) {
			selected.add(athletes.get(i));
		}
		Difficulty difficulty = (normalCheckBox.isSelected() ? Difficulty.NORMAL : Difficulty.HARD); 
		game.onSetupFinished(nameField.getText(), lengthSlider.getValue()
				, selected, difficulty);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setupFrame();
		addLables();
		addNameField();
		addDifficultyCheckBoxes();
		addGameLengthSlider();
		addAthleteList();
		addContinueButtom();
	}

	private void addAthleteList() {

		JLabel selectLabel = new JLabel("Select your athletes");
		selectLabel.setBounds(416, 230, 229, 16);
		frame.getContentPane().add(selectLabel);
		
		JLabel remindLabel = new JLabel("(Select by hold control and click)");
		remindLabel.setBounds(416, 258, 288, 16);
		frame.getContentPane().add(remindLabel);
		
		selectAthleteLabel = new JLabel("Select four athletes from this list");
		selectAthleteLabel.setForeground(new Color(255, 4, 0));
		selectAthleteLabel.setBounds(20, 196, 271, 16);
		frame.getContentPane().add(selectAthleteLabel);
		
		DefaultListModel<Athlete> listModel = new DefaultListModel<Athlete>();
		listModel.addAll(athletes);
		athletesList = new JList<Athlete>(listModel);
		athletesList.setVisibleRowCount(6);
		athletesList.setBounds(20, 226, 378, 223);
		athletesList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				checkContinue();
			}
			
		});
		frame.getContentPane().add(athletesList);
	}
	


	private void addGameLengthSlider() {
		
		JLabel lengthLabel = new JLabel("How long would your tournament be?");
		lengthLabel.setBounds(20, 167, 358, 15);
		frame.getContentPane().add(lengthLabel);
		
		lengthSlider = new JSlider();
		lengthSlider.setMajorTickSpacing(5);
		lengthSlider.setMinorTickSpacing(1);
		lengthSlider.setSnapToTicks(true);
		lengthSlider.setPaintLabels(true);
		lengthSlider.setPaintTicks(true);
		lengthSlider.setMinimum(5);
		lengthSlider.setMaximum(15);
		lengthSlider.setBounds(473, 155, 190, 42);
		frame.getContentPane().add(lengthSlider);

	}
	

	private void addNameField() {
		JLabel enterNameLaabel = new JLabel("What's the name of your team?");
		enterNameLaabel.setBounds(20, 76, 239, 35);
		frame.getContentPane().add(enterNameLaabel);
		
		nameRequirementLabel = new JLabel(GameEnvironmentUi.NAME_REQUIRMENTS);
		nameRequirementLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		nameRequirementLabel.setForeground(new Color(255, 4, 0));
		nameRequirementLabel.setBounds(411, 58, 352, 16);
		frame.getContentPane().add(nameRequirementLabel);
		
		nameField = new JTextField();
		nameField.setBounds(411, 86, 304, 26);
		frame.getContentPane().add(nameField);
		nameField.setColumns(10);
		nameField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				checkContinue();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				checkContinue();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				checkContinue();
			}
			});
		}
			
		
		

	private void addDifficultyCheckBoxes() {
		
		JLabel difficultyLabel = new JLabel("Select difficulty of your tournament");
		difficultyLabel.setBounds(20, 123, 407, 16);
		frame.getContentPane().add(difficultyLabel);
		
		normalCheckBox = new JCheckBox("Normal");
		normalCheckBox.setBounds(463, 120, 128, 23);
		normalCheckBox.setSelected(true);
		frame.getContentPane().add(normalCheckBox);
		
		hardCheckBox = new JCheckBox("Hard");
		hardCheckBox.setBounds(610, 118, 128, 23);
		frame.getContentPane().add(hardCheckBox);

		
		ButtonGroup checkBoxGroup = new ButtonGroup();
		checkBoxGroup.add(hardCheckBox);
		checkBoxGroup.add(normalCheckBox);
	}

	private void setupFrame() {
		frame = new JFrame();
		frame.setTitle("SUMO AGENT setup");
		frame.setBounds(100, 100, 750, 500);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}
	
	public void addLables() {
		JLabel titleLable = new JLabel("Welcome to SUMO AGENT");
		titleLable.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		titleLable.setBounds(224, 12, 405, 62);
		frame.getContentPane().add(titleLable);
		


	}

	private void addContinueButtom() {
		continueButton = new JButton("Continue");
		continueButton.addActionListener(e -> onSetupFinish());
		continueButton.setEnabled(false);
		continueButton.setBounds(621, 431, 117, 29);
		frame.getContentPane().add(continueButton);

	}
	
	/**
	 * Enable continue button if a valid name is entered and four athletes are selected 
	 */
	private void checkContinue() {
		boolean validName = nameField.getText().matches(GameEnvironmentUi.NAME_REGEX);
		nameRequirementLabel.setVisible(!validName);
		boolean validSelection = (athletesList.getSelectedValuesList().size() == 4);
		selectAthleteLabel.setVisible(!validSelection);
		continueButton.setEnabled(validSelection && validName);
		
	}
}
