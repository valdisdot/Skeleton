package com.valdisdot.util.ui.gui.element;

import com.valdisdot.util.data.DataCell;

import javax.swing.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CheckBox extends AbstractElement<String> {
    public CheckBox(String name, JCheckBox checkBox, String valueIfSelected, String valueIfNotSelected){
        checkBox.setName(name);

        Supplier<String> supplierFunction = () -> checkBox.isSelected() ? valueIfSelected : valueIfNotSelected;
        //any String will work as a switcher
        Consumer<String> consumerFunction = (value) -> checkBox.setSelected(!checkBox.isSelected());

        completeInitialization(
                checkBox,
                new DataCell<>(
                        supplierFunction,
                        consumerFunction
                )
        );
    }

    public CheckBox(String name, String displayText, boolean isSelected, String valueIfSelected, String valueIfNotSelected){
        this(name, new JCheckBox(displayText, isSelected), valueIfSelected, valueIfNotSelected);
    }
}
