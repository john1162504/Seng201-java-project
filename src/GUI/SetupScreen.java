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

public class SetupScreen extends Screen{
	
	ArrayList<Athlete> athletes;

	String name;
	private JTextField nameField;
	private JSlider lengthSlider;
	private JList<Athlete> athletesList;
	
	JLabel nameRequirementLabel;
	JLabel selectAthleteLabel;
	JButton continueButton;
	JCheckBox normalCheckBox;
	JCheckBox hardCheckBox;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			Gui gui = new Gui();
			GameEnvironment game = new GameEnvironment(gui);
			public void run() {
				try {
					SetupScreen window = new SetupScreen(game);
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
		selectLabel.setBounds(332, 226, 229, 16);
		frame.getContentPane().add(selectLabel);
		
		JLabel remindLabel = new JLabel("(Select by hold control and click)");
		remindLabel.setBounds(332, 254, 210, 16);
		frame.getContentPane().add(remindLabel);
		
		selectAthleteLabel = new JLabel("Select four athletes from this list");
		selectAthleteLabel.setForeground(new Color(255, 4, 0));
		selectAthleteLabel.setBounds(20, 196, 271, 16);
		frame.getContentPane().add(selectAthleteLabel);
		
		DefaultListModel<Athlete> listModel = new DefaultListModel<Athlete>();
		listModel.addAll(athletes);
		athletesList = new JList<Athlete>(listModel);
		athletesList.setVisibleRowCount(6);
		athletesList.setBounds(20, 224, 298, 127);
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
		lengthLabel.setBounds(20, 166, 253, 16);
		frame.getContentPane().add(lengthLabel);
		
		lengthSlider = new JSlider();
		lengthSlider.setBackground(new Color(65, 138, 255));
		lengthSlider.setMajorTickSpacing(5);
		lengthSlider.setMinorTickSpacing(1);
		lengthSlider.setSnapToTicks(true);
		lengthSlider.setPaintLabels(true);
		lengthSlider.setPaintTicks(true);
		lengthSlider.setMinimum(5);
		lengthSlider.setMaximum(15);
		lengthSlider.setBounds(319, 153, 190, 42);
		frame.getContentPane().add(lengthSlider);

	}
	

	private void addNameField() {
		JLabel enterNameLaabel = new JLabel("What's the name of your team?");
		enterNameLaabel.setBounds(20, 76, 239, 35);
		frame.getContentPane().add(enterNameLaabel);
		
		nameRequirementLabel = new JLabel(GameEnvironmentUi.NAME_REQUIRMENTS);
		nameRequirementLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		nameRequirementLabel.setForeground(new Color(255, 4, 0));
		nameRequirementLabel.setBounds(295, 64, 309, 16);
		frame.getContentPane().add(nameRequirementLabel);
		
		nameField = new JTextField();
		nameField.setBounds(319, 80, 262, 26);
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
		difficultyLabel.setBounds(20, 123, 239, 16);
		frame.getContentPane().add(difficultyLabel);
		
		normalCheckBox = new JCheckBox("Normal");
		normalCheckBox.setBounds(319, 118, 128, 23);
		normalCheckBox.setSelected(true);
		frame.getContentPane().add(normalCheckBox);
		
		hardCheckBox = new JCheckBox("Hard");
		hardCheckBox.setBounds(453, 118, 128, 23);
		frame.getContentPane().add(hardCheckBox);

		
		ButtonGroup checkBoxGroup = new ButtonGroup();
		checkBoxGroup.add(hardCheckBox);
		checkBoxGroup.add(normalCheckBox);
	}

	private void setupFrame() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("SUMO AGENT setup");
		frame.setBounds(100, 100, 610, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}
	
	public void addLables() {
		JLabel titleLable = new JLabel("Welcome to SUMO AGENT");
		titleLable.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		titleLable.setBounds(172, 6, 253, 62);
		frame.getContentPane().add(titleLable);
		


	}

	private void addContinueButtom() {
		continueButton = new JButton("Continue");
		continueButton.addActionListener(e -> onSetupFinish());
		continueButton.setEnabled(false);
		continueButton.setBounds(487, 337, 117, 29);
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
