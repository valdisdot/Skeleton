package com.valdisdot.util.ui.gui.element;

import com.valdisdot.util.data.DataCell;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Vector;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

//implementation for JComboBox
public class ComboList extends JElement<String> {

    public ComboList(
            String name,
            JComboBox<String> comboBox,
            //user has to define the behaviour if DataCell.setData is calling by BiFunction - must return the index for JComboBox.setSelectedIndex
            BiFunction<String, Collection<String>, Integer> setItemFunction
    ) {
        comboBox.setName(name);

        Supplier<String> supplierFunction = () -> (String) comboBox.getSelectedItem();
        Consumer<String> consumerFunction;
        //if setItemFunction == null, DataCell.setData will set item to the index 0 in the combo box (like a reset)
        if (Objects.isNull(setItemFunction)) consumerFunction = (value) -> comboBox.setSelectedIndex(0);
        else {
            consumerFunction = (value) -> {
                //fetch items from JComboBox
                ComboBoxModel<String> comboBoxModel = comboBox.getModel();
                ArrayList<String> items = new ArrayList<>(comboBoxModel.getSize());
                for (int i = 0; i < comboBoxModel.getSize(); ++i) items.add(comboBoxModel.getElementAt(i));
                comboBox.setSelectedIndex(
                        Math.max(
                                //top bound
                                Math.min(setItemFunction.apply(value, items), comboBoxModel.getSize() - 1),
                                //bottom bound
                                0
                        )
                );
            };
        }
        component = comboBox;
        dataCell = new DataCell<>(supplierFunction, consumerFunction);
    }

    //if BiFunction returns value greater than size of the combo box items - the last one element will be selected
    public ComboList(String name, Collection<String> elements, BiFunction<String, Collection<String>, Integer> setItemFunction) {
        this(name, new JComboBox<>(new Vector<>(elements)), setItemFunction);
    }
}
