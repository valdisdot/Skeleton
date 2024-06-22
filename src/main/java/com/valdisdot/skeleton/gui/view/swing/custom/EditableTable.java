package com.valdisdot.skeleton.gui.view.swing.custom;

import com.valdisdot.skeleton.gui.view.swing.JElement;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A generic editable table that can display and edit data of type {@code Entity}.
 * @param <Entity> the type of entities to be displayed in the table
 * @since 1.0
 * @author Vladyslav Tymchenko
 */
public class EditableTable<Entity> {
    private final LinkedHashMap<String, BiConsumer<Entity, String>> columnSetters;
    private final LinkedHashMap<String, Function<Entity, String>> columnGetters;
    private List<? extends Entity> entityInRow;
    private final List<String> head;

    private Color background = Color.WHITE;
    private Color foreground = Color.BLACK;
    private Font font = new JLabel().getFont();

    /**
     * Constructs an empty editable table.
     */
    public EditableTable() {
        columnSetters = new LinkedHashMap<>();
        columnGetters = new LinkedHashMap<>();
        entityInRow = new LinkedList<>();
        head = new LinkedList<>();
    }

    /**
     * Binds a column to getter and setter functions for the specified column name.
     * @param columnName the name of the column
     * @param getter     the function to get the value of the column from an entity
     * @param setter     the function to set the value of the column to an entity
     */
    public void bindField(String columnName, Function<Entity, String> getter, BiConsumer<Entity, String> setter) {
        head.add(Objects.requireNonNull(columnName, "Column name is null"));
        columnGetters.put(columnName, Objects.requireNonNull(getter, "Getter function is null"));
        columnSetters.put(columnName, Objects.requireNonNullElse(setter, (e, s) -> {
        }));
    }

    /**
     * Sets the collection of entities to be displayed in the table.
     * @param entities the collection of entities
     */
    public void setEntities(Collection<? extends Entity> entities) {
        entityInRow = new ArrayList<>(entities);
    }

    /**
     * Sets the background color of the table.
     * @param background the background color
     */
    public void setBackground(Color background) {
        this.background = background;
    }

    /**
     * Sets the foreground color of the table.
     * @param foreground the foreground color
     */
    public void setForeground(Color foreground) {
        this.foreground = foreground;
    }

    /**
     * Sets the font of the table.
     * @param font the font
     */
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * Displays the table in a new window with the specified title and icon.
     * @param displayTitleSupplier a supplier for the window title
     * @param displayIcon          the window icon
     */
    public void show(Supplier<String> displayTitleSupplier, Image displayIcon) {
        Objects.requireNonNull(displayTitleSupplier, "Display title supplier is null");
        JFrame frame = new JFrame(displayTitleSupplier.get());
        frame.setIconImage(displayIcon);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        JComponent view = getView();
        if (Toolkit.getDefaultToolkit().getScreenSize().height * 0.8 <= view.getPreferredSize().height) {
            JScrollPane pane = new JScrollPane(view, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            pane.setWheelScrollingEnabled(true);
            pane.setPreferredSize(new Dimension(view.getPreferredSize().width + 18, (int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.8)));
            view = pane;
        }
        frame.add(view);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Gets the view component of the table for a custom usage.
     * @return the view component
     */
    public JComponent getView() {
        return new TablePanel().getComponent();
    }

    /**
     * A panel that represents the table and listens for changes to the table model.
     */
    private class TablePanel extends JElement implements TableModelListener {
        private final TableModel model;


        /**
         * Constructs a TablePanel instance.
         */
        TablePanel() {
            Vector<Vector<String>> tableData = new Vector<>();
            Vector<String> row;
            for (Entity entity : entityInRow) {
                row = new Vector<>();
                for (String columnName : head) {
                    row.add(columnGetters.get(columnName).apply(entity));
                }
                tableData.add(row);
            }
            model = new DefaultTableModel(tableData, new Vector<>(head));
            model.addTableModelListener(this);
            JTable table = new JTable(model);
            table.setName("table_" + UUID.randomUUID());
            table.setCellSelectionEnabled(true);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            int width = Toolkit.getDefaultToolkit().getScreenSize().width * 2 / 3;
            List<Integer> columnContentSizes = new ArrayList<>(head.size());
            int sumColumnContentSizes = 0;
            //quick-and-dirty way to count a relational width
            {
                int s;
                int max = width / head.size();
                for (String columnName : head) {
                    s = Math.min(new JLabel(columnName).getPreferredSize().width, max);
                    columnContentSizes.add(s);
                    sumColumnContentSizes += s;
                }
            }

            for (int i = 0; i < columnContentSizes.size(); ++i) {
                table.getColumnModel().getColumn(i).setPreferredWidth(columnContentSizes.get(i) * width / sumColumnContentSizes);
            }
            table.setPreferredScrollableViewportSize(table.getPreferredSize());
            addPart(table);
            addPart(table.getTableHeader());
            JScrollPane pane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            pane.setBorder(null);
            pane.setName(table.getName());
            setComponent(pane);

            for (JComponent component : getInternal()) {
                component.setBackground(background);
                component.setForeground(foreground);
                component.setFont(font);
            }
        }

        @Override
        public void tableChanged(TableModelEvent e) {
            columnSetters
                    .get(head.get(e.getColumn()))
                    .accept(
                            entityInRow.get(e.getFirstRow()),
                            model.getValueAt(e.getFirstRow(), e.getColumn()).toString()
                    );
        }
    }
}
