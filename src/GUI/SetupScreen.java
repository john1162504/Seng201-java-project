package GUI;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableColumnModel;

import Cores.Athlete;
import Cores.GameEnvironment;
import GUI.GridBagConstraintsBuilder;
import UI.GameEnvironmentUi;
import UI.CmdLineUi;
import UI.CmdLineUi.Difficulty;

//import seng201.rocketmanager.ui.gui.CustomWidthJTable;
//import seng201.rocketmanager.ui.gui.RocketTableModel;
//import seng201.rocketmanager.ui.gui.SetupScreen.RocketTableSelectionModel;

import javax.swing.JLabel;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JCheckBox;
import javax.swing.JSlider;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class SetupScreen extends Screen{
	
	private GameEnvironment game;
	private ArrayList<Athlete> athletes;
	private Difficulty difficulty;

	private JFrame frmSetUp;
	String name;
	private JTextField fieldName;
	private JRadioButton buttonDifficulty;
	private JSlider lengthSlider;
	private JLabel lblError;
	private JButton btnAccept;
	private JTable table;
	private JLabel athleteLabel;


	/**
	 * Create the application.
	 */
	public SetupScreen(GameEnvironment game) {
		super("Set up", game);
		
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	@Override
	protected void initialise(final Container container) {
		
		container.setLayout(new GridBagLayout());
		this.athletes = super.getGame().generateAthletes(6);
		final GridBagConstraintsBuilder layoutBuilder = new GridBagConstraintsBuilder();
		
		addLabels(container, layoutBuilder);
		addNameField(container, layoutBuilder);
		addDifficultyButton(container, layoutBuilder);
		addSlider(container, layoutBuilder);
		addAthleteList(container, layoutBuilder);
		addButtons(container, layoutBuilder);
	}
	
	private void setupComplete() {
		final AthleteTableModel model = (AthleteTableModel) table.getModel();
		final boolean hard = buttonDifficulty.isSelected();
		if(hard) {
			difficulty = Difficulty.HARD;
		}
		else {
			difficulty = Difficulty.NORMAL;
		}
		game.onSetupFinished(fieldName.getText(), lengthSlider.getValue(), model.getSelectedAthletes(table.getSelectedRows()), difficulty);
	}

	private void addAthleteList(Container container, GridBagConstraintsBuilder layoutBuilder) {
		
		final ArrayList<Athlete> startingAthletes = athletes;

		final AthleteTableModel model = new AthleteTableModel(startingAthletes);
		final AthleteTableSelectionModel selectionModel = new AthleteTableSelectionModel();

		table = new CustomWidthJTable(model) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			// Alter the table selection behaviour so that clicking on a row always toggles its selection.
			// See the Javadoc for changeSelection for a description of the toggle and extend parameters.
			@Override
			public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
				super.changeSelection(rowIndex, columnIndex, true, false);
			}
		};

		table.setRowSelectionAllowed(true);
		table.setShowHorizontalLines(false);
		table.setShowVerticalLines(false);
		table.setSelectionModel(selectionModel);

		// Remove the mission status columns from the view
		final TableColumnModel columnModel = table.getColumnModel();
		columnModel.removeColumn(columnModel.getColumn(table.convertColumnIndexToView(AthleteTableModel.COL_MAX_STAMINA)));
		columnModel.removeColumn(columnModel.getColumn(table.convertColumnIndexToView(AthleteTableModel.COL_STATUS)));
		columnModel.removeColumn(columnModel.getColumn(table.convertColumnIndexToView(AthleteTableModel.COL_PRICE)));
		columnModel.removeColumn(columnModel.getColumn(table.convertColumnIndexToView(AthleteTableModel.COL_WORTH)));

		selectionModel.addListSelectionListener(event -> checkCanContinue());

		final JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(scrollPane.getPreferredSize());

		layoutBuilder.setLocation(9, 0)
				.fill()
				.spanRemainingColumns()
				.setVerticalWeight(1);

		container.add(scrollPane, layoutBuilder.get());
	}
	
	private void addSlider(Container container, GridBagConstraintsBuilder layoutBuilder) {
		lengthSlider = new JSlider();
		lengthSlider.setMajorTickSpacing(5);
		lengthSlider.setMinorTickSpacing(1);
		lengthSlider.setSnapToTicks(true);
		lengthSlider.setPaintLabels(true);
		lengthSlider.setPaintTicks(true);
		lengthSlider.setMinimum(5);
		lengthSlider.setMaximum(15);
		layoutBuilder.setLocation(5,1)
        			.spanRemainingColumns()
        			.fillHorizontal()
        			.setHorizontalWeight(1)
        			.setInsets(0, 10, 10, 10);
		
		container.add(lengthSlider, layoutBuilder.get());

	}
	

	private void addNameField(Container container, GridBagConstraintsBuilder layoutBuilder) {
		// Create a label to describe valid input for the name field
		lblError = new JLabel();
		lblError.setText(GameEnvironmentUi.NAME_REQUIRMENTS);
		lblError.setForeground(Color.RED);
		lblError.setFont(lblError.getFont().deriveFont(10f));
		// Explicitly set the preferred size of the label so that when the user has
		// entered a valid name, setting the text to null does not cause the label to
		// be resized, which would result in other components also being resized.
		lblError.setPreferredSize(lblError.getPreferredSize());

		layoutBuilder.setLocation(2,1)
		            .spanRemainingColumns()
					.fillHorizontal()
					.setHorizontalWeight(1)
					.setInsets(0, 10, 10, 10);

		container.add(lblError, layoutBuilder.get());

		fieldName = new JTextField();
		fieldName.setToolTipText("Your name");

		layoutBuilder.setLocation(1,1)
					.setHorizontalWeight(1)
					.spanRemainingColumns()
					.fillHorizontal();

		container.add(fieldName, layoutBuilder.get());

		// Add a document listener to detect when the user enters text into the field so
		// that we can check if we can enable the accept button.
		fieldName.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				checkCanContinue();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				checkCanContinue();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				checkCanContinue();
			}
		});
	}

	private void addDifficultyButton(Container container, GridBagConstraintsBuilder layoutBuilder) {
		buttonDifficulty = new JRadioButton("Select to increase difficulty");
		

		layoutBuilder.setLocation(3,1)
					.setHorizontalWeight(1)
					.spanRemainingColumns()
					.fillHorizontal();

		container.add(buttonDifficulty, layoutBuilder.get());
		
		// Add a document listener to detect when the user enters text into the field so
		// that we can check if we can enable the accept button.
		fieldName.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				checkCanContinue();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				checkCanContinue();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				checkCanContinue();
			}
		});
	}

	
	private void addLabels(Container container, GridBagConstraintsBuilder layoutBuilder) {
		JLabel titleLabel = new JLabel("Welcome to SUMO AGENT");
		titleLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		layoutBuilder.setLocation(0, 0)
		.spanRemainingColumns()
		.setHorizontalWeight(1);

		container.add(titleLabel, layoutBuilder.get());
		
		
		JLabel enterNameLabel = new JLabel("What's the name of your team?");
		layoutBuilder.setLocation(1, 0)
					.anchorLineStart();
		
		container.add(enterNameLabel, layoutBuilder.get());
		
		
		JLabel difficultyLabel = new JLabel("Would you like to increase the difficulty?");
		layoutBuilder.setLocation(3, 0)
					.anchorLineStart();

		container.add(difficultyLabel, layoutBuilder.get());
		
		JLabel lengthLabel = new JLabel("How long would your tournament be?");
		layoutBuilder.setLocation(5, 0)
					.anchorLineStart();

		container.add(lengthLabel, layoutBuilder.get());
		
		JLabel athleteLabel = new JLabel("Select your 4 starting athletes");
		layoutBuilder.setLocation(7, 0)
					.anchorLineStart();

		container.add(athleteLabel, layoutBuilder.get());
	}

	private void addButtons(Container container, GridBagConstraintsBuilder layoutBuilder) {
		btnAccept = new JButton("Accept");
		btnAccept.setEnabled(false);
		btnAccept.addActionListener(e -> setupComplete());

		layoutBuilder.setLocation(11,1)
				.anchorLineEnd();

		container.add(btnAccept, layoutBuilder.get());
	}
	
	public Color getLengthSliderBackground() {
		return lengthSlider.getBackground();
	}
	
	public void setLengthSliderBackground(Color background) {
		lengthSlider.setBackground(background);
	
	}
	
	/**
	 * Enables the accept button if the user has selected one or more rockets and has entered
	 * a valid name. Otherwise disables the accept button.
	 */
	private void checkCanContinue() {
		boolean validName = fieldName.getText().matches(GameEnvironmentUi.NAME_REGEX);
		// Hide the name requirements text if the input is valid
		lblError.setText(validName ? null : GameEnvironmentUi.NAME_REQUIRMENTS);

		btnAccept.setEnabled(validName && table.getSelectedRowCount() == 4);
	}
	
	/**
	 * Defines the selection model for selecting {@link Rocket}s from this screen's rocket table
	 */
	private class AthleteTableSelectionModel extends DefaultListSelectionModel {

		public static final String ERROR_MSG = "You can only select up to " + GameEnvironment.MAX_ACTIVE_TEAM_SIZE + " athletes";

		public AthleteTableSelectionModel() {
			super();
		}

		/**
		 * Overrides {@link DefaultListSelectionModel#setSelectionInterval} to prevent the user from selecting more than
		 * {@link RocketManager#MAX_ROCKETS} rockets.
		 *
		 * @param index0 One end of the selection interval
		 * @param index1 The other end of the selection interval
		 */
		@Override
		public void setSelectionInterval(int index0, int index1) {
			if (table.getSelectedRowCount() >= GameEnvironment.MAX_ACTIVE_TEAM_SIZE) {
				JOptionPane.showMessageDialog(getParentComponent(), ERROR_MSG);
				return;
			}
			super.setSelectionInterval(index0, index1);
		}

		/**
		 * Overrides {@link DefaultListSelectionModel#addSelectionInterval} to prevent the user from selecting more than
		 * {@link RocketManager#MAX_ROCKETS} rockets.
		 *
		 * @param index0 One end of the selection interval
		 * @param index1 The other end of the selection interval
		 */
		@Override
		public void addSelectionInterval(int index0, int index1) {
			if (table.getSelectedRowCount() >= GameEnvironment.MAX_ACTIVE_TEAM_SIZE) {
				JOptionPane.showMessageDialog(getParentComponent(), ERROR_MSG);
				return;
			}
			super.addSelectionInterval(index0, index1);
		}
	}
}
