package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Cores.Athlete;
import Cores.GameEnvironment;
import Cores.Item;

/**
 *
 *
 */
public class ClubScreen extends Screen {
	// ListModel that models active team to a list
	private DefaultListModel<Athlete> activeModel;
	// ListModel that models reserve team to a list
	private DefaultListModel<Athlete> reserveModel;
	// ListModel that models inventory as a list
	private DefaultListModel<Item> inventoryModel;
	// JList that display active team
	private JList<Athlete> activeTeamList;
	// JList that display reserve team
	private JList<Athlete> reserveTeamList;
	// Jlist that display inventory
	private JList<Item> inventoryList;
	// Label that use to illustrate number of first item in inventory
	private JLabel firstLabel;
	// Label that use to illustrate number of second item in inventory
	private JLabel secondLabel;
	// Label that use to illustrate number of third item in inventory
	JLabel thirdLabel;
	// Instance of the first item in inventory
	private Object first;
	// Instance of the second item in inventory
	private Object second;
	// Instance of the third item in inventory
	private Object third;

	/**
	 * Create the application.
	 */
	public ClubScreen(GameEnvironment game) {
		super(game);
		initialize();
	}

	/**
	 * Add the JList contain active team to the frame
	 *
	 */
	private void addActiveTeam() {
		JLabel activeTeamLabel = new JLabel("Active Team");
		activeTeamLabel.setBounds(30, 6, 120, 32);
		frame.getContentPane().add(activeTeamLabel);

		activeTeamList = new JList<>(activeModel);
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

	/**
	 * Add the add button to the frame add button can add a selected reserve athlete
	 * to active team
	 */
	private void addAddButton() {
		JButton adButton = new JButton("Add");
		adButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Athlete selected = reserveTeamList.getSelectedValue();
					String result = game.addAthletetoActive(selected);
					JOptionPane.showMessageDialog(frame, result);
					updateLists();
					refreshLabels();
				} catch (Exception error) {
					showError(error.getMessage());
				}
			}
		});
		adButton.setBounds(491, 217, 117, 29);
		frame.getContentPane().add(adButton);
	}

	/**
	 * Add the back button to the frame back button can take user back to main
	 * screen
	 */
	private void addBackButton() {
		JButton backButton = new JButton("Go Back");

		backButton.addActionListener(e -> game.launchMain());
		backButton.setBounds(609, 413, 117, 29);
		frame.getContentPane().add(backButton);
	}

	/**
	 * Add the JList contain inventory to the frame and the labels illustrate how
	 * many items you have
	 */
	private void addInventory() {
		JLabel inventoryLabel = new JLabel("Inventory");
		inventoryLabel.setBounds(30, 260, 120, 32);
		frame.getContentPane().add(inventoryLabel);

		inventoryList = new JList<>(inventoryModel);
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

		first = game.getInventory().keySet().toArray()[0];
		firstLabel = new JLabel("You have " + game.getInventory().get(first));
		firstLabel.setBounds(440, 304, 168, 16);
		frame.getContentPane().add(firstLabel);

		second = game.getInventory().keySet().toArray()[1];
		secondLabel = new JLabel("You have " + game.getInventory().get(second));
		secondLabel.setBounds(440, 318, 168, 16);
		frame.getContentPane().add(secondLabel);

		third = game.getInventory().keySet().toArray()[2];
		thirdLabel = new JLabel("You have " + game.getInventory().get(third));
		thirdLabel.setBounds(440, 332, 136, 16);
		frame.getContentPane().add(thirdLabel);
	}

	/**
	 * Add the rename button to the frame rename button can rename a selected
	 * athlete
	 */
	private void addRenameButton() {
		JButton renameButton = new JButton("Rename");
		renameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String newName = JOptionPane.showInputDialog("Enter new name");
					Athlete selected = (activeTeamList.isSelectionEmpty() ? reserveTeamList.getSelectedValue()
							: activeTeamList.getSelectedValue());
					String feedback = game.changeAtheleName(selected, newName);
					JOptionPane.showMessageDialog(frame, feedback);
					updateLists();
					refreshLabels();
				} catch (Exception ecept) {
					showError(ecept.getMessage());
				}
			}
		});
		renameButton.setBounds(609, 217, 117, 29);
		frame.getContentPane().add(renameButton);

	}

	/**
	 * Add the JList contain active team to the frame
	 *
	 */
	private void addReserveTeam() {
		JLabel reserveTeamLabel = new JLabel("Reserve Team");
		reserveTeamLabel.setBounds(383, 6, 120, 32);
		frame.getContentPane().add(reserveTeamLabel);
		reserveTeamList = new JList<>(reserveModel);
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

	/**
	 * Add the swap button to the frame Swap button can swap the position of two
	 * selected athletes
	 */
	private void addSwapButton() {
		JButton swapButton = new JButton("Swap");
		swapButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Athlete active = game.getActiveTeam().get(activeTeamList.getMaxSelectionIndex());
					Athlete reserve = (reserveTeamList.isSelectionEmpty()
							? game.getActiveTeam().get(activeTeamList.getMinSelectionIndex())
							: reserveTeamList.getSelectedValue());
					String feedback = game.swapAthletes(active, reserve);
					JOptionPane.showMessageDialog(frame, feedback);
					updateLists();
				} catch (Exception error) {
					showError("You need to select two athletes!");
				}
			}

		});
		swapButton.setBounds(373, 217, 117, 29);
		frame.getContentPane().add(swapButton);
	}

	/**
	 * Add the use item button to the frame use item button can use a item to a
	 * selected athlete
	 */
	private void addUseItemButton() {
		JButton useItemButton = new JButton("Use Item");
		useItemButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int selectedItemIndex = inventoryList.getSelectedIndex();
					Athlete selectedAthlete = (activeTeamList.isSelectionEmpty() ? reserveTeamList.getSelectedValue()
							: activeTeamList.getSelectedValue());
					String feedback = game.useItem(selectedItemIndex, selectedAthlete);
					JOptionPane.showMessageDialog(frame, feedback);
					updateLists();
					refreshLabels();
				} catch (Exception error) {
					showError(error.getMessage());
				}
			}
		});
		useItemButton.setBounds(30, 413, 117, 29);
		frame.getContentPane().add(useItemButton);
	}

	/**
	 * This method clear {@link activeTeamList} , {@link reserveTeamList} and
	 * {@link inventoryList} selection when more than two selection is selected
	 * among these three JLists
	 */
	private void checkSelection() {
		boolean greaterThanTwo = ((activeTeamList.getSelectedValuesList().size()
				+ reserveTeamList.getSelectedValuesList().size() + inventoryList.getSelectedValuesList().size()) > 2);
		if (greaterThanTwo) {
			activeTeamList.clearSelection();
			reserveTeamList.clearSelection();
			inventoryList.clearSelection();
		}

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setupFrame();
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

	/**
	 * Refresh the labels illustrate how many items user have
	 */
	private void refreshLabels() {
		firstLabel.setText("You Have " + game.getInventory().get(first));
		secondLabel.setText("You Have " + game.getInventory().get(second));
		thirdLabel.setText("You Have " + game.getInventory().get(third));
	}

	/**
	 * Setup this frame
	 */
	private void setupFrame() {
		frame = new JFrame();
		frame.setTitle("Club");
		frame.setResizable(false);
		frame.setBounds(100, 100, 750, 500);
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}

	/**
	 * Setup ListModels
	 */
	private void setupModels() {
		activeModel = new DefaultListModel<>();
		reserveModel = new DefaultListModel<>();
		inventoryModel = new DefaultListModel<>();
		activeModel.addAll(game.getActiveTeam());
		reserveModel.addAll(game.getReservesTeam());
		inventoryModel.addAll(game.getInventory().keySet());
	}

	/**
	 * UPdate the illustration on both active team list and reserve team list
	 */
	private void updateLists() {
		activeModel.clear();
		reserveModel.clear();
		reserveModel.addAll(game.getReservesTeam());
		activeModel.addAll(game.getActiveTeam());
		activeTeamList.setModel(activeModel);
		reserveTeamList.setModel(reserveModel);
	}
}
