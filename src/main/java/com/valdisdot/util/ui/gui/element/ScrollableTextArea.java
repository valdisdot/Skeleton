package com.valdisdot.util.ui.gui.element;

import com.valdisdot.util.ui.gui.tool.Colors;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

//class wraps a JTextArea into a JScrollPane
//it is useful when user inputs a lot of text into text area -> auto scrolling
public class ScrollableTextArea extends TextInputElement {
    private final JScrollPane scrollPane;

    public ScrollableTextArea(String name, JTextArea textField) {
        super(name, textField);
        scrollPane = new JScrollPane(super.get(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setName(name);
        scrollPane.setWheelScrollingEnabled(true);
        JScrollBar horizontal = scrollPane.getHorizontalScrollBar();
        JScrollBar vertical = scrollPane.getVerticalScrollBar();
        Color background = Colors.isDark(textField.getBackground().getRGB()) ? textField.getBackground().brighter() : textField.getBackground().darker();
        horizontal.setPreferredSize(new Dimension(10, 7));
        horizontal.setBackground(background);
        horizontal.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor =
                        Colors.isDark(textField.getBackground().getRGB()) ?
                                textField.getBackground().brighter().brighter() :
                                textField.getBackground().darker().darker();
            }


        });
        vertical.setPreferredSize(new Dimension(7, 10));
        vertical.setBackground(background);
        vertical.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Colors.isDark(textField.getBackground().getRGB()) ?
                        textField.getBackground().brighter().brighter() :
                        textField.getBackground().darker().darker();
            }
        });
        component = scrollPane;
    }
}
