package com.valdisdot.skeleton.gui.view.swing.unit.presentable;

import com.valdisdot.skeleton.core.data.DataBean;
import com.valdisdot.skeleton.core.data.DataUnit;
import com.valdisdot.skeleton.core.view.PresentablePair;
import com.valdisdot.skeleton.gui.view.swing.unit.JMultiPresentableUnit;

import javax.swing.*;
import java.util.Collection;
import java.util.List;

public class ComboBox extends JMultiPresentableUnit implements DataUnit<String> {
    private final JComboBox<PresentablePair> comboBox;

    public ComboBox(String id, Collection<PresentablePair> view) {
        JComboBox<PresentablePair> comboBox = new JComboBox<>();
        comboBox.setName(id);
        this.comboBox = setComponent(comboBox);
        if (view != null && !view.isEmpty()) replacePresentations(view);
    }

    public ComboBox(String id) {
        this(id, null);
    }

    @Override
    public DataBean<String> getBean() {
        return new DataBean<>(getId(), comboBox.getSelectedItem() == null ? null : comboBox.getSelectedItem().toString());
    }

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

    @Override
    public void reset() {
        comboBox.setSelectedIndex(0);
    }

    @Override
    protected void updateView() {
        List<PresentablePair> view = getCurrentView();
        if (!view.isEmpty()) {
            comboBox.removeAllItems();
            for (PresentablePair pair : view) {
                comboBox.addItem(pair);
            }
        }
    }
}
