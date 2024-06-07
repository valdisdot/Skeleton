package com.valdisdot.skeleton.OLD.ui.gui.component;

import javax.swing.*;
import java.util.Objects;
import java.util.function.Supplier;

public class ControlButton implements Supplier<JButton> {
    private final JButton button;

    public ControlButton(String name, JButton button, Runnable dataController) {
        this.button = Objects.requireNonNull(button);
        this.button.setName(Objects.requireNonNull(name, "Control button name can not be null"));
        if (Objects.nonNull(dataController)) this.button.addActionListener(l -> dataController.run());
        button.setFocusable(false);
    }

    public ControlButton(String name, String buttonLabel, Runnable dataController) {
        this(name, new JButton(buttonLabel), dataController);
    }

    public String getName() {
        return button.getName();
    }

    @Override
    public JButton get() {
        return button;
    }
}
