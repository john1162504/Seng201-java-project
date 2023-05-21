package GUI;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;

/**
 * A {@link JTable} whose column widths are adjusted to fit the column header
 * text without truncation.
 */
public class CustomWidthJTable extends JTable {

    /**
     * Creates a custom width JTable with the given table model. After construction
     * the columns widths will be adjusted if necessary to ensure header text is not
     * truncated.
     *
     * @param dm The data model for the table
     * @see JTable#JTable(TableModel)
     */
    public CustomWidthJTable(TableModel dm) {
        super(dm);
        setColumnWidths();
    }

    /**
     * Sets the width of this table's columns so that no header text is truncated.
     * By default, the width of a {@link TableColumn} is 75 and all columns are given
     * the same width. If a column's header text requires more space than the default
     * of 75, this method sets the preferred width of that column to the width required
     * to display the text without truncation.
     * This method is automatically called after JTable construction.
     *
     * @see TableColumn#getWidth()
     */
    public void setColumnWidths() {
        final TableColumnModel columnModel = getColumnModel();
        final TableCellRenderer headerRenderer = getTableHeader().getDefaultRenderer();
        final int columnCount = columnModel.getColumnCount();

        for (int col = 0; col < columnCount; col++) {
            TableColumn column = columnModel.getColumn(col);
            Component comp = headerRenderer.getTableCellRendererComponent(
                    null, column.getHeaderValue(),
                    false, false, 0, 0);

            column.setPreferredWidth(Math.max(comp.getPreferredSize().width, column.getPreferredWidth()));
        }
    }
}
