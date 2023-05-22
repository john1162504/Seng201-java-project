package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;

import Cores.Athlete;
import Cores.GameEnvironment;
import Cores.Item;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
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
private JLabel firstLabel;
private Object second;
private JLabel thirdLabel;
private Object first;
private JLabel secondLabel;
private Object third;

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
		frame.setTitle("Club");
		frame.setResizable(false);
		frame.setBounds(100, 100, 750, 500);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		setupModels();
		addActiveTeam();
		addReserveTeam();
		addInventory();
		addSwapButton();	
		addUseItemButton();
		addBackButton();
		addRenameButton();
		addAddButton();


	}

	private void addUseItemButton() {
		JButton useItemButton = new JButton("Use Item");
		useItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int selectedItemIndex = inventoryList.getSelectedIndex();
					Athlete selectedAthlete = (activeTeamList.isSelectionEmpty() ? reserveTeamList.getSelectedValue() :
						activeTeamList.getSelectedValue());
					String feedback = game.useItem(selectedItemIndex, selectedAthlete);
					JOptionPane.showMessageDialog(frame, feedback);
					updateLists();
					refreshLabels();
				}
				catch (Exception error) {
					showError(error.getMessage());
				}
			}
		});
		useItemButton.setBounds(30, 413, 117, 29);
		frame.getContentPane().add(useItemButton);
	}

	private void addBackButton() {
		JButton backButton = new JButton("Go Back");

		backButton.addActionListener(e -> game.launchMain());
		backButton.setBounds(609, 413, 117, 29);
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
				game.changeAtheleName(selected, newName);
				}
				catch (Exception ecept){
					showError(ecept.getMessage());
				}
			}
		});
		renameButton.setBounds(609, 217, 117, 29);
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
		swapButton.setBounds(373, 217, 117, 29);
		frame.getContentPane().add(swapButton);
	}

	private void addInventory() {
		JLabel inventoryLabel = new JLabel("Inventory");
		inventoryLabel.setBounds(30, 260, 120, 32);
		frame.getContentPane().add(inventoryLabel);

		inventoryList = new JList<Item>(inventoryModel);
		inventoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		inventoryList.setVisibleRowCount(3);
		inventoryList.setBounds(30, 304, 392, 97);
		inventoryList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				checkSelection();
			}
		});
		frame.getContentPane().add(inventoryList);
		
		
		first = (Item) game.getInventory().keySet().toArray()[0];
		firstLabel = new JLabel("You have " + game.getInventory().get(first));
		firstLabel.setBounds(440, 304, 168, 16);
		frame.getContentPane().add(firstLabel);
		
		second = (Item) game.getInventory().keySet().toArray()[1];
		secondLabel = new JLabel("You have " + game.getInventory().get(second));
		secondLabel.setBounds(440, 318, 168, 16);
		frame.getContentPane().add(secondLabel);
		
		third = (Item) game.getInventory().keySet().toArray()[2];
		thirdLabel = new JLabel("You have " + game.getInventory().get(third));
		thirdLabel.setBounds(440, 332, 136, 16);
		frame.getContentPane().add(thirdLabel);
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
		activeTeamList.setBounds(30, 50, 311, 155);
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
		reserveTeamLabel.setBounds(383, 6, 120, 32);
		frame.getContentPane().add(reserveTeamLabel);
		reserveTeamList = new JList<Athlete>(reserveModel);
		reserveTeamList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		reserveTeamList.setVisibleRowCount(5);
		reserveTeamList.setBounds(373, 50, 350, 155);
		reserveTeamList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				checkSelection();
			}
		});
		frame.getContentPane().add(reserveTeamList);
		
	}
	
	private void addAddButton() {
		JButton adButton = new JButton("Add");
		adButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Athlete selected = reserveTeamList.getSelectedValue();
					String result = game.addAthletetoActive(selected);
					JOptionPane.showMessageDialog(frame, result);
					updateLists();
					refreshLabels();
				}
				catch (Exception error){
					showError(error.getMessage());
				}
			}
		});
		adButton.setBounds(491, 217, 117, 29);
		frame.getContentPane().add(adButton);
	}
	

	private void updateLists() {
		activeModel.clear();
		reserveModel.clear();
		reserveModel.addAll(game.getReservesTeam());
		activeModel.addAll(game.getActiveTeam());
		activeTeamList.setModel(activeModel);
		reserveTeamList.setModel(reserveModel);
	}
	
	private void refreshLabels() {
		firstLabel.setText("You Have " +  game.getInventory().get(first));
		secondLabel.setText("You Have " +  game.getInventory().get(second));
		thirdLabel.setText("You Have " +  game.getInventory().get(third));
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
