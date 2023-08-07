package com.valdisdot.util.ui.gui.component;

import com.valdisdot.util.data.controller.DataController;

import javax.swing.*;
import java.util.Objects;
import java.util.function.Supplier;

public class ControlButton implements Supplier<JComponent> {
    private final JButton button;

    public ControlButton(JButton button, DataController dataController) {
        this.button = Objects.requireNonNull(button);
        Objects.requireNonNull(dataController);
        this.button.addActionListener(l -> dataController.process());
    }

    public ControlButton(String buttonLabel, DataController dataController) {
        this(new JButton(buttonLabel), dataController);
    }

    @Override
    public JComponent get() {
        return button;
    }
}
