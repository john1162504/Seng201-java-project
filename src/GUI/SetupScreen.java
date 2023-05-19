package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import Cores.Athlete;
import Cores.GameEnvironment;

import javax.swing.JLabel;
import java.awt.Font;
import java.util.*;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JSlider;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JList;

public class SetupScreen extends Screen{
	
	private GameEnvironment game;
	ArrayList<Athlete> athletes;

	private JFrame frmSetUp;
	String name;
	private JTextField textField;
	private JSlider lengthSlider;
	private JList list;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			Gui gui = new Gui();
			GameEnvironment game = new GameEnvironment(gui);
			public void run() {
				try {
					SetupScreen window = new SetupScreen(game);
					window.frmSetUp.setVisible(true);
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
		game.onSetupFinished(name, 0, athletes, null);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setupFrame();
		addLables();
		addTextField();
		addCheckBoxes();
		addSlider();
		addAthleteList();
		addButtom();
	}

	private void addAthleteList() {
		list = new JList(athletes.toArray());
		list.setVisibleRowCount(6);
		list.setBounds(20, 212, 298, 139);
		frmSetUp.getContentPane().add(list);
	}
	


	private void addSlider() {
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
		frmSetUp.getContentPane().add(lengthSlider);

	}
	

	private void addTextField() {
		textField = new JTextField();
		textField.setBounds(319, 80, 262, 26);
		frmSetUp.getContentPane().add(textField);
		textField.setColumns(10);
	}

	private void addCheckBoxes() {
		JCheckBox normalCheckBox = new JCheckBox("Normal");
		normalCheckBox.setBounds(319, 118, 128, 23);
		frmSetUp.getContentPane().add(normalCheckBox);
		
		JCheckBox HardCheckBox = new JCheckBox("Hard");
		HardCheckBox.setBounds(453, 118, 128, 23);
		frmSetUp.getContentPane().add(HardCheckBox);

		
		ButtonGroup checkBoxGroup = new ButtonGroup();
		checkBoxGroup.add(HardCheckBox);
		checkBoxGroup.add(normalCheckBox);
	}

	private void setupFrame() {
		frmSetUp = new JFrame();
		frmSetUp.setResizable(false);
		frmSetUp.setTitle("Set up");
		frmSetUp.setBounds(100, 100, 610, 400);
		frmSetUp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSetUp.getContentPane().setLayout(null);
	}
	
	public void addLables() {
		JLabel titleLable = new JLabel("Welcome to SUMO AGENT");
		titleLable.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		titleLable.setBounds(172, 6, 253, 62);
		frmSetUp.getContentPane().add(titleLable);
		
		JLabel enterNameLaabel = new JLabel("What's the name of your team?");
		enterNameLaabel.setBounds(20, 76, 239, 35);
		frmSetUp.getContentPane().add(enterNameLaabel);
		
		
		JLabel difficultyLabel = new JLabel("Select difficulty of your tournament");
		difficultyLabel.setBounds(20, 123, 239, 16);
		frmSetUp.getContentPane().add(difficultyLabel);
		
		JLabel lengthLabel = new JLabel("How long would your tournament be?");
		lengthLabel.setBounds(20, 166, 253, 16);
		frmSetUp.getContentPane().add(lengthLabel);
	}

	private void addButtom() {
		JButton continueButton = new JButton("Continue");
		continueButton.addActionListener(e -> onSetupFinish());
		continueButton.setEnabled(false);
		continueButton.setBounds(487, 337, 117, 29);
		frmSetUp.getContentPane().add(continueButton);
	}
	
	public Color getLengthSliderBackground() {
		return lengthSlider.getBackground();
	}
	
	public void setLengthSliderBackground(Color background) {
		lengthSlider.setBackground(background);
	
	}
}
