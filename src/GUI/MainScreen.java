package GUI;

import Cores.Athlete;
import Cores.GameEnvironment;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JSlider;
import javax.swing.JRadioButton;

public class MainScreen  {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					MainScreen window = new MainScreen();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	protected MainScreen(GameEnvironment incomingManager) {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(0, 2, 0, 0));
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("New radio button");
		frame.getContentPane().add(rdbtnNewRadioButton);
		
		JSlider slider = new JSlider();
		frame.getContentPane().add(slider);
	}

}
