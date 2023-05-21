package GUI;

import Cores.Athlete;

import javax.swing.table.AbstractTableModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Defines a table model for displaying {@link Rocket}s in a {@link javax.swing.JTable}.
 */
class AthleteTableModel extends AbstractTableModel implements PropertyChangeListener {

    // Names for the table columns
    private final static String[] columns = {"Name", "Attack", "Defence", "Stamina", "Max Stamina", "Status", "Price", "Worth"};

    // Table column indexes
    static final int COL_NAME = 0;
    static final int COL_ATTACK = 1;
    static final int COL_DEFENCE = 2;
    static final int COL_STAMINA = 3;
    static final int COL_MAX_STAMINA = 4;
    static final int COL_STATUS = 5;
    static final int COL_PRICE = 6;
    static final int COL_WORTH = 7;
    // The data for this model
    private final ArrayList<Athlete> athletes;

    /**
     * Creates a RocketTableModel with the given list of Rockets.
     *
     * @param rockets The dataset for this model
     */
    public AthleteTableModel(ArrayList<Athlete> athletes) {
        this.athletes = athletes;

        // Make this model receive status updates for each rocket
        for (Athlete athlete : athletes) {
            athlete.addListener(this);
        }
    }

    public String getColumnName(int col) {
        return columns[col];
    }

    public int getRowCount() { return athletes.size(); }

    public int getColumnCount() { return columns.length; }

    public Object getValueAt(int row, int col) {
        Athlete athlete = athletes.get(row);
        switch (col) {
            case COL_NAME:
                return athlete.getName();
            case COL_ATTACK:
                return athlete.getAttack();
            case COL_DEFENCE:
                return athlete.getDefence();
            case COL_STAMINA:
                return athlete.getCurrentStamina();
            case COL_MAX_STAMINA:
                return athlete.getMaxStamina();
            case COL_STATUS:
            	return athlete.getStatus();
            case COL_PRICE:
            	return athlete.getPrice();
            case COL_WORTH:
            	return athlete.getWorth();
            default:
                throw new IllegalStateException("No column defined for index " + col);
        }
    }

    /**
     * Gets the list of currently selected rockets.
     *
     * @param selectedRows An array containing the currently selected rows
     * @return The Rockets corresponding to the given selected rows
     */
    protected ArrayList<Athlete> getSelectedAthletes(int[] selectedRows) {
        ArrayList<Athlete> list = new ArrayList<>();
        for (int row : selectedRows) {
            list.add(athletes.get(row));
        }
        return list;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final Athlete athlete = (Athlete) evt.getSource();
        int index = athletes.indexOf(athlete);
        if (index != -1) {
            // Notify listeners that the athlete at index has changed.
            fireTableRowsUpdated(index, index);
        }
    }
}
