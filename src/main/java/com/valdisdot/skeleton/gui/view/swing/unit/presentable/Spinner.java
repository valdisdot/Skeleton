package com.valdisdot.skeleton.gui.view.swing.unit.presentable;

import com.valdisdot.skeleton.core.DataUnit;
import com.valdisdot.skeleton.gui.view.swing.unit.JMultiPresentableUnit;

import javax.swing.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * The implementation of a spinner element.
 * @since 1.0
 * @author Vladyslav Tymchenko
 */
public class Spinner extends JMultiPresentableUnit implements DataUnit<String> {
    private final JSpinner spinner;

    /**
     * Instantiates a new spinner.
     *
     * @param id   an id, not null
     * @param view a view, nullable
     * @see Pair
     */
    public Spinner(String id, Collection<Pair> view) {
        JSpinner spinner = new JSpinner();
        spinner.setName(id);
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setEditable(false);
        this.spinner = setComponent(spinner);
        replacePresentations(view);
    }

    /**{@inheritDoc}*/
    @Override
    public String getData() {
        Pair value = (Pair) spinner.getValue();
        return value == null ? null : value.getId();
    }

    /**{@inheritDoc}*/
    @Override
    public void setData(String data) {
        for (Pair pair : getCurrentView()) {
            if (pair.getId().equals(data)) {
                spinner.setValue(pair);
                return;
            }
        }
    }

    /**{@inheritDoc}*/
    @Override
    public void reset() {
        if (!getCurrentView().isEmpty()) spinner.setValue(getCurrentView().get(0));
    }

    /**{@inheritDoc}*/
    @Override
    protected void updateView() {
        List<Pair> view = getCurrentView();
        Collections.reverse(view);
        spinner.setModel(new SpinnerListModel(view));
        spinner.setValue(view.get(view.size() - 1));
    }
}
