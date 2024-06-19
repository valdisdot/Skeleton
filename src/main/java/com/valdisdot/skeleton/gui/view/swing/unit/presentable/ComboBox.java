package com.valdisdot.skeleton.gui.view.swing.unit.presentable;

import com.valdisdot.skeleton.core.DataUnit;
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
    private final JComboBox<Pair> comboBox;

    /**
     * Instantiates a new combo box.
     *
     * @param id   an id, not null
     * @param view a view, nullable
     * @see Pair
     */
    public ComboBox(String id, Collection<Pair> view) {
        JComboBox<Pair> comboBox = new JComboBox<>();
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
    public String getData() {
        return comboBox.getSelectedItem() == null ? null : comboBox.getSelectedItem().toString();
    }

    /**{@inheritDoc}*/
    @Override
    public void setData(String data) {
        int i = 0;
        for (Pair pair : getCurrentView()) {
            if (pair.getId().equals(data)) {
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
        List<Pair> view = getCurrentView();
        if (!view.isEmpty()) {
            for (Pair pair : view) {
                comboBox.addItem(pair);
            }
        }
    }
}
