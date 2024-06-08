package com.valdisdot.skeleton.gui.view.swing.unit.presentable;

import com.valdisdot.skeleton.core.data.DataBean;
import com.valdisdot.skeleton.core.data.DataUnit;
import com.valdisdot.skeleton.gui.view.swing.unit.JSinglePresentableUnit;

import javax.swing.*;

public class Label extends JSinglePresentableUnit implements DataUnit<String> {
    private final JLabel label;
    private String title;

    public Label(String id, String title) {
        this.title = title == null ? "" : title;
        JLabel label = new JLabel(this.title);
        label.setName(id);
        this.label = setComponent(label);
    }

    public Label(String id) {
        this(id, null);
    }

    @Override
    public DataBean<String> getBean() {
        return new DataBean<>(getId(), label.getText());
    }

    @Override
    public void setBean(DataBean<String> data) {
        if (data.isPresent()) label.setText(data.fetchFirst());
    }

    @Override
    public void reset() {
        label.setText(title);
    }

    @Override
    public void setPresentation(String presentation) {
        title = presentation;
        label.setText(title);
    }
}
