package com.valdisdot.skeleton.OLD.ui.gui.element;

import com.valdisdot.skeleton.OLD.data.DataCell;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.LinkedHashMap;
import java.util.function.Supplier;

//implementation for a group (from 1 to n) of JRadioButton. Title of JRadioButton and return value is different and must be passed through the constructor's args as Map 'radioButtonValueMap'
public class RadioButtons extends JElement<String> {
    private Supplier<String> valueSupplier;

    public RadioButtons(String name, LinkedHashMap<JRadioButton, String> radioButtonValueMap) {
        //container for a group of buttons
        JPanel container = new JPanel(new MigLayout("wrap,insets 0"));
        container.setName(name);
        //return "" if none is selected
        valueSupplier = () -> "";
        //add radiobutton to the container and to the group
        ButtonGroup buttonGroup = new ButtonGroup();
        radioButtonValueMap.keySet().forEach(jButton -> {
            container.add(jButton);
            buttonGroup.add(jButton);
            //set as value supplier if selected
            jButton.addActionListener(l -> {
                if (jButton.isSelected()) valueSupplier = () -> radioButtonValueMap.get(jButton);
            });
        });

        component = container;
        dataCell = new DataCell<>(
                //get data from current supplier or ""
                () -> valueSupplier.get(),
                val -> {
                    buttonGroup.clearSelection();
                    //set to supplier of ""
                    valueSupplier = () -> "";
                });
    }
}
