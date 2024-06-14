package com.valdisdot.skeleton.gui.view.swing.unit.presentable;

import com.valdisdot.skeleton.core.data.DataBean;
import com.valdisdot.skeleton.core.data.DataUnit;
import com.valdisdot.skeleton.core.view.PresentablePair;
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
     * @see PresentablePair
     */
    public Spinner(String id, Collection<PresentablePair> view) {
        JSpinner spinner = new JSpinner();
        spinner.setName(id);
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setEditable(false);
        this.spinner = setComponent(spinner);
        replacePresentations(view);
    }

    /**{@inheritDoc}*/
    @Override
    public DataBean<String> getBean() {
        PresentablePair value = (PresentablePair) spinner.getValue();
        return new DataBean<>(getId(), value == null ? null : value.getId());
    }

    /**{@inheritDoc}*/
    @Override
    public void setBean(DataBean<String> data) {
        if (data.isPresent()) {
            String value = data.fetchFirst();
            for (PresentablePair pair : getCurrentView()) {
                if (pair.getId().equals(value)) {
                    spinner.setValue(pair);
                    return;
                }
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
        List<PresentablePair> view = getCurrentView();
        Collections.reverse(view);
        spinner.setModel(new SpinnerListModel(view));
        spinner.setValue(view.get(view.size() - 1));
    }
}
