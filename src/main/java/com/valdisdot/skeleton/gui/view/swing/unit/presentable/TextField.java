package com.valdisdot.skeleton.gui.view.swing.unit.presentable;

import com.valdisdot.skeleton.core.data.DataBean;
import com.valdisdot.skeleton.core.data.DataUnit;
import com.valdisdot.skeleton.gui.view.swing.unit.JSinglePresentableUnit;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class TextField extends JSinglePresentableUnit implements DataUnit<String> {
    private final JTextField textField;
    private String defaultText;

    public TextField(String id, String defaultText) {
        this.defaultText = Objects.requireNonNullElse(defaultText, "");
        JTextField textField = new JTextField(this.defaultText);
        textField.setName(id);
        textField.setPreferredSize(new Dimension(50, 20));
        this.textField = setComponent(textField);
    }

    public TextField(String id) {
        this(id, null);
    }

    @Override
    public DataBean<String> getBean() {
        return new DataBean<>(getId(), textField.getText());
    }

    @Override
    public void setBean(DataBean<String> data) {
        if (data.isPresent()) textField.setText(data.fetchFirst());
    }

    @Override
    public void reset() {
        textField.setText(defaultText);
    }

    @Override
    public void setPresentation(String presentation) {
        this.defaultText = presentation;
        textField.setText(presentation);
    }
}
