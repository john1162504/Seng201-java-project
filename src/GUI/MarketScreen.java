package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

import Cores.Athlete;
import Cores.GameEnvironment;
import Cores.Item;
import Cores.Purchasable;
/**
 *Class that models a graphical user interface for market 
 *
 */
public class MarketScreen extends Screen {
	// ListModel that models purchasable sell information
	private DefaultListModel<String> purchasableModel;
	// ListModel that models purchasable object own by user sell information
	private DefaultListModel<String> sellableModel;
	// JList that models purchasable sell information
	private JList<String> purchasablesList;
	// JList that models purchasable object own by user sell information
	private JList<String> sellableList;
	// List that contains purchasable object own by user sell information
	ArrayList<Purchasable> sellable;
	// List that contain all purchasable objects in market
	ArrayList<Purchasable> purchasable;
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
	// Labels that illustrate how much money user has
	private JLabel moneyLabel;

	/**
	 * Create the application.
	 */
	public MarketScreen(GameEnvironment game) {
		super(game);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setupFrame();
		addbackButton();
		addLabels();
		initiateModels();
		addLists();
		addPurchaseButton();
		addSellButton();
	}

	/**
	 * Setup this frame
	 */
	private void setupFrame() {
		frame = new JFrame();
		frame.setTitle("Market");
		frame.setBounds(100, 100, 750, 500);
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}

	/**
	 * Add sell button to the frame sell button allows user to sell a selected
	 * purchasable object
	 */
	private void addSellButton() {
		JButton sellButton = new JButton("Sell");
		sellButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int selectedIndex = sellableList.getSelectedIndex();
					Purchasable selected = sellable.get(selectedIndex);
					String feedback = (selected instanceof Item ? game.sellItem(selectedIndex)
							: game.sellAthlete((Athlete) selected));
					JOptionPane.showMessageDialog(frame, feedback);
					updateInterface();
				} catch (Exception error) {
					showError(error.getMessage());
				}
			}
		});
		sellButton.setBounds(377, 278, 117, 29);
		frame.getContentPane().add(sellButton);
	}

	/**
	 * Add purchase button to the frame purchase button allow user to purchase a
	 * object thats on the market
	 */
	private void addPurchaseButton() {
		JButton purchaseButton = new JButton("Purchase");
		purchaseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int selected = purchasablesList.getSelectedIndex();
					String feedback = game.buyPurchasable(selected);
					JOptionPane.showMessageDialog(frame, feedback);
					updateInterface();
				} catch (Exception error) {
					showError(error.getMessage());
				}
			}
		});
		purchaseButton.setBounds(377, 12, 117, 29);
		frame.getContentPane().add(purchaseButton);
	}

	/**
	 * Add the back button to the frame back button can take user back to main
	 * screen
	 */
	private void addbackButton() {
		JButton backButton = new JButton("Go Back");
		backButton.setBounds(621, 431, 117, 29);
		backButton.addActionListener(e -> game.launchMain());
		frame.getContentPane().add(backButton);
	}

	/**
	 * Add purchasablesList and sellableList to the frame these two list purpose is
	 * to display contain object sell information
	 */
	private void addLists() {
		purchasablesList = new JList<>(purchasableModel);
		purchasablesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		purchasablesList.setVisibleRowCount(6);
		purchasablesList.setBounds(6, 46, 488, 187);
		frame.getContentPane().add(purchasablesList);

		sellableList = new JList<>(sellableModel);
		sellableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		sellableList.setBounds(6, 312, 488, 132);
		frame.getContentPane().add(sellableList);
	}

	/**
	 * Add all the information labels to the frame
	 */
	private void addLabels() {
		moneyLabel = new JLabel("You have " + game.getMoney() + "$");
		moneyLabel.setBounds(594, 12, 128, 16);
		frame.getContentPane().add(moneyLabel);

		JLabel shopLabel = new JLabel("Market's goods");
		shopLabel.setBounds(6, 18, 178, 16);
		frame.getContentPane().add(shopLabel);

		JLabel goodsLabel = new JLabel("Your goods");
		goodsLabel.setBounds(12, 284, 142, 16);
		frame.getContentPane().add(goodsLabel);

		first = game.getInventory().keySet().toArray()[0];
		firstLabel = new JLabel("You have " + game.getInventory().get(first));
		firstLabel.setBounds(512, 307, 168, 16);
		frame.getContentPane().add(firstLabel);

		second = game.getInventory().keySet().toArray()[1];
		secondLabel = new JLabel("You have " + game.getInventory().get(second));
		secondLabel.setBounds(512, 323, 168, 16);
		frame.getContentPane().add(secondLabel);

		third = game.getInventory().keySet().toArray()[2];
		thirdLabel = new JLabel("You have " + game.getInventory().get(third));
		thirdLabel.setBounds(512, 337, 136, 16);
		frame.getContentPane().add(thirdLabel);
	}

	/**
	 * Update the contents of sellableModel and purchasableModel
	 */
	private void updateModel() {
		sellable = game.getSellable();
		purchasable = game.getPurchasables();
		int i = 0;
		for (Purchasable object : sellable) {
			sellableModel.add(i, object.getSellInfo());
			i++;
		}
		int j = 0;
		for (Purchasable object : purchasable) {
			purchasableModel.add(j, object.getBuyInfo());
			j++;
		}
	}

	/**
	 * Initialize List models
	 */
	private void initiateModels() {
		purchasableModel = new DefaultListModel<>();
		sellableModel = new DefaultListModel<>();
		updateModel();
	}

	/**
	 * Update all dynamic components in this frame
	 */
	private void updateInterface() {
		purchasableModel.clear();
		sellableModel.clear();
		updateModel();
		refreshLabels();
		purchasablesList.setModel(purchasableModel);
		sellableList.setModel(sellableModel);

	}

	/**
	 * Refresh all dynamic labels in this frame
	 */
	private void refreshLabels() {
		firstLabel.setText("You Have " + game.getInventory().get(first));
		secondLabel.setText("You Have " + game.getInventory().get(second));
		thirdLabel.setText("You Have " + game.getInventory().get(third));
		moneyLabel.setText("You have " + game.getMoney() + "$");
	}

}
