package GUI;

import java.awt.EventQueue;


import javax.swing.JFrame;

import Cores.GameEnvironment;
import Cores.Item;
import Cores.Purchasable;


import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;


public class MarketScreen extends Screen{

//	private JFrame frame;
	
	private DefaultListModel<String> purchasableModel;
	private DefaultListModel<String> sellableModel;

	private JList<String> purchasablesList;
	private JList<String> sellableList;
	
	ArrayList<Purchasable> sellable;
	ArrayList<Purchasable> purchasable;
	
	
	private JLabel firstLabel;
	private JLabel secondLabel;
	private JLabel thirdLabel;
	private Item first;
	private Item second;
	private Item third;
	private JLabel moneyLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameEnvironment game = new GameEnvironment();
					MarketScreen window = new MarketScreen(game);
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
	public MarketScreen(GameEnvironment game) {
		super(game);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Market");
		frame.setBounds(100, 100, 610, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton backButton = new JButton("Go Back");
		backButton.setBounds(487, 337, 117, 29);
		backButton.addActionListener(e -> game.launchMain());
		frame.getContentPane().add(backButton);
		
		moneyLabel = new JLabel("You have " + game.getMoney() + "$");
		moneyLabel.setBounds(476, 6, 128, 16);
		frame.getContentPane().add(moneyLabel);
		
		purchasableModel = new DefaultListModel<String>();
		sellableModel = new DefaultListModel<String>();
		
		sellable = game.getSellable();
		purchasable = game.getPurchasables();
		int i = 0;
		for (Purchasable object: sellable) {
			sellableModel.add(i, object.getSellInfo());
			i++;
		}
		i = 0;
		for (Purchasable object: purchasable) {
			purchasableModel.add(i, object.getBuyInfo());
		}

		purchasablesList = new JList<String>(purchasableModel);
		purchasablesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		purchasablesList.setVisibleRowCount(6);
		purchasablesList.setBounds(6, 46, 355, 112);
		frame.getContentPane().add(purchasablesList);
		
		sellableList = new JList<String>(sellableModel);
		sellableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		sellableList.setBounds(6, 217, 390, 134);
		frame.getContentPane().add(sellableList);
		
		
		first = (Item) game.getInventory().keySet().toArray()[0];
		firstLabel = new JLabel("You have " + game.getInventory().get(first));
		firstLabel.setBounds(420, 217, 168, 16);
		frame.getContentPane().add(firstLabel);
		
		second = (Item) game.getInventory().keySet().toArray()[1];
		secondLabel = new JLabel("You have " + game.getInventory().get(second));
		secondLabel.setBounds(420, 231, 168, 16);
		frame.getContentPane().add(secondLabel);
		
		third = (Item) game.getInventory().keySet().toArray()[2];
		thirdLabel = new JLabel("You have " + game.getInventory().get(third));
		thirdLabel.setBounds(420, 245, 136, 16);
		frame.getContentPane().add(thirdLabel);
		
		JLabel shopLabel = new JLabel("Market's goods");
		shopLabel.setBounds(6, 18, 178, 16);
		frame.getContentPane().add(shopLabel);
		
		JLabel goodsLabel = new JLabel("Your goods");
		goodsLabel.setBounds(6, 189, 142, 16);
		frame.getContentPane().add(goodsLabel);
		
		JButton purchaseButton = new JButton("Purchase");
		purchaseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selected = purchasablesList.getSelectedIndex();
				String feedback = game.buyPurchasable(selected);
				JOptionPane.showMessageDialog(frame, feedback);
				updateInterface();
			}
		});
		purchaseButton.setBounds(113, 13, 117, 29);
		frame.getContentPane().add(purchaseButton);
		
		JButton sellButton = new JButton("Sell");
		sellButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		sellButton.setBounds(113, 184, 117, 29);
		frame.getContentPane().add(sellButton);
	}
	
	private void updateInterface() {
		purchasableModel.clear();
		sellableModel.clear();
		
//		purchasableModel.addAll(game.getPurchasables());
//		sellableModel.addAll(game.getInventory().keySet());
//		sellableModel.addAll(game.getReservesTeam());
//		
		purchasablesList.setModel(purchasableModel);
		sellableList.setModel(sellableModel);
		
		firstLabel.setText("You Have " +  game.getInventory().get(first));
		secondLabel.setText("You Have " +  game.getInventory().get(second));
		thirdLabel.setText("You Have " +  game.getInventory().get(third));
		moneyLabel.setText("You have " + game.getMoney() + "$");


		
	}

}
