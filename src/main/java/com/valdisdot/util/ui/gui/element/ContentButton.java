package com.valdisdot.util.ui.gui.element;

import com.valdisdot.util.data.DataCell;
import com.valdisdot.util.tool.Colors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Objects;
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
            if(wasPressed) button.setBackground(clicked);
            else button.setBackground(original);
        });

        Supplier<String> supplierFunction = () -> wasPressed ? valueIfWasPressed : valueIfWasNotPressed;
        Consumer<String> consumerFunction = (value) -> wasPressed = !wasPressed;
        completeInitialization(
                button,
                new DataCell<>(supplierFunction, consumerFunction)
        );
    }

    public ContentButton(String name, String buttonLabel, Collection<ActionListener> actionListeners, String valueIfWasPressed, String valueIfWasNotPressed) {
        this(
                name, //set name
                new JButton(buttonLabel) { //create custom anonymous JButton class
                    {
                        if (Objects.nonNull(actionListeners))
                            actionListeners.forEach(this::addActionListener); //add actions for this button with init-block
                    }
                },
                valueIfWasPressed, //->|
                valueIfWasNotPressed); //|<-
    }

    @Override
    protected final boolean pleaseAcceptThatYouHaveDone() {
        return true;
    }
}
