package com.valdisdot.skeleton.ui.gui.element;

import com.valdisdot.skeleton.data.DataCell;

import javax.swing.*;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Objects;

public class Slider<D> extends JElement<D>{
    public Slider(String name, LinkedHashMap<String, D> labelValueMap){
        if(Objects.requireNonNull(labelValueMap).isEmpty()) throw new ArrayIndexOutOfBoundsException("LabelValue map is empty");
        Hashtable<Integer, JLabel> labels = new Hashtable<>();
        int index = 0;
        for(String label : labelValueMap.keySet()) labels.put(index++, new JLabel(label));
        JSlider slider = new JSlider(0, labelValueMap.size() - 1);
        slider.setName(name);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        slider.setLabelTable(labels);

        component = slider;
        dataCell = new DataCell<>(
                () -> labelValueMap.get(labels.get(slider.getValue()).getText()),
                val -> slider.setValue(0)
        );
    }
}
