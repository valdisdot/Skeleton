package com.valdisdot.util.ui.gui.element;

import com.valdisdot.util.data.DataCell;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

//the class uses the same logic as for CheckBox class
public class ContentButton extends AbstractElement<String> {
    private boolean wasPressed;

    public ContentButton(String name, JButton button, String valueIfWasPressed, String valueIfWasNotPressed) {
        button.setName(name);
        Supplier<String> supplierFunction = () -> wasPressed ? valueIfWasPressed : valueIfWasNotPressed;
        Consumer<String> consumerFunction = (value) -> wasPressed = !wasPressed;
        button.addActionListener(l -> wasPressed = !wasPressed);
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
