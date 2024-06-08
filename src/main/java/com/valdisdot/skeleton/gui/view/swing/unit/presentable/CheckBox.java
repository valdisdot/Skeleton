package com.valdisdot.skeleton.gui.view.swing.unit.presentable;

import com.valdisdot.skeleton.core.data.DataBean;
import com.valdisdot.skeleton.core.data.DataUnit;
import com.valdisdot.skeleton.gui.view.swing.unit.JSinglePresentableUnit;

import javax.swing.*;
import java.util.Objects;

public class CheckBox extends JSinglePresentableUnit implements DataUnit<String> {
    private final boolean preselected;
    private final String selected;
    private final String deselected;
    private final JCheckBox checkBox;

    public CheckBox(String id, String title, boolean preselected, String selected, String deselected) {
        JCheckBox checkBox = new JCheckBox(title == null ? "" : title);
        checkBox.setSelected(preselected);
        checkBox.setName(id);
        this.preselected = preselected;
        this.selected = Objects.requireNonNullElse(selected, "true");
        this.deselected = Objects.requireNonNullElse(deselected, "false");
        this.checkBox = setComponent(checkBox);
    }

    public CheckBox(String id, String title, String selected, String deselected) {
        this(id, title, false, selected, deselected);
    }

    public CheckBox(String id, String title) {
        this(id, title, false, null, null);
    }

    public CheckBox(String id) {
        this(id, null, false, null, null);
    }

    @Override
    public DataBean<String> getBean() {
        return new DataBean<>(getId(), checkBox.isSelected() ? selected : deselected);
    }

    @Override
    public void setBean(DataBean<String> data) {
        if (data.isPresent()) {
            String text = data.fetchFirst();
            if (text.equals(selected)) checkBox.setSelected(true);
            else if (text.equals(deselected)) {
                checkBox.setSelected(false);
            }
        }
        //else do nothing
    }

    @Override
    public void reset() {
        checkBox.setSelected(preselected);
    }

    @Override
    public void setPresentation(String presentation) {
        checkBox.setText(presentation);
    }
}
