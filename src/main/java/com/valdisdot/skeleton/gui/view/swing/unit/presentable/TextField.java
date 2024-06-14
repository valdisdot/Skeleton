package com.valdisdot.skeleton.gui.view.swing.unit.presentable;

import com.valdisdot.skeleton.core.data.DataBean;
import com.valdisdot.skeleton.core.data.DataUnit;
import com.valdisdot.skeleton.gui.view.swing.unit.JSinglePresentableUnit;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * The implementation of a text field element.
 * @since 1.0
 * @author Vladyslav Tymchenko
 */
public class TextField extends JSinglePresentableUnit implements DataUnit<String> {
    private final JTextField textField;
    private String defaultText;

    /**
     * Instantiates a new text field.
     *
     * @param id          an id
     * @param defaultText default text, nullable
     */
    public TextField(String id, String defaultText) {
        this.defaultText = Objects.requireNonNullElse(defaultText, "");
        JTextField textField = new JTextField(this.defaultText);
        textField.setName(id);
        textField.setPreferredSize(new Dimension(50, 20));
        this.textField = setComponent(textField);
    }

    /**
     * Instantiates a new text field.
     *
     * @param id an id, not null
     */
    public TextField(String id) {
        this(id, null);
    }

    /**{@inheritDoc}*/
    @Override
    public DataBean<String> getBean() {
        return new DataBean<>(getId(), textField.getText());
    }

    /**{@inheritDoc}*/
    @Override
    public void setBean(DataBean<String> data) {
        if (data.isPresent()) textField.setText(data.fetchFirst());
    }

    /**{@inheritDoc}*/
    @Override
    public void reset() {
        textField.setText(defaultText);
    }

    /**{@inheritDoc}*/
    @Override
    public void setPresentation(String presentation) {
        if(presentation != null) {
            this.defaultText = presentation;
            textField.setText(presentation);
        }
    }
}
