package GUI;

import java.awt.*;

/**
 * A builder for configuring a {@link GridBagConstraints}.
 */
public class GridBagConstraintsBuilder {
    private static final Insets INSETS = new Insets(10, 10, 10, 10);

    private GridBagConstraints constraints;

    /**
     * Creates a {@link GridBagConstraints} builder with default insets of 10 all round.
     */
    public GridBagConstraintsBuilder() {
        constraints = new GridBagConstraints();
        setDefaults();
    }

    /**
     * Gets the configured {@link GridBagConstraints} and initialises this builder ready
     * to build another.
     *
     * @return The {@link GridBagConstraints} as configured via this builder.
     */
    public GridBagConstraints get() {
        GridBagConstraints result = constraints;
        constraints = new GridBagConstraints();
        setDefaults();
        return result;
    }

    /**
     * Sets the default grid bag constraint options for this builder, which is
     * insets of 10 all round.
     */
    private void setDefaults() {
        constraints.insets = INSETS;
    }

    /**
     * Sets the position of the component in the layout.
     *
     * @param row The row in which to place the component
     * @param column The column in which to place the component
     * @return This builder
     */
    public GridBagConstraintsBuilder setLocation(int row, int column) {
        constraints.gridx = column;
        constraints.gridy = row;
        return this;
    }

    /**
     * Specifies the number cells the component should occupy.
     *
     * @param rowSpan    The number of rows to span
     * @param columnSpan The number of columns to span
     * @return This builder
     */
    public GridBagConstraintsBuilder setSpan(int rowSpan, int columnSpan) {
        constraints.gridwidth = columnSpan;
        constraints.gridheight = rowSpan;
        return this;
    }

    /**
     * Specifies that the component should occupy all remaining columns following
     * the column in which it is placed
     * @return This builder
     */
    public GridBagConstraintsBuilder spanRemainingColumns() {
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        return this;
    }

    /**
     * Specifies the number of columns the component should occupy.
     *
     * @param columnSpan The number of columns to span
     * @return This builder
     */
    public GridBagConstraintsBuilder setColumnSpan(int columnSpan) {
        constraints.gridwidth = columnSpan;
        return this;
    }

    /**
     * Specifies the number of rows the component should occupy.
     *
     * @param rowSpan The number of rows to span
     * @return This builder
     */
    public GridBagConstraintsBuilder setRowSpan(int rowSpan) {
        constraints.gridheight = rowSpan;
        return this;
    }

    /**
     * Makes the component fill leftover horizontal and vertical space in the layout.
     *
     * @return This builder
     */
    public GridBagConstraintsBuilder fill() {
        constraints.fill = GridBagConstraints.BOTH;
        return this;
    }

    /**
     * Makes the component fill leftover horizontal space in the layout.
     *
     * @return This builder
     */
    public GridBagConstraintsBuilder fillHorizontal() {
        constraints.fill = GridBagConstraints.HORIZONTAL;
        return this;
    }

    /**
     * Makes the component fill leftover vertical space in the layout.
     *
     * @return This builder
     */
    public GridBagConstraintsBuilder fillVertical() {
        constraints.fill = GridBagConstraints.VERTICAL;
        return this;
    }

    /**
     * Specifies how to distribute extra space.
     *
     * @param weightx A weight as defined by {@link GridBagConstraints#weightx}
     * @param weighty A weight as defined by {@link GridBagConstraints#weighty}
     * @return This builder
     */
    public GridBagConstraintsBuilder setWeight(double weightx, double weighty) {
        constraints.weightx = weightx;
        constraints.weighty = weighty;
        return this;
    }

    /**
     * Sets how much extra horizontal space is given to the component.
     *
     * @param weight A weight as defined by {@link GridBagConstraints#weightx}
     * @return This builder
     */
    public GridBagConstraintsBuilder setHorizontalWeight(double weight) {
        constraints.weightx = weight;
        return this;
    }

    /**
     * Sets how much extra vertical space is given to the component.
     *
     * @param weight A weight as defined by {@link GridBagConstraints#weighty}
     * @return This builder
     */
    public GridBagConstraintsBuilder setVerticalWeight(double weight) {
        constraints.weighty = weight;
        return this;
    }

    /**
     * Creates and sets new insets with the specified values.
     *
     * @param top The inset from the top
     * @param left The inset from the left
     * @param bottom The inset from the bottom
     * @param right The inset from the right
     * @see GridBagConstraints#insets
     * @return This builder
     */
    public GridBagConstraintsBuilder setInsets(int top, int left, int bottom, int right) {
        constraints.insets = new Insets(top, left, bottom, right);
        return this;
    }

    /**
     * Aligns the component as defined by {@link GridBagConstraints#LINE_START}.
     *
     * @return This builder
     */
    public GridBagConstraintsBuilder anchorLineStart() {
        constraints.anchor = GridBagConstraints.LINE_START;
        return this;
    }

    /**
     * Aligns the component as defined by {@link GridBagConstraints#LINE_END}.
     *
     * @return This builder
     */
    public GridBagConstraintsBuilder anchorLineEnd() {
        constraints.anchor = GridBagConstraints.LINE_END;
        return this;
    }
}