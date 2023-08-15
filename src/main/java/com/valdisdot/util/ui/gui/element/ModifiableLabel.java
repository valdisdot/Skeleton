package com.valdisdot.util.ui.gui.element;

import com.valdisdot.util.data.DataCell;

import javax.swing.*;

//class for cases where JLabel can be changed on-demand
public class ModifiableLabel extends JElement<String> {
    public ModifiableLabel(String name, JLabel label) {
        label.setName(name);
        component = label;
        dataCell = new DataCell<>(label::getText, label::setText);
    }
}
