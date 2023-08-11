package com.valdisdot.util.ui.gui.element;

import com.valdisdot.util.data.DataCell;

import javax.swing.*;

//class for cases where JLabel can be changed dynamically
public class ModifiableLabel extends JElement<String> {
    public ModifiableLabel(String name, JLabel label) {
        label.setName(name);
        completeInitialization(
                label,
                new DataCell<>(label::getText, label::setText)
        );
    }

    public ModifiableLabel(String name) {
        this(name, new JLabel());
    }

    @Override
    protected boolean pleaseAcceptThatYouHaveDone() {
        return true;
    }
}
