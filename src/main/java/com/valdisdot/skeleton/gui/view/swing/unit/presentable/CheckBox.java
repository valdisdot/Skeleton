package com.valdisdot.skeleton.gui.view.swing.unit.presentable;

import com.valdisdot.skeleton.core.data.DataBean;
import com.valdisdot.skeleton.core.data.DataUnit;
import com.valdisdot.skeleton.gui.view.swing.unit.JSinglePresentableUnit;

import javax.swing.*;
import java.util.Objects;

/**
 * The implementation of a checkbox element.
 * @since 1.0
 * @author Vladyslav Tymchenko
 */
public class CheckBox extends JSinglePresentableUnit implements DataUnit<String> {
    private final boolean preselected;
    private final String selected;
    private final String deselected;
    private final JCheckBox checkBox;

    /**
     * Instantiates a new checkbox.
     *
     * @param id          an id, not null
     * @param title       a title, nullable
     * @param preselected flag indicates an initial state of the checkbox
     * @param selected    a selected value, nullable
     * @param deselected  a deselected value, nullable
     */
    public CheckBox(String id, String title, boolean preselected, String selected, String deselected) {
        JCheckBox checkBox = new JCheckBox(title == null ? "" : title);
        checkBox.setSelected(preselected);
        checkBox.setName(id);
        this.preselected = preselected;
        this.selected = Objects.requireNonNullElse(selected, "true");
        this.deselected = Objects.requireNonNullElse(deselected, "false");
        this.checkBox = setComponent(checkBox);
    }

    /**
     * Instantiates a new checkbox.
     *
     * @param id         an id, not null
     * @param title      a title, nullable
     * @param selected   a selected value, nullable
     * @param deselected a deselected value, nullable
     */
    public CheckBox(String id, String title, String selected, String deselected) {
        this(id, title, false, selected, deselected);
    }

    /**
     * Instantiates a new checkbox.
     *
     * @param id    an id, not null
     * @param title a title
     */
    public CheckBox(String id, String title) {
        this(id, title, false, null, null);
    }

    /**
     * Instantiates a new checkbox.
     *
     * @param id an id
     */
    public CheckBox(String id) {
        this(id, null, false, null, null);
    }

    /**{@inheritDoc}*/
    @Override
    public DataBean<String> getBean() {
        return new DataBean<>(getId(), checkBox.isSelected() ? selected : deselected);
    }

    /**{@inheritDoc}*/
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

    /**{@inheritDoc}*/
    @Override
    public void reset() {
        checkBox.setSelected(preselected);
    }

    /**{@inheritDoc}*/
    @Override
    public void setPresentation(String presentation) {
        if(presentation != null) checkBox.setText(presentation);
    }
}
