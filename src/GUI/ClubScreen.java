package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;

import Cores.Athlete;
import Cores.GameEnvironment;
import Cores.Item;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import java.awt.Insets;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextPane;
import javax.swing.JSpinner;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ClubScreen extends Screen{

//	private JFrame frame;	
	
private DefaultListModel<Athlete> activeModel;
private DefaultListModel<Athlete> reserveModel;
private DefaultListModel<Item> inventoryModel;
private JList<Athlete> activeTeamList;
private JList<Athlete> reserveTeamList;
private JList<Item> inventoryList;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		GameEnvironment game = new GameEnvironment();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClubScreen window = new ClubScreen(game);
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
	public ClubScreen(GameEnvironment game) {
		super(game);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 610, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		setupModels();
		addActiveTeam();
		addReserveTeam();
		addInventory();
		addSwapButton();	
		addUseItemButton();
		addBackButton();
		addRenameButton();


	}

	private void addUseItemButton() {
		JButton useItemButton = new JButton("Use Item");
		useItemButton.setBounds(33, 312, 117, 29);
		frame.getContentPane().add(useItemButton);
	}

	private void addBackButton() {
		JButton backButton = new JButton("Go Back");
		backButton.addActionListener(e -> game.launchMain());
		backButton.setBounds(487, 337, 117, 29);
		frame.getContentPane().add(backButton);
	}

	private void addRenameButton() {
		JButton renameButton = new JButton("Rename");
		renameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				String newName = JOptionPane.showInputDialog("Enter new ame");
				Athlete selected = (activeTeamList.isSelectionEmpty() ? reserveTeamList.getSelectedValue() :
																		activeTeamList.getSelectedValue());
				game.cahngeAtheleName(selected, newName);
				}
				catch (Exception ecept){
					showError(ecept.getMessage());
				}
			}
		});
		renameButton.setBounds(475, 162, 117, 29);
		frame.getContentPane().add(renameButton);
	}

	private void addSwapButton() {
		JButton swapButton = new JButton("Swap");
		swapButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Athlete active = game.getActiveTeam().get(activeTeamList.getMaxSelectionIndex());
					Athlete reserve = (reserveTeamList.isSelectionEmpty() ? game.getActiveTeam().get(activeTeamList.getMinSelectionIndex()) :
																			reserveTeamList.getSelectedValue());
					String feedback = game.swapAthletes(active, reserve);
					JOptionPane.showMessageDialog(frame, feedback);
					updateLists();
				}
				catch (Exception error) {
					showError("You need to select two athletes!");
				}
			}

		});
		swapButton.setBounds(339, 162, 117, 29);
		frame.getContentPane().add(swapButton);
	}

	private void addInventory() {
		JLabel inventoryLabel = new JLabel("Inventory");
		inventoryLabel.setBounds(30, 159, 120, 32);
		frame.getContentPane().add(inventoryLabel);

		inventoryList = new JList<Item>(inventoryModel);
		inventoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		inventoryList.setVisibleRowCount(3);
		inventoryList.setBounds(30, 203, 321, 97);
		inventoryList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				checkSelection();
			}
		});
		frame.getContentPane().add(inventoryList);
		
		
		Item first = (Item) game.getInventory().keySet().toArray()[0];
		JLabel firstLabel = new JLabel("You have " + game.getInventory().get(first));
		firstLabel.setBounds(363, 203, 168, 16);
		frame.getContentPane().add(firstLabel);
		
		Item second = (Item) game.getInventory().keySet().toArray()[1];
		JLabel secondLabel = new JLabel("You have " + game.getInventory().get(second));
		secondLabel.setBounds(363, 222, 168, 16);
		frame.getContentPane().add(secondLabel);
		
		Item third = (Item) game.getInventory().keySet().toArray()[2];
		JLabel thridLebal = new JLabel("You have " + game.getInventory().get(third));
		thridLebal.setBounds(363, 242, 136, 16);
		frame.getContentPane().add(thridLebal);
	}
	private void setupModels() {
		activeModel = new DefaultListModel<Athlete>();
		reserveModel = new DefaultListModel<Athlete>();
		inventoryModel = new DefaultListModel<Item>();
		activeModel.addAll(game.getActiveTeam());
		reserveModel.addAll(game.getReservesTeam());
		inventoryModel.addAll(game.getInventory().keySet());
	}
	
	private void addActiveTeam() {
		JLabel activeTeamLabel = new JLabel("Active Team");
		activeTeamLabel.setBounds(30, 6, 120, 32);
		frame.getContentPane().add(activeTeamLabel);
		
		activeTeamList = new JList<Athlete>(activeModel);
		activeTeamList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		activeTeamList.setVisibleRowCount(4);
		activeTeamList.setBounds(30, 50, 267, 97);
		activeTeamList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				checkSelection();
			}
		});
		frame.getContentPane().add(activeTeamList);
	}
	private void addReserveTeam() {
		JLabel reserveTeamLabel = new JLabel("Reserve Team");
		reserveTeamLabel.setBounds(339, 6, 120, 32);
		frame.getContentPane().add(reserveTeamLabel);
		reserveTeamList = new JList<Athlete>(reserveModel);
		reserveTeamList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		reserveTeamList.setVisibleRowCount(5);
		reserveTeamList.setBounds(325, 50, 267, 97);
		reserveTeamList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				checkSelection();
			}
		});
		frame.getContentPane().add(reserveTeamList);
		
	}
	

	private void updateLists() {
		activeModel.clear();
		reserveModel.clear();
		reserveModel.addAll(game.getReservesTeam());
		activeModel.addAll(game.getActiveTeam());
		activeTeamList.setModel(activeModel);
		reserveTeamList.setModel(reserveModel);
	}
	

	private void checkSelection() {
		boolean greaterThanTwo = ((activeTeamList.getSelectedValuesList().size() + 
								reserveTeamList.getSelectedValuesList().size() + 
								inventoryList.getSelectedValuesList().size()) > 2);
		if	(greaterThanTwo) {
			activeTeamList.clearSelection();
			reserveTeamList.clearSelection();
			inventoryList.clearSelection();
		}
		
	}

}
