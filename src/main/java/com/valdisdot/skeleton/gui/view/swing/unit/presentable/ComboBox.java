package com.valdisdot.skeleton.gui.view.swing.unit.presentable;

import com.valdisdot.skeleton.core.data.DataBean;
import com.valdisdot.skeleton.core.data.DataUnit;
import com.valdisdot.skeleton.core.view.PresentablePair;
import com.valdisdot.skeleton.gui.view.swing.unit.JMultiPresentableUnit;

import javax.swing.*;
import java.util.Collection;
import java.util.List;

/**
 * The implementation of a combo box element.
 * @since 1.0
 * @author Vladyslav Tymchenko
 */
public class ComboBox extends JMultiPresentableUnit implements DataUnit<String> {
    private final JComboBox<PresentablePair> comboBox;

    /**
     * Instantiates a new combo box.
     *
     * @param id   an id, not null
     * @param view a view, nullable
     * @see PresentablePair
     */
    public ComboBox(String id, Collection<PresentablePair> view) {
        JComboBox<PresentablePair> comboBox = new JComboBox<>();
        comboBox.setName(id);
        this.comboBox = setComponent(comboBox);
        replacePresentations(view);
    }

    /**
     * Instantiates a new combo box.
     *
     * @param id an id, not null
     */
    public ComboBox(String id) {
        this(id, null);
    }

    /**{@inheritDoc}*/
    @Override
    public DataBean<String> getBean() {
        return new DataBean<>(getId(), comboBox.getSelectedItem() == null ? null : comboBox.getSelectedItem().toString());
    }

    /**{@inheritDoc}*/
    @Override
    public void setBean(DataBean<String> data) {
        int i = 0;
        for (PresentablePair pair : getCurrentView()) {
            if (pair.getId().equals(data.getId())) {
                comboBox.setSelectedIndex(i);
                return;
            }
        }
    }

    /**{@inheritDoc}*/
    @Override
    public void reset() {
        comboBox.setSelectedIndex(0);
    }

    /**{@inheritDoc}*/
    @Override
    protected void updateView() {
        comboBox.removeAllItems();
        List<PresentablePair> view = getCurrentView();
        if (!view.isEmpty()) {
            for (PresentablePair pair : view) {
                comboBox.addItem(pair);
            }
        }
    }
}
