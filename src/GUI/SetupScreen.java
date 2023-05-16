package GUI;
import Cores.Athlete;
import Cores.GameEnvironment;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import java.util.ArrayList;

public class SetupScreen {

	private JFrame frmGameenvironmentSetupScreen;
	private JTextField nameTextField;
	private ArrayList<Athlete> startingAthletes = new ArrayList<>();
	private GameEnvironment manager;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					SetupScreen window = new SetupScreen();
//					window.frmGameenvironmentSetupScreen.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public SetupScreen(GameEnvironment incomingManager) {
		manager = incomingManager;
		startingAthletes = manager.generateAthletes(6);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmGameenvironmentSetupScreen = new JFrame();
		frmGameenvironmentSetupScreen.setTitle("GameEnvironment Setup Screen");
		frmGameenvironmentSetupScreen.setBounds(100, 100, 450, 300);
		frmGameenvironmentSetupScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGameenvironmentSetupScreen.getContentPane().setLayout(null);
		
		JLabel lblWelcome = new JLabel("Welcome to the Game!");
		lblWelcome.setBounds(10, 11, 118, 14);
		frmGameenvironmentSetupScreen.getContentPane().add(lblWelcome);
		
		JLabel lblName = new JLabel("What is the name of your team?");
		lblName.setBounds(10, 33, 164, 14);
		frmGameenvironmentSetupScreen.getContentPane().add(lblName);
		
		JLabel lblLength = new JLabel("Select your season length");
		lblLength.setBounds(10, 58, 164, 14);
		frmGameenvironmentSetupScreen.getContentPane().add(lblLength);
		
		JLabel lblAthletes = new JLabel("Select your starting Athletes");
		lblAthletes.setBounds(10, 83, 164, 14);
		frmGameenvironmentSetupScreen.getContentPane().add(lblAthletes);
		
		nameTextField = new JTextField();
		nameTextField.setBounds(255, 30, 129, 20);
		frmGameenvironmentSetupScreen.getContentPane().add(nameTextField);
		nameTextField.setColumns(10);
		
		JSlider lengthSlider = new JSlider();
		lengthSlider.setMaximum(15);
		lengthSlider.setMinimum(5);
		lengthSlider.setBounds(184, 58, 200, 26);
		frmGameenvironmentSetupScreen.getContentPane().add(lengthSlider);
		
		DefaultListModel<Athlete> athleteListModel = new DefaultListModel<Athlete>();
		athleteListModel.addAll(startingAthletes);
		JList<Athlete> athleteList = new JList<Athlete>(athleteListModel);
		athleteList.setBounds(20, 171, 362, -62);
		frmGameenvironmentSetupScreen.getContentPane().add(athleteList);
		
		athleteList.getSelectedValue();
	}
}
