package com.valdisdot.util.ui.gui.element;

import javax.swing.*;

//class wraps a JTextArea into a JScrollPane
//it is useful when user inputs a lot of text into text area -> auto scrolling
public class ScrollableTextArea extends TextInputElement {
    private final JScrollPane scrollPane;

    public ScrollableTextArea(String name, JTextArea textField) {
        super(name, textField);
        this.scrollPane = new JScrollPane(super.get());
    }

    @Override
    public JComponent get() {
        return scrollPane;
    }
}
