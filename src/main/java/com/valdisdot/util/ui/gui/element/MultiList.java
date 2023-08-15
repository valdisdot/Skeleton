package com.valdisdot.util.ui.gui.element;

import com.valdisdot.util.data.DataCell;
import com.valdisdot.util.ui.gui.tool.Colors;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.util.List;
import java.util.Objects;

public class MultiList<D> extends JElement<List<D>> {
    private final JScrollPane scrollPane;

    public MultiList(String name, JList<D> list) {
        scrollPane = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setName(name);
        scrollPane.setWheelScrollingEnabled(true);
        JScrollBar horizontal = scrollPane.getHorizontalScrollBar();
        JScrollBar vertical = scrollPane.getVerticalScrollBar();
        Color background = Colors.isDark(list.getBackground().getRGB()) ? list.getBackground().brighter() : list.getBackground().darker();
        horizontal.setPreferredSize(new Dimension(10, 7));
        horizontal.setBackground(background);
        horizontal.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor =
                        Colors.isDark(list.getBackground().getRGB()) ?
                                list.getBackground().brighter().brighter() :
                                list.getBackground().darker().darker();
            }


        });
        vertical.setPreferredSize(new Dimension(7, 10));
        vertical.setBackground(background);
        vertical.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Colors.isDark(list.getBackground().getRGB()) ?
                        list.getBackground().brighter().brighter() :
                        list.getBackground().darker().darker();
            }
        });
        list.setName(Objects.requireNonNull(name));
        component = scrollPane;
        dataCell = new DataCell<>(
                list::getSelectedValuesList,
                (val) -> list.clearSelection()
        );
    }
}
