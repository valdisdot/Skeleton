package com.valdisdot.skeleton.gui.view.swing.unit.presentable;

import com.valdisdot.skeleton.core.DataUnit;
import com.valdisdot.skeleton.gui.view.swing.unit.JSinglePresentableUnit;

import javax.swing.*;

/**
 * The implementation of a label element.
 * @since 1.0
 * @author Vladyslav Tymchenko
 */
public class Label extends JSinglePresentableUnit implements DataUnit<String> {
    private final JLabel label;
    private String title;

    /**
     * Instantiates a new label.
     *
     * @param id    an id, not null
     * @param title a title, nullable
     */
    public Label(String id, String title) {
        this.title = title == null ? "" : title;
        JLabel label = new JLabel(this.title);
        label.setName(id);
        this.label = setComponent(label);
    }

    /**
     * Instantiates a new label.
     *
     * @param id an id, not null
     */
    public Label(String id) {
        this(id, null);
    }

    /**{@inheritDoc}*/
    @Override
    public String getData() {
        return label.getText();
    }

    /**{@inheritDoc}*/
    @Override
    public void setData(String data) {
        label.setText(data == null ? "" : data);
    }

    /**{@inheritDoc}*/
    @Override
    public void resetData() {
        label.setText(title);
    }

    /**{@inheritDoc}*/
    @Override
    public void setPresentation(String presentation) {
        if(presentation != null) {
            title = presentation;
            label.setText(title);
        }
    }
}
