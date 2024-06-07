package com.valdisdot.skeleton.OLD.ui.gui.element;

import com.valdisdot.skeleton.OLD.data.DataCell;

import javax.swing.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

//implementation for JCheckBox
public class CheckBox extends JElement<String> {
    public CheckBox(String name, JCheckBox checkBox, String valueIfSelected, String valueIfNotSelected) {
        checkBox.setName(name);
        Supplier<String> supplierFunction = () -> checkBox.isSelected() ? valueIfSelected : valueIfNotSelected;
        //any String will work as a switcher
        Consumer<String> consumerFunction = (value) -> checkBox.setSelected(!checkBox.isSelected());
        component = checkBox;
        dataCell = new DataCell<>(
                supplierFunction,
                consumerFunction
        );
    }

    public CheckBox(String name, String title, boolean isSelected, String valueIfSelected, String valueIfNotSelected) {
        this(name, new JCheckBox(title, isSelected), valueIfSelected, valueIfNotSelected);
    }
}
