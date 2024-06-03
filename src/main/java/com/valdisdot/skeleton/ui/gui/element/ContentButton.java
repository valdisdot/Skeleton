package com.valdisdot.skeleton.ui.gui.element;

import com.valdisdot.skeleton.data.DataCell;
import com.valdisdot.skeleton.ui.gui.tool.Colors;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

//the class uses the same logic as for CheckBox class
public class ContentButton extends JElement<String> {
    private boolean wasPressed = false;

    public ContentButton(String name, JButton button, String valueIfWasPressed, String valueIfWasNotPressed) {
        button.setName(name);
        button.setFocusable(false);
        Color original = button.getBackground();
        Color clicked = new Color(Colors.isDark(original.getRGB()) ? original.getRGB() + 128 : original.getRGB() - 64);
        button.addActionListener(l -> {
            wasPressed = !wasPressed;
            if (wasPressed) button.setBackground(clicked);
            else button.setBackground(original);
        });

        Supplier<String> supplierFunction = () -> wasPressed ? valueIfWasPressed : valueIfWasNotPressed;
        Consumer<String> consumerFunction = (value) -> wasPressed = !wasPressed;
        component = button;
        dataCell = new DataCell<>(supplierFunction, consumerFunction);
    }
}
