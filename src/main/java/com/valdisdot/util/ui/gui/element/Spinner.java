package com.valdisdot.util.ui.gui.element;

import com.valdisdot.util.data.DataCell;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class Spinner extends JElement<String> {
    public Spinner(String name, JSpinner visibleSpinner, SpinnerModel spinnerModel){
        visibleSpinner.setName(Objects.requireNonNull(name));
        visibleSpinner.setModel(spinnerModel);
        ((JSpinner.DefaultEditor) visibleSpinner.getEditor()).getTextField().setEditable(false);

        Object first = spinnerModel.getValue();
        component = visibleSpinner;
        dataCell = new DataCell<>(
                ((JSpinner.DefaultEditor) visibleSpinner.getEditor()).getTextField()::getText,
                (v) -> visibleSpinner.setValue(first) //simple set to 0 index
        );
    }

    public Spinner(String name, SpinnerModel spinnerModel) {
        this(name, new JSpinner(), spinnerModel);
    }

    //ready to use SpinnerModels
    public static SpinnerModel asNumberRange(int min, int max, int step){
        return new SpinnerNumberModel(min, min, Math.max(max, min), step);
    }

    public static SpinnerModel asList(Collection<String> values){
        return new SpinnerListModel(new ArrayList<>(values));
    }
}
